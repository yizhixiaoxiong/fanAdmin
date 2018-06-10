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
	 * 列表
	 */
	@Override
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) mapper.findForList("PicMapper.datalistPage", page);
	}
	
	/**
	 * 新增(上传)
	 */
	@Override
	public void save(PageData pd) throws Exception {
		mapper.save("PicMapper.save", pd);
	}

	/**
	 * 删除
	 */
	@Override
	public void delete(PageData pd) throws Exception {
		mapper.delete("PicMapper.delete", pd);
	}
	
	/**
	 * 更新
	 */
	@Override
	public void edit(PageData pd) throws Exception {
		mapper.update("PicMapper.update", pd);
	}
	
	/**
	 * 根据id查找
	 */
	@Override
	public PageData findById(PageData pd) throws Exception {
		return (PageData) mapper.findForObject("PicMapper.findById", pd);
	}
	
	/**
	 * 根据id删除全部
	 */
	@Override
	public void deleteAll(String[] ids) throws Exception {
		mapper.delete("PicMapper.deleteAll", ids);
	}
	
	/**
	 * 获取全部
	 */
	@Override
	public List<PageData> getAllById(String[] ids) throws Exception {
		return (List<PageData>) mapper.findForList("PicMapper.getAllById", ids);
	}
	
	/**
	 * 删除图片
	 */
	@Override
	public void delTp(PageData pd) throws Exception {
		mapper.delete("PicMapper.delTp", pd);
	}

}
