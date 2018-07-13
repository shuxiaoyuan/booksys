package com.qianfeng.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qianfeng.dao.IAdminDao;
import com.qianfeng.entity.Admin;
import com.qianfeng.service.IAdminService;
import com.qianfeng.util.StringUtil;

@Service
public class AdminService implements IAdminService{

	@Autowired
	private IAdminDao adminDao;
	
	@Override
	public void login(String name, String password) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				if(StringUtil.isNullOrEmpty(name)) {
					throw new RuntimeException("����Ա����Ϊ��");
				}
				if(StringUtil.isNullOrEmpty(password)) {
					throw new RuntimeException("����Ϊ��");
				}
				try{
					Admin admin = adminDao.findByName(name);		
					if(admin == null) {
						throw new RuntimeException("����Ա����������");
					}else {
						if(!admin.getPassword().equals(password)) {
							throw new RuntimeException("�������");
						}
					}
				}catch(Exception e){
					throw new RuntimeException(e.getMessage());
				}
	}

}
