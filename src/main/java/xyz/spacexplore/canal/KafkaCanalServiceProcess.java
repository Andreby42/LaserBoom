package xyz.spacexplore.canal;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("kafkaCanalServiceProcess")
public class KafkaCanalServiceProcess implements CanalService {
    private static final Logger logger = LoggerFactory.getLogger(CanalServiceProcess.class);

    @Resource
    private KafkaCanalClient kafkaCanalClient;

    @Override
    @PostConstruct
    public void initCanalService() {
        kafkaCanalClient.start();
    }

    @Override
    @PreDestroy
    public void destroyCanalService() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    logger.info("## stop the kafkacanal client");
                    kafkaCanalClient.stop();
                    System.out.println("canal client is stopping");
                } catch (Throwable e) {
                    logger.warn("##something goes wrong when stopping kafkacanal:\n{}", ExceptionUtils.getFullStackTrace(e));
                } finally {
                    logger.info("## kafkacanal client is down.");
                    System.out.println("kafkacanal client is down");
                }
            }
        });
    }

}
