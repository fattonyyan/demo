package com.yan.project.core.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.yan.project.core.base.entity.BaseEntity;

public class JdbcUtils {

	/*
	 * 支持 Spring JDBC 的工具类
	 * 仅仅是支持泛型的增删改以及批量操作，以及最基本的查询
	 * @author fattony
	 */
	/* insert into table (,,) values(,,) */
	private static final String SAVE = " INSERT INTO ";
	
	/* update table set(=,=,=) */
	private static final String ALTER = " UPDATE ";
	
	/* delete from table */
	private static final String DEL = " DELETE FROM ";
//	
//	/* select * from table where  */
//	private static final String SELECT = " SELECT ";
	
	private static final int DEFAULT_PAGE_SIZE = 100;
	
	
	/*
	 * 这里存在一个可以优化的点，也就是把 entity 的类型设置 为 <? extends BaseEntity>  
	 * 下面这种形式才是正确的。
	 *  <T extends BaseEntity> 这里是声明是 泛型
	 */
	public static final <T extends BaseEntity> String getSaveSQL(Class<T> clazz) throws Exception{
		StringBuilder sql =  new StringBuilder(SAVE);
		
		String tableName = EntityUtils.getTableNameByClass(clazz);
		List<String> fieldList = EntityUtils.getAllFieldNamesByClass(clazz);
		
		StringBuilder paramSQL = new StringBuilder(" ( ");
		StringBuilder valueSQL = new StringBuilder(" VALUES ( ");
		
		for(String field : fieldList){
			paramSQL.append(field).append(",");
			valueSQL.append(":").append(field).append(",");
		}
		String pstr = paramSQL.substring(0, paramSQL.length()-1) + ")";
		String vstr = valueSQL.substring(0, valueSQL.length()-1) + ")";
		sql.append(tableName).append(pstr).append(vstr);
		
		System.out.println(sql.toString());
		return sql.toString();
	}
	
	public static final <T extends BaseEntity> String getUpdateSQL(T entity, boolean onlyNotNull) throws Exception{
		StringBuilder sql = new StringBuilder(ALTER);
		Class<? extends BaseEntity> clazz = entity.getClass();
		String pkName = EntityUtils.getPKNameByClass(clazz);
		
		String tableName = EntityUtils.getTableNameByClass(clazz);
		List<String> fieldList = EntityUtils.getAllFieldNamesByClass(clazz);
		fieldList.remove(pkName);
		
		StringBuilder setSQL = new StringBuilder();
		String whereSQL = " WHERE " + pkName + " =: " + pkName;
		
		for(String field: fieldList){
			if(onlyNotNull){
				PropertyDescriptor pd = new PropertyDescriptor(field, clazz);
				Method getMethod = pd.getReadMethod();
				if(getMethod.invoke(entity) == null) continue;
			}
			setSQL.append(field).append(" =:").append(field).append(",");
		}
		
		/* 但是存在风险，那就是所有字段都没有存入。 */
		if(setSQL.equals("")){
			throw new Exception("could not update null");
		}
		
		String str = setSQL.substring(0, setSQL.length()-1);
		sql.append(tableName).append(" SET ").append(str).append(whereSQL);
		
		System.out.println(sql.toString());
		return sql.toString();
	}
	
	public static final <T extends BaseEntity> String getDeleteSQL(Class<T> clazz) throws Exception{
		StringBuilder sql = new StringBuilder(DEL);
		String tableName = EntityUtils.getTableNameByClass(clazz);
		String pkName = EntityUtils.getPKNameByClass(clazz);
		
		sql.append(tableName).append(" WHERE ").append(pkName).append(" =:").append(pkName);
		System.out.println(sql.toString());
		return sql.toString();
	}
	
	public static final <T extends BaseEntity> String getBatchDeleteSQL(Class<T> clazz) throws Exception{
		StringBuilder sql = new StringBuilder(DEL);
		String tableName = EntityUtils.getTableNameByClass(clazz);
		String pkName = EntityUtils.getPKNameByClass(clazz);
		
		sql.append(tableName).append(" WHERE ").append(pkName).append(" in (:").append(pkName).append(")");
		System.out.println(sql.toString());
		return sql.toString();
	}
	
	public static final <T extends BaseEntity> String getSelectSQL(T entity) throws Exception{
		return getSelectSQL(entity, 0, DEFAULT_PAGE_SIZE);
	}
	
	public static final <T extends BaseEntity> String getSelectSQL(T entity, int rows) throws Exception{
		return getSelectSQL(entity, 0, rows);
	}
	
	public static final <T extends BaseEntity> String getSelectSQL(T entity, int offest, int rows) throws Exception{
		StringBuilder sql = new StringBuilder(" FROM ");
		Class<? extends BaseEntity> clazz = entity.getClass();
		String tableName = EntityUtils.getTableNameByClass(clazz);
		Map<String, Object> paramMap = EntityUtils.getParamMapByEntity(entity, true);
		
		System.out.println(paramMap);
		
		sql.append(tableName).append(" WHERE ");
		Set<String> fieldSet = paramMap.keySet();
		for(String field : fieldSet){
			sql.append(field).append(" =:").append(field).append(" AND ");
		}
		sql.append(" 1=1 ");
		
		if(rows < 0){
			throw new Exception("ROWS can't be negative");
		}
		
		sql.append(" LIMIT ").append(offest).append(",").append(rows);
		System.out.println(sql.toString());
		return sql.toString();
	}
	
	public static final <T extends BaseEntity> String getListSelectSQL(Class<T> clazz) throws Exception{
		return getListSelectSQL(clazz, 0, DEFAULT_PAGE_SIZE);
	}
	
	public static final <T extends BaseEntity> String getListSelectSQL(Class<T> clazz, int rows) throws Exception{
		return getListSelectSQL(clazz, 0, rows);
	}
	
	public static final <T extends BaseEntity> String getListSelectSQL(Class<T> clazz, int offest, int rows) throws Exception{
		if(rows < 0){
			throw new Exception("ROWS can't be negative");
		}
		
		StringBuilder sql = new StringBuilder();
		sql.append(" FROM ").append(EntityUtils.getTableNameByClass(clazz))
		.append(" LIMIT ").append(offest).append(",").append(rows);
		
		return sql.toString();
	}
	
}
