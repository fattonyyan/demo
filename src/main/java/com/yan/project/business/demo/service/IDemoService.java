package com.yan.project.business.demo.service;

import java.util.List;

import com.yan.project.business.demo.entity.Demo;
import com.yan.project.core.base.service.IBaseService;

public interface IDemoService extends IBaseService {

	int saveDemo(Demo demo) throws Exception;
	
	int updateDemo(Demo demo) throws Exception;
	
	void deleteDemo(Demo demo) throws Exception;
	
	void deleteDemo(Demo demo, boolean deleteReal) throws Exception;
	
	List<Demo> selectDemo(Demo demo) throws Exception;
	
	List<Demo> selectDemoByAge(int age);
	
	boolean checkIsExist(String name) throws Exception;
	
	List<Demo> selectDemoForList() throws Exception;
}
