package xyz.spacexplore.canal;

import java.io.File;
import java.io.FileFilter;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component
public class InitData {

    public static List<Class<?>> clazes;

    private static final String DTO_PACKAGE = "xyz.spacexplore.domain.dto";

    private ClassLoader classLoader = getClass().getClassLoader();

    public static final Map<String, Class<?>> tableClassMap = new HashMap<>();

    private static final String DTO_SUFFIX = "DTO";

    /**
     * @throws ClassNotFoundException
     * @doc:思路是这样的,首先加载所有的表名称,然后扫描指定包下的class文件 ,根据表名和class.simplename进行匹配,如果匹配成功,那么就进行组装map(table,
     *                                         class)
     **/
    @PostConstruct
    public void initData() throws ClassNotFoundException {
        initClass();
    }

    /**
     * 
     * @doc:获取所有的dto文件.
     * @author Andreby
     * @throws ClassNotFoundException
     * @date 2018年9月20日 下午3:03:10
     */
    public void initClass() throws ClassNotFoundException {
        clazes = new ArrayList<Class<?>>();
        File[] resources = getResources();// 获取到包下所有的class文件

        for (int i = 0; i < resources.length; i++) {
            // 载入包下的类
            Class<?> clazz = classLoader.loadClass(DTO_PACKAGE + "." + resources[i].getName().replace(".class", ""));
            // 判断满足的话加入到策略列表
            tableClassMap.put(clazz.getSimpleName().replace(DTO_SUFFIX, "").toLowerCase(), clazz);// 获得小写类名:class
        }

    }

    private File[] getResources() {
        try {
            File file = new File(classLoader.getResource(DTO_PACKAGE.replace(".", "/")).toURI());
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


}
