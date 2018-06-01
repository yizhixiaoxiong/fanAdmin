package com.fan.manage.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import serviceUtils.Jurisdiction;
import serviceUtils.RightsHelper;

import com.fan.manage.pojo.Menu;
import com.fan.manage.pojo.Role;
import com.fan.manage.pojo.User;
import com.fan.manage.service.ButtonRightsService;
import com.fan.manage.service.ButtonService;
import com.fan.manage.service.MenuService;
import com.fan.manage.service.RoleService;
import com.fan.manage.service.UserService;

import common.utils.AppUtil;
import common.utils.CommonUtils;
import common.utils.Const;
import common.utils.DateUtil;
import common.utils.PageData;
import common.utils.Tools;

@Controller
public class LoginController extends BaseController {
	
	@Resource(name = "userService")
	private UserService userService;
	@Resource(name = "menuService")
	private MenuService menuService;
	@Resource(name = "roleService")
	private RoleService roleService;
	@Resource(name = "buttonService")
	private ButtonService buttonService;
	@Resource(name = "buttonRightsService")
	private ButtonRightsService buttonRightsService;
	
	/**
	 * 跳转到登陆页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/login_toLogin")
	public ModelAndView toLogin() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
//		pd = this.setLoginPd(pd); // 设置登陆页面的配置参数 TODU
		mv.setViewName("system/index/login");
		mv.addObject("pd", pd);
		return mv;
		
	}
	
	/**
	 * 身份验证，返回信息，在前端进行校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/login_login")
	@ResponseBody
	public Object login() throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String errInfo = "";
		String KEYDATA[] = pd.getString("KEYDATA").split(",");
		//用户名和密码
		if(null != KEYDATA && KEYDATA.length == 3){
			//获取shiro管理的session
			Session session = Jurisdiction.getSession();
			//获取存储到session中的验证码
			String sessionCode = (String)session.getAttribute(Const.SESSION_SECURITY_CODE);	
			String code = KEYDATA[2];
			//非空判断
			if(null == code && "".equals(code)){
				errInfo = "nullcode"; 	
			}else{
				String USERNAME = KEYDATA[0];	//登录过来的用户名
				String PASSWORD  = KEYDATA[1];	//登录过来的密码
				pd.put("USERNAME", USERNAME);
				if(Tools.notEmpty(sessionCode) && sessionCode.equalsIgnoreCase(code)){
					String passwd = new SimpleHash("SHA-1",USERNAME, PASSWORD).toString();
					pd.put("PASSWORD", passwd);
					//根据用户名和密码去获得用户信息
					pd = userService.getUserByNameAndPwd(pd);
					//如果用户存在
					if(pd != null){
						this.removeSession(USERNAME);
						pd.put("LAST_LOGIN",DateUtil.getTime().toString());//登陆的时间
						userService.updateLastLogin(pd);	//更新数据库时间
						User user = new User();//获取用户信息存入session
						user.setUSER_ID(pd.getString("USER_ID"));
						user.setUSERNAME(pd.getString("USERNAME"));
						user.setPASSWORD(pd.getString("PASSWORD"));
						user.setNAME(pd.getString("NAME"));
						user.setRIGHTS(pd.getString("RIGHTS"));
						user.setROLE_ID(pd.getString("ROLE_ID"));
						user.setLAST_LOGIN(pd.getString("LAST_LOGIN"));
						user.setIP(pd.getString("IP"));
						user.setSTATUS(pd.getString("STATUS"));
						session.setAttribute(Const.SESSION_USER, user);	
						session.removeAttribute(Const.SESSION_SECURITY_CODE);	//清除登录验证码的session
						//shiro加入身份认证
						Subject subject = SecurityUtils.getSubject();
						UsernamePasswordToken token = new UsernamePasswordToken(USERNAME, PASSWORD); 
						
						try {
							subject.login(token);
						} catch (AuthenticationException e) {
							errInfo = "身份认证失败";
						}
					}else{
						errInfo = "usererror";//用户或者密码错误
					}
				}else{
					errInfo = "codeerror";	//验证码错误
				}
				
				if(Tools.isEmpty(errInfo)){
					errInfo = "success";
				}
				}
			}else{
				errInfo = "error";//缺少参数
			}
		map.put("result", errInfo);
		return  AppUtil.returnObject(new PageData(), map);
	}
	
	
	/**
	 * 访问系统首页
	 * @param chengeMenu
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/main/{changeMenu}")
	public ModelAndView toMain(@PathVariable("changeMenu")String changeMenu) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pageData = new PageData();
		pageData = this.getPageData();
		
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);			//用户信息
		if(user != null){
			User userr = (User) session.getAttribute(Const.SESSION_USERROL);
			if(userr == null){
				user = userService.getUserAndRoleById(user.getUSER_ID());		//包含角色的用户信息
				session.setAttribute(Const.SESSION_USERROL, user);
			}else{
				user = userr;		
			}
			String USERNAME = user.getUSERNAME();
			Role role = user.getRole();
			String roleRights = role != null ? role.getRIGHTS():"" ;					//不为空则获取权限
			session.setAttribute(USERNAME + Const.SESSION_ROLE_RIGHTS, roleRights); 	//将角色权限存入session
			session.setAttribute(Const.SESSION_USERNAME, USERNAME);						//放入用户名到session
			List<Menu> allmenuList = new ArrayList<Menu>();
			allmenuList = this.getAttributeMenu(session, USERNAME, roleRights);			//菜单缓存
			List<Menu> menuList = new ArrayList<Menu>();
			menuList = this.changeMenuF(allmenuList, session, USERNAME, changeMenu);	//切换菜单
			if(null == session.getAttribute(USERNAME + Const.SESSION_QX)){
				session.setAttribute(USERNAME + Const.SESSION_QX, this.getUQX(USERNAME));//按钮权限放到session中
			}
			this.getRemortIP(USERNAME);	//更新登录IP
			mv.setViewName("system/index/main");
			mv.addObject("user", user);
			mv.addObject("menuList", menuList);
			
		}else{
			mv.setViewName("system/index/login");		//session失效后跳转登录页面
		}
		pageData.put("SYSNAME", "FAN 后台系统");
		mv.addObject("pd", pageData);
		return mv;
	}
	
	/**
	 * 更新登陆用户的IP
	 * @param uSERNAME
	 * @throws Exception 
	 */
	private void getRemortIP(String USERNAME) throws Exception {
		PageData pd = new PageData();
		String ip = CommonUtils.getClientIP(this.getRequest());
		pd.put("USERNAME", USERNAME);
		pd.put("IP", ip);
		userService.saveIP(pd);
	}
	
