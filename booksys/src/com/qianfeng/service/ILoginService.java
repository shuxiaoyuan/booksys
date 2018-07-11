package com.qianfeng.service;

import com.qianfeng.entity.User;

public interface ILoginService {

	// µÇÂ¼
	public void login(String name, String password);
	
	// ×¢²á
	public void register(User user);
	
	public boolean userIsExist(String name);
}
