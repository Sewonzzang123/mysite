package com.douzone.mysite.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.douzone.mysite.dto.JsonResult;
import com.douzone.mysite.service.UserService;
import com.douzone.mysite.vo.UserVo;

// UserController가 두개여서 servlet에서 읽지 못하기 때문에 id를설정해준다
@RestController("userControllerApi")
@RequestMapping("/user/api")
public class UserController {

	@Autowired
	private UserService userService;
	
//	@ResponseBody RestController를 써 주면 자동 responsebody
	@GetMapping("/checkemail")
	public JsonResult checkEmail(@RequestParam(value = "email", required = true, defaultValue = "") String email) {
		UserVo userVo = userService.getUser(email);
		boolean data = (userVo != null);
	
//		JsonResult result = JsonResult.success(data); >>문제없이 checkemail을 하였으므로 success
//		JsonResult result2 = JsonResult.fail("...."); >>globalExceptionHandler에서 fail을 준다.
		
		return JsonResult.success(data);
	}
}
