package com.qianfeng.service;

import com.qianfeng.entity.User;

public interface ILoginService {

	// 登录
	public void login(String name, String password);
	
	// 注册
	public void register(User user);
	
	public boolean userIsExist(String name);
	
	public boolean userIsLock(String name);
	
	// 锁定用户账户
	public void lockUser(User user);
	
}
