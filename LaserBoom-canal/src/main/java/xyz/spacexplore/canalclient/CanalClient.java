package xyz.spacexplore.canalclient;

import java.util.List;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.alibaba.otter.canal.protocol.Message;
import com.alibaba.otter.canal.protocol.exception.CanalClientException;
import com.google.common.util.concurrent.ListeningExecutorService;

import xyz.spacexplore.support.CanalMessageEvent;
import xyz.spacexplore.support.CanalMessagePublisher;

public class CanalClient {

	private volatile boolean running = false;

	private ListeningExecutorService executorService;
	private CanalConnector canalConnector;
	private CanalMessagePublisher canalMessagePublisher;

	public CanalClient() {

	}

	public CanalClient(ListeningExecutorService executorService, CanalConnector canalConnector,
			CanalMessagePublisher publisher) {
		super();
		this.executorService = executorService;
		this.canalConnector = canalConnector;
		this.canalMessagePublisher = publisher;
	}

	public CanalMessagePublisher getPublisher() {
		return canalMessagePublisher;
	}

	public void setPublisher(CanalMessagePublisher publisher) {
		this.canalMessagePublisher = publisher;
	}

	public ListeningExecutorService getExecutorService() {
		return executorService;
	}

	public void setExecutorService(ListeningExecutorService executorService) {
		this.executorService = executorService;
	}

	public CanalConnector getCanalConnector() {
		return canalConnector;
	}

	public void setCanalConnector(CanalConnector canalConnector) {
		this.canalConnector = canalConnector;
	}

	public void start() {
		running = true;
		executorService.submit(new Runnable() {
			@Override
			public void run() {
				process();
			}
		});
	}

	private void process() {
		while (running) {
			try {
				canalConnector.connect();
				canalConnector.subscribe();
				while (running) {
					Message message = canalConnector.getWithoutAck(1000);

					long batchId = message.getId();
					int size = message.getEntries().size();
					if (size == 0 || batchId == -1) {
						// do something
					} else {
						List<Entry> entries = message.getEntries();
						CanalMessageEvent event = new CanalMessageEvent(entries);
						canalMessagePublisher.publisher(event);
					}
					canalConnector.ack(batchId);
				}
			} catch (CanalClientException e) {
				// do something
			} finally {
				canalConnector.disconnect();
			}

		}
	}

	public void stop() {
		if (!running || executorService.isShutdown()) {
			return;
		}
		running = false;
		executorService.shutdown();
	}

}
