package com.qianfeng.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.qianfeng.entity.User;
import com.qianfeng.service.IAdminService;
import com.qianfeng.service.ILoginService;
import com.qianfeng.vo.JsonBean;

@Controller
public class LoginController {
	@Autowired
	private ILoginService loginService;
	
	@Autowired
	private IAdminService adminService;
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public @ResponseBody JsonBean login(@RequestParam("userName") String username, @RequestParam("passWord") String password, HttpSession session, HttpServletResponse response) {
		JsonBean bean = new JsonBean();
		try {
			loginService.login(username, password);
			// ��½�ɹ������û����浽session��
			session.setAttribute("loginname", username);
			String sessionId = session.getId();
			Cookie cookie = new Cookie("JSESSIONID", sessionId);
			cookie.setMaxAge(1800);
			cookie.setPath("/booksys/");
			response.addCookie(cookie);
			
			bean.setCode(1);
		}catch(Exception e) {
			bean.setCode(0);
			bean.setMsg(e.getMessage());
		}
		return bean;
	}
	
	@RequestMapping(value="/adminLogin", method=RequestMethod.POST)
	public @ResponseBody JsonBean adminLogin(@RequestParam("userName") String adminName, @RequestParam("passWord") String password, HttpSession session, HttpServletResponse response)
	{
		JsonBean bean = new JsonBean();
		
		try {
			adminService.login(adminName, password);
			
			session.setAttribute("adminname", adminName);
			String sessionId = session.getId();
			Cookie cookie = new Cookie("JSESSIONID", sessionId);
			cookie.setMaxAge(1800);
			response.addCookie(cookie);
			bean.setCode(1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bean.setCode(0);
			bean.setMsg(e.getMessage());
		}
		
		return bean;
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public @ResponseBody JsonBean register(User user) {
		JsonBean bean = new JsonBean();
		try {
			loginService.register(user);
			bean.setCode(1);
		}catch(Exception e) {
			bean.setCode(0);
			bean.setMsg(e.getMessage());
		}
		return bean;
	}
	
	@RequestMapping("/check")
	public @ResponseBody JsonBean check(String userName) {
		JsonBean bean = new JsonBean();
		try {
			boolean ret = loginService.userIsExist(userName);
			if(ret == true) {
				bean.setCode(-1);
			}else {
				bean.setCode(1);
			}
		}catch(Exception e) {
			bean.setCode(0);
			bean.setMsg(e.getMessage());
		}
		return bean;
	}
	
	@RequestMapping("/logout")
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
	    // �ɵ� session
	    HttpSession session = request.getSession();
	    session.invalidate();
	    
	    // �ɵ� cookie
        Cookie cookie = new Cookie("JSESSIONID", "");
        cookie.setMaxAge(0);
        cookie.setPath("/booksys/");
        response.addCookie(cookie);
        
        return new ModelAndView("redirect:login.html");
	    
	}
	
}
