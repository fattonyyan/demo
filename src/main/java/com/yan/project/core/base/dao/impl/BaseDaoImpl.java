package com.yan.project.core.base.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.yan.project.core.base.dao.IBaseDao;
import com.yan.project.core.base.entity.BaseEntity;
import com.yan.project.core.util.EntityUtils;
import com.yan.project.core.util.JdbcUtils;

public abstract class BaseDaoImpl implements IBaseDao {
	
	/*
	 * 这里是否要使用泛型，泛型是否要放在类名上？ 
	 */
	
	public abstract NamedParameterJdbcTemplate getJdbc();
	
	//	使用读写分离的数据库
	public NamedParameterJdbcTemplate getReadJdbc(){
		return getJdbc();
	}
	
	public NamedParameterJdbcTemplate getWriteJdbc(){
		return getJdbc();
	}
	
	/*下面是 使用 Spring Jdbc 的 NamedParameterJdbcTemplate 的具体的数据库操作方法*/
	
	public <T extends BaseEntity> int save(T entity) throws Exception {
		Class<? extends BaseEntity> clazz = entity.getClass();
		String sql = JdbcUtils.getSaveSQL(clazz);
		Map<String, Object> paramMap = EntityUtils.getAllParamMapByEntity(entity);
		
		paramMap.put(EntityUtils.getPKNameByClass(clazz), getDbId());
		
		SqlParameterSource sps = new MapSqlParameterSource(paramMap);
		
		/* the rows affected */
		return getWriteJdbc().update(sql, sps);
	}
	
	public <T extends BaseEntity> int[] batchSave(List<T> list) throws Exception{
		T instance = list.get(0);
		Class<? extends BaseEntity> clazz = instance.getClass();
		String sql = JdbcUtils.getSaveSQL(clazz);
		String pkName = EntityUtils.getPKNameByClass(clazz);
		
		SqlParameterSource[] sps = new SqlParameterSource[list.size()];
		int i = 0;
		for(T entity:list){
			Map<String, Object> paramMap = EntityUtils.getAllParamMapByEntity(entity);
			paramMap.put(pkName, getDbId());
			sps[i] = new MapSqlParameterSource(paramMap);
			i++;
		}
		
		
		return getWriteJdbc().batchUpdate(sql, sps);
	}
	
	public <T extends BaseEntity> int update(T entity, boolean onlyNotNull) throws Exception{
		String sql = JdbcUtils.getUpdateSQL(entity, onlyNotNull);
		Map<String, Object> paramMap = EntityUtils.getParamMapByEntity(entity, onlyNotNull);
		
		SqlParameterSource sps = new MapSqlParameterSource(paramMap);
		
		/* the rows affected */
		return getWriteJdbc().update(sql, sps);
	}
	
	public <T extends BaseEntity> int update(T entity) throws Exception{
		return update(entity, true);
	}
	
	public <T extends BaseEntity> int[] batchUpdate(List<T> list) throws Exception{
		T instance = list.get(0);
		String sql = JdbcUtils.getUpdateSQL(instance, false);
		
		SqlParameterSource[] sps = new SqlParameterSource[list.size()];
		int i = 0;
		for(T entity:list){
			sps[i] = new MapSqlParameterSource(EntityUtils.getAllParamMapByEntity(entity));
			i++;
		}
		
		return getWriteJdbc().batchUpdate(sql, sps);
	}
	
	
	public <T extends BaseEntity> int DeleteById(String id, Class<T> clazz) throws Exception{
		String sql = JdbcUtils.getDeleteSQL(clazz);
		String pkName = EntityUtils.getPKNameByClass(clazz);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(pkName, id);
		
		SqlParameterSource sps = new MapSqlParameterSource(paramMap);
		
		return getWriteJdbc().update(sql, sps);
	}
	public <T extends BaseEntity> int DeleteByIds(List<String> ids, Class<T> clazz) throws Exception{
		String sql = JdbcUtils.getDeleteSQL(clazz);
		String pkName = EntityUtils.getPKNameByClass(clazz);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(pkName, ids);
		
		SqlParameterSource sps = new MapSqlParameterSource(paramMap);
		
		return getWriteJdbc().update(sql, sps);
	}

	public <T extends BaseEntity> List<T> selectForList(Class<T> clazz) throws Exception{
		String resultMapper = EntityUtils.getResultMapperByClass(clazz);
		String sql = "SELECT "+ resultMapper + JdbcUtils.getListSelectSQL(clazz);
		
		return (List<T>) getReadJdbc().query(sql, new MapSqlParameterSource(), new BeanPropertyRowMapper<>(clazz));
	}
	
	public <T extends BaseEntity> List<T> selectAllForList(T entity) throws Exception{
		String sql = "SELECT * " + JdbcUtils.getSelectSQL(entity);
		// 实际上，上面的方法中获取过一次 paramMap
		Map<String, Object> paramMap = EntityUtils.getParamMapByEntity(entity, true);
		
		SqlParameterSource sps = new MapSqlParameterSource(paramMap);
		
		return (List<T>) getReadJdbc().query(sql, sps, new BeanPropertyRowMapper<>(entity.getClass()));
	}
	
	public <T extends BaseEntity> List<T> selectResultMapperForList(T entity) throws Exception{
		String resultMapper = EntityUtils.getResultMapperByClass(entity.getClass());
		String sql = "SELECT "+ resultMapper + JdbcUtils.getSelectSQL(entity);
		Map<String, Object> paramMap = EntityUtils.getParamMapByEntity(entity, true);
		
		SqlParameterSource sps = new MapSqlParameterSource(paramMap);
		
		return (List<T>) getReadJdbc().query(sql, sps, BeanPropertyRowMapper.newInstance(entity.getClass()));
	}
	
	
	private String getDbId(){
		return UUID.randomUUID().toString().replace("_", "");
	}
}
