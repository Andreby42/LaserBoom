package xyz.spacexplore.canalconf;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import xyz.spacexplore.canalclient.CanalClient;
import xyz.spacexplore.support.CanalMessagePublisher;
import xyz.spacexplore.support.Constants;

@Configuration
@PropertySource("classpath*:canal.properties")
public class CanalConfiguration {
	@Value("${canal.sockets}")
	private String sockets;
	@Value("${canal.sockets.destination}")
	private String sDestination;
	@Value("${canal.sockets.mysql.user}")
	private String sMysqlUser;
	@Value("${canal.sockets.mysql.password}")
	private String sMysqlPasswd;

	@Value("${canal.zkServer}")
	private String zkServer;
	@Value("${canal.zkServer.destination}")
	private String zkDestination;
	@Value("${canal.zkServer.mysql.user}")
	private String zkMysqlUser;
	@Value("${canal.zkServer.mysql.password}")
	private String zkMysqlPasswd;
	@Value("${canal.poolsize}")
	private String poolSize;

	@Bean(value = "canalConnector")
	public CanalConnector canalConnector() {
		CanalConnector canalConnector = null;
		if (!StringUtils.isEmpty(sockets) || !StringUtils.isEmpty(sDestination) || !StringUtils.isEmpty(sMysqlUser)
				|| !StringUtils.isEmpty(sMysqlPasswd)) {
			List<SocketAddress> socketAddress = socketAddress();
			if (!CollectionUtils.isEmpty(socketAddress)) {
				canalConnector = CanalConnectors.newClusterConnector(socketAddress, sDestination, sMysqlUser,
						sMysqlPasswd);
			} else if (socketAddress != null && socketAddress.size() == 1) {
				canalConnector = CanalConnectors.newSingleConnector(socketAddress.get(0), sDestination, sMysqlUser,
						sMysqlPasswd);
			}
		}
		if (!StringUtils.isEmpty(zkServer) || !StringUtils.isEmpty(zkDestination) || !StringUtils.isEmpty(zkMysqlUser)
				|| !StringUtils.isEmpty(zkMysqlPasswd)) {
			canalConnector = CanalConnectors.newClusterConnector(zkServer, zkDestination, zkMysqlUser, zkMysqlPasswd);
		}
		return canalConnector;
	}

	@Bean("executorService")
	public ExecutorService executorService() {
		if (StringUtils.isEmpty(poolSize)) {
			return Executors.newFixedThreadPool(Constants.DEFAULT_THREAD_POOL_SIZE);
		} else {
			return Executors.newFixedThreadPool(Integer.valueOf(poolSize));
		}
	}

	@Bean(value = "canalClient")
	public CanalClient canalClient(@Qualifier(value = "executorService") ExecutorService executorService,
			@Qualifier(value = "canalConnector") CanalConnector canalConnector,
			@Qualifier(value = "canalMessagePublisher") CanalMessagePublisher canalMessagePublisher) {

		Assert.isTrue(canalConnector != null, "CanalConnector can't be null");

		ListeningExecutorService service = MoreExecutors.listeningDecorator(executorService);
		CanalClient canalClient = new CanalClient(service, canalConnector, canalMessagePublisher);

		return canalClient;
	}

	private List<SocketAddress> socketAddress() {
		List<SocketAddress> list = new ArrayList<>();
		if (!StringUtils.isEmpty(sockets)) {
			String[] addrs = sockets.split(Constants.COMMA_SEPARATOR);
			if (null != addrs) {
				for (String addr : addrs) {
					String serverUrl = addr.substring(0, addr.lastIndexOf(Constants.SEMICOLON_SEPARATOR));
					String port = addr.substring(addr.lastIndexOf(Constants.SEMICOLON_SEPARATOR) + 1);
					list.add(new InetSocketAddress(serverUrl, Integer.valueOf(port)));
				}
			}
		}
		return list;
	}
}
