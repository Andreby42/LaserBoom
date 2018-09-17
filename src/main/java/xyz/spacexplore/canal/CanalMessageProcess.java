package xyz.spacexplore.canal;

import com.alibaba.otter.canal.protocol.CanalEntry.Entry;

public interface CanalMessageProcess {
	public void handleData(Entry entry) throws Exception;
}
