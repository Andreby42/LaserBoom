package xyz.spacexplore.rabbit.producer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
@Service
@RabbitListener(queues = "customqueue")
public class MessageReceiver {
	@RabbitHandler
	public void recevier() {
		// do something
	}

}
