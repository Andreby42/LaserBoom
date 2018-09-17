package xyz.spacexplore.canal;

import xyz.spacexplore.domain.EventDTO;
import xyz.spacexplore.domain.LeagueDTO;

/**
 * 
 * @ClassName: TableEnum
 * @Description:表名枚举(md5)
 * @author Andreby
 * @date 2017年3月14日 下午5:01:02
 */
@SuppressWarnings("rawtypes")
public enum TableEnum {
    // 这里是 product_table 去掉_table 的md5
    EVENT("4119639092e62c55ea8be348e4d9260d", EventDTO.class),
    LEAGUE("517b08dafd8aa7559790bf0715de47c6", LeagueDTO.class);
    // Constants.RedisKey.REDISDB_KEY_COMMON_INFO),
    // ATOM("3e10f8c809242d3a0f94c18e7addb866",Atom.class,10);
    private String md5;

    private Class claz;



    public Class getClaz() {
        return claz;
    }

    public void setClaz(Class claz) {
        this.claz = claz;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    private TableEnum(String md5) {
        this.md5 = md5;
    }

    private TableEnum(String md5, Class claz) {
        this.md5 = md5;
        this.claz = claz;
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<T> getClaz(String md5) {
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(md5)) {
            for (TableEnum tab : TableEnum.values()) {
                if (md5.equalsIgnoreCase(tab.getMd5())) {
                    return tab.getClaz();
                }
            }
        }
        return null;
    }
    //
    // public static int getIndex(String md5) {
    // if (org.apache.commons.lang3.StringUtils.isNotEmpty(md5)) {
    // for (TableEnum tab : TableEnum.values()) {
    // if (md5.equalsIgnoreCase(tab.getMd5())) {
    // return tab.getDbInde();
    // }
    // }
    // }
    // return -1;
    // }

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        System.out.println(new MD5().getMD5ofStr("league").toLowerCase());;
    }
}
