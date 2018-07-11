package com.qianfeng.service;


import com.qianfeng.entity.Books;
import com.qianfeng.vo.PageBean;

public interface IBookService {
	
	public PageBean<Books> findByPage(Integer page);
}
