package com.yan.project.core.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yan.project.core.persistence.annotation.Id;
import com.yan.project.core.persistence.annotation.Table;
import com.yan.project.core.persistence.annotation.Transient;

public class EntityUtils {

	/*
	 * 对于实体类的工具类，使用反射技术，并获取了注解信息
	 * 
	 * @author fattony
	 */
	
	public static String getTableNameByClass(Class<?> clazz) throws Exception{
		Table table = (Table) clazz.getAnnotation(Table.class);
		String tableName = table.name();
		
		// 不知道这样抛出错误是否稳妥？
		// 是否需要一个判断字符串是否为空以及空字符串的 util ?
		if(tableName == null || "".equals(tableName)){
			throw new Exception("Cannot find the correspond Table of Entity.");
		}
		return tableName;
	}
	
	public static List<String> getAllFieldNamesByClass(Class<?> clazz) throws Exception{
		Field[] fieldNames = clazz.getDeclaredFields();
		List<String> fieldList = new ArrayList<String>();
		
		/* 需要不断遍历类型以及其父类链的属性。 */
		for(Field field : fieldNames){
			if(field.isAnnotationPresent(Transient.class)) continue;
			fieldList.add(field.getName());
		}
		
		return fieldList;
	}
	
	public static String getPKNameByClass(Class<?> clazz) throws Exception{
		/* 存在一个问题是多主键的问题，似乎是MySQL只能设置一个主键 */
		Field[] fieldNames = clazz.getDeclaredFields();
		for(Field field : fieldNames){
			if(field.isAnnotationPresent(Id.class)) {
				return field.getName();
			}
				
		}
		throw new Exception("Cannot find annoation of Id from Entity for the primary key.");
	}
	
	public static String getResultMapperByClass(Class<?> clazz){
		Table table = (Table) clazz.getAnnotation(Table.class);
		String resultMapper = table.resultMap();
		
		if(resultMapper == null ||"".equals(resultMapper)){
			resultMapper = " * ";
		}
		return resultMapper + " ";
	}
	
	public static Map<String, Object> getParamMapByEntity(Object entity, boolean onlyNotNull) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Class<? extends Object> clazz = entity.getClass();
		Field[] fieldNames = clazz.getDeclaredFields();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		for(Field field : fieldNames){
			String fieldName = field.getName();
			PropertyDescriptor pd = new PropertyDescriptor(fieldName, clazz);//第一个异常
			Method getMethod = pd.getReadMethod();
			Object value = getMethod.invoke(entity);//后面三个异常
			
			/* 考虑一个问题 value 为 "" 是否过滤 */
			if(onlyNotNull && value == null) 
				continue;
			
			paramMap.put(fieldName, value);
		}
		return paramMap;
	}
	
	public static Map<String, Object> getAllParamMapByEntity(Object entity) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException{
		return getParamMapByEntity(entity, false);
	}
	
	public static Object getFieldValueFromEntity(String fieldName, Object entity) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		PropertyDescriptor pd = new PropertyDescriptor(fieldName, entity.getClass());
		Method getMethod = pd.getReadMethod();
		
		return getMethod.invoke(entity);
	}
	
	public static void setFieldValueOnEntity(String fieldName, Object value, Object entity) throws Exception{
		PropertyDescriptor pd = new PropertyDescriptor(fieldName, entity.getClass());
		Method setMethod = pd.getWriteMethod();
		if(!value.getClass().isAssignableFrom(setMethod.getReturnType())){
			throw new Exception("The value was not corret Type correspond to the Field.");
		}
		setMethod.invoke(entity, value);
	}
}
