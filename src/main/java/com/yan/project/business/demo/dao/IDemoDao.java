package com.yan.project.business.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yan.project.business.demo.entity.Demo;

public interface IDemoDao {

	List<Demo> selectDemoByAge(@Param("ageLimit") int age);
}
