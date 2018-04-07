package com.yan.project.business.demo.service.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yan.project.business.common.dao.ICommonDao;
import com.yan.project.business.demo.dao.IDemoDao;
import com.yan.project.business.demo.entity.Demo;
import com.yan.project.business.demo.service.IDemoService;
import com.yan.project.core.base.service.impl.BaseServiceImpl;

@Service("demoService")
/* 是否使用 @Transactional */
public class DemoServiceImpl extends BaseServiceImpl implements IDemoService {
	
	private static Logger logger = LoggerFactory.getLogger(DemoServiceImpl.class);
	
	@Resource
	private ICommonDao commonDao;
	
	@Autowired
	private IDemoDao demoDao;
	
	public int saveDemo(Demo demo) throws Exception{
		Timestamp creatTime = new Timestamp(System.currentTimeMillis());
		demo.setCreateTime(creatTime);
		return commonDao.save(demo);
	}
	
	public int updateDemo(Demo demo) throws Exception{
		Timestamp modifyTime = new Timestamp(System.currentTimeMillis());
		demo.setModifyTime(modifyTime);
		return commonDao.update(demo);
	}
	
	public void deleteDemo(Demo demo) throws Exception {
		deleteDemo(demo, false);
	}
	
	public void deleteDemo(Demo demo, boolean deleteReal) throws Exception{
		if(!deleteReal){
			demo.setDeleteFlag(0);
			commonDao.update(demo);
		} else {
			logger.info("delete Demo#"+demo.getId());
			commonDao.DeleteById(demo.getId(), Demo.class);
		}
	}
	
	public List<Demo> selectDemo(Demo demo) throws Exception{
		return commonDao.selectAllForList(demo);
	}
	
	public List<Demo> selectDemoByAge(int age){
		return demoDao.selectDemoByAge(age);
	}

	public boolean checkIsExist(String name) throws Exception{
		if(name == null){
			return false;
		}
		Demo demo = new Demo();
		demo.setName(name);
		
		List<Demo> results = commonDao.selectResultMapperForList(demo);
		
		if(results.size()>0){
			return true;
		} else {
			return false;
		}
	}
	
	public List<Demo> selectDemoForList() throws Exception{
		return commonDao.selectForList(Demo.class);
	}
}
