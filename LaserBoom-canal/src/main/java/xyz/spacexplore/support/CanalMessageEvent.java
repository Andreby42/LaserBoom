package xyz.spacexplore.support;

import java.util.List;

import org.springframework.context.ApplicationEvent;

public class CanalMessageEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;
	private volatile boolean flag;

	public CanalMessageEvent(Object source) {
		super(source);
		if (source instanceof java.util.List) {
			@SuppressWarnings("unchecked")
			List<Object> list = (List<Object>) source;
			if (list == null || list.size() == 0) {
				this.flag = false;
			}
			flag = true;
		}
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

}
