package com.fan.manage.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fan.manage.service.FileService;

import common.utils.PageData;

/**
 * 文件控制类
 * @author hanxiaofan
 *
 */
@RequestMapping("/file")
@Controller
public class FileController extends BaseController{
	
	@Resource(name="fileService")
	private FileService fileService;
	
	/**
	 * 文件列表
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.addObject("pd", pd);
		mv.setViewName("information/file/fileList");
		return mv;
	}
	
	
}
