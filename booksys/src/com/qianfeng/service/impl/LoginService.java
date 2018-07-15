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
	
	// ��¼����������
	private static int wrongPasswordCount = 0;
	
	@Override
	public void login(String name, String password) {
		// TODO Auto-generated method stub
		if(StringUtil.isNullOrEmpty(name)) {
			throw new RuntimeException("�û���Ϊ��");
		}
		if(StringUtil.isNullOrEmpty(password)) {
			throw new RuntimeException("����Ϊ��");
		}
		try{
			User user = userDao.findByName(name);		
			if(user == null) {
				throw new RuntimeException("�û���������");
			}else {
			    // ���ж��Ƿ��˺ű�����
			    if(userIsLock(name)) {
                    throw new RuntimeException("�����˻��ѱ�����������ϵ����Ա�������ٵ�¼��");
                }
			    // ���ж������Ƿ����
				if(!user.getPassWord().equals(password)) {
				    if(++wrongPasswordCount >= 3) {
				        // �����˺�
				        user.setIslock(1);
				        lockUser(user);
				        // Ҫ����������������Ϊ 0 ����Ȼ����Ա����֮��ֻ��һ���������
				        wrongPasswordCount = 0;
				        throw new RuntimeException("��������������룬�˻��ѱ�����������ϵ����Ա������");
				    }
				    else {
				        throw new RuntimeException("�������");
				    }
				}
				
				// ��¼�ɹ�����������������Ϊ 0
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
			throw new RuntimeException("�û���Ϣ����Ϊ��");
		}
		if(!user.getPassWord().equals(user.getRePassWord())) {
			throw new RuntimeException("�������벻һ��");
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
        
        // �����������
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
