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
@Service("fileService")
public class FileServiceImpl implements FileService {

	@Resource(name="mapperSupport")
	private MapperSupport mapper;
	
	@Override
	public void save(PageData pd) throws Exception {
		mapper.save("FileMapper.save", pd);
	}

	@Override
	public void delete(PageData pd) throws Exception {
		mapper.delete("FileMapper.delete", pd);
	}

	@Override
	public void update(PageData pd) throws Exception {
		mapper.update("FileMapper.update", pd);
	}

	@Override
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) mapper.findForList("FileMapper.list", page);
	}

	@Override
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) mapper.findForList("FileMapper.listAll", pd);
	}

	@Override
	public PageData findById(PageData pd) throws Exception {
		return (PageData) mapper.findForObject("FileMapper.findById", pd);
	}

	@Override
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		mapper.delete("FileMapper.deleteAll", ArrayDATA_IDS);
	}

}
