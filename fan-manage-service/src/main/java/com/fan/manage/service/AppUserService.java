package com.fan.manage.service;

import java.util.List;
import java.util.Map;

import com.fan.manage.pojo.Page;

import common.utils.PageData;

/**
 * 会员接口
 * @author hanxiaofan
 *
 */
public interface AppUserService {
	//增删该查，批量导出
	/**
	 * 根据角色id到处所有会员列表
	 * @param pd
	 * @return
	 */
	public List<PageData> listAllAppUserByRoleId(PageData pd) throws Exception;
	
	/**
	 * 会员列表
	 * @param page
	 * @return
	 */
	public List<PageData> listPageUser(Page page) throws Exception;
	
	/**
	 * 根据用户名获取数据
	 * @param pd
	 * @return
	 */
	public PageData findByUsername(PageData pd) throws Exception;
	/**
	 * 通过邮箱获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByEmail(PageData pd) throws Exception; 
	
	/**
	 * 通过编号获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByNumber(PageData pd) throws Exception;
	
	/**
	 * 保存用户
	 * @param pd
	 * @throws Exception
	 */
	public void saveU(PageData pd) throws Exception;
	
	/**
	 * 删除用户
	 * @param USER_ID
	 * @throws Exception
	 */
	public void deleteU(Long USER_ID) throws Exception;
	
	/**
	 * 修改用户
	 * @param pd
	 * @throws Exception
	 */
	public void editU(Map<String, Object> map) throws Exception;
	
	/**
	 * 通过id获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByUiId(PageData pd) throws Exception;
	
	/**
	 * 全部会员
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listAllUser(PageData pd) throws Exception;
	
	/**
	 * 批量删除用户
	 * @param USER_IDS
	 * @throws Exception
	 */
	public void deleteAllU(long[] USER_IDS) throws Exception;
	
	/**
	 * 获取总数
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public PageData getAppUserCount(String value) throws Exception;
	
} 
