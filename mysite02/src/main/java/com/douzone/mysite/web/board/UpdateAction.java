package com.douzone.mysite.web.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;
import com.douzone.web.Action;
import com.douzone.web.util.MvcUtils;

public class UpdateAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String p = request.getParameter("p");
		// 접근제어(인증이 필요한 접근에 대한 체크)
		HttpSession session = request.getSession();
		if (session == null) {
			MvcUtils.redirect(request.getContextPath(), request, response);
			return;
		}
		Long userNo = Long.valueOf(request.getParameter("userNo"));
		
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null || authUser.getNo()!= userNo) {
			MvcUtils.redirect(request.getContextPath(), request, response);
			return;
		}
		
		Long no = Long.valueOf(request.getParameter("no"));
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		BoardVo vo = new BoardVo();
		
		vo.setNo(no);
		vo.setTitle(title);
		vo.setContent(content);
		
		new BoardRepository().update(vo);
		
		MvcUtils.redirect(request.getContextPath()+"/board?a=view&no="+no+"&p="+p, request, response);
		

	}

}
