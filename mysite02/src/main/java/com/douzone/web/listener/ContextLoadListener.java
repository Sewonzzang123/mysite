package com.douzone.web.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextLoadListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent sce) {
		ServletContext sc = sce.getServletContext();
		String contextConfigLocagion = sc.getInitParameter("contextConfigLocation");
		
		System.out.println("Application Starts...."+contextConfigLocagion);
	}

	public void contextDestroyed(ServletContextEvent sce) {
		
	}

}
