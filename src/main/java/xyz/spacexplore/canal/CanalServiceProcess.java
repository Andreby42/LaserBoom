package xyz.spacexplore.canal;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("canalService")
public class CanalServiceProcess implements CanalService {
	private static final Logger logger = LoggerFactory.getLogger(CanalServiceProcess.class);

	// @Resource
	private CustomCanalClient canalClient;

	@Override
	// @PostConstruct
	public void initCanalService() {
		canalClient.start();
	}

	@Override
	// @PreDestroy
	public void destroyCanalService() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				try {
					logger.info("## stop the canal client");
					canalClient.stop();
					System.out.println("canal client is stopping");
				} catch (Throwable e) {
					logger.warn("##something goes wrong when stopping canal:\n{}", ExceptionUtils.getFullStackTrace(e));
				} finally {
					logger.info("## canal client is down.");
					System.out.println("canal client is down");
				}
			}
		});
	}

}
