package com.fan.manage.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fan.manage.mapper.MapperSupport;
import com.fan.manage.pojo.Page;
import com.fan.manage.service.PictureService;

import common.utils.PageData;

/**
 * 图片实现
 * @author hanxiaofan
 *
 */
@SuppressWarnings("unchecked")
@Service("pictureService")
public class PictureServiceImpl implements PictureService{

	@Resource(name="mapperSupport")
	private MapperSupport mapper;
	
	/**
	 * 
	 */
	@Override
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) mapper.findForList("PicturesMapper.datalistPage.", page);
	}

	@Override
	public void save(PageData pd) throws Exception {
		mapper.save("PicturesMapper.save", pd);
	}

	@Override
	public void delete(PageData pd) throws Exception {
		mapper.delete("PicturesMapper.delete", pd);
	}

	@Override
	public void edit(PageData pd) throws Exception {
		mapper.update("PicturesMapper.update", pd);
	}

	@Override
	public PageData findById(PageData pd) throws Exception {
		return (PageData) mapper.findForObject("PicturesMapper.findById", pd);
	}

	@Override
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		mapper.delete("PicturesMapper.deleteAll", ArrayDATA_IDS);
	}

	@Override
	public List<PageData> getAllById(String[] ArrayDATA_IDS) throws Exception {
		return (List<PageData>) mapper.findForList("PicturesMapper.getAllById", ArrayDATA_IDS);
	}
	
	/**
	 * 删除图片
	 */
	@Override
	public void delTp(PageData pd) throws Exception {
		mapper.delete("PicturesMapper.delTp", pd);
	}

}
