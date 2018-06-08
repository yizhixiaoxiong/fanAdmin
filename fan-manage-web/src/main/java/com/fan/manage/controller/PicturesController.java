package com.fan.manage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import serviceUtils.Jurisdiction;

import com.fan.manage.pojo.Page;
import com.fan.manage.service.PictureService;

import common.utils.Const;
import common.utils.DateUtil;
import common.utils.PageData;

/**
 * 图片控制类
 * @author hanxiaofan
 *
 */
@RequestMapping("pictures")
@Controller
public class PicturesController extends BaseController{
	
	@Resource(name = "pictureService")
	private PictureService pictureService;
	
	/**
	 * 图片列表
	 * @param page	分页
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public ModelAndView list(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		page.setPd(pd);
		List<PageData> varList = pictureService.list(page);
		mv.addObject("varList", varList);
		mv.addObject("QX", Jurisdiction.getHC());
		mv.setViewName("information/pictures/picturesList");
		return mv;
	}
	
	/**
	 * 新增(上传)
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public String add(MultipartFile file){
		Map<String,String> map = new HashMap<String,String>();
		String  ffile = DateUtil.getDays(), fileName = "";
		PageData pd = new PageData();
		if(null != file && !file.isEmpty()){	//非空
			String filePath = Const.FILEPATHIMG + ffile;
		}
		return null;
	}
	
}
