package com.douzone.mysite.web.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.web.Action;
import com.douzone.web.util.MvcUtils;

public class SearchAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String search = request.getParameter("kwd");
		if("".equals(search)) {
			MvcUtils.forward("board/list", request, response);
			return;
		}
		
		List<BoardVo> list = new BoardRepository().search(search);
		request.setAttribute("boardList", list);
		
		MvcUtils.forward("board/list", request, response);
	}

}
