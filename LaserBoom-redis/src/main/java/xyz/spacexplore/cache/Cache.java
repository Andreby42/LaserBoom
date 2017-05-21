package xyz.spacexplore.cache;

import java.util.List;

public interface Cache {

	public <T> void add(List<CacheDTO<T>> list);

	public <T> void update(List<CacheDTO<T>> list);

	public void delete(List<Object> list);

}
