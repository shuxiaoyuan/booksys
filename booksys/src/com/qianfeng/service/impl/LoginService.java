package com.qianfeng.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qianfeng.dao.IUserDao;
import com.qianfeng.entity.User;
import com.qianfeng.service.ILoginService;
import com.qianfeng.util.StringUtil;

@Service
public class LoginService implements ILoginService {

	@Autowired
	private IUserDao userDao;
	
	// 记录密码错误次数
	private static int wrongPasswordCount = 0;
	
	@Override
	public void login(String name, String password) {
		// TODO Auto-generated method stub
		if(StringUtil.isNullOrEmpty(name)) {
			throw new RuntimeException("用户名为空");
		}
		if(StringUtil.isNullOrEmpty(password)) {
			throw new RuntimeException("密码为空");
		}
		try{
			User user = userDao.findByName(name);		
			if(user == null) {
				throw new RuntimeException("用户名不存在");
			}else {
			    // 先判断是否账号被锁定
			    if(userIsLock(name)) {
                    throw new RuntimeException("您的账户已被锁定，请联系管理员解锁后再登录！");
                }
			    // 再判断密码是否错误
				if(!user.getPassWord().equals(password)) {
				    if(++wrongPasswordCount >= 3) {
				        // 锁定账号
				        user.setIslock(1);
				        lockUser(user);
				        // 要把密码错误次数重置为 0 ，不然管理员解锁之后只有一次输入机会
				        wrongPasswordCount = 0;
				        throw new RuntimeException("连续三次输错密码，账户已被锁定，请联系管理员解锁！");
				    }
				    else {
				        throw new RuntimeException("密码错误");
				    }
				}
				
				// 登录成功则重置密码错误次数为 0
				wrongPasswordCount = 0;
				
			}
		}catch(Exception e){
			throw new RuntimeException(e.getMessage());
		}
			
	}

	@Override
	public void register(User user) {
		// TODO Auto-generated method stub
		if(user == null) {
			throw new RuntimeException("用户信息不能为空");
		}
		if(!user.getPassWord().equals(user.getRePassWord())) {
			throw new RuntimeException("两次密码不一致");
		}
		try {
			userDao.add(user);
		}catch(Exception e) {
			throw e;
		}
		
	}
	
	@Override
	public boolean userIsExist(String name) {
		try {
			User user = userDao.findByName(name);
			if(user == null){
				return false;
			}else{
				return true;
			}
		}catch(Exception e) {
			throw e;
		}
	}

    @Override
    public boolean userIsLock(String name) {
        // TODO Auto-generated method stub
        
        // 按坏情况考虑
        int isLock = 1;
        try {
            isLock = userDao.isLock(name);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return isLock == 1;
    }

    @Override
    public void lockUser(User user) {
        // TODO Auto-generated method stub
        try {
            userDao.updateIsLock(user);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
