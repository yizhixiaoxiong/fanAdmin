package com.fan.manage.controller;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import serviceUtils.Jurisdiction;
import serviceUtils.RightsHelper;

import com.fan.manage.pojo.Menu;
import com.fan.manage.pojo.Role;
import com.fan.manage.service.MenuService;
import com.fan.manage.service.RoleService;
import com.fan.manage.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.utils.PageData;
import common.utils.Tools;

/**
 * 角色控制器
 * @author hanxiaofan
 *
 */
@Controller
@RequestMapping("role")
public class RoleController extends BaseController {
	
	@Resource(name = "roleService")
	private RoleService roleService;
	@Resource(name = "userService")
	private UserService userService;
	@Resource(name = "menuService")
	private MenuService menuService;
	
	/**
	 * 跳转到角色列表页面
	 * @return
	 */
	@RequestMapping
	public ModelAndView roleAllList() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		try{
			pd = this.getPageData();
			if(pd.getString("ROLE_ID") == null || "".equals(pd.getString("ROLE_ID").trim())){
				pd.put("ROLE_ID", "1");										//默认列出第一组角色(初始设计系统用户和会员组不能删除)
			}
			PageData fpd = new PageData();
			fpd.put("ROLE_ID", "0");
			List<Role> roleList = roleService.listAllRolesByPId(fpd);		//列出组(页面横向排列的一级组)
			List<Role> roleList_z = roleService.listAllRolesByPId(pd);		//列出此组下架角色
			pd = roleService.findObjectById(pd);							//取得点击的角色组(横排的)，在前台循环的时候用来判断
			mv.addObject("pd", pd);
			mv.addObject("roleList", roleList);
			mv.addObject("roleList_z", roleList_z);
			mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
			mv.setViewName("system/role/roleList");
		} catch(Exception e){
//			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 去新增页面
	 * @return
	 */
	@RequestMapping("/toAdd")
	public ModelAndView toAdd(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.addObject("msg", "add");
		mv.addObject("pd", pd);
		mv.setViewName("system/role/roleEdit");
		return mv;
	}
	
	/**
	 * 新增角色
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView add() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();

		try {
			pd = this.getPageData();
			String parent_id = pd.getString("PARENT_ID"); // 父角色id
			pd.put("ROLE_ID", parent_id); // 获取父类的权限
			if ("0".equals(parent_id)) {  //新增角色组方法，和角色的区别在id
				pd.put("RIGHTS", "");
			} else {
				String rights = roleService.findObjectById(pd).getString("RIGHTS");
				pd.put("RIGHTS", (null == rights) ? "" : rights);
			}
			pd.put("ROLE_ID", this.get32UUID()); // 主键
			pd.put("ADD_QX", "0"); 	// 初始新增权限为否
			pd.put("DEL_QX", "0"); 	// 删除权限
			pd.put("EDIT_QX", "0"); // 修改权限
			pd.put("CHA_QX", "0"); 	// 查看权限
			roleService.add(pd);
		} catch (Exception e) {
			e.printStackTrace();
			mv.addObject("msg", "failed");
		}
		mv.setViewName("saveResult");
		return mv;
	}
	
	/**
	 * 去编辑页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/toEdit")
	public ModelAndView toEdit() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		pd = roleService.findObjectById(pd);	//查找角色信息
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		mv.setViewName("system/role/roleEdit");
		return mv;
	}
	
	/**
	 * 修改，传递一个roleid，传递parent_id=0
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/edit")
	public ModelAndView edit() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		try {
			roleService.edit(pd);
			mv.addObject("msg", "success");
		} catch (Exception e) {
			//错误日志输出
		}
		mv.setViewName("saveResult");
		return mv;
	}
	
	/**
	 * 删除角色
	 * @param ROLE_ID
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(@RequestParam String ROLE_ID) throws Exception{
		Map<String,String> map = new HashMap<String, String>();
		PageData pd = new PageData();
		String errInfo = "";
		pd.put("ROLE_ID", ROLE_ID);
		List<Role> roleList_z = roleService.listAllRolesByPId(pd); //列出此部门所有的下级
		if(roleList_z.size() > 0){	
			errInfo = "false";			//如果有多个，则不能删除
		}else{
			List<PageData> userList = userService.listAllUserByRoldId(pd);	//查找角色下的所有用户
			if(userList.size() > 0){
				errInfo = "false2";
			}
			roleService.deleteRoleById(ROLE_ID);	//执行删除
			errInfo = "success";
		}
		map.put("result", errInfo);
		ObjectMapper mapper = new ObjectMapper();  
		String jsonfromMap =  mapper.writeValueAsString(map);
		return jsonfromMap;
	}
	
	/**
	 * 菜单权限(显示当前组的菜单的ztree,json,第二行)
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/menuqx")
	public ModelAndView menuqx(Model model,String ROLE_ID) throws Exception{
		ModelAndView mv = this.getModelAndView();
		try {
			Role role = roleService.getRoleById(ROLE_ID);			//获取角色，组角色
			String roleRights = role.getRIGHTS();					//获取权限
			List<Menu> menuList = menuService.listAllMenuQx("0");	//获取所有菜单
			menuList = this.readMenu(menuList,roleRights);			//根据角色权限处理菜单权限状态
			//List 转json
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(menuList);
			json = json.replaceAll("menu_ID", "id").replaceAll("parent_ID", "pId").replaceAll("menu_NAME", "name").replaceAll("subMenu", "nodes").replaceAll("hasMenu", "checked");
			model.addAttribute("zTreeNodes", json);
			mv.addObject("ROLE_ID",ROLE_ID);
			mv.setViewName("system/role/menuqx");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	/**
	 * 读取菜单，判断是否此菜单属于权限
	 * @param menuList		总菜单
	 * @param roleRights	加密的权限字符串
	 * @return
	 */
	public List<Menu> readMenu(List<Menu> menuList, String roleRights) {
		for (int i = 0; i < menuList.size(); i++) {
			menuList.get(i).setHasMenu(RightsHelper.testRights(roleRights, menuList.get(i).getMENU_ID()));
			this.readMenu(menuList.get(i).getSubMenu(), roleRights);	//是继续排查子菜单
		}
		return menuList;
	}
	
	/**
	 * 保存菜单权限
	 * @param ROLE_ID		角色id
	 * @param menuIds		勾选的菜单id集合，以,链接的字符串
	 * @throws Exception
	 */
	@RequestMapping("/saveMenuqx")
	@ResponseBody
	public String saveMenuqx(@RequestParam String ROLE_ID, @RequestParam String menuIds) throws Exception{
		Map<String,String> map = new HashMap<String, String>();
		PageData pd = new PageData();
		String info = "";
		try {
			//处理串
			if(menuIds != null && !"".equals(menuIds.trim())){
				String[] str = Tools.str2StrArray(menuIds);
				BigInteger rights = RightsHelper.sumRights(str);	//用菜单ID做权处理
				Role role = roleService.getRoleById(ROLE_ID);
				role.setRIGHTS(rights.toString());
				roleService.updateRoleRights(role);	//更新角色的权限
			}else{
				Role role = new Role();
				role.setRIGHTS("");
				role.setROLE_ID(ROLE_ID);
				roleService.updateRoleRights(role);				//更新当前角色菜单权限(没有任何勾选)
				pd.put("rights","");
			}
			pd.put("ROLE_ID", ROLE_ID);		
			if(!"1".equals(ROLE_ID)){			//不是系统管理员
				roleService.setAllRights(pd);	//则更新子角色
			}
			info = "success";
		} catch (Exception e) {
			info = "error";
		}
		map.put("result", info);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(map);
	}
	
	/**
	 * 根据此角色设置按钮权限(增删改查)
	 * @param ROLE_ID	角色id
	 * @param msg		内容
	 * @param model		模型
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/b4Button")
	public ModelAndView b4Button(@RequestParam String ROLE_ID, @RequestParam String msg, Model model) throws Exception{
		ModelAndView mv = this.getModelAndView();
		try {
			List<Menu> menuList = menuService.listAllMenuQx("0");	//获取所有
			Role role = roleService.getRoleById(ROLE_ID);
			String roleRights = "";
			if("add_qx".equals(msg)){
				roleRights = role.getADD_QX();	//新增权限
			}else if("del_qx".equals(msg)){
				roleRights = role.getDEL_QX();	//删除权限
			}else if("edit_qx".equals(msg)){
				roleRights = role.getEDIT_QX();	//修改权限
			}else if("cha_qx".equals(msg)){
				roleRights = role.getCHA_QX();	//查看权限
			}
			menuList = this.readMenu(menuList, roleRights);	//根据角色权限处理菜单权限状态(递归处理)
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(menuList);
			json = json.replaceAll("menu_ID", "id").replaceAll("parent_ID", "pId").replaceAll("menu_NAME", "name").replaceAll("subMenu", "nodes").replaceAll("hasMenu", "checked");
			model.addAttribute("zTreeNodes", json);
			mv.addObject("ROLE_ID", ROLE_ID);
			mv.addObject("msg", msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.setViewName("system/role/b4Button");
		return mv;
	}
	
	/**
	 * 保存按钮权限
	 * @param ROLE_ID	角色id
	 * @param ids		菜单id集合
	 * @param model
	 * @throws Exception 
	 */
	@RequestMapping("/saveB4Button")
	@ResponseBody
	public String saveB4Button(@RequestParam String ROLE_ID,@RequestParam String menuIds , @RequestParam String msg) throws Exception{
		Map<String,String> map = new HashMap<String, String>();
		String info = "";
		try {
			PageData pd =this.getPageData();
			
			if(menuIds != null && !"".equals(menuIds)){
				BigInteger rights = RightsHelper.sumRights(Tools.str2StrArray(menuIds));
				pd.put("value", rights);
			}else{
				pd.put("value", "");
			}
			pd.put("ROLE_ID", ROLE_ID);
			roleService.saveB4Button(msg, pd);
			info = "success";
		} catch (Exception e) {
			info = e.toString();
		}
		map.put("result", info);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(map);
		return json;
	}
}
