package xyz.spacexplore.rabbit.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.StringUtils;

import com.rabbitmq.client.ConnectionFactory;

@Configuration
@PropertySource("mq.properties")
public class MqConfiguration {
	@Value("${mq.addrs}")
	private String addrs;
	@Value("${mq.username}")
	private String username;
	@Value("${mq.password}")
	private String password;
	@Value("${mq.vhost}")
	private String vhost;

	@Bean
	public ConnectionFactory connectionFactory() {
		if (!StringUtils.isEmpty(addrs.trim()) || !StringUtils.isEmpty(username.trim())
				|| !StringUtils.isEmpty(password.trim()) || !StringUtils.isEmpty(addrs.trim())) {

		}
		return null;
	}

}
