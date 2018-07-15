package com.qianfeng.dao;

import com.qianfeng.base.IBaseDao;
import com.qianfeng.entity.User;

public interface IUserDao extends IBaseDao<User> {
	
//	public User findById();
	
	public User findByName(String name);
	
	public int isLock(String name);
	
	public void updateIsLock(User user);
	
}
