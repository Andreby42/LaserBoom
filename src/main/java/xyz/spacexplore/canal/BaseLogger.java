/**  
 * @Title: BaseLog.java
 * @Package com.newnet.b2c.utils.log
 * @Description: 提供logger对象的父类
 * @author wangbingya
 * @date 2016年10月13日
 */
package xyz.spacexplore.canal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**  
 * @ClassName: BaseLog  
 * @Description: 继承它获得logger对象 不用再每个类中加那一行
 * @author wangbingya
 * @date 2016年10月13日 下午6:24:08    
*/
public class BaseLogger {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
}
