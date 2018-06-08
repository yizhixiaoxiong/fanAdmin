package com.fan.manage.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fan.manage.mapper.MapperSupport;
import com.fan.manage.pojo.Page;
import com.fan.manage.service.AppUserService;

import common.utils.PageData;
/**
 * 会员实现类
 * @author hanxiaofan
 *
 */
@SuppressWarnings("unchecked")
@Service("appUserService")
public class AppUserServiceImpl implements AppUserService{

	@Resource(name = "mapperSupport")
	private MapperSupport mapper;
	
	/**
	 * 根据角色id到处所有会员列表
	 * @param pd
	 * @return
	 */
	@Override
	public List<PageData> listAllAppUserByRoleId(PageData pd) throws Exception {
		return (List<PageData>) mapper.findForList("AppuserMapper.listAllAppUserByRoleId", pd);
	}
	
	/**
	 * 会员列表
	 * @param page
	 * @return
	 */
	@Override
	public List<PageData> listPageUser(Page page) throws Exception {
		return (List<PageData>) mapper.findForList("AppuserMapper.userlistPage", page);
	}

	/**
	 * 根据用户名获取数据
	 * @param pd
	 * @return
	 */
	@Override
	public PageData findByUsername(PageData pd) throws Exception {
		return (PageData) mapper.findForObject("AppuserMapper.findByUsername", pd);
	}
	
	/**
	 * 通过邮箱获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@Override
	public PageData findByEmail(PageData pd) throws Exception {
		return (PageData) mapper.findForObject("AppuserMapper.findByEmail", pd);
	}

	/**
	 * 通过编号获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@Override
	public PageData findByNumber(PageData pd) throws Exception {
		return (PageData) mapper.findForObject("AppuserMapper.findByNumber", pd);
	}
	
	/**
	 * 保存用户
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void saveU(PageData pd) throws Exception {
		mapper.save("AppuserMapper.saveU", pd);
	}
	/**
	 * 删除用户
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void deleteU(Long USER_ID) throws Exception {
		mapper.delete("AppuserMapper.deleteU", USER_ID);
	}
	/**
	 * 修改用户
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void editU(Map<String, Object> map) throws Exception {
		mapper.update("AppuserMapper.editU", map);
	}
	/**
	 * 通过id获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@Override
	public PageData findByUiId(PageData pd) throws Exception {
		return (PageData) mapper.findForObject("AppuserMapper.findByUiId", pd);
	}
	/**
	 * 全部会员
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<PageData> listAllUser(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 批量删除用户
	 * @param USER_IDS
	 * @throws Exception
	 */
	@Override
	public void deleteAllU(long[] USER_IDS) throws Exception {
		mapper.delete("AppuserMapper.deleteAllU", USER_IDS);
	}
	/**
	 * 获取总数
	 * @param value
	 * @return
	 * @throws Exception
	 */
	@Override
	public PageData getAppUserCount(String value) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
