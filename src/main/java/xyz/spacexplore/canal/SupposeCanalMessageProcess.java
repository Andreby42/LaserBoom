package xyz.spacexplore.canal;

import java.lang.reflect.Type;
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

import xyz.spacexplore.domain.dao.EventDao;
import xyz.spacexplore.support.ApplicationContextUtil;

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

    /*
     * Entry Header logfileName [binlog文件名] logfileOffset [binlog position] executeTime
     * [binlog里记录变更发生的时间戳,精确到秒] schemaName tableName eventType [insert/update/delete类型] entryType
     * [事务头BEGIN/事务尾END/数据ROWDATA] storeValue [byte数据,可展开，对应的类型为RowChange] RowChange
     * 
     * isDdl [是否是ddl变更操作，比如create table/drop table]
     * 
     * sql [具体的ddl sql]
     * 
     * rowDatas [具体insert/update/delete的变更数据，可为多条，1个binlog event事件可对应多条变更，比如批处理]
     * 
     * beforeColumns [Column类型的数组，变更前的数据字段]
     * 
     * afterColumns [Column类型的数组，变更后的数据字段]
     * 
     * 
     * Column
     * 
     * index
     * 
     * sqlType [jdbc type]
     * 
     * name [column name]
     * 
     * isKey [是否为主键]
     * 
     * updated [是否发生过变更]
     * 
     * isNull [值是否为null]
     * 
     * value [具体的内容，注意为string文本]
     */
    @Override
    public void handleData(Entry entry) throws Exception {
        String pk = null;
        String tableName = entry.getHeader().getTableName();
        // CanalUtil.getPrimaryKeyName(columns);
        EventType eventType = RowChange.parseFrom(entry.getStoreValue()).getEventType();
        List<RowData> rowDatasList = RowChange.parseFrom(entry.getStoreValue()).getRowDatasList();



        for (RowData rowData : rowDatasList) {
            if (eventType == EventType.DELETE) {
                pk = CanalUtil.getPrimaryKeyName(rowData.getBeforeColumnsList());
                List<Column> columns = rowData.getBeforeColumnsList();
                Map<String, String> columnToMap = CanalUtil.columnToMap(columns);
                String key = columnToMap.get(pk);
                String daoName = CanalUtil.getColumnName(tableName, false);
                Class<?> classz = InitData.tableDaoMap.get(daoName);
                Object bean = CanalUtil.getBean(classz.newInstance(),classz);
            } else {
                /*
                 * pk = CanalUtil.getPrimaryKeyName(rowData.getAfterColumnsList()); List<Column>
                 * columns = rowData.getAfterColumnsList(); Map<String, String> columnToMap =
                 * CanalUtil.columnToMap(columns);
                 * 
                 * String jsonData = null; try { // jsonData =
                 * CanalUtil.parseColumnMapToDTO(columnToMap, // entry.getHeader().getTableName());
                 * } catch (Exception e) { logger.error(e.toString(),
                 * "[parse map to Obj Json failed@CanalMessageHandlerImpl@doHandler]"); } String key
                 * = columnToMap.get(pk);
                 * 
                 * System.out.println(Thread.currentThread().getName() + "--" +
                 * Thread.currentThread().getId() + "---" + DateUtil.getTime(new Date()) + "---" +
                 * jsonData); logger.info(eventType + "操作,@" + entry.getHeader().getSchemaName() +
                 * "@" + entry.getHeader().getTableName() + "@RedisDbUniKey::" + key); String
                 * row_data = header_str + row_str + "\"before\":" + dataBefore + ",\"after\":" +
                 * dataAfter + ",\"time\":\"" + DateUtil.getTime() + "\"}"; logger.info(row_data);
                 */
            }
        }
    }
}
