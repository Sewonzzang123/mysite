package com.douzone.mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	private String path="/WEB-INF/views/";
	
	@RequestMapping("")
	public String main() {
		return path+"main/index.jsp";
	}
}
