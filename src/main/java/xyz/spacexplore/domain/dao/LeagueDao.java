package xyz.spacexplore.domain.dao;

import java.util.List;

import xyz.spacexplore.domain.CustomDao;
import xyz.spacexplore.domain.dto.LeagueDTO;

public interface LeagueDao extends CustomDao {
    int deleteByPrimaryKey(Integer lid);

    int insert(LeagueDTO record);

    int insertSelective(LeagueDTO record);

    LeagueDTO selectByPrimaryKey(Integer lid);

    int updateByPrimaryKeySelective(LeagueDTO record);

    int updateByPrimaryKey(LeagueDTO record);

    List<Integer> selectAllLid();

    void insertOrUpdate(List<LeagueDTO> llist);

    String selectLidByCnShort(String cnshort);

    List<String> selectAllLn();

    Integer selectByCnshort(String cnshort);
}
