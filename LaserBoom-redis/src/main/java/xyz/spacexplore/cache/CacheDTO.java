package xyz.spacexplore.cache;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 
 * @doc:用于redis 存储对象
 * @author Andreby
 * @date 2017年5月21日 下午7:50:18
 * @param <T>
 */
@Component
@Scope("prototype")
public class CacheDTO<T> implements Serializable {
	private static final long serialVersionUID = -5479965679484004667L;

	private Object key;

	private T value;

	private int dbIndex;

	public CacheDTO(Object key, T value, int dbIndex) {
		super();
		this.key = key;
		this.value = value;
		this.dbIndex = dbIndex;
	}

	public int getDbIndex() {
		return dbIndex;
	}

	public void setDbIndex(int dbIndex) {
		this.dbIndex = dbIndex;
	}

	public Object getKey() {
		return key;
	}

	public void setKey(Object key) {
		this.key = key;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + dbIndex;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CacheDTO<?> other = (CacheDTO<?>) obj;
		if (dbIndex != other.dbIndex)
			return false;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CacheDTO [key=" + key + ", value=" + value + ", dbIndex=" + dbIndex + "]";
	}

}
