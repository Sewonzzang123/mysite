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
	
	@Autowired
	private DataSource dataSource;

	public boolean insert(GuestbookVo vo) {
		System.out.println(vo);
		int count = sqlSession.insert("guestbook.insert", vo);
		System.out.println(vo);
		return count==1;
	}

	public List<GuestbookVo> findAll() {
		List<GuestbookVo> result = sqlSession.selectList("guestbook.findAll");
		return result;
	}

	public boolean delete(Long no, String password) {
		GuestbookVo vo= new GuestbookVo();
		vo.setNo(no);
		vo.setPassword(password);
		int count = sqlSession.delete("delete", vo);
		return count==1;

	}
}
