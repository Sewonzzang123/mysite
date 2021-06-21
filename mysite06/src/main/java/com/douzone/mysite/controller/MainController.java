package com.douzone.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douzone.mysite.service.SiteService;
import com.douzone.mysite.vo.SiteVo;
import com.douzone.mysite.vo.UserVo;

@Controller
public class MainController {

	@Autowired
	private SiteService siteService;

	@RequestMapping("")
	public String main(Model model) {
		SiteVo vo = siteService.getSite();
		model.addAttribute("siteVo", vo);
		return "main/index";
	}

	
//	No converter found for return value of type
//	string은 바꾸는 message converter는 있지만 
//	other object는 없기 때문에 설정을 해 줘야 해
//	@ResponseBody
//	@RequestMapping("/hello")
//	public UserVo hello() {
//		UserVo vo = new UserVo();
//		vo.setNo(10L);
//		vo.setEmail("jangsewon@email.com");
//		return vo;
//	}
	
//	spring-servlet.xml에서 messageConverter bean설정
	@ResponseBody
	@RequestMapping("/msg1")
	public String message1() {
		return "안녕~~~~~~~~~~~~~";
	}
	
	
	@ResponseBody
	@RequestMapping("/msg2")
	public Object message2() {
		UserVo vo = new UserVo();
		vo.setNo(10L);
		vo.setName("장세원");
		vo.setEmail("jangsewon@email.com");
		return vo;
	}
	
	
}
