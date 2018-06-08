package com.fan.manage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import serviceUtils.Jurisdiction;

import com.fan.manage.pojo.Menu;
import com.fan.manage.service.MenuService;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.utils.PageData;

/**
 * 菜单控制类
 * @author hanxiaofan
 *
 */
@Controller
@RequestMapping("menu")
public class MenuController extends BaseController{
	
	@Resource(name = "menuService")
	private MenuService menuService;
	
	
	/**
	 * 菜单列表(ztree需要的json数据结构)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listAllMenu")
	public ModelAndView listAllMenu(String MENU_ID) throws Exception{
		ModelAndView mv = this.getModelAndView();
		try {
			//查询所有菜单的列表，包括子菜单
			List<Menu> menuList = menuService.listAllMenu("0");	
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(menuList);
			json = json.replaceAll("parent_ID", "pId").replaceAll("menu_ID", "id").replaceAll("menu_URL", "url").replaceAll("hasMenu", "checked").replaceAll("subMenu", "nodes").replaceAll("menu_NAME", "name");
			mv.addObject("zTreeNodes", json);	//ztree需要的json数据
			mv.addObject("MENU_ID", MENU_ID);
			mv.setViewName("system/menu/menuTree");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	/**
	 * 显示菜单列表
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/list")
	public ModelAndView list() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// 如果菜单id为空，则默认是0
		String MENU_ID = (null == pd.getString("MENU_ID") || "".equals(pd.getString("MENU_ID"))) ? "0" : pd.getString("MENU_ID")
				.toString();
		List<Menu> menuList = menuService.listSubMenuByParentId(MENU_ID); // 找到所有一级菜单，父id为0
		mv.addObject("pd", menuService.getMenuById(pd));
		mv.addObject("MENU_ID", MENU_ID);
		mv.addObject("MSG", null == pd.getString("MSG") ? "list" : pd.get("MSG").toString()); // MSG=change 则为编辑或删除后跳转过来的
		mv.addObject("menuList", menuList);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		mv.setViewName("system/menu/menuList");
		return mv;
	}
	
	/**
	 * 去新增菜单
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/toAddmenu")
	public ModelAndView toAddmenu() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//如果是顶级id，则取0，如果不是则取其父类id(作为取消之后返回上一级目录的参数)
		String parentId = (null == pd.getString("PARENT_ID") || "".equals(pd.getString("PARENT_ID")))? "0": pd.getString("PARENT_ID").trim().toString();
		pd.put("MENU_ID", parentId);
		mv.addObject("pds", menuService.getMenuById(pd));		
		mv.addObject("pd", pd);					//这里面带有一个参数parentId，pd.PARENT_ID用来表示父id
		mv.addObject("MSG", "addMenu");			//action需要的参数
		mv.addObject("MENU_ID", parentId);		
		mv.setViewName("system/menu/menuEdit");
		return mv;
	}
	
	/**
	 * 保存新增
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/addMenu")
	public ModelAndView addMenu() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Menu menu = new Menu();
		try {
			menu.setMENU_ID((String.valueOf(Integer.parseInt(menuService.findMaxId(pd).get("MID").toString())+1)));	//id+1
			menu.setPARENT_ID(pd.getString("PARENT_ID"));
			menu.setMENU_NAME(pd.getString("MENU_NAME"));
			menu.setMENU_STATE(pd.getString("MENU_STATE"));
			menu.setMENU_TYPE(pd.getString("MENU_TYPE"));
			menu.setMENU_URL(pd.getString("MENU_URL"));
			
			menu.setMENU_ORDER(pd.getString("MENU_ORDER"));
			menu.setMENU_ICON("menu-icon fa fa-leaf black");	//默认图标
			menuService.saveMenu(menu);
		} catch (Exception e) {
			//错误日志
			e.printStackTrace();
		}
		mv.setViewName("redirect:list?MSG='change'&MENU_ID="+menu.getPARENT_ID()); //保存成功跳转到列表页面
		return mv;
	}
	
	/**
	 * 去编辑页
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/toEditmenu")
	public ModelAndView toEditmenu() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();					//本菜单的id
		pd = menuService.getMenuById(pd);
		String PARENT_ID = pd.getString("PARENT_ID");		//父id
		PageData pds = new PageData();
		pds.put("MENU_ID", PARENT_ID);
		pds = menuService.getMenuById(pds);					//父类信息
		mv.addObject("pd", pd);								//本菜单信息
		mv.addObject("pds", pds);
		mv.addObject("MENU_ID", PARENT_ID);					
		mv.addObject("MSG", "editMenu");
		mv.setViewName("system/menu/menuEdit");
		return mv;
	}
	
	/**
	 * 编辑菜单
	 * 参数:MENU_ID,本菜单id。PARENT_ID:父ID。
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/editMenu")
	public ModelAndView editMenu(Menu menu) throws Exception{
		ModelAndView mv = this.getModelAndView();
		try {
			menuService.edit(menu);
		} catch (Exception e) {
			//失败日志
			e.printStackTrace();
		}
		mv.setViewName("redirect:list?MSG='change'&MENU_ID="+menu.getPARENT_ID()); //保存成功跳转到列表页面
		return mv;
	}
	
	
	/**
	 * 删除菜单(是否有子菜单。)
	 * @param MENU_ID
	 * @return
	 * @throws Exceptinon
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(@RequestParam String MENU_ID) throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "";
		try {
			if(menuService.listSubMenuByParentId(MENU_ID).size() > 0){	//如果大于0，则有子菜单
				errInfo = "false";
			}else{
				menuService.deleteMenuById(MENU_ID);
				errInfo = "success";
			}
		} catch (Exception e) {
			//错误日志
		}
		map.put("result", errInfo);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(map);
		return json;
	}
	
	/**
	 * 进入编辑页面
	 * 参数：MENU_ID
	 * @return
	 */
	@RequestMapping("/toEditicon")
	public ModelAndView toEditicon(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		try {
			mv.addObject("pd", pd);	//里面有MENU_ID
			mv.setViewName("system/menu/menuIcon");
		} catch (Exception e) {
			//错误日志
		}
		return mv;
	}
	
	/**
	 * 保存编辑
	 * 参数：MENU_ID,MENU_ICON
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/editicon")
	public ModelAndView editicon() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		try {
			pd = menuService.editicon(pd);
			mv.addObject("msg", "success");
		} catch (Exception e) {
			//错误日志
			mv.addObject("msg", "field");
		}
		mv.setViewName("saveResult");
		return mv;
	}
	
}
