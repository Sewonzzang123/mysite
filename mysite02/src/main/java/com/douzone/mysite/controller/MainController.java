package com.douzone.mysite.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.web.main.MainActionFactory;
import com.douzone.mysite.web.user.UserActionFactory;
import com.douzone.web.Action;
import com.douzone.web.util.MvcUtils;

public class MainController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		System.out.println("MainController.init() called");
		String configPath = getServletConfig().getInitParameter("config");
		System.out.println(configPath);
		super.init();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		EncodingFilter에서 구현
//		request.setCharacterEncoding("utf-8");
		String actionName = request.getParameter("a");
		
		Action action = new MainActionFactory().getAction(actionName);
		action.execute(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
