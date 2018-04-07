package com.yan.project.core.util;

import java.io.Serializable;
import java.util.Map;

public class ResponseData implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String SUCCESS = "SUCCESS";
	private static final String WARNING = "WARNING";
	private static final String ERROR = "ERROR";
	
	private String statusCode = "200";
	
	private String status;
	
	private String message;
	
	private Object data;
	
	private Map<String, Object> map;
	
	private Page page;
	
	private boolean success = false;

	public ResponseData(String status){
		this.status = status;
	}
	public ResponseData(String status, String message){
		this.status = status;
		this.message = message;
	}
	public ResponseData(String status, String message, Object data){
		this.status = status;
		this.message = message;
		this.data = data;
	}
	
	
	public static ResponseData getSuccessInstance(){
		ResponseData repsonseData = new ResponseData(ResponseData.SUCCESS);
		repsonseData.setSuccess(true);
		return repsonseData;
	}
	public static ResponseData getWarningInstance(){
		ResponseData repsonseData = new ResponseData(ResponseData.WARNING);
		repsonseData.setStatusCode("404");
		return repsonseData;
	}
	public static ResponseData getErrorInstance(){
		ResponseData repsonseData = new ResponseData(ResponseData.ERROR);
		repsonseData.setStatusCode("500");
		return repsonseData;
	}
	
	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	
}
