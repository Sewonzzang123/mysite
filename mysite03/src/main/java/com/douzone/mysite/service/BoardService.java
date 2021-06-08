package com.douzone.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;

@Service
public class BoardService {
	@Autowired
	private BoardRepository boardRepository;

	public List<BoardVo> findByPage(String search, String keyword,int pageNum) {
		List<BoardVo> list = boardRepository.findByPage(search,keyword,pageNum);
		return list;
	}

	public Map<String, Integer> pageInfo(String search,String keyword, String page) {
		int currentPage = Integer.parseInt(page);
		double totalPage = 1;
		int index=0;
		if ("".equals(keyword)) {
			if (Math.ceil(boardRepository.findTotalPage() / 5) != 0) {
				totalPage = Math.ceil(boardRepository.findTotalPage() / 5);
				}
			 index = (int) boardRepository.findTotalPage();
		} else {
			if (Math.ceil(boardRepository.findTotalPage(search,keyword) / 5) != 0) {
				totalPage = Math.ceil(boardRepository.findTotalPage(search,keyword) / 5);
				}
			index = (int) boardRepository.findTotalPage(search,keyword);
		}
		int lastPageNo = 1;
		if ((int) Math.ceil((double) currentPage / 5) * 5 != 0) {
			lastPageNo = (int) Math.ceil((double) currentPage / 5) * 5;
		}
		int firstPageNo = 1;
		if (lastPageNo > 4) {
			firstPageNo = lastPageNo - 4;
		}

		Map<String, Integer> map = new HashMap<>();

		map.put("firstPageNo", firstPageNo);
		map.put("lastPageNo", lastPageNo);
		map.put("nextPageNo", currentPage + 1);
		map.put("prevPageNo", currentPage - 1);
		map.put("currentPage", currentPage);
		map.put("totalPage", (int) totalPage);
		map.put("totalCount", index);

		return map;
	}

	public BoardVo view(Long boardNo) {
		boardRepository.updateHit(boardNo);
		return boardRepository.findByNo(boardNo);
	}

	public int findMaxGroupNo() {
		return boardRepository.findMaxGroupNo();
	}

	public Long insert(BoardVo vo) {
		return boardRepository.insert(vo);

	}

	public BoardVo findByNo(Long no) {
		return boardRepository.findByNo(no);
	}

	public boolean update(BoardVo vo) {
		return boardRepository.update(vo) == 1;
	}

	public boolean reply(int groupNo, int orderNo) {
		return boardRepository.reply(groupNo, orderNo);

	}

	public boolean delete(Long no) {
		boardRepository.delete(no);
		return boardRepository.updateDelete(no);
	}


}
