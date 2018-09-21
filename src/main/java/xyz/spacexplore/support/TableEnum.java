package xyz.spacexplore.support;

import xyz.spacexplore.domain.dao.EventDao;
import xyz.spacexplore.domain.dao.LeagueDao;

/**
 * 
 * @ClassName: TableEnum
 * @Description:表名枚举(md5)
 * @author Andreby
 * @date 2017年3月14日 下午5:01:02
 */
@SuppressWarnings("rawtypes")
public enum TableEnum {
    EVENT("event", EventDao.class), LEAGUE("league", LeagueDao.class);


    private String tableName;

    private Class claz;



    private TableEnum(String tableName, Class claz) {
        this.tableName = tableName;
        this.claz = claz;
    }

    public Class getClaz() {
        return claz;
    }

    public void setClaz(Class claz) {
        this.claz = claz;
    }


    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<T> getClaz(String md5) {
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(md5)) {
            for (TableEnum tab : TableEnum.values()) {
                if (md5.equalsIgnoreCase(tab.getTableName())) {
                    return tab.getClaz();
                }
            }
        }
        return null;
    }

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {}
}
