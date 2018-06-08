package com.fan.manage.service;

import java.util.List;

import com.fan.manage.pojo.Page;
import com.fan.manage.pojo.User;

import common.utils.PageData;

/**
 * 系统用户接口
 * @author hanxiaofan
 *
 */
public interface UserService {
	
	/**
	 * 根据用户名和密码获取用户
	 * @param pageData
	 * @return
	 */
	public PageData getUserByNameAndPwd(PageData pageData) throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findById(PageData pageData)throws Exception;
	
	/**修改用户
	 * @param pd
	 * @throws Exception
	 */
	public void editU(PageData pageData)throws Exception;
	
	/**保存用户
	 * @param pd
	 * @throws Exception
	 */
	public void saveU(PageData pageData)throws Exception;
	
	/**删除用户
	 * @param pd
	 * @throws Exception
	 */
	public void deleteU(PageData pageData)throws Exception;
	
	/**批量删除用户
	 * @param USER_IDS
	 * @throws Exception
	 */
	public void deleteAllU(String[] USER_IDS)throws Exception;
	
	/**用户列表(全部)
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listAllUser(PageData pageData)throws Exception;
	
	/**通过USERNAEME获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByUsername(PageData pageData)throws Exception;
	
	/**用户列表
	 * @param page 带分页
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listUsers(Page page)throws Exception;
	
	/**
	 * 根据邮箱，查找是否有不是本用户的邮箱以及注册
	 * @param pageData
	 * @return
	 * @throws Exception
	 */
	public PageData findByUE(PageData pageData) throws Exception;

	/**
	 * 判断此编码是否有别人用过
	 * @param pd
	 * @return
	 */
	public PageData findByUN(PageData pageData) throws Exception;
	
	/**
	 * 更新登陆时间
	 * @param pageData
	 * @throws Exception 
	 */
	public void updateLastLogin(PageData pageData) throws Exception;
	
	/**
	 * 根据用户id查找用户的角色信息
	 * @param user_ID
	 * @return
	 * @throws Exception 
	 */
	public User getUserAndRoleById(String user_ID) throws Exception;
	
	/**
	 * 更改登陆用户的IP
	 * @param pd
	 * @throws Exception 
	 */
	public void saveIP(PageData pd) throws Exception;
	
	/**
	 * 列出某角色下的所有用户
	 * @param pd
	 * @return
	 * @throws Exception 
	 */
	public List<PageData> listAllUserByRoldId(PageData pd) throws Exception;

}
