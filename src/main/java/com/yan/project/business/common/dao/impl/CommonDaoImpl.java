package com.yan.project.business.common.dao.impl;

import javax.annotation.Resource;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yan.project.business.common.dao.ICommonDao;
import com.yan.project.core.base.dao.impl.BaseDaoImpl;

@Repository("commonDao")
public class CommonDaoImpl extends BaseDaoImpl implements ICommonDao {

	@Resource
	NamedParameterJdbcTemplate jdbc;
	
	@Override
	public NamedParameterJdbcTemplate getJdbc() {
		return jdbc;
	}

}
