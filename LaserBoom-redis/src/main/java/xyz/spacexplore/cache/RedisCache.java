package xyz.spacexplore.cache;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service("redisCache")
@SuppressWarnings({"rawtypes","unchecked"})
public class RedisCache implements Cache {
	@Resource
	protected RedisTemplate redisTemplate;

	@Override
	public <T> void add(final List<CacheDTO<T>> list) {
		RedisCallback<List<Object>> pipelineCallback = new RedisCallback<List<Object>>() {
			@Override
			public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
				connection.openPipeline();
				for (CacheDTO<T> cacheDTO : list) {
					byte[] rawKey = redisTemplate.getKeySerializer().serialize(cacheDTO.getKey());
					byte[] rawValue = redisTemplate.getValueSerializer().serialize(cacheDTO.getValue());
					connection.setNX(rawKey, rawValue);
				}
				return connection.closePipeline();
			}

		};
		redisTemplate.execute(pipelineCallback);

	}

	@Override
	public <T> void update(final List<CacheDTO<T>> list) {
		RedisCallback<List<Object>> pipelineCallback = new RedisCallback<List<Object>>() {
			@Override
			public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
				connection.openPipeline();
				for (CacheDTO<T> cacheDTO : list) {
					byte[] rawKey = redisTemplate.getKeySerializer().serialize(cacheDTO.getKey());
					byte[] rawValue = redisTemplate.getValueSerializer().serialize(cacheDTO.getValue());
					connection.set(rawKey, rawValue);
				}
				return connection.closePipeline();
			}

		};
		redisTemplate.execute(pipelineCallback);
	}

	@Override
	public void delete(final List<Object> list) {
		RedisCallback<List<Object>> pipelineCallback = new RedisCallback<List<Object>>() {
			@Override
			public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
				connection.openPipeline();
				for (Object obj : list) {
					byte[] rawKey = redisTemplate.getKeySerializer().serialize(obj);
					connection.del(rawKey);
				}
				return connection.closePipeline();
			}

		};
		redisTemplate.execute(pipelineCallback);
	}
}
