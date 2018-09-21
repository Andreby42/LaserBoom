package xyz.spacexplore.domain.dao;

import java.util.List;

import xyz.spacexplore.domain.CustomDao;
import xyz.spacexplore.domain.dto.EventDTO;

public interface EventDao extends CustomDao{
    int deleteByPrimaryKey(Integer id);

    int insert(EventDTO record);

    int insertSelective(EventDTO record);

    EventDTO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EventDTO record);

    int updateByPrimaryKey(EventDTO record);

    List<String> selectTables();
}