	/**
	 * 菜单切换 页面左侧菜单栏使用。不全，仅供参考
	 * @param allmenuList	全部菜单
	 * @param session		session
	 * @param USERNAME
	 * @param changeMenu
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Menu> changeMenuF(List<Menu> allmenuList, Session session,
			String USERNAME, String changeMenu) {
		List<Menu> menuList = new ArrayList<Menu>();
		//当前菜单为null
		if(null == session.getAttribute(USERNAME + Const.SESSION_menuList) || ("yes".equals(changeMenu))){
			List<Menu> menuList1 = new ArrayList<Menu>();
			List<Menu> menuList2 = new ArrayList<Menu>();
			for(int i=0;i<allmenuList.size();i++){//拆分菜单
				Menu menu = allmenuList.get(i);
				if("1".equals(menu.getMENU_TYPE())){	//这里是拆分菜单的逻辑
					menuList1.add(menu);
				}else{
					menuList2.add(menu);
				}
			}
			session.removeAttribute(USERNAME + Const.SESSION_menuList);
			if("2".equals(session.getAttribute("changeMenu"))){
				session.setAttribute(USERNAME + Const.SESSION_menuList, menuList1);
				session.removeAttribute("changeMenu");
				session.setAttribute("changeMenu", "1");
				menuList = menuList1;
			}else{
				session.setAttribute(USERNAME + Const.SESSION_menuList, menuList2);
				session.removeAttribute("changeMenu");
				session.setAttribute("changeMenu", "2");
				menuList = menuList2;
			}
		}else{
			menuList = (List<Menu>)session.getAttribute(USERNAME + Const.SESSION_menuList);
		}
		return menuList;
	}

	/**
	 * 菜单缓存，存入session中
	 * @param session
	 * @param uSERNAME
	 * @param roleRights 权限
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	private List<Menu> getAttributeMenu(Session session, String USERNAME,
			String roleRights) throws Exception {
		List<Menu> allmenuList = new ArrayList<Menu>();
		if(null == session.getAttribute(USERNAME + Const.SESSION_allmenuList)){ //为空
			allmenuList = menuService.listAllMenuQx("0");
			if(Tools.notEmpty(roleRights)){	//不为空
				allmenuList = this.readMenu(allmenuList, roleRights);		//根据角色权限获取本权限的菜单列表
			}
			session.setAttribute(USERNAME + Const.SESSION_allmenuList, allmenuList);	//菜单权限放入session中
		}else{
			allmenuList = (List<Menu>) session.getAttribute(USERNAME + Const.SESSION_allmenuList);
		}
		
		return allmenuList;
	}
	
	/**
	 * 根据角色获取本权限的菜单列表(递归)
	 * @param menuList 传入的总菜单
	 * @param roleRights 加密的权限字符串
	 * @return
	 */
	public List<Menu> readMenu(List<Menu> menuList, String roleRights) {
		for (int i = 0; i < menuList.size(); i++) {
			menuList.get(i).setHasMenu(RightsHelper.testRights(roleRights, menuList.get(i).getMENU_ID()));
			if(menuList.get(i).isHasMenu()){	//判断是否有此菜单
				this.readMenu(menuList.get(i).getSubMenu(), roleRights);//是：继续排查其子菜单
			}
		}
		return menuList;
	}

//	@RequestMapping("/left")
//	public String toLeft(){
//		return "system/index/left";
//	}
	
