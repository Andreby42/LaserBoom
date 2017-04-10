package xyz.spacexplore.rabbit.conf;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SerializerMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.util.StringUtils;

import com.rabbitmq.client.Channel;

@Configuration
@PropertySource("classpath:mq.properties")
public class MqConfiguration {
	@Value("${mq.addrs}")
	private String addrs;
	@Value("${mq.username}")
	private String username;
	@Value("${mq.password}")
	private String password;
	@Value("${mq.vhost}")
	private String vhost;
	@Value("${mq.exchange}")
	private String exchange;
	@Value("${mq.routingkey}")
	private String routingkey;

	@Value("mq.customQueue")
	private String customQueue;

	@Bean("connectionFactory")
	public CachingConnectionFactory connectionFactory() {
		if (!StringUtils.isEmpty(addrs.trim()) || !StringUtils.isEmpty(username.trim())
				|| !StringUtils.isEmpty(password.trim()) || !StringUtils.isEmpty(addrs.trim())) {
			CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
			connectionFactory.setAddresses(addrs);
			connectionFactory.setUsername(username);
			connectionFactory.setPassword(password);
			connectionFactory.setVirtualHost(vhost);
			connectionFactory.setPublisherConfirms(true); // 必须要设置
			return connectionFactory;
		}
		return null;
	}

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	// 这里的bean的阈必须是prototype
	public RabbitTemplate rabbitTemplate(
			@Qualifier(value = "connectionFactory") CachingConnectionFactory connectionFactory,
			@Qualifier(value = "jsonMessageConverter") MessageConverter jsonMessageConverter) {
		RabbitTemplate template = new RabbitTemplate(connectionFactory);
		template.setMessageConverter(jsonMessageConverter);
		template.setEncoding("UTF-8");
		return template;
	}

	/**
	 * 针对消费者配置 1. 设置交换机类型 2. 将队列绑定到交换机
	 * 
	 * FanoutExchange: 将消息分发到所有的绑定队列，无 routingkey 的概念
	 * HeadersExchange:通过添加属性key-value 匹配 DirectExchange: 按照 routingkey 分发到指定队列
	 * TopicExchange: 多关键字匹配
	 */
	@Bean(value = "customeExchange")
	public DirectExchange defaultExchange() {
		return new DirectExchange(exchange.trim());
	}

	@Bean(value = "customQueue")
	public Queue queue() {
		return new Queue(customQueue, true); // 第二个参数为队列持久
	}

	@Bean // 绑定
	public Binding binding() {
		return BindingBuilder.bind(queue()).to(defaultExchange()).with(routingkey);
	}

	@Bean
	public SimpleMessageListenerContainer messageContainer() {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory());
		container.setQueues(queue());
		container.setExposeListenerChannel(true);
		container.setMaxConcurrentConsumers(1);
		container.setConcurrentConsumers(1);
		container.setAcknowledgeMode(AcknowledgeMode.MANUAL); // 设置确认模式手工确认
		container.setMessageListener(new ChannelAwareMessageListener() {
			@Override
			public void onMessage(Message message, Channel channel) throws Exception {
				byte[] body = message.getBody();
				System.out.println("receive msg :" + new String(body));
				channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); // 确认消息成功消费
			}
		});
		return container;
	}

	@Bean(value = "jsonMessageConverter")
	public MessageConverter jsonMessageConverter() {
		return new JsonMessageConverter();
	}

	@Bean(value = "serializerMessageConverter")
	public MessageConverter serializerMessageConverter() {
		return new SerializerMessageConverter();
	}
}