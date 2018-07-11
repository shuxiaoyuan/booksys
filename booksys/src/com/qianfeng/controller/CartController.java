package com.qianfeng.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qianfeng.entity.Books;
import com.qianfeng.service.ICartService;
import com.qianfeng.vo.JsonBean;

@Controller
public class CartController {
	@Autowired
	private ICartService cartService;
	
	// 向购物车添加数据，将数据放到cookie中
	@RequestMapping(value="/carts", method=RequestMethod.POST)// @CookieValue("cartids") 读取指定的cookie
	public @ResponseBody JsonBean addCart(String[] bookIds, @CookieValue(value="cartids", defaultValue="") String cartId, HttpServletResponse response) {
		JsonBean bean = new JsonBean();
		try {
			String info = cartService.addCart(bookIds, cartId);
			Cookie cookie = new Cookie("cartids", info);
			cookie.setMaxAge(30 * 24 * 3600);
			response.addCookie(cookie);		
			 
			bean.setCode(1);
		}catch(Exception e) {
			bean.setMsg(e.getMessage());
		}
		
		return bean;
	}
	
	@RequestMapping(value="/carts", method=RequestMethod.GET)
	public @ResponseBody JsonBean carts(@CookieValue("cartids") String cartId) {
		
		JsonBean bean = new JsonBean();
		try {
			List<Books> infos = cartService.findCartInfo(cartId);
			bean.setMsg(infos);
			bean.setCode(1);
		}catch(Exception e) {
			bean.setCode(0);
			bean.setMsg(e.getMessage());
		}
		
		return bean;
		
	}
	
}
