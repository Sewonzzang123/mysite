package com.douzone.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.BoardVo;

@Repository
public class BoardRepository {
	
	@Autowired
	private SqlSession sqlSession;

	public List<BoardVo> findByPage(String search, String keyword, int pageNum) {
		Map<String, String> map = new HashMap<>();
		map.put("keyword", keyword);
		pageNum = ((pageNum - 1) * 5);
		map.put("pageNum", String.valueOf(pageNum));
		map.put("search", search);
		return sqlSession.selectList("board.findByPage", map);
		
	}

	public double findTotalPage() {		
		return sqlSession.selectOne("board.findTotalPage");
	}

	public int findMaxGroupNo() {
		return sqlSession.selectOne("board.findMaxGroupNo");
	}

	public Long insert(BoardVo vo) {
		sqlSession.insert("board.insert", vo);
		return vo.getNo();
				
	}

	public BoardVo findByNo(Long boardNo) {
		return sqlSession.selectOne("board.findByNo", boardNo);

	}

	public int updateHit(Long boardNo) {
		return sqlSession.update("board.updateHit", boardNo);
	}

	public int update(BoardVo vo) {
		return sqlSession.update("board.update", vo);
		
	}

	public boolean delete(Long no) {
		return sqlSession.delete("board.delete", no)==1;
		
	}

	public boolean reply(int groupNo, int orderNo) {
		Map<String, Integer> map = new HashMap<>();
		map.put("groupNo", groupNo);
		map.put("orderNo", orderNo);
		return sqlSession.update("board.reply", map)==1;

	}

	public double findTotalPage(String search,String keyword) {
		Map<String, String> map = new HashMap<>();
		map.put("search", search);
		map.put("keyword", keyword);
		return sqlSession.selectOne("board.findTotalPageBySearch", map);
	}

	public boolean updateDelete(Long no) {
		return sqlSession.update("updateDelete", no)==1;
	}

}
