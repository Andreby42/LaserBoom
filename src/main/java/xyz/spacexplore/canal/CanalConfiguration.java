package xyz.spacexplore.canal;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.kafka.client.KafkaCanalConnector;
import com.alibaba.otter.canal.kafka.client.KafkaCanalConnectors;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

@Configuration
public class CanalConfiguration extends BaseLogger {
    @Resource
    private Environment env;
    @Value("${kafka.canal.zkservers}")
    private String zkServers;

    @Value("${kafka.canal.bootstrap.servers}")
    private String servers;

    @Value("${kafka.canal.topic}")
    private String topic;

    @Value("${kafka.canal.group.id}")
    private String groupId;



    @Bean(value = "canalConnector")
    public CanalConnector canalConnector() {
        String zkAddr = "";
        String zkInstanceName = "";
        String zkCanalMysqlUsername = "";
        String zkCannalMysqlPassword = "";
        String socketAddrs = "";
        String socketInstance = "";
        String socketUser = "";
        String socketPassword = "";
        try {
            zkAddr = env.getProperty(Constants.CanalConf.CANAL_ZK_ADDRESS_KEY);
            zkInstanceName = env.getProperty(Constants.CanalConf.CANAL_CLUSTER_INSTANCE_KEY);
            zkCanalMysqlUsername = env.getProperty(Constants.CanalConf.CANAL_CLUSTER_MYSQL_USERNAME_KEY);
            zkCannalMysqlPassword = env.getProperty(Constants.CanalConf.CANAL_CLUSTER_MYSQL_PASSWORD_KEY);
            socketAddrs = env.getProperty(Constants.CanalConf.CANAL_SOCKETADDR_KEY);
            socketInstance = env.getProperty(Constants.CanalConf.CANAL_SOCKET_INSTANCE_KEY);
            socketUser = env.getProperty(Constants.CanalConf.CANAL_SOCKET_USERNAME_KEY);
            socketPassword = env.getProperty(Constants.CanalConf.CANAL_SOCKET_PASSWORD_KEY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        CanalConnector canalConnector = null;
        if (!org.apache.commons.lang3.StringUtils.isEmpty(zkAddr) && !org.apache.commons.lang3.StringUtils.isEmpty(zkInstanceName) && !org.apache.commons.lang3.StringUtils.isEmpty(zkCanalMysqlUsername) && !org.apache.commons.lang3.StringUtils.isEmpty(zkCannalMysqlPassword)) {
            canalConnector = CanalConnectors.newClusterConnector(zkAddr, zkInstanceName, zkCanalMysqlUsername, zkCannalMysqlPassword);
            return canalConnector;
        }
        if (!org.apache.commons.lang3.StringUtils.isEmpty(socketAddrs) && !org.apache.commons.lang3.StringUtils.isEmpty(socketInstance) && !org.apache.commons.lang3.StringUtils.isEmpty(socketUser) && !org.apache.commons.lang3.StringUtils.isEmpty(socketPassword)) {
            List<SocketAddress> socketAddress = getSocketAddressByString(socketAddrs);
            if (socketAddress != null && socketAddress.size() > 1) {
                canalConnector = CanalConnectors.newClusterConnector(socketAddress, socketInstance.trim(), socketUser.trim(), socketPassword.trim());
                return canalConnector;

            }
            if (socketAddress != null && socketAddress.size() == 1) {
                canalConnector = CanalConnectors.newSingleConnector(socketAddress.get(0), socketInstance.trim(), socketUser.trim(), socketPassword.trim());
                return canalConnector;

            }
        }
        return null;
    }

    @Bean(value = "executorService")
    public ExecutorService executorService() {
        return Executors.newFixedThreadPool(200);
    }

    @Bean(value = "canalClient")
    public CustomCanalClient canalClient(@Qualifier("canalConnector") CanalConnector canalConnector, @Qualifier("canalServicePublisher") CanalServicePublisher canalServicePublisher, @Qualifier("executorService") ExecutorService executorService) {
        ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(executorService);
        String instancename = env.getProperty(Constants.CanalConf.CANAL_INSTANCENAME_KEY);
        CustomCanalClient canalClient = null;
        try {
            canalClient = new CustomCanalClient(canalConnector, instancename, canalServicePublisher, listeningExecutorService);
        } catch (Exception e) {
            logger.info(e.toString(), "get CanalClient failed @CanalConfiguration @canalSingleClient");
        }
        return canalClient;
    }

    private List<SocketAddress> getSocketAddressByString(String socketAddrs) {
        List<SocketAddress> list = new ArrayList<>();
        String[] socketAddress = null;
        if (!org.apache.commons.lang3.StringUtils.isEmpty(socketAddrs)) {
            socketAddress = socketAddrs.split(Constants.CanalConf.COMMA_SEPARATOR);
        }
        if (null != socketAddress) {
            for (String socketAddr : socketAddress) {
                String serverUrl = socketAddr.substring(0, socketAddr.lastIndexOf(":"));
                String port = socketAddr.substring(socketAddr.lastIndexOf(":") + 1);
                InetSocketAddress inetSocketAddress = new InetSocketAddress(serverUrl, Integer.valueOf(port));
                list.add(inetSocketAddress);
            }
        }
        return list;
    }

    @Bean
    public KafkaCanalConnector kafkaCanalConnector() {
        return KafkaCanalConnectors.newKafkaConnector(zkServers, servers, topic, groupId);
    }

    @Bean(value = "kafkaCanalClient")
    public KafkaCanalClient kafkaCanalClient(@Qualifier("kafkaCanalConnector") KafkaCanalConnector kafkaCanalConnector, @Qualifier("canalServicePublisher") CanalServicePublisher canalServicePublisher, @Qualifier("executorService") ExecutorService executorService) {
        ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(executorService);
        KafkaCanalClient canalClient = null;
        try {
            canalClient = new KafkaCanalClient(kafkaCanalConnector, canalServicePublisher, listeningExecutorService);
        } catch (Exception e) {
            logger.info(e.toString(), "get kafkaCanalClient failed @CanalConfiguration @canalSingleClient");
        }
        return canalClient;
    }
}