	@RequestMapping("/tab")
	public String tap(){
		return "system/index/tab";
	}
	
	@RequestMapping("/login_default")
	public ModelAndView defaultPage() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pageData = new PageData();
		mv.addObject("pd", pageData);
		mv.setViewName("system/index/default");
		return mv;
	}
	
	/**
	 * 清理session
	 */
	public void removeSession(String USERNAME){
		Session session = Jurisdiction.getSession();	//以下清除session缓存
		session.removeAttribute(Const.SESSION_USER);
		session.removeAttribute(USERNAME + Const.SESSION_ROLE_RIGHTS);
		session.removeAttribute(USERNAME + Const.SESSION_allmenuList);
		session.removeAttribute(USERNAME + Const.SESSION_menuList);
		session.removeAttribute(USERNAME + Const.SESSION_QX);
		session.removeAttribute(Const.SESSION_userpds);
		session.removeAttribute(Const.SESSION_USERNAME);
		session.removeAttribute(Const.SESSION_USERROL);
		session.removeAttribute("changeMenu");
		session.removeAttribute("DEPARTMENT_IDS");
		session.removeAttribute("DEPARTMENT_ID");
	}
	
	/**
	 * 获取用户权限
	 * @param USERNAME
	 * @return
	 * @throws Exception 
	 */
	public Map<String,String> getUQX(String USERNAME) throws Exception{
		PageData pd = new PageData();
		Map<String, String> map = new HashMap<String, String>();
		
		try {
			pd.put(Const.SESSION_USERNAME, USERNAME);
			pd.put("ROLE_ID", userService.findByUsername(pd).get("ROLE_ID").toString());
			pd = roleService.findObjectById(pd);		//Role
			map.put("adds", pd.getString("ADD_QX"));	//增
			map.put("dels", pd.getString("DEL_QX"));	//删
			map.put("edits", pd.getString("EDIT_QX"));	//改
			map.put("chas", pd.getString("CHA_QX"));	//查
			List<PageData> buttonQXnamelist = new ArrayList<PageData>();
			if("admin".equals(USERNAME)){
				buttonQXnamelist = buttonService.listAll(pd);					//admin用户拥有所有按钮权限
			}else{
				buttonQXnamelist = buttonRightsService.listAllBrAndQxname(pd);	//此角色拥有的按钮权限标识列表
			}
			for (int i = 0; i < buttonQXnamelist.size(); i++) {
				map.put(buttonQXnamelist.get(i).getString("QX_NAME"),"1");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return map;
	}
}
