package com.fan.manage.service;

import java.util.List;

import com.fan.manage.pojo.Page;

import common.utils.PageData;

/**
 * 图片接口
 * @author hanxiaofan
 *
 */
public interface PictureService{
	
	/**
	 * 列表
	 * @param page	分页
	 * @return
	 * @throws Exception
	 */
	public List<PageData> list(Page page) throws Exception;
	
	/**
	 * 新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**
	 * 删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
	/**
	 * 编辑
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	
	/**
	 * 根据id查找
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**
	 * 批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ids)throws Exception;
	
	/**
	 * 批量获取
	 * @param ArrayDATA_IDS
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getAllById(String[] ids)throws Exception;
	
	/**
	 * 删除图片
	 * @param pd
	 * @throws Exception
	 */
	public void delTp(PageData pd)throws Exception;
}
