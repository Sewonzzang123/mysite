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

public class UpdateAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 접근제어(인증이 필요한 접근에 대한 체크)
		HttpSession session = request.getSession();
		if (session == null) {
			MvcUtils.redirect(request.getContextPath(), request, response);
			return;
		}
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			MvcUtils.redirect(request.getContextPath(), request, response);
			return;
		}

		UserVo vo = new UserVo();

		String email = request.getParameter("email");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String gender = request.getParameter("gender");

		vo.setEmail(email);
		vo.setName(name);
		vo.setPassword(password);
		vo.setGender(gender);

		new UserRepository().update(vo);
		
		Long userNo = authUser.getNo();
		UserVo userVo = new UserRepository().updateAuthUser(userNo);
		System.out.println(userVo.toString());
		session.setAttribute("authUser", userVo);
		
		response.sendRedirect(request.getContextPath());
	}

}
