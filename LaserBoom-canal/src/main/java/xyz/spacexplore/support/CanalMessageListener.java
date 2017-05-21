package xyz.spacexplore.support;

import org.springframework.transaction.event.TransactionalEventListener;

public class CanalMessageListener {
	/**
	 * 
	 * @param event
	 * canal事件接受者接收条件为flag为true
	 */
	@TransactionalEventListener(value = CanalMessageEvent.class, condition = "#canalMessageEvent.flag")
	public void onApplicationEvent(CanalMessageEvent event) {
		//用枚举去做是不行的 枚举就是不可变常量池 缓存策略
		// mq 进行  动态解析xml进行解析对应的类名及db或者collectionName 将配置文件的hash值 大小   或者 某个字段如version放置在redis或本地缓存或者内存中  如果
		//如果 监听到 变化那么 将 static (redis中的原有读取数据进行重新存储 )判断的时候进行 拿全局就行
	}
}
