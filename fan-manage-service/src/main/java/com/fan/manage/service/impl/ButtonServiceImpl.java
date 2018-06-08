package com.fan.manage.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fan.manage.mapper.MapperSupport;
import com.fan.manage.pojo.Page;
import com.fan.manage.service.ButtonService;

import common.utils.PageData;

@SuppressWarnings("unchecked")
@Service("buttonService")
public class ButtonServiceImpl implements ButtonService{
	
	
	@Resource(name = "mapperSupport")
	private MapperSupport mapper;

	public void save(PageData pd) throws Exception {
		mapper.save("ButtonMapper.save", pd);
		
	}

	public void delete(PageData pd) throws Exception {
		mapper.delete("ButtonMapper.delete", pd);
		
	}

	public void update(PageData pd) throws Exception {
		mapper.update("ButtonMapper.update", pd);
		
	}

	/**
	 * 按钮列表
	 */
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) mapper.findForList("ButtonMapper.datalistPage", page);
	}

	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) mapper.findForList("ButtonMapper.listAll", pd);
	}
	
	public PageData findById(PageData pd) throws Exception {
		return (PageData) mapper.findForObject("ButtonMapper.findById", pd);
	}
	
	/**
	 * 批量删除
	 */
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		mapper.delete("ButtonMapper.deleteAll", ArrayDATA_IDS);
		
	}
}
