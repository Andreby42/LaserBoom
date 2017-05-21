package xyz.spacexplore.cache;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service("redisCacheHandler")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class RedisCacheHandler implements Cache {
	@Resource
	protected RedisTemplate redisTemplate;

	@Override
	public <T> void add(final List<CacheDTO<T>> list) {
		RedisCallback<List<Object>> pipelineCallback = new RedisCallback<List<Object>>() {
			@Override
			public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
				connection.openPipeline();
				for (CacheDTO<T> cacheDTO : list) {
					connection.select(cacheDTO.getDbIndex());
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
					// TODO:添加index
					connection.select(cacheDTO.getDbIndex());
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
	public <T> void delete(final List<CacheDTO<T>> list) {
		RedisCallback<List<Object>> pipelineCallback = new RedisCallback<List<Object>>() {
			@Override
			public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
				// TODO:添加index
				connection.openPipeline();
				for (CacheDTO cacheDTO : list) {
					connection.select(cacheDTO.getDbIndex());
					byte[] rawKey = redisTemplate.getKeySerializer().serialize(cacheDTO.getKey());
					connection.del(rawKey);
				}
				return connection.closePipeline();
			}

		};
		redisTemplate.execute(pipelineCallback);
	}

}
