package com.douzone.mysite.controller.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douzone.mysite.service.UserService;
import com.douzone.mysite.vo.UserVo;

// UserController가 두개여서 servlet에서 읽지 못하기 때문에 id를설정해준다
@Controller("userControllerApi")
@RequestMapping("/user/api")
public class UserController {

	@Autowired
	private UserService userService;
	
	@ResponseBody
	@RequestMapping("/checkemail")
	public Object checkEmail(@RequestParam(
			value = "email", required = true, defaultValue = "") String email) {
		System.out.println("------------> "+email);
		UserVo userVo = userService.getUser(email);
		
		Map<String, Object> result = new HashMap<>();
		result.put("result", "success");
		result.put("exist", userVo!= null);
		
		return result;
	}
}
