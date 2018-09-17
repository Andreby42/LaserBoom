package xyz.spacexplore.canal;

import java.util.Date;
import java.util.List;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.otter.canal.protocol.CanalEntry.Column;
import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.alibaba.otter.canal.protocol.CanalEntry.EventType;
import com.alibaba.otter.canal.protocol.CanalEntry.RowChange;
import com.alibaba.otter.canal.protocol.CanalEntry.RowData;
import com.google.protobuf.InvalidProtocolBufferException;

/**
 * 
 * @ClassName: SupposeCanalMessageProcess
 * @Description:一个处理器类的样例
 * @author Andreby
 * @date 2017年2月24日 下午6:26:54
 */
@Service("canalMessageProcess")
public class SupposeCanalMessageProcess implements CanalMessageProcess {
    private static final Logger logger = LoggerFactory.getLogger("sync");

    @Override
    public void handleData(Entry entry) throws Exception {
        RowChange rowChage = null;
        try {
            rowChage = RowChange.parseFrom(entry.getStoreValue());
        } catch (InvalidProtocolBufferException e) {
            logger.error(e.toString(), "[get rowchange failed@CanalMessageHandlerImpl@doHandler]");
        }
        if (rowChage.getDdlSchemaName().equals("league")) {
            System.out.println("got it");
        }
        // int index = TableEnum.getIndex(new MD5().getMD5ofStr(entry.getHeader().getTableName()));
        EventType eventType = rowChage.getEventType();
        String row_str = "\"eventType\":\"" + eventType + "\",";
        String dataBefore = "";
        String dataAfter = "";
        String header_str = "{\"binlog\":\"" + entry.getHeader().getLogfileName() + ":" + entry.getHeader().getLogfileOffset() + "\"," + "\"db\":\"" + entry.getHeader().getSchemaName() + "\"," + "\"table\":\"" + entry.getHeader().getTableName() + "\",";

        List<RowData> rowDataList = rowChage.getRowDatasList();
        for (RowData rowData : rowDataList) {
            String pk = "";
            if (eventType == EventType.DELETE) {
                pk = CanalUtil.getPrimaryKeyName(rowData.getBeforeColumnsList());
                List<Column> columns = rowData.getBeforeColumnsList();
                Map<String, String> columnToMap = CanalUtil.columnToMap(columns);
                dataBefore = JSON.toJSONString(columnToMap);

                String key = columnToMap.get(pk);
            } else {
                pk = CanalUtil.getPrimaryKeyName(rowData.getAfterColumnsList());
                List<Column> columns = rowData.getAfterColumnsList();
                Map<String, String> columnToMap = CanalUtil.columnToMap(columns);

                String jsonData = null;
                try {
                    jsonData = CanalUtil.parseColumnMapToDTO(columnToMap, entry.getHeader().getTableName());
                } catch (Exception e) {
                    logger.error(e.toString(), "[parse map to Obj Json failed@CanalMessageHandlerImpl@doHandler]");
                }
                String key = columnToMap.get(pk);

                System.out.println(Thread.currentThread().getName() + "--" + Thread.currentThread().getId() + "---" + DateUtil.getTime(new Date()) + "---" + jsonData);
                logger.info(eventType + "操作,@" + entry.getHeader().getSchemaName() + "@" + entry.getHeader().getTableName() + "@RedisDbUniKey::" + key);
                String row_data = header_str + row_str + "\"before\":" + dataBefore + ",\"after\":" + dataAfter + ",\"time\":\"" + DateUtil.getTime() + "\"}";
                logger.info(row_data);
            }
        }
    }
}
