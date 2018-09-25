package xyz.spacexplore.domain;

public interface CustomDao {
    int updateSelectiveIgnoreOnDuplicateUpdate();

    <T> int deleteByPrimaryKey(T t);
   
    <T> int insertSelectiveIgnoreOnDuplicateUpdate(T t);
    
    

}
