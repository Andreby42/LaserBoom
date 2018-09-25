package xyz.spacexplore.canal;

import java.io.File;
import java.io.FileFilter;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import xyz.spacexplore.domain.CustomDao;
import xyz.spacexplore.domain.dao.EventDao;
import xyz.spacexplore.domain.dao.LeagueDao;
import xyz.spacexplore.support.ApplicationContextUtil;

@Component
public class InitData implements InitializingBean {
    @Resource
    private EventDao eventDao;
    @Resource
    private LeagueDao leagueDao;
    @Resource
    private ApplicationContextUtil applicationContextUtil;

    private static final String DTO_PACKAGE = "xyz.spacexplore.domain.dto";
    private static final String DAO_PACKAGE = "xyz.spacexplore.domain.dao";


    private ClassLoader classLoader = getClass().getClassLoader();

    public static final Map<String, Class<?>> tableClassMap = new HashMap<>();

    public static final Map<String, CustomDao> tableDaoMap = new HashMap<>();

    private static final String DTO_SUFFIX = "DTO";
    private static final String DAO_SUFFIX = "Dao";


    /**
     * @throws ClassNotFoundException
     * @doc:思路是这样的,首先加载所有的表名称,然后扫描指定包下的class文件 ,根据表名和class.simplename进行匹配,如果匹配成功,那么就进行组装map(table,
     *                                         class)
     **/
    public void initData() throws ClassNotFoundException {
        initClass();
        initTableAndDaoClazes();
    }

    private void initTableAndDaoClazes() throws ClassNotFoundException {
        List<String> tables = eventDao.selectTables();
        File[] resources = getResources(DAO_PACKAGE);
        if (tables.size() < resources.length) {
            throw new RuntimeException("项目配置错误");
        }
        for (int i = 0; i < resources.length; i++) {
            // 载入包下的类
            Class<?> clazz = classLoader.loadClass(DAO_PACKAGE + "." + resources[i].getName().replace(".class", ""));
            String tableName = clazz.getSimpleName().replace(DAO_SUFFIX, "").toLowerCase();
            // 判断满足 加入到策略列表

            long count = tables.stream().filter(str -> str.equalsIgnoreCase(tableName)).count();
            if (count > 0) {
                // 拿到对象 放map 从spring中
                CustomDao bean = (CustomDao) applicationContextUtil.getBean(tableName + DAO_SUFFIX);
                tableDaoMap.put(tableName, bean);
            }
        }
    }

    /**
     * 
     * @doc:获取所有的dto文件.
     * @author Andreby
     * @throws ClassNotFoundException
     * @date 2018年9月20日 下午3:03:10
     */
    public void initClass() throws ClassNotFoundException {
        File[] resources = getResources(DTO_PACKAGE);// 获取到包下所有的class文件

        for (int i = 0; i < resources.length; i++) {
            // 载入包下的类
            Class<?> clazz = classLoader.loadClass(DTO_PACKAGE + "." + resources[i].getName().replace(".class", ""));
            // 判断满足的话加入到策略列表
            tableClassMap.put(clazz.getSimpleName().replace(DTO_SUFFIX, "").toLowerCase(), clazz);// 获得小写类名:class
        }

    }

    private File[] getResources(String packagePath) {
        try {
            File file = new File(classLoader.getResource(packagePath.replace(".", "/")).toURI());
            return file.listFiles(new FileFilter() {
                public boolean accept(File pathname) {
                    if (pathname.getName().endsWith(".class")) {// 我们只扫描class文件
                        return true;
                    }
                    return false;
                }
            });
        } catch (URISyntaxException e) {
            throw new RuntimeException("未找到策略资源");
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initData();
    }


}
