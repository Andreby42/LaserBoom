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
		// mq 进行
	}

}
