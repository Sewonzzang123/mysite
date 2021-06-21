package com.douzone.mysite.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.GalleryVo;

@Repository
public class GalleryRepository {
	
	@Autowired
	private SqlSession sqlSession;

	public List<GalleryVo> findAll() {
		return sqlSession.selectList("gallery.findAll");
	}

	public boolean delete(Long no) {
		return sqlSession.delete("gallery.deleteByNo", no)==1;
		
	}

	public boolean insert(GalleryVo vo) {
		return sqlSession.insert("gallery.insert", vo)==1;
	}

	public List<GalleryVo> findByPage(int page) {
		page = (page-1)*15;
		return sqlSession.selectList("gallery.findByPage", page);
		
	}

	public int findTotalPage() {
		return sqlSession.selectOne("gallery.findTotalPage");
	}
}
