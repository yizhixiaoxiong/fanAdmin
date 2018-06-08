package com.fan.manage.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import serviceUtils.Jurisdiction;

import com.fan.manage.pojo.Page;
import com.fan.manage.pojo.Role;
import com.fan.manage.service.AppUserService;
import com.fan.manage.service.RoleService;
import common.utils.CommonUtils;
import common.utils.PageData;

/**
 * 会员 控制器
 * @author hanxiaofan
 *
 */
@SuppressWarnings("null")
@Controller
@RequestMapping("/happuser")
public class AppUserController extends BaseController{
	
	@Resource(name = "appUserService")
	private AppUserService appUserService;
	@Resource(name = "roleService")
	private RoleService roleService;
	
	/**
	 * 显示用户列表(可以是带条件检索)
	 * @param page 分页
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/listUsers")
	public ModelAndView listUsers(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		try {
			String keywords = pd.getString("keywords");	//获取关键字
			if(keywords == null && "".equals(keywords)){
				pd.put("keywords", keywords.trim());
			}
			page.setPd(pd);
			List<PageData> userList = appUserService.listPageUser(page);	//带分页查找所有会员
			pd.put("ROLE_ID", "2");	//会员组角色的id
			List<Role> roleList = roleService.listAllRolesByPId(pd);	//列出会员组角色
			mv.setViewName("system/appuser/appuserList");
			mv.addObject("userList", userList);
			mv.addObject("roleList", roleList);
			mv.addObject("pd", pd);
			mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		} catch (Exception e) {
			//日志
			e.printStackTrace();
		}
		return mv;
	}
	
	/**
	 * 新增会员
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/goAddU")
	public ModelAndView goAddU() throws Exception{
		ModelAndView mv = this.getModelAndView();
		//传递所属角色的列表
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("ROLE_ID", "2");
		List<Role> roleList = roleService.listAllRolesByPId(pd);
		mv.addObject("msg", "saveU");
		mv.addObject("roleList", roleList);
		mv.addObject("pd", pd);
		mv.setViewName("system/appuser/appuserEdit");
		return mv;
	}
	
	/**
	 * 保存新增用户
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/saveU")
	public ModelAndView saveU() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String ip = CommonUtils.getClientIP(this.getRequest());	//获取新增者的ip
		//获取角色
		String ROLE_ID = pd.getString("ROLE_ID");
		Role role = roleService.getRoleById(ROLE_ID);
		pd.put("RIGHTS", role == null ?"": role.getRIGHTS());
		pd.put("ip", ip);
		pd.put("SKIN", "default");
		pd.put("PASSWORD",  new SimpleHash("SHA-1", pd.getString("USERNAME"), pd.getString("PASSWORD")).toString());
		if(null == appUserService.findByUsername(pd)){	//如果不存在此用户名的会员
			appUserService.saveU(pd);
			mv.addObject("msg", "success");
		}else{
			mv.addObject("msg", "failed");
		}
		mv.setViewName("saveResult");
		return mv;
	}
	
	/**
	 * 去编辑页面
	 * 参数：用户id，角色id
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/toEdit")
	public ModelAndView toEdit() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		try {
			pd.put("ROLE_ID", "2");	//会员组是roleId是2
			List<Role> roleList = roleService.listAllRolesByPId(pd);//获取所有的角色列表
			pd = appUserService.findByUiId(pd);	//获取用户信息
			mv.addObject("pd", pd);
			mv.addObject("roleList", roleList);
			mv.addObject("msg", "editU");
		} catch (Exception e) {
			//错误日志
			e.printStackTrace();
		}
		mv.setViewName("system/appuser/appuserEdit");
		return mv;
	}
	
	/**
	 * 更新用户
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/editU")
	public ModelAndView editU() throws Exception{
		ModelAndView mv = this.getModelAndView();
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = this.getPageData();
		if(pd.getString("PASSWORD") != null && !"".equals(pd.getString("PASSWORD"))){
			pd.put("PASSWORD", new SimpleHash("SHA-1", pd.getString("PASSWORD"), pd.getString("PASSWORD").toString()));
		}
		String user_id = pd.getString("USER_ID");
		long USER_ID = Long.valueOf(user_id).longValue();
		pd.remove("USER_ID");
		map.put("pd", pd);
		map.put("USER_ID", USER_ID);
		try {
			appUserService.editU(map);
			mv.addObject("msg", "success");
		} catch (Exception e) {
			e.printStackTrace();
			mv.addObject("msg", "failed");
		}
		mv.setViewName("saveResult");
		return mv;
	}
	
	/**
	 * 根据id删除会员
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/deleteU")
	@ResponseBody
	public String deleteU() throws Exception{
		PageData pd = this.getPageData();
		Map<String, String> map = new HashMap<String, String>();
		String info = "";
		String userId = pd.getString("USER_ID");
		Long USER_ID = Long.valueOf(userId);
		try {
			appUserService.deleteU(USER_ID);
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
	@RequestMapping("/deleteAllU")
	@ResponseBody
	public String deleteAllU() throws Exception{
		Map<String , Object> map = new HashMap<String, Object>();
		PageData pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String USER_IDS = pd.getString("USER_IDS");
		if(null != USER_IDS && !"".equals(USER_IDS)){
			String[] ArrayUSER_IDS = USER_IDS.split(",");
			long[] longArrnum = (long[]) ConvertUtils.convert(ArrayUSER_IDS, long.class);
			appUserService.deleteAllU(longArrnum);	//批量删除
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(map);
		return json;
	}
	
	/**
	 * 根据用户名判断是否已经存在
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/hasU")
	@ResponseBody
	public String hasU() throws Exception{
		Map<String,String> map = new HashMap<String, String>();
		PageData pd = this.getPageData();
		String info = "";
		if(null ==appUserService.findByUsername(pd)){
			info = "success";
		}else{
			info = "failed";
		}
		map.put("result", info);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(map);
		return json;
	} 
	
	/**
	 * 根据邮箱判断是否已经存在
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/hasE")
	@ResponseBody
	public String hasE() throws Exception{
		Map<String,String> map = new HashMap<String, String>();
		PageData pd = this.getPageData();
		String info = "";
		if(null ==appUserService.findByEmail(pd)){
			info = "success";
		}else{
			info = "failed";
		}
		map.put("result", info);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(map);
		return json;
		
	}
	/**
	 * 根据编码判断是否已经存在
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/hasN")
	@ResponseBody
	public String hasN() throws Exception{
		Map<String,String> map = new HashMap<String, String>();
		PageData pd = this.getPageData();
		String info = "";
		if(null ==appUserService.findByNumber(pd)){
			info = "success";
		}else{
			info = "failed";
		}
		map.put("result", info);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(map);
		return json;
		
	}
}
