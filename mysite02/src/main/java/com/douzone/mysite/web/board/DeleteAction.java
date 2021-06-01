package com.douzone.mysite.web.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.UserVo;
import com.douzone.web.Action;
import com.douzone.web.util.MvcUtils;

public class DeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String p = request.getParameter("p");
		if ("".equals(p) || p == null) {
			p = "1";
		}
		
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
		
		Long no = Long.valueOf(request.getParameter("no"));
		new BoardRepository().delete(no);
		new BoardRepository().updateDelete(no);
		
		MvcUtils.redirect(request.getContextPath()+"/board?a=list&p="+p, request, response);
		
	}

}
