package com.qianfeng.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qianfeng.entity.Books;
import com.qianfeng.service.IBookService;
import com.qianfeng.vo.JsonBean;
import com.qianfeng.vo.PageBean;

@Controller
public class BookController {
	@Autowired
	private IBookService bookService;
	
	@RequestMapping(value="/books/page/{page}", method=RequestMethod.GET)
	public @ResponseBody JsonBean findByPage(@PathVariable("page") Integer page) {
		// @PathVariable("page") 为从路径中取对应变量的值
		
		JsonBean bean = new JsonBean();
		try {
			PageBean<Books> infos = bookService.findByPage(page);
			bean.setCode(1);
			bean.setMsg(infos);
		}catch(Exception e) {
			bean.setCode(0);
			bean.setMsg(e.getMessage());
		}
		return bean;	
	}
}
