package com.qianfeng.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qianfeng.dao.IBookDao;
import com.qianfeng.entity.Books;
import com.qianfeng.service.ICartService;
import com.qianfeng.util.StringUtil;

@Service
public class CartService implements ICartService {
	
	@Autowired
	private IBookDao bookDao;
	
	@Override
	public String addCart(String[] bookIds, String cartId) {
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<>();
		if(!StringUtil.isNullOrEmpty(cartId)) {			
			String[] split = cartId.split(",");			
			for(String v : split) {
				list.add(v);
			}
		}
		
		if(bookIds == null || bookIds.length == 0) {
			throw new RuntimeException("没有剖选择图书");
		}
		
		String info = "";
		for(int i = 0; i < bookIds.length; i++) {
			if(!list.contains(bookIds[i])) {
				list.add(bookIds[i]);
			}
		}
		
		for(String v : list) {
			info += v + ",";
		}		
		info.substring(0, info.length() - 1); // 去掉尾逗号
		return info;
	}
	
	@Override
	public List<Books> findCartInfo(String ids) {
		// TODO Auto-generated method stub
		
		if(StringUtil.isNullOrEmpty(ids)) {
			throw new RuntimeException("购物车中没有数据");
		}
		try{
			List<String> list = new ArrayList<>();
			String[] split = ids.split(",");
			for(String info : split) {
				list.add(info);
			}
			List<Books> books = bookDao.findByIds(list);
			return books;
		}catch(Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

}
