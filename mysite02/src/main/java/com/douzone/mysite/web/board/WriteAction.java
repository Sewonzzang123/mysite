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

public class WriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 접근제어(인증이 필요한 접근에 대한 체크)
		HttpSession session = request.getSession();
		if(session == null){
			MvcUtils.redirect(request.getContextPath(), request, response);
			return;
		}
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			MvcUtils.redirect(request.getContextPath(), request, response);
			return;
		}

		Long userNo = authUser.getNo();	
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		int groupNo = new BoardRepository().findMaxGroupNo();
		
		BoardVo vo = new BoardVo();
		vo.setTitle(title);
		vo.setContent(content);
		vo.setUserNo(userNo);
		vo.setGroupNo(groupNo+1);
		vo.setOrderNo(0);
		vo.setDepth(0);
		
		new BoardRepository().insert(vo);
		
		MvcUtils.redirect(request.getContextPath()+"/board?a=list", request, response);
	}

}
