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

public class ReplyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String p = request.getParameter("p");
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
		
		int groupNo = Integer.parseInt(request.getParameter("groupNo"));
		int orderNo = Integer.parseInt(request.getParameter("orderNo"));
		int depth = Integer.parseInt(request.getParameter("depth"));
		Long parentNo = Long.parseLong(request.getParameter("no"));
		new BoardRepository().reply(groupNo, (orderNo+1));
		
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		BoardVo vo = new BoardVo();
		vo.setUserNo(authUser.getNo());
		vo.setTitle(title);
		vo.setContent(content);
		vo.setGroupNo(groupNo);
		vo.setOrderNo(orderNo+1);
		vo.setDepth(depth+1);
		vo.setParentNo(parentNo);
		
		new BoardRepository().insert(vo);
		
		MvcUtils.redirect(request.getContextPath()+"/board?a=list&p="+p, request, response);
		
	}

}
