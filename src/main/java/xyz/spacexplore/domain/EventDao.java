package xyz.spacexplore.domain;

import xyz.spacexplore.domain.EventDTO;

public interface EventDao {
    int deleteByPrimaryKey(Integer id);

    int insert(EventDTO record);

    int insertSelective(EventDTO record);

    EventDTO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EventDTO record);

    int updateByPrimaryKey(EventDTO record);
}