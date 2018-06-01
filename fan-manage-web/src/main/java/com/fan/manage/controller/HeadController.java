package com.fan.manage.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fan.manage.pojo.Role;
import com.fan.manage.service.RoleService;
import com.fan.manage.service.UserPhotoService;
import com.fan.manage.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import common.utils.Const;
import common.utils.PageData;
import serviceUtils.Jurisdiction;

/**
 * 头部控制器
 * @author hanxiaofan
 *
 */
@Controller
@RequestMapping("/head")
public class HeadController extends BaseController {
	
	@Resource(name="userService")
	private UserService userService;
	
	@Resource(name = "roleService")
	private RoleService roleService;
	
	@Resource(name="userPhotoService")
	private UserPhotoService userPhotoService;
	
	/**
	 * 获取头部信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getList")
	@ResponseBody
	public String getList() throws Exception {
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		Session session = Jurisdiction.getSession();
		PageData pds = new PageData();
		pds = (PageData)session.getAttribute(Const.SESSION_userpds);
		if(null == pds){
			pd.put("USERNAME", Jurisdiction.getUsername());//当前登录者用户名
			pds = userService.findByUsername(pd);
			session.setAttribute(Const.SESSION_userpds, pds);
		}
		pdList.add(pds);
		map.put("list", pdList);
		PageData pdPhoto = userPhotoService.findById(pds);
		map.put("userPhoto", null == pdPhoto?"static/ace/avatars/user.jpg":pdPhoto.getString("PHOTO2"));//用户头像
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(map);
		return json;
	}
	
	
	/**
	 * 进入修改头像页面
	 * @return
	 */
	@RequestMapping("/editPhoto")
	public ModelAndView editPhoto() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("system/userphoto/userphotoEdit");
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**
	 * 修改用户资料
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/goEditMyU")
	public ModelAndView goEditMyU() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.addObject("fx", "head");
		pd.put("ROLE_ID", "1");		//角色id为1
		List<Role> roleList = roleService.listAllRolesByPId(pd);	//角色列表
		pd.put("USERNAME", Jurisdiction.getUsername());		//读取姓名
		pd = userService.findByUsername(pd);//查找用户
		mv.addObject("msg", "editU");
		mv.addObject("pd", pd);
		mv.addObject("roleList", roleList);
		mv.setViewName("system/user/userEdit");
		return mv;
	}
	
	//系统设置
	@RequestMapping("/goSystem")
	public ModelAndView goSystem() {
		ModelAndView mv = this.getModelAndView();
		
		return mv;
	}
}
