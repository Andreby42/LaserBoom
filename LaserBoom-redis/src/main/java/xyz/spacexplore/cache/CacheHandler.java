package xyz.spacexplore.cache;

import java.util.List;

public interface CacheHandler {

	public <T> void add(List<CacheDTO<T>> list);

	public <T> void update(List<CacheDTO<T>> list);

	public <T> void delete(List<CacheDTO<T>> list);

}
