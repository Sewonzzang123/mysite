package com.douzone.mysite.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.mysite.service.FileUploadService;
import com.douzone.mysite.service.GalleryService;
import com.douzone.mysite.vo.GalleryVo;

@Controller
@RequestMapping("/gallery")
public class GalleryController {
	
	@Autowired
	private GalleryService galleryService;
	
	@Autowired
	private FileUploadService fileUploadService;
	
	@RequestMapping({"","/{p}"})
	public String index(Model model,@PathVariable(value= "p", required=false)String page) {
		if (page == null) {
			page = "1";
		}	
		List<GalleryVo> list = galleryService.findByPage(Integer.parseInt(page));
//		Map<String, Integer> map = galleryService.pageInfo(Integer.parseInt(page));
//		for(GalleryVo vo :list) {
//			System.out.println(vo);
//		}
//		System.out.println(map.toString());
//		model.addAttribute("pageInfo", map);
		model.addAttribute("list", list);
		return "gallery/index";
	}
	
	@RequestMapping(value ="/upload", method = RequestMethod.POST)
	public String upload(GalleryVo vo,MultipartFile file) {
		String url = fileUploadService.restore(file);
		vo.setUrl(url);
		galleryService.insert(vo);
		return "redirect:/gallery";
	}
	@RequestMapping(value="/delete/{no}", method=RequestMethod.GET)
	public String deleteform(@PathVariable(value="no", required=true)String no) {	
		return "gallery/delete";
	}
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(@RequestParam("no") String no) {
		galleryService.delete(Long.parseLong(no));
		return "redirect:/gallery";
	}
}
