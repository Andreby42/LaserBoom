package xyz.spacexplore.canal;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.otter.canal.protocol.CanalEntry.Column;

/**
 * 
 * @ClassName: CanalUtil
 * @Description: Canal相关的工具类
 * @author Andreby
 * @date 2017年2月24日 下午6:30:27
 */
public class CanalUtil {
	private static final String SEPARATOR_HYPHEN = "-";
	private static final String YUAN = "";
	private static final String M = "m";

	/**
	 * 
	 * @doc:将字段list更改为map
	 * @author Andreby
	 * @date 2017年4月18日 下午6:52:42
	 * @param columns
	 *            columns列表
	 * @return
	 */
	public static Map<String, String> columnToMap(List<Column> columns) {
		Map<String, String> map = new HashMap<String, String>();
		for (Column column : columns) {
			String column_value = column.getValue();
			String column_name = getColumnName(column.getName());
			map.put(column_name, column_value);
		}
		return map;
	}

	/**
	 * 
	 * @doc:解析数据库字段为驼峰式实体类字段
	 * @author Andreby
	 * @date 2017年4月18日 下午6:52:22
	 * @param name
	 *            推送过来的字段名称
	 * @return
	 */
	public static String getColumnName(String name) {
		// 先转换为实体类驼峰式
		// PRODUCT_ID
		char[] charArray = name.toCharArray();
		List<Integer> indexList = new ArrayList<>();
		int indexs = 0;
		for (char c : charArray) {
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
//	public static String parseColumnMapToDTO(Map<String, String> map, String tableName) throws Exception {
//		Object obj = null;
//		AgentProductDTO ap = null;
//		Class<?> claz = TableEnum.getClaz(new MD5().getMD5ofStr(tableName));
//		if (claz != null) {
//			obj = claz.newInstance();
//		}
//		for (java.util.Map.Entry<String, String> entry : map.entrySet()) {
//
//			String key = entry.getKey();
//			String value = entry.getValue();
//
//			obj = transferValues(key, value, obj, tableName);
//
//		}
//		if (tableName.equals("agent_product_table")) {
//			ap = (AgentProductDTO) obj;
//			if (ap.getBusinessCategoryName().equals("话费")) {
//				ap.setAgentProductId(ap.getProvinceName() + SEPARATOR_HYPHEN + ap.getCarrieroperatorName()
//						+ SEPARATOR_HYPHEN + ap.getParValue() + YUAN + SEPARATOR_HYPHEN + ap.getBusinessCategoryName()
//						+ SEPARATOR_HYPHEN + ap.getProvinceName() + SEPARATOR_HYPHEN + ap.getMerchantId());
//			} else if (ap.getBusinessCategoryName().equals("流量")) {
//				ap.setAgentProductId(ap.getProvinceName() + SEPARATOR_HYPHEN + ap.getCarrieroperatorName()
//						+ SEPARATOR_HYPHEN + ap.getParValue() + M + SEPARATOR_HYPHEN + ap.getBusinessCategoryName()
//						+ SEPARATOR_HYPHEN + ap.getProvinceName() + SEPARATOR_HYPHEN + ap.getMerchantId());
//			}
//
//			return JSON.toJSONString(ap);
//		}
//		return JSON.toJSONString(obj);
//
//	}

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

	public static void main(String[] args) {
	}

	public static String getPrimaryKeyName(List<Column> columns) {
		for (Column column : columns) {
			if (column.getIsKey()) {
				return getColumnName(column.getName());
			}
		}
		return null;
	}

    public static String parseColumnMapToDTO(Map<String, String> columnToMap, String tableName) {
        // TODO Auto-generated method stub
        return null;
    }
}
