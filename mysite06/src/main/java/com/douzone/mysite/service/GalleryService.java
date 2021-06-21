package com.douzone.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.GalleryRepository;
import com.douzone.mysite.vo.GalleryVo;

@Service
public class GalleryService {

	@Autowired
	private GalleryRepository galleryRepository;

	public boolean delete(Long no) {
		return galleryRepository.delete(no);

	}

	public boolean insert(GalleryVo vo) {
		return galleryRepository.insert(vo);

	}

	public List<GalleryVo> findByPage(int page) {
		return galleryRepository.findByPage(page);
	}

	private static final int LIST_SIZE = 16; // 리스팅되는 게시물의 수
	private static final int PAGE_SIZE = 5; // 페이지 리스트의 페이지 수

	public Map<String, Integer> pageInfo(int currentPage) {
		int totalCount = galleryRepository.findTotalPage();
		int pageCount = (int) Math.ceil((double) totalCount / LIST_SIZE);
		int blockCount = (int) Math.ceil((double) pageCount / PAGE_SIZE);
		int currentBlock = (int) Math.ceil((double) currentPage / PAGE_SIZE);

		if (currentPage > pageCount) {
			currentPage = pageCount;
			currentBlock = (int) Math.ceil((double) currentPage / PAGE_SIZE);
		}

		if (currentPage < 1) {
			currentPage = 1;
			currentBlock = 1;
		}

		// 3. view에서 페이지 리스트를 렌더링 하기위한 데이터 값 계산
		int beginPage = currentBlock == 0 ? 1 : (currentBlock - 1) * PAGE_SIZE + 1;
		int prevPage = currentPage-1;
		int nextPage = currentPage+1;
		int endPage = (nextPage > 0) ? (beginPage - 1) + LIST_SIZE : pageCount;

		Map<String, Integer> map = new HashMap<>();

		map.put("firstPageNo", beginPage);
		map.put("lastPageNo", endPage);
		map.put("nextPageNo", nextPage);
		map.put("prevPageNo", prevPage);
		map.put("currentPage", currentPage);
		map.put("totalPage", endPage);

		return map;
	}
}
