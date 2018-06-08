package com.fan.manage.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fan.manage.mapper.MapperSupport;
import com.fan.manage.pojo.Page;
import com.fan.manage.pojo.User;
import com.fan.manage.service.UserService;

import common.utils.PageData;

/**
 * 用户接口实现类
 * @author hanxiaofan
 *
 */
@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Resource(name = "mapperSupport")
	private MapperSupport mapper;
	
	/**用户列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listUsers(Page page)throws Exception{
		return (List<PageData>) mapper.findForList("UserMapper.userlistPage", page);
	}
	
	
	/**通过id获取数据
	 * @param pageData
	 * @return
	 * @throws Exception
	 */
	public PageData findById(PageData pageData) throws Exception {
		return (PageData) mapper.findForObject("UserMapper.findById", pageData);
	}

	/**修改用户
	 * @param pageData
	 * @throws Exception
	 */
	public void editU(PageData pageData) throws Exception {
		mapper.update("UserMapper.updateU", pageData);
	}

	/**保存用户
	 * @param pageData
	 * @throws Exception
	 */
	public void saveU(PageData pageData) throws Exception {
		mapper.save("UserMapper.saveU", pageData);
	}

	/**删除用户
	 * @param pageData
	 * @throws Exception
	 */
	public void deleteU(PageData pageData) throws Exception {
		mapper.delete("UserMapper.deleteU", pageData);
	}

	/**批量删除用户
	 * @param USER_IDS
	 * @throws Exception
	 */
	public void deleteAllU(String[] USER_IDS) throws Exception {
		mapper.delete("UserMapper.deleteAllU", USER_IDS);
	}

	/**用户列表(全部)
	 * @param pageData
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAllUser(PageData pageData) throws Exception {
		return  (List<PageData>) mapper.findForList("UserMapper.listAllUser", pageData);
	}

	/**通过USERNAEME获取数据
	 * @param pageData
	 * @return
	 * @throws Exception
	 */
	public PageData findByUsername(PageData pageData) throws Exception {
		return (PageData)mapper.findForObject("UserMapper.findByUsername", pageData);
	}
	
	/**
	 * 根据用户名密码查找对象
	 */
	public PageData getUserByNameAndPwd(PageData pageData) throws Exception {
		return (PageData) mapper.findForObject("UserMapper.getUserInfo", pageData);
	}


	/**
	 * 根据邮箱，查找是否有不是本用户的邮箱以及注册
	 */
	@Override
	public PageData findByUE(PageData pageData) throws Exception {
		return (PageData) mapper.findForObject("UserMapper.findByUE", pageData);
	}

	
	/**
	 * 判断此编码是否有别人用过
	 */
	@Override
	public PageData findByUN(PageData pageData) throws Exception {
		return (PageData) mapper.findForObject("UserMapper.findByUN", pageData);
	}

	
	/**
	 * 更新登陆时间
	 * @throws Exception 
	 */
	@Override
	public void updateLastLogin(PageData pageData) throws Exception {
		mapper.update("UserMapper.updateLastLogin", pageData);
	}

	/**
	 * 根据id查找用户和角色
	 * @throws Exception 
	 */
	@Override
	public User getUserAndRoleById(String user_ID) throws Exception {
		return (User) mapper.findForObject("UserMapper.getUserAndRoleById", user_ID);
	}

	/**
	 * 更新登陆的ip
	 * @throws Exception 
	 */
	@Override
	public void saveIP(PageData pd) throws Exception {
		mapper.update("UserMapper.saveIP", pd);
		
	}

	/**
	 * 列出某角色下的所有用户
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listAllUserByRoldId(PageData pd) throws Exception {
		return (List<PageData>) mapper.findForList("UserMapper.listAllUserByRoldId", pd);
	}
	
}
