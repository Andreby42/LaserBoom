package xyz.spacexplore.domain;

import java.util.List;

import xyz.spacexplore.domain.dto.EventDTO;

public interface EventDao {
    int deleteByPrimaryKey(Integer id);

    int insert(EventDTO record);

    int insertSelective(EventDTO record);

    EventDTO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EventDTO record);

    int updateByPrimaryKey(EventDTO record);

    List<String> selectTables();
}