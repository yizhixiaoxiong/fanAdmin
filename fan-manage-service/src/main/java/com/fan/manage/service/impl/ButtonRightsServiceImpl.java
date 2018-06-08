package com.fan.manage.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fan.manage.mapper.MapperSupport;
import com.fan.manage.service.ButtonRightsService;

import common.utils.PageData;
@SuppressWarnings("unchecked")
@Service("buttonRightsService")
public class ButtonRightsServiceImpl implements ButtonRightsService {
	
	@Resource(name = "mapperSupport")
	private MapperSupport mapper;
	
	/**
	 * 新增按钮
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void add(PageData pd) throws Exception {
		mapper.save("ButtonRightsMapper.add", pd);
	}

	/**
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@Override
	public PageData findById(PageData pd) throws Exception {
		return (PageData) mapper.findForObject("ButtonRightsMapper.findById", pd);
	}
	
	/**
	 * 删除按钮
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void delete(PageData pd) throws Exception {
		mapper.delete("ButtonRightsMapper.delete", pd);
	}
	
	/**
	 * 查找所有按钮
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) mapper.findForList("ButtonRightsMapper.listAll", pd);
	}
	
	/**
	 * 列表(全部)左连接按钮表,查出安全权限标识
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<PageData> listAllBrAndQxname(PageData pd) throws Exception {
		return (List<PageData>) mapper.findForList("ButtonRightsMapper.listAllBrAndQxname", pd);
	}

}
