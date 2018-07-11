package com.qianfeng.dao;

import java.util.List;
import java.util.Map;

import com.qianfeng.base.IBaseDao;
import com.qianfeng.entity.Books;

public interface IBookDao extends IBaseDao<Books>{
	
	public List<Books> findByIndex(Map<String, Object> pageInfo);
	
	public List<Books> findByIds(List<String> ids);
	
}
