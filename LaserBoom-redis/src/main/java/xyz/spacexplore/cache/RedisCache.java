package xyz.spacexplore.cache;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service("redisCache")
public class RedisCache implements Cache {
	@SuppressWarnings("rawtypes")
	@Resource
	protected RedisTemplate redisTemplate;

	/**
	 * 
	 * @doc:pipelineSet方法
	 * @author Andreby
	 * @date 2017年5月21日 下午6:59:51
	 * @param key
	 * @param value
	 */
	@SuppressWarnings("unchecked")
	public void pipelineSet(String key, Object value) {
		final byte[] rawKey = redisTemplate.getKeySerializer().serialize(key);
		final byte[] rawValue = redisTemplate.getValueSerializer().serialize(value);
		RedisCallback<List<Object>> pipelineCallback = new RedisCallback<List<Object>>() {

			@Override
			public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
				connection.openPipeline();
				connection.set(rawKey, rawValue);
				return connection.closePipeline();
			}

		};
		redisTemplate.execute(pipelineCallback);
	}
}
