package com.qianfeng.service;

import java.util.List;

import com.qianfeng.entity.Books;

public interface ICartService {
	
	public String addCart(String[] bookIds, String cartId);
	
	public List<Books> findCartInfo(String ids);
}
