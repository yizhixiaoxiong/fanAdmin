package com.fan.manage.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import serviceUtils.Jurisdiction;

import com.fan.manage.pojo.Page;
import com.fan.manage.service.ButtonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.utils.AppUtil;
import common.utils.PageData;

/**
 * 按钮管理控制
 * @author hanxiaofan
 *
 */
@Controller
@RequestMapping("fhbutton")
public class ButtonControlle extends BaseController {
	
	@Resource(name = "buttonService")
	private ButtonService buttonService;
	
	/**
	 * 	按钮列表
	 * @param page	分页
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public ModelAndView list(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		
		String keywords = pd.getString("keywords");
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData> varList = buttonService.list(page);	//带分页
		mv.setViewName("system/button/buttonList");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//从session中获取按钮权限
		return mv;
	}
	
	/**
	 * 去新增页面
	 * @return
	 */
	@RequestMapping("/goAdd")
	public ModelAndView toAdd(){	
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.setViewName("system/button/buttonEdit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
		
	}
	
	/**
	 * 新增按钮
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/save")
	public ModelAndView save() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		pd.put("FHBUTTON_ID",get32UUID());	//设置主键
		buttonService.save(pd);
		mv.setViewName("saveResult");
		mv.addObject("msg", "success");
		return mv;
	}
	
	/**
	 * 去编辑页面 ,传递过来一个FHBUTTON_ID，包装在PageData中。
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/goEdit")
	public ModelAndView toEdit() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		pd = buttonService.findById(pd);
		mv.setViewName("system/button/buttonEdit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**
	 * 编辑。根据传递过来的id
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/edit")
	public ModelAndView edit() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		buttonService.update(pd);	//更新
		mv.setViewName("saveResult");
		mv.addObject("msg", "success");
		return mv;
	}
	
	/**
	 * 删除按钮
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public String delete() throws Exception{
		Map<String,String> map = new HashMap<String, String>();
		String info = "";
		PageData pd = this.getPageData();
		try {
			buttonService.delete(pd);
			info = "success";
		} catch (Exception e) {
			e.printStackTrace();
			info = "failed";
		}
		map.put("result", info);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(map);
		return json;
	}
	
	/**
	 * 批量删除
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> map = new HashMap<String,Object>();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			buttonService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
}
