package xyz.spacexplore.canalservice;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import xyz.spacexplore.canalclient.CanalClient;

@Service("canalServer")
public class CanalServerProcess implements CanalServer {

	private static final Logger logger = LoggerFactory.getLogger(CanalServerProcess.class);

	@Resource
	private CanalClient canalClient;

	@PostConstruct
	@Override
	public void startCanal() {
		canalClient.start();
	}

	@PreDestroy
	@Override
	public void destroyCanal() {
		Runtime.getRuntime().addShutdownHook(new Thread() {

			public void run() {
				try {
					canalClient.stop();
				} catch (Throwable e) {
					logger.warn("##something goes wrong when stopping canal:\n{}", ExceptionUtils.getFullStackTrace(e));
				} finally {
					logger.info("## canal client is down.");
				}
			}

		});
	}

}
