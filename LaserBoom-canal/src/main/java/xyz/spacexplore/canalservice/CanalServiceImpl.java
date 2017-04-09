package xyz.spacexplore.canalservice;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import xyz.spacexplore.canalclient.CanalClient;

@Service("canalService")
public class CanalServiceImpl implements CanalService {
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
					// do someting
				} finally {
					// do something
				}
			}

		});
	}

}
