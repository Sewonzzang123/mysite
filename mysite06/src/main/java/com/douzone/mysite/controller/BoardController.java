package com.douzone.mysite.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.mysite.security.Auth;
import com.douzone.mysite.security.AuthUser;
import com.douzone.mysite.service.BoardService;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;

	@RequestMapping({ "", "/{p}","/search","/search/{p}"})
	public String list(@PathVariable(value = "p", required = false) String page, Model model,HttpServletRequest request) {
		String keyword = request.getParameter("kwd");
		String search = request.getParameter("search");
		if(keyword==null) {
			keyword="";
		}
		if (page == null) {
			page = "1";
		}	
		List<BoardVo> list = boardService.findByPage(search,keyword,Integer.parseInt(page));
		Map<String, Integer> map = boardService.pageInfo(search,keyword,page);
		model.addAttribute("pageInfo", map);
		model.addAttribute("list", list);
		model.addAttribute("kwd", keyword);
		model.addAttribute("search", search);
		return "board/list";
	}

	@RequestMapping(value={"/view/{no}/{p}","view/{no}"})
	public String view(@PathVariable(value = "no", required = true) Long boardNo,
			@PathVariable(value = "p", required = false) String page, Model model) {
		BoardVo vo = boardService.view(boardNo);
		model.addAttribute("board", vo);
		return "board/view";
	}

	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String writeform(@ModelAttribute BoardVo boardVo) {
		return "board/write";
	}


	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String write(
			@Valid @ModelAttribute BoardVo boardVo, 
			BindingResult result,
			@AuthUser UserVo authUser,
			Model model) {
		if(result.hasErrors()) {
				model.addAllAttributes(result.getModel());
			return "board/write";
		}
		int groupNo = boardService.findMaxGroupNo();
		
		Long userNo = authUser.getNo();
		boardVo.setUserNo(userNo);
		boardVo.setGroupNo(groupNo + 1);
		boardVo.setOrderNo(0);
		boardVo.setDepth(0);
		boardVo.setParentNo(0L);

		String no = String.valueOf(boardService.insert(boardVo));

		return "redirect:/board/view/"+no;
	}
	
	@RequestMapping(value={"/update/{no}","update/{no}/{p}"}, method=RequestMethod.GET)
	public String updateForm(
			@PathVariable("no") Long no, 
			@PathVariable(value="p",required = false) String page,
			Model model) {
		BoardVo vo = boardService.findByNo(no);		
		model.addAttribute("boardVo", vo);
		return "board/update";
	}
	
	@Auth
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String update(@Valid @ModelAttribute BoardVo boardVo,
			BindingResult result,
			Model model) {
		if(result.hasErrors()) {
			model.addAllAttributes(result.getModel());
			return "board/update";
		}
		
		boardService.update(boardVo);
		return "redirect:/board/view/"+boardVo.getNo();
	}
	
	@RequestMapping(value={"/reply/{no}","/reply/{no}/{p}"}, method=RequestMethod.GET)
	public String replyForm(@PathVariable("no") Long parentNo, 
			@PathVariable(value="p", required = false) String page,
			Model model) {
		BoardVo vo = boardService.findByNo(parentNo);
		model.addAttribute("boardVo", vo);
		
		return "board/reply";
	}
	
	@Auth
	@RequestMapping(value="/reply", method=RequestMethod.POST)
	public String reply(
			@Valid @ModelAttribute BoardVo boardVo,
			BindingResult result,
			@AuthUser UserVo authUser,
			HttpServletRequest request,
			Model model) {
		
		Long parentNo = Long.parseLong(request.getParameter("no"));
		int groupNo = Integer.parseInt(request.getParameter("groupNo"));
		int orderNo = Integer.parseInt(request.getParameter("orderNo"));
		int depth = Integer.parseInt(request.getParameter("depth"));
		
		if(result.hasErrors()) {
			boardVo.setNo(parentNo);
			model.addAllAttributes(result.getModel());
			return "board/reply";
		}

		boardService.reply(groupNo, (orderNo+1));
		
		boardVo.setUserNo(authUser.getNo());
		boardVo.setGroupNo(groupNo);
		boardVo.setOrderNo(orderNo+1);
		boardVo.setDepth(depth+1);
		boardVo.setParentNo(parentNo);
		
		Long no = boardService.insert(boardVo);

		return "redirect:/board/view/"+no;
	}
	
	@Auth
	@RequestMapping("/delete/{no}/{p}")
	public String delete(@PathVariable("no") Long no, @PathVariable("p") String page) {
		boardService.delete(no);
		return "redirect:/board/"+page;
	}
	

}
