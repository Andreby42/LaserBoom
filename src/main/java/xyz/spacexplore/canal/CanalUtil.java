package xyz.spacexplore.canal;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.otter.canal.protocol.CanalEntry.Column;
import com.sun.org.apache.xml.internal.security.Init;

import xyz.spacexplore.domain.dto.EventDTO;
import xyz.spacexplore.domain.dto.LeagueDTO;
import xyz.spacexplore.support.DateUtil;
import xyz.spacexplore.support.MD5;

/**
 * 
 * @ClassName: CanalUtil
 * @Description: Canal相关的工具类
 * @author Andreby
 * @date 2017年2月24日 下午6:30:27
 */
public class CanalUtil {

    /**
     * 
     * @doc:将字段list更改为map
     * @author Andreby
     * @date 2017年4月18日 下午6:52:42
     * @param columns columns列表
     * @return
     */
    public static Map<String, String> columnToMap(List<Column> columns) {
        Map<String, String> map = new HashMap<String, String>();
        for (Column column : columns) {
            String column_value = column.getValue();
            String column_name = getColumnName(column.getName(), false);
            map.put(column_name, column_value);
        }
        return map;
    }

    /**
     * 
     * @doc:将带下划线的表名转为驼峰式 解析数据库字段为驼峰式实体类字段 只用在没有指定的实体类上,
     * @author Andreby
     * @date 2018年9月21日 上午10:59:42
     * @param name 表名
     * @param firstUpper 是否首字母大写
     * @return
     */
    @Deprecated
    public static String getColumnName(String name, boolean firstUpper) {
        // 先转换为实体类驼峰式
        char[] charArray = name.toCharArray();
        List<Integer> indexList = new ArrayList<>();
        int indexs = 0;
        for (char c : charArray) {
            if (firstUpper) {
                if (indexs == 0) {
                    indexList.add(indexs);
                }
            }
            if (c == '_') {
                indexList.add(indexs + 1);
            }
            indexs++;
        }
        String valueNew = name.toLowerCase();
        StringBuffer sb = new StringBuffer(valueNew);
        for (Integer index : indexList) {
            sb.replace(index, index + 1, String.valueOf(name.charAt(index)).toUpperCase());
        }
        return sb.toString().replace("_", "");
    }

    /**
     * 
     * @doc: 将columnMap解析为实体类对象
     * @author Andreby
     * @date 2017年4月18日 下午6:51:37
     * @param map
     * @param tableName
     * @return
     * @throws Exception
     */
    public static String parseColumnMapToDTO(Map<String, String> map, String tableName) throws Exception {
        Object obj = null;
        Class<?> claz = InitData.tableClassMap.get(tableName.replace("_", ""));
        if (claz != null) {
            obj = claz.newInstance();
        }
        for (java.util.Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            obj = transferValues(key, value, obj, tableName);
        }
        // TODO:对单另的需要进行主键设置的进行特殊处理
        /*
         * if (tableName.equals("league")) {
         * 
         * }
         */
        return JSON.toJSONString(obj);

    }

    /**
     * 
     * @doc:如果数据库表字段有新的类型再添加 进行反射设置字段类型对应的值
     * @author Andreby
     * @date 2017年4月18日 下午6:51:04
     * @param key
     * @param value
     * @param obj
     * @param tableName
     * @return
     * @throws Exception
     */
    private static Object transferValues(String key, String value, Object obj, String tableName) throws Exception {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getName().equals(key)) {
                if (field.getGenericType().toString().equals("class java.lang.String")) {
                    if (StringUtils.isNotEmpty(value)) {
                        field.set(obj, value);
                    }
                    break;
                }

                if (field.getGenericType().toString().equals("class java.lang.Integer")) {
                    if (StringUtils.isNotEmpty(value)) {
                        field.set(obj, Integer.valueOf(value));
                    }
                    break;
                }

                if (field.getGenericType().toString().equals("class java.lang.Long")) {
                    if (StringUtils.isNotEmpty(value)) {
                        field.set(obj, Long.valueOf(value));
                    }
                    break;
                }

                if (field.getGenericType().toString().equals("class java.lang.Double")) {
                    if (StringUtils.isNotEmpty(value)) {
                        field.set(obj, Double.valueOf(value));
                    }
                    break;
                }

                if (field.getGenericType().toString().equals("class java.lang.Float")) {
                    if (StringUtils.isNotEmpty(value)) {
                        field.set(obj, Double.valueOf(value));
                    }
                    break;
                }

                if (field.getGenericType().toString().equals("class java.math.BigDecimal")) {
                    // TODO:小数点
                    if (StringUtils.isNotEmpty(value)) {
                        field.set(obj, new BigDecimal(value));
                    }
                    break;
                }

                if (field.getGenericType().toString().equals("class java.util.Date")) {
                    if (StringUtils.isNotEmpty(value)) {
                        field.set(obj, DateUtil.fomatDate2(value));
                    }
                    break;
                }

                if (field.getGenericType().toString().equals("class java.lang.Boolean")) {
                    if (StringUtils.isNotEmpty(value)) {
                        field.set(obj, BooleanUtils.toBoolean(value));
                    }
                    break;
                }
            }
        }
        return obj;
    }


    public static String getPrimaryKeyName(List<Column> columns) {
        for (Column column : columns) {
            if (column.getIsKey()) {
                return getColumnName(column.getName(), false);
            }
        }
        return null;
    }

    /**
     * 
     * @doc:思路是这样的,首先加载所有的表名称,然后扫描指定包下的class文件 ,根据表名和class.simplename进行匹配,如果匹配成功,那么就进行组装map(table,
     *                                         class)
     * @author Andreby
     * @date 2018年9月20日 下午2:36:39
     * @param args
     */
    public static void main(String[] args) {
        String columnName = getColumnName("event_sss", false);
        System.out.println(columnName);
        String className = EventDTO.class.getName();
        System.out.println(className);
        String simpleName = EventDTO.class.getSimpleName();
        System.out.println(simpleName);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(T t, Class<?> claz) throws InstantiationException, IllegalAccessException {
        return (T) claz.newInstance();
    }
}
