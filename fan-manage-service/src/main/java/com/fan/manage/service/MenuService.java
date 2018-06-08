package com.fan.manage.service;

import java.util.List;

import com.fan.manage.pojo.Menu;
import common.utils.PageData;

/**
 * 
 * @author hanxiaofan
 *
 */
public interface MenuService {
	
	/**
	 * 根据父ID查找子菜单集合
	 * @return
	 * @throws Exception
	 */
	public List<Menu> listSubMenuByParentId(String parentId) throws Exception;
		
	/**
	 * 根据菜单id返回PageData
	 * @param pageData
	 * @return
	 * @throws Exception
	 */
	public PageData getMenuById(PageData pageData) throws Exception; 
	
	/**
	 * 保存一个菜单对象
	 * @param menu
	 * @throws Exception
	 */
	public void saveMenu(Menu menu) throws Exception;
	
	/**
	 * 查找最大ID
	 * @param pageData
	 * @return
	 * @throws Exception
	 */
	public PageData findMaxId(PageData pageData) throws Exception;
	/**
	 * 根据按钮id删除菜单
	 * @param MENU_ID
	 * @throws Exception
	 */
	public void deleteMenuById(String MENU_ID) throws Exception;
	
	/**
	 * 编辑菜单
	 * @param menu
	 * @throws Exception
	 */
	public void edit(Menu menu) throws Exception;
	
	/**
	 * 编辑菜单图标
	 * @param pageData
	 * @return
	 * @throws Exception
	 */
	public PageData editicon(PageData pageData) throws Exception;
	
	/**
	 * 获取所有菜单并填充每个菜单的子菜单列表(菜单管理)(递归处理)
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	public List<Menu> listAllMenu(String MENU_ID) throws Exception;
	
	/**
	 * 获取所有菜单并填充每个菜单的子菜单列表(系统菜单列表)(递归处理)
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	public List<Menu> listAllMenuQx(String MENU_ID) throws Exception;
}
