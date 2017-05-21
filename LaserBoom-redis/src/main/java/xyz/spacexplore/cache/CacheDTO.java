package xyz.spacexplore.cache;

import java.io.Serializable;

/**
 * 
 * @doc:用于redis 存储对象
 * @author Andreby
 * @date 2017年5月21日 下午7:50:18
 * @param <T>
 */
public class CacheDTO<T> implements Serializable {
	private static final long serialVersionUID = -5479965679484004667L;

	private Object key;

	private T value;

	public CacheDTO(Object key, T value) {
		super();
		this.key = key;
		this.value = value;
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
		return "CacheDTO [key=" + key + ", value=" + value + "]";
	}

}
