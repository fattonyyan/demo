package com.yan.project.core.persistence.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public abstract @interface Table {

	/*
	 * 用于持久化的注解，对应表的实体类型 
	 */
	/* 表名 */
	public abstract String name() default "";
	
	/* 基本的返回列 */
	public abstract String resultMap() default "";
}
