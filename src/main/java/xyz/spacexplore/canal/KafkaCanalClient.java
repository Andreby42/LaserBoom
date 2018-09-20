package xyz.spacexplore.canal;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.util.Assert;

import com.alibaba.otter.canal.kafka.client.KafkaCanalConnector;
import com.alibaba.otter.canal.protocol.Message;
import com.google.common.util.concurrent.ListeningExecutorService;

import xyz.spacexplore.support.CommonUtil;

public class KafkaCanalClient {
    private final static Logger logger = LoggerFactory.getLogger(CustomCanalClient.class);

    private volatile boolean running = false;

    private KafkaCanalConnector kafkaCanalConnector;

    private CanalServicePublisher canalServicePublisher;

    private long batchId;

    private ListeningExecutorService service;

    public KafkaCanalConnector getKafkaCanalConnector() {
        return kafkaCanalConnector;
    }

    public void setKafkaCanalConnector(KafkaCanalConnector kafkaCanalConnector) {
        this.kafkaCanalConnector = kafkaCanalConnector;
    }


    public CanalServicePublisher getCanalServicePublisher() {
        return canalServicePublisher;
    }

    public void setCanalServicePublisher(CanalServicePublisher canalServicePublisher) {
        this.canalServicePublisher = canalServicePublisher;
    }

    public ListeningExecutorService getService() {
        return service;
    }

    public void setService(ListeningExecutorService service) {
        this.service = service;
    }



    public KafkaCanalClient(KafkaCanalConnector kafkaCanalConnector, CanalServicePublisher canalServicePublisher, ListeningExecutorService service) {
        super();
        this.kafkaCanalConnector = kafkaCanalConnector;
        this.canalServicePublisher = canalServicePublisher;
        this.service = service;
    }

    public void start() {
        Assert.notNull(kafkaCanalConnector, "kafkaCanalConnector is null");
        service.submit(new Runnable() {
            @Override
            public void run() {
                process();
            }
        });
        running = true;
    }

    @SuppressWarnings("static-access")
    private void process() {
        while (running) {
            try {
                kafkaCanalConnector.connect();
                logger.info("cannal 成功链接上了");
                kafkaCanalConnector.subscribe();
                while (running) {
                    Message message = kafkaCanalConnector.getWithoutAck(100L, TimeUnit.MILLISECONDS);
                    if (message != null) {
                        batchId = message.getId();
                        int size = message.getEntries().size();

                        if (batchId == -1 || size == 0) {
                            Thread.currentThread().sleep(1000);
                            continue; 
                        } else {
                            ApplicationEvent event = new CanalMessageEvent(CustomCanalClient.class, message);
                            canalServicePublisher.publishEvent(event);
                        }
                        kafkaCanalConnector.ack();
                    }
                }
            } catch (Exception e) {
                logger.error("process error@CustomCanalClient@process Method", CommonUtil.getStackTraceInfo(e));
            } finally {
                // kafkaCanalConnector.disconnnect();
            }
        }
    }

    public void stop() {
        if (!running && !service.isShutdown()) {
            return;
        }
        running = false;
        service.shutdown();
    }

}
