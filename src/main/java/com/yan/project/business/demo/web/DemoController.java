package com.yan.project.business.demo.web;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yan.project.business.demo.dto.DemoDTO;
import com.yan.project.business.demo.entity.Demo;
import com.yan.project.business.demo.service.IDemoService;
import com.yan.project.core.base.web.BaseConrtroller;
import com.yan.project.core.util.ResponseData;

@Controller
@RequestMapping("/demo")
public class DemoController extends BaseConrtroller {
	
	static Logger logger = LoggerFactory.getLogger(DemoController.class);
	
	@Resource
	private IDemoService demoService;

	@RequestMapping("/save")
	@ResponseBody
	public ResponseData saveDemo(DemoDTO data){
		/*
		 * 1. 进行数据有效性验证
		 * 2. 将数据转化为实体类
		 * 3. 调用 Service 层API 
		 * 4. 返回结果 
		 */
		ResponseData responseData = ResponseData.getWarningInstance();
		boolean isExist;
		
		if(!data.getPassword().equals(data.getSecPassword())){
			logger.error("两次密码不相等");
			responseData.setMessage("信息输入错误");
			return responseData;
		}
		
		try {
			isExist =  demoService.checkIsExist(data.getName());
		} catch (Exception ex) {
			isExist = true;
			ex.printStackTrace();
			logger.error("demo 名称已存在", ex);
		}
		
		if(isExist){
			logger.error("demo 名称已存在");
			responseData.setMessage("名称已存在");
			return responseData;
		}
		
		Demo demo = new Demo();
		try {
			BeanUtils.copyProperties(demo, data);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
			logger.error("DTO data could not tranfer to Entity Data", e);
			responseData.setMessage("数据转换失败");
			return responseData;
		}
		
		try {
			demoService.saveDemo(demo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("NameParameterJdbcTemplate#update is error", e);
			return ResponseData.getErrorInstance();
		}
		
		return ResponseData.getSuccessInstance();
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public ResponseData updateDemo(DemoDTO data){
		
		ResponseData responseData = ResponseData.getWarningInstance();
		
		if(data.getId() == null){
			responseData.setMessage("信息不足");
			return responseData;
		}
		
		Demo demo = new Demo();
		try {
			BeanUtils.copyProperties(demo, data);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
			logger.error("DTO data could not tranfer to Entity Data", e);
			responseData.setMessage("数据转换失败");
			return responseData;
		}
		
		try {
			demoService.updateDemo(demo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("NameParameterJdbcTemplate#update is error", e);
			return ResponseData.getErrorInstance();
		}
		
		return ResponseData.getSuccessInstance();
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public ResponseData deleteDemo(DemoDTO data){
		ResponseData responseData = ResponseData.getWarningInstance();
		
		if(data.getId() == null){
			responseData.setMessage("信息不足");
			return responseData;
		}
		
		Demo demo = new Demo();
		try {
			BeanUtils.copyProperties(demo, data);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
			logger.error("DTO data could not tranfer to Entity Data", e);
			responseData.setMessage("数据转换失败");
			return responseData;
		}
		
		try {
			demoService.deleteDemo(demo, true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("NameParameterJdbcTemplate#update is error", e);
			return ResponseData.getErrorInstance();
		}
		
		return ResponseData.getSuccessInstance();
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public ResponseData listDemo(){
		ResponseData responseData;

		List<Demo> results;
		
		try {
			results = demoService.selectDemoForList();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("NameParameterJdbcTemplate#update is error", e);
			return ResponseData.getErrorInstance();
		}
		
		responseData = ResponseData.getSuccessInstance();
		responseData.setData(results);
		return responseData;
	}
	
	@RequestMapping("/select")
	@ResponseBody
	public ResponseData selectByInfo(DemoDTO data){
		ResponseData responseData;
		
		Demo demo = new Demo();
		try {
			BeanUtils.copyProperties(demo, data);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
			logger.error("DTO data could not tranfer to Entity Data", e);
			responseData = ResponseData.getWarningInstance();
			responseData.setMessage("数据转换失败");
			return responseData;
		}
		
		List<Demo> results;
		try {
			results = demoService.selectDemo(demo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("NameParameterJdbcTemplate#update is error", e);
			return ResponseData.getErrorInstance();
		}
		responseData = ResponseData.getSuccessInstance();
		responseData.setData(results);
		return responseData;
	}
	
	@RequestMapping("/age")
	@ResponseBody
	public ResponseData getDemoByAge(HttpServletRequest request){
		String ageStr = request.getParameter("age");
		
		int age = Integer.valueOf(ageStr);
		if(age <= 0){
			return ResponseData.getWarningInstance();
		}
		
		List<Demo> results = demoService.selectDemoByAge(age);
		
		ResponseData responseData = ResponseData.getSuccessInstance();
		responseData.setData(results);
		return responseData;
	}
}
