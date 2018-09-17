package xyz.spacexplore.canal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.util.Assert;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.protocol.Message;
import com.google.common.util.concurrent.ListeningExecutorService;

public class CustomCanalClient {
	private final static Logger logger = LoggerFactory.getLogger(CustomCanalClient.class);
	private volatile boolean running = false;

	@SuppressWarnings("unused")
	private Thread.UncaughtExceptionHandler handler = new Thread.UncaughtExceptionHandler() {
		public void uncaughtException(Thread t, Throwable e) {
			logger.error("parse canal events has an error", e);
		}
	};
	private CanalConnector connector;
	private String destination;
	private CanalServicePublisher canalServicePublisher;
	private long batchId;
	private ListeningExecutorService service;

	public ListeningExecutorService getService() {
		return service;
	}

	public void setService(ListeningExecutorService service) {
		this.service = service;
	}

	public CanalServicePublisher getCanalServicePublisher() {
		return canalServicePublisher;
	}

	public void setCanalServicePublisher(CanalServicePublisher canalServicePublisher) {
		this.canalServicePublisher = canalServicePublisher;
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		logger.debug("Autowired applicationContext");
	}

	public CustomCanalClient() {

	}

	public CustomCanalClient(CanalConnector connector, String destination, CanalServicePublisher canalServicePublisher,
			ListeningExecutorService service) {
		super();
		this.connector = connector;
		this.destination = destination;
		this.canalServicePublisher = canalServicePublisher;
		this.service = service;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public void start() {
		Assert.notNull(connector, "connector is null");
		service.submit(new Runnable() {
			@Override
			public void run() {
				process();
			}
		});
		running = true;
	}

	public void stop() {
		if (!running && !service.isShutdown()) {
			return;
		}
		running = false;
		service.shutdown();
	}

	@SuppressWarnings("static-access")
	private void process() {
		while (running) {
			try {
				connector.connect();
				connector.subscribe("");
				while (running) {
					Message message = connector.getWithoutAck(Constants.CanalConf.CANAL_DEFAULT_BATCHSIZE); // 获取指定数量的数据
					batchId = message.getId();
					int size = message.getEntries().size();

					if (batchId == -1 || size == 0) {
						Thread.currentThread().sleep(1000);
						continue;
					} else {

						ApplicationEvent event = new CanalMessageEvent(CustomCanalClient.class, message);
						canalServicePublisher.publishEvent(event);
					}

					connector.ack(batchId); // 提交确认
				}
			} catch (Exception e) {
				logger.error("process error@CustomCanalClient@process Method", e);
				connector.rollback(batchId); // 处理失败, 回滚数据
			} finally {
				connector.disconnect();
			}
		}
	}

	public void setConnector(CanalConnector connector) {
		this.connector = connector;
	}

}
