package xyz.spacexplore.canal;

import org.springframework.context.ApplicationEvent;

import com.alibaba.otter.canal.protocol.Message;

/**
 * 
 * @ClassName: CanalMessageEvent
 * @Description: CanalMessage事件
 * @author Andreby
 * @date 2017年2月23日 下午5:49:30
 */
public class CanalMessageEvent extends ApplicationEvent {
	private static final long serialVersionUID = 1L;
	private Message message;

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public CanalMessageEvent(Object source) {
		super(source);
	}

	public CanalMessageEvent(Object source, Message message) {
		super(source);
		this.message = message;
	}

}
