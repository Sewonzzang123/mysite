package com.douzone.mysite.web.board;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.web.Action;
import com.douzone.web.util.MvcUtils;

public class ListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int currentPage = 0;
		String page = request.getParameter("p");
		if ("".equals(page) || page == null) {
			currentPage = 1;
		} else {
			currentPage = Integer.parseInt(page);
		}
		
		List<BoardVo> list =new BoardRepository().findByPage(currentPage);
		double totalPage = 1;
		if (Math.ceil(new BoardRepository().findTotalPage()/5) != 0) {
			totalPage = Math.ceil(new BoardRepository().findTotalPage()/5);
		};
		int index = (int)new BoardRepository().findTotalPage();
		int lastPageNo = 1;
		if((int)Math.ceil((double)currentPage/5)*5 != 0) {
			lastPageNo = (int)Math.ceil((double)currentPage/5)*5;
		}
		int firstPageNo = 1;
		if(lastPageNo > 4) {
			firstPageNo = lastPageNo-4; 
		}		

		Map<String, Integer> map = new HashMap<>();
		
		map.put("firstPageNo",firstPageNo);
		map.put("lastPageNo", lastPageNo);
		map.put("nextPageNo", currentPage+1);
		map.put("prevPageNo", currentPage-1);
		map.put("currentPage", currentPage);
		map.put("totalPage", (int)totalPage);
		
		request.setAttribute("pageInfo", map);
		request.setAttribute("boardList", list);
		request.setAttribute("totalCount", index);
		MvcUtils.forward("board/list", request, response);
	}

}
