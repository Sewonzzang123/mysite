package com.douzone.mysite.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.douzone.mysite.security.Auth;
import com.douzone.mysite.service.UserService;
import com.douzone.mysite.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String join() {
		return "user/joinform";
	}

	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String join(UserVo vo) {
		userService.join(vo);
		return "redirect:/user/joinsuccess";
	}

	@RequestMapping(value = "/joinsuccess", method = RequestMethod.GET)
	public String joinSuccess() {
		return "user/joinsuccess";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "user/login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(
			HttpSession session,
			@RequestParam(value = "email", required = true, defaultValue = "") String email,
			@RequestParam(value = "password", required = true, defaultValue = "") String password,
			Model model) {
		
		UserVo authUser = userService.getUser(email,password);
		if(authUser==null) {
			model.addAttribute("result", "fail");
			model.addAttribute("email", email);
			return "user/login";
		}
		//session처리
		session.setAttribute("authUser", authUser);
		
		return "redirect:/";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		//접근 제어
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser==null) {
			return "redirect:/";
		}
		//로그아웃 처리
		session.removeAttribute("authUser");
		session.invalidate();
		return "redirect:/";
	}
	
	@Auth
	@RequestMapping(value="/update", method=RequestMethod.GET)
	public String update(HttpSession session, Model model) {
		//접근 제어
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser==null) {
			return "redirect:/";
		}
		//접근제어와 함께 session의 no값을 필요로 하기 때문에 빼 낼수 없다		
		//==>인터셉터, argument resolver, annotation
		
		Long no = authUser.getNo();
		UserVo vo = userService.getUser(no);
		model.addAttribute("user", vo);
		return "user/update";
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String update(UserVo userVo, HttpSession session) {
		//접근 제어
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser==null) {
			return "redirect:/";
		}
		userVo.setNo(authUser.getNo());		
		userService.updateUser(userVo);		
		
		return "redirect:/user/update";
	}

}
