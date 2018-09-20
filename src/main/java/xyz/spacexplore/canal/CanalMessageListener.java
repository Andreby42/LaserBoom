package xyz.spacexplore.canal;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.alibaba.otter.canal.protocol.CanalEntry.EntryType;
import com.alibaba.otter.canal.protocol.CanalEntry.EventType;
import com.alibaba.otter.canal.protocol.CanalEntry.RowChange;
import com.alibaba.otter.canal.protocol.Message;

/**
 * 
 * @ClassName: CanalMessageListener
 * @Description:监听处理canal客户端消息处理
 * @author Andreby
 * @date 2017年2月23日 下午5:48:58
 */
@Component("canalMessageListener")
public class CanalMessageListener {
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(CanalMessageListener.class);

    @Resource
    private SupposeCanalMessageProcess supposeCanalMessageProcess;


    @Async
    @EventListener(value = CanalMessageEvent.class)
    public void onApplicationEvent(CanalMessageEvent event) throws Exception {
        Message message = event.getMessage();
        preHandle(message);
    }

    private void preHandle(Message message) throws Exception {
        for (Entry entry : message.getEntries()) {
            if (entry.getEntryType() == EntryType.TRANSACTIONBEGIN || entry.getEntryType() == EntryType.TRANSACTIONEND) {
                continue;
            }

            if (entry.getEntryType() == EntryType.ROWDATA) {
                EventType eventType = RowChange.parseFrom(entry.getStoreValue()).getEventType();
                if (eventType == EventType.QUERY) {
                    continue;
                }
                supposeCanalMessageProcess.handleData(entry);
            }
        }
    }
}
