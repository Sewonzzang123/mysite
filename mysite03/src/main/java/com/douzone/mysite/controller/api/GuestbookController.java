package com.douzone.mysite.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douzone.mysite.dto.JsonResult;
import com.douzone.mysite.service.GuestbookService;
import com.douzone.mysite.vo.GuestbookVo;
import com.fasterxml.jackson.databind.node.POJONode;

@Controller("guestbookControllerApi")
@RequestMapping("/guestbook/api")
public class GuestbookController {
	@Autowired
	private GuestbookService guestbookService;
	
	@ResponseBody
	@RequestMapping("/list")
	public JsonResult list(@RequestParam Long no) {
		List<GuestbookVo> list = guestbookService.getMessageList(no);		
		return JsonResult.success(list);
	}
	
	@ResponseBody
	@RequestMapping("/add")
	public JsonResult add(@RequestBody GuestbookVo vo) {
		Long no = guestbookService.addMessage(vo);
		vo.setNo(no);
		vo.setPassword("");
		return JsonResult.success(vo);
	}
	
	@ResponseBody
	@RequestMapping("/delete/{no}")
	public JsonResult delete(@PathVariable("no") Long no, @RequestParam String password) {
		boolean result = guestbookService.deleteMessage(no, password);
		return JsonResult.success( result == true ? no: -1);
	}
	
	
	
}
