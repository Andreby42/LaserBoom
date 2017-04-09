package xyz.spacexplore.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

/**
 * 
 * @author andy canal发送消息事件发布者
 */
@Component(value="canalMessagePublisher")
public class CanalMessagePublisher implements ApplicationEventPublisherAware {

	private ApplicationEventPublisher publisher;

	@Autowired
	public CanalMessagePublisher(ApplicationEventPublisher publisher) {
		super();
		this.publisher = publisher;
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		publisher = applicationEventPublisher;
	}

	public void publisher(ApplicationEvent event) {
		publisher.publishEvent(event);
	}

}
