package com.douzone.mysite.security;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.douzone.mysite.service.UserService;
import com.douzone.mysite.vo.UserVo;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private UserService userService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		UserVo authUser = userService.getUser(email, password);
		if(authUser ==null) {
			request.setAttribute("email", email);
			request.setAttribute("result", "fail");
			RequestDispatcher view= request.getRequestDispatcher("/WEB-INF/views/user/login.jsp");
			view.forward(request, response);
			return false;
		}
		//authUser가 있음 >>session처리
//		System.out.println(authUser);
		HttpSession session = request.getSession(true);
		session.setAttribute("authUser", authUser);
		response.sendRedirect(request.getContextPath());
		return false;
	}
}
