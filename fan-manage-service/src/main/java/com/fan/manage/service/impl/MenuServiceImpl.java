package com.fan.manage.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fan.manage.mapper.MapperSupport;
import com.fan.manage.pojo.Menu;
import com.fan.manage.service.MenuService;

import common.utils.PageData;

/**
 * 菜单服务实现类
 * @author hanxiaofan
 *
 */
@SuppressWarnings("unchecked")
@Service("menuService")
public class MenuServiceImpl implements MenuService{
	
	//引入bean
	@Resource(name = "mapperSupport")
	private MapperSupport mapper;
	
	/**
	 * 通过ID获取其子 一级菜单
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	
	public List<Menu> listSubMenuByParentId(String parentId) throws Exception {
		return (List<Menu>) mapper.findForList("MenuMapper.listSubMenuByParentId", parentId);
	}

	public PageData getMenuById(PageData pageData) throws Exception {
		return (PageData) mapper.findForObject("MenuMapper.getMenuById", pageData);
	}

	public void saveMenu(Menu menu) throws Exception {
		mapper.save("MenuMapper.save", menu);
		
	}

	public PageData findMaxId(PageData pageData) throws Exception {
		return (PageData) mapper.findForObject("MenuMapper.findMaxId", pageData);
	}

	public void deleteMenuById(String MENU_ID) throws Exception {
		mapper.delete("MenuMapper.deleteMenuById", MENU_ID);
		
	}

	public void edit(Menu menu) throws Exception {
		mapper.update("MenuMapper.edit", menu);
	}

	public PageData editicon(PageData pageData) throws Exception {
		return (PageData) mapper.findForObject("MenuMapper.editicon", pageData);
	}

	/**
	 * 获取所有菜单并填充每个菜单的子菜单列表(菜单管理)(递归处理)
	 */
	public List<Menu> listAllMenu(String MENU_ID) throws Exception {
		List<Menu> menuList = this.listSubMenuByParentId(MENU_ID);	//获得id为MENU_ID的子菜单
		//循环递归
		for (Menu menu : menuList) {
			menu.setMENU_URL("rest/menu/toEditmenu?MENU_ID="+menu.getMENU_ID());	//用来点击左侧，右边显示编辑页面
			menu.setSubMenu(this.listAllMenu(menu.getMENU_ID()));
			menu.setTarget("treeFrame");		//ztree的iframe
		}
		return menuList;
	}
	
	/**
	 * 获取所有菜单并填充每个菜单的子菜单列表
	 * @param MENU_ID 一级菜单
	 */
	public List<Menu> listAllMenuQx(String MENU_ID) throws Exception {
		List<Menu> menuList = this.listSubMenuByParentId(MENU_ID);
		for (Menu menu : menuList) {
			menu.setSubMenu(this.listAllMenuQx(menu.getMENU_ID()));
			menu.setTarget("treeFrame");	//	ztree的iframe
		}
		return menuList;
	}

}
