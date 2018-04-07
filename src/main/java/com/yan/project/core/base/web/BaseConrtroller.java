package com.yan.project.core.base.web;

import java.beans.PropertyEditorSupport;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

public class BaseConrtroller {

	public final Logger logger = LoggerFactory.getLogger(getClass());
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport(){
			@Override
			public void setAsText(String text){
				text = text.trim();
				if(text!=null&&!"".equals(text)){
					try {
						if (text.length() <= 4) {
							setValue(new Date(new SimpleDateFormat("yyyy").parse(text).getTime()));
						}else  if (text.length() <= 7) {
							setValue(new Date(new SimpleDateFormat("yyyy-MM").parse(text).getTime()));
						}else if (text.length() <= 10) {
							setValue(new Date(new SimpleDateFormat("yyyy-MM-dd").parse(text).getTime()));
						} else {
							setValue(new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(text).getTime()));
						}
					} catch (ParseException ex) {
						IllegalArgumentException iae = new IllegalArgumentException(
								"Could not parse date: " + ex.getMessage());
						iae.initCause(ex);
						logger.error(ex.getMessage(), ex);
						throw iae;
					}
				} else {
					setValue(null);
				}
			}
			
		});
		
		binder.registerCustomEditor(String.class, new PropertyEditorSupport(){
			@Override
			public void setAsText(String text){
				setValue(text == null ? null : StringEscapeUtils.escapeHtml4(text.trim()));
			}
			@Override
			public String getAsText(){
				Object value = getValue();
				return value != null ? value.toString() : "";
			}
		});
		
		binder.registerCustomEditor(Integer.class, new PropertyEditorSupport(){
			public void setAsText(String text){
				try {
					if(text!=null&&!"".equals(text)){
						setValue(Integer.valueOf(text));
					} else {
						setValue(null);
					}
				} catch (NumberFormatException e) {
					setValue(null);
					logger.error(e.getMessage(), e);
				}
			}
		});
		
		binder.registerCustomEditor(BigDecimal.class, new PropertyEditorSupport() {
			public void setAsText(String text) {
				try {
					if (text!=null&&!"".equals(text)) {
						setValue(new BigDecimal(text));
					} else {
						setValue(null);
					}
				} catch (Exception e) {
					// 为什么是 Exception 而不是具体的异常子类(是异常的类型很多吗，看下一个)
					setValue(null);
					logger.error(e.getMessage(), e);
				}
			}
		});
	}
}
