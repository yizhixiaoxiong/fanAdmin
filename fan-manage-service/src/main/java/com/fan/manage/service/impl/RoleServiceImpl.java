package com.fan.manage.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fan.manage.mapper.MapperSupport;
import com.fan.manage.pojo.Role;
import com.fan.manage.service.RoleService;
import common.utils.PageData;

/**
 * 角色接口实现
 * 
 * @author hanxiaofan
 *
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {

	@Resource(name = "mapperSupport")
	private MapperSupport mapper;

	/**
	 * 列出此组下级角色
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Role> listAllRolesByPId(PageData pd) throws Exception {
		return (List<Role>) mapper.findForList("RoleMapper.listAllRolesByPId",
				pd);
	}

	/**
	 * 通过id查找
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findObjectById(PageData pd) throws Exception {
		return (PageData) mapper.findForObject("RoleMapper.findObjectById", pd);
	}

	/**
	 * 添加
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void add(PageData pd) throws Exception {
		mapper.save("RoleMapper.insert", pd);
	}

	/**
	 * 保存修改
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd) throws Exception {
		mapper.update("RoleMapper.edit", pd);
	}

	/**
	 * 删除角色
	 * 
	 * @param ROLE_ID
	 * @throws Exception
	 */
	public void deleteRoleById(String ROLE_ID) throws Exception {
		mapper.delete("RoleMapper.deleteRoleById", ROLE_ID);
	}

	/**
	 * 给当前角色附加菜单权限
	 * 
	 * @param role
	 * @throws Exception
	 */
	public void updateRoleRights(Role role) throws Exception {
		mapper.update("RoleMapper.updateRoleRights", role);
	}

	/**
	 * 通过id查找
	 * 
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public Role getRoleById(String ROLE_ID) throws Exception {
		return (Role) mapper.findForObject("RoleMapper.getRoleById", ROLE_ID);
	}

	/**
	 * 给全部子角色加菜单权限
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void setAllRights(PageData pd) throws Exception {
		mapper.update("RoleMapper.setAllRights", pd);
	}

	/**
	 * 权限(增删改查)
	 * 
	 * @param msg
	 *            区分增删改查
	 * @param pd
	 * @throws Exception
	 */
	public void saveB4Button(String msg, PageData pd) throws Exception {
		mapper.update("RoleMapper." + msg, pd);
	}

}