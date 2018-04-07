package com.yan.project.core.base.dao;

import java.util.List;

import com.yan.project.core.base.entity.BaseEntity;

public interface IBaseDao {
	
	/*
	 * 对于重复的泛型的优化方式
	 * IBaseDao<T extends BaseEntity>
	 * 注：这也是之前看到的这样用法的原因 
	 * 
	 */
	<T extends BaseEntity> int save(T entity) throws Exception;
	
	<T extends BaseEntity> int[] batchSave(List<T> list) throws Exception;
	
	<T extends BaseEntity> int update(T entity, boolean onlyNotNull) throws Exception;
	
	<T extends BaseEntity> int update(T entity) throws Exception;
	
	<T extends BaseEntity> int[] batchUpdate(List<T> list) throws Exception;
	
	<T extends BaseEntity> int DeleteById(String id, Class<T> clazz) throws Exception;
	
	<T extends BaseEntity> int DeleteByIds(List<String> ids, Class<T> clazz) throws Exception;
	
	<T extends BaseEntity> List<T> selectForList(Class<T> clazz) throws Exception;
	
	<T extends BaseEntity> List<T> selectAllForList(T entity) throws Exception;
	
	<T extends BaseEntity> List<T> selectResultMapperForList(T entity) throws Exception;

}
