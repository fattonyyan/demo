package com.yan.project.core.persistence.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public abstract @interface Id {

	/*
	 * 持久化中，注解 ID 主键 
	 */
	public abstract String value() default "";
}
