package com.douzone.mysite.repository;


import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.GuestbookVo;

@Repository
public class GuestbookRepository {
	
	@Autowired
	private SqlSession sqlSession;

	public Long insert(GuestbookVo vo) {
		sqlSession.insert("guestbook.insert", vo);
		return vo.getNo();
	}

	public List<GuestbookVo> findAll() {
		List<GuestbookVo> result = sqlSession.selectList("guestbook.findAll");
		return result;
	}
	
	public List<GuestbookVo> findAll(Long no) {
		List<GuestbookVo> result = sqlSession.selectList("guestbook.findAllByNo", no);
		return result;
	}

	public boolean delete(Long no, String password) {
		GuestbookVo vo= new GuestbookVo();
		vo.setNo(no);
		vo.setPassword(password);
		int count = sqlSession.delete("guestbook.delete", vo);
		return count==1;

	}

	public Boolean hasNext(Long no) {
		return sqlSession.selectList("guestbook.findAllByNo", no).size()!=0 ? true : false;
	}
}
