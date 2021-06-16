package com.douzone.mysite.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.mysite.security.Auth;
import com.douzone.mysite.service.FileUploadService;
import com.douzone.mysite.service.SiteService;
import com.douzone.mysite.vo.SiteVo;

@Auth(role="ADMIN")
@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private SiteService siteService;
	
	@Autowired
	private FileUploadService fileUploadService;
	
	@RequestMapping("")
	public String main(@ModelAttribute SiteVo vo,Model model) {
		vo = siteService.getSite();
		model.addAttribute("siteVo", vo);
		return "admin/main";
	}
	@RequestMapping(value="/main/update", method=RequestMethod.POST)
	public String updateMain(
			@ModelAttribute @Valid SiteVo vo,BindingResult result,
			@RequestParam("profileimage") MultipartFile profile,		
			Model model) {
		if(result.hasErrors()) {
			model.addAttribute("profileimage", siteService.getSite().getProfile());
			model.addAllAttributes(result.getModel());
			return "admin/main";
		}
		//profile을 살펴보기
		String url = fileUploadService.restore(profile);
		vo.setProfile(url);
		siteService.update(vo);
		return "redirect:/admin";
	}
	@RequestMapping("/guestbook")
	public String guestbook() {
		return "admin/guestbook";
	}
	@RequestMapping("/board")
	public String board() {
		return "admin/board";
	}
	@RequestMapping("/user")
	public String user() {
		return "admin/user";
	}
	//처리해야함
	@RequestMapping("/logout")
	public String logout() {
		return "main/index";
	}
	
	
}
