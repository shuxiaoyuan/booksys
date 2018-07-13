package com.qianfeng.dao;

import com.qianfeng.base.IBaseDao;
import com.qianfeng.entity.Admin;;

public interface IAdminDao extends IBaseDao<Admin>{
	
	public Admin findByName(String name);

}
