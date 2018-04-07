package com.yan.project.core.base.service;

public interface IBaseService {

	
}


/*
 * 20180401- 注释作废
 * 原本这个接口的作用是用来承接来自 BaseDao 的依赖注入，
 * 方便其他 应用层Service 使用 数据库API 。
 * 目前可以直接 定义 ICommonDao 进行依赖注入并直接使用数据库操作 API
 */