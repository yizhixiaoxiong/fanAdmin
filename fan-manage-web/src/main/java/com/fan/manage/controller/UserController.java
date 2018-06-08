package com.fan.manage.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import serviceUtils.Jurisdiction;

import com.fan.manage.pojo.Page;
import com.fan.manage.pojo.Role;
import com.fan.manage.service.RoleService;
import com.fan.manage.service.UserService;
import common.utils.AppUtil;
import common.utils.PageData;
import common.utils.Tools;

/**
 * 用户Controller
 * @author hanxiaofan
 *
 */
@Controller
@RequestMapping("user")
public class UserController extends BaseController{
	
	@Resource(name = "userService")
	private UserService userService;
	@Resource(name = "roleService")
	private RoleService roleService;
	
	
	/**
	 * 用户列表
	 * @param page	分页
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listUsers")
	public ModelAndView listUsers(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String lastLoginStart = pd.getString("lastLoginStart");	//开始时间
		String lastLoginEnd = pd.getString("lastLoginEnd");		//结束时间
		if(lastLoginStart != null && !"".equals(lastLoginStart)){
			pd.put("lastLoginStart", lastLoginStart+" 00:00:00");
		}
		if(lastLoginEnd != null && !"".equals(lastLoginEnd)){
			pd.put("lastLoginEnd", lastLoginEnd+" 00:00:00");
		} 
		page.setPd(pd);
		List<PageData>	userList = userService.listUsers(page);	//列出用户列表
		pd.put("ROLE_ID", "1");
		List<Role> roleList = roleService.listAllRolesByPId(pd);//列出所有系统的用户角色
		mv.setViewName("system/user/userList");
		mv.addObject("userList", userList);
		mv.addObject("roleList", roleList);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**
	 * 跳转到增加用户页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/goAddU")
	public ModelAndView goAddU() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		pd.put("ROLE_ID", "1");
		List<Role> roleList = roleService.listAllRolesByPId(pd);
		mv.addObject("roleList", roleList);
		mv.addObject("msg", "saveU");
		mv.setViewName("system/user/userEdit");
		return mv;
	}
	
	/**
	 * 新增用户保存
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/saveU")
	public ModelAndView saveU() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		pd.put("USER_ID", this.get32UUID());	//获得主键
		pd.put("LAST_LOGIN", "");		//最后登录时间
		pd.put("IP", "");				//IP
		pd.put("STATUS", "0");			//状态
		pd.put("SKIN", "default");		//获得皮肤颜色
		pd.put("RIGHTS", "");			//获得权限
		pd.put("PASSWORD", new SimpleHash("SHA-1", pd.getString("USERNAME"), pd.getString("PASSWORD")).toString());	//密码加密
		//根据名字判断是否已经存在此用户
		if(null == userService.findByUsername(pd)){
			//如果存在，则保存
			userService.saveU(pd); 
			mv.addObject("msg","success");
		}else{
			mv.addObject("msg", "failed");
		}
		mv.setViewName("saveResult");
		return mv;
	}
	
	
	
	/**
	 * 跳转到编辑用户页面
	 * 带着参数，要查找的用户user_id
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/goEditU")
	public ModelAndView goEditU() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = userService.findById(pd);//查询对象
		mv.addObject("pd", pd);
		mv.addObject("msg", "editU");
		mv.setViewName("system/user/userEdit");
		return mv;
	}
	/**
	 * 编辑用户
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/editU")
	public ModelAndView editU() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//将前台获取的密码加密
		pd.put("PASSWORD", new SimpleHash("SHA-1", pd.getString("USERNAME"), pd.getString("PASSWORD")).toString());
		userService.editU(pd);	//执行修改
		//返回前端状态
		mv.addObject("msg","success");
		mv.setViewName("saveResult");
		return mv;
	}
	
	
	/**
	 * 查看单个用户
	 * 带着用户id，user_id
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/view")
	public ModelAndView goview() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//判断用户
		if("admin".equals(pd.getString("USERNAME"))){return null;}
		//根据用户名查找用户
		pd = userService.findByUsername(pd);
	
		mv.addObject("msg", "editU");	//fan
		mv.addObject("pd", pd);
		mv.setViewName("system/user/userView");
		return mv;
	}
	
	/**
	 * 判断此邮箱是否有别的用户注册过
	 * @return
	 */
	@RequestMapping("/hasE")
	@ResponseBody
	public Object hasE(){
		Map<String , String> map = new HashMap<String, String>();
		String errInfo = "success";
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			if(userService.findByUE(pd) != null){//如果没有查到了除了本用户之外，其他的用户注册了本邮箱
				errInfo = "error";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("result", errInfo);
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**
	 * 判断此编码是否有别人用过
	 * @return
	 */
	@RequestMapping("/hasN")
	@ResponseBody
	public Object hasN(){
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(userService.findByUN(pd) != null){
				errInfo = "error";
			}
		} catch(Exception e){
			//日志系统
		}
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**
	 * 查看用户名是否存在
	 * @return
	 */
	@RequestMapping("/hasU")
	@ResponseBody
	public Object hasU(){
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(userService.findByUsername(pd) != null){
				errInfo = "error";
			}
		} catch(Exception e){
			//日志系统
		}
		
		map.put("result", errInfo);
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**
	 * 删除指定用户
	 * @param out
	 * @throws Exception 
	 */
	@RequestMapping("/deleteU")
	@ResponseBody
	public String deleteU() throws Exception{
		Map<String,String> map = new HashMap<String, String>();
		String info = "";
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			userService.deleteU(pd);
			info = "success";
		} catch (Exception e) {
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
	public Object deleteAllU() throws Exception{
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String, Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String USER_IDS = pd.getString("USER_IDS");	//获取数据
		//非空判断
		if(Tools.notEmpty(USER_IDS)){
			String ArrayUSER_IDS[] = USER_IDS.split(",");	//分割id
			userService.deleteAllU(ArrayUSER_IDS);		//批量删除，后台使用in(ids)
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
}
