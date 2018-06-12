package com.fan.manage.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fan.manage.mapper.MapperSupport;
import com.fan.manage.pojo.Page;
import com.fan.manage.service.FileService;

import common.utils.PageData;

/**
 * 文件管理 实体类
 * @author hanxiaofan
 *
 */
@SuppressWarnings("unchecked")
@Service("fileService")
public class FileServiceImpl implements FileService {

	@Resource(name="mapperSupport")
	private MapperSupport mapper;
	
	/**
	 * 新增文件
	 */
	@Override
	public void save(PageData pd) throws Exception {
		mapper.save("FileMapper.save", pd);
	}

	/**
	 * 删除文件
	 */
	@Override
	public void delete(PageData pd) throws Exception {
		mapper.delete("FileMapper.delete", pd);
	}
	
	/**
	 * 更新文件
	 */
	@Override
	public void update(PageData pd) throws Exception {
		mapper.update("FileMapper.update", pd);
	}
	
	/**
	 * 列表
	 * page:分页
	 */
	@Override
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) mapper.findForList("FileMapper.datalistPage", page);
	}

	/**
	 * 全部列表
	 */
	@Override
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) mapper.findForList("FileMapper.listAll", pd);
	}
	
	/**
	 * 根据id查找
	 */
	@Override
	public PageData findById(PageData pd) throws Exception {
		return (PageData) mapper.findForObject("FileMapper.findById", pd);
	}
	
	/**
	 * 删除全部
	 * ArrayDATA_IDS：id集合
	 */
	@Override
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		mapper.delete("FileMapper.deleteAll", ArrayDATA_IDS);
	}

	/**
	 * 根据id批量查找
	 */
	@Override
	public List<PageData> findListByIds(String[] ids) throws Exception {
		return  (List<PageData>) mapper.findForList("FileMapper.findListByIds", ids);
	}

}
