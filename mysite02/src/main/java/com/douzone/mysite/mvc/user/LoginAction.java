package com.douzone.mysite.mvc.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mvc.Action;
import com.douzone.mvc.util.MvcUtils;
import com.douzone.mysite.repository.UserRepository;
import com.douzone.mysite.vo.UserVo;

public class LoginAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		UserVo userVo = new UserRepository().findByEmailAndPassword(email, password);
		// 실패
		if (userVo == null) {
			request.setAttribute("result", "fail");
			request.setAttribute("email", email);
			MvcUtils.forward("user/loginform", request, response);
			return;
		}
		// 인증 처리(session 처리) 
//		true:없으면 만들어서 보내기 false:없으면 null
		HttpSession session =  request.getSession(true);
		session.setAttribute("authUser", userVo);
		
		MvcUtils.redirect(request.getContextPath(), request, response);
	}

}
