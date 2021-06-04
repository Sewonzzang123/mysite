package com.douzone.mysite.repository;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.UserVo;

@Repository
public class UserRepository {
	@Autowired
	private SqlSession sqlSession;

	public boolean insert(UserVo vo) {
		System.out.println(vo);
		int result = sqlSession.insert("user.insert", vo);
		return result==1;
	}

	public UserVo findByEmailAndPassword(String email, String password) {
		Map<String, String> map = new HashMap<>();
		map.put("email",email);
		map.put("password",password);
		UserVo result = sqlSession.selectOne("user.findByEmailAndPassword", map);
		return result;
	}

	public UserVo findByNo(Long userNo) {
		UserVo result = null;
		
		return result;

	}
	
	public boolean update(UserVo vo) {
		boolean result = false;

		return result;
	}
	public UserVo updateAuthUser(Long userNo) {
		UserVo result = null;
		
		return result;

	}
}
