package com.fan.manage.service;

import java.util.List;

import common.utils.PageData;

/**
 * 按钮权限接口
 * @author hanxiaofan
 *
 */
public interface ButtonRightsService {
	
	/**新增按钮
	 * @param pd
	 * @throws Exception
	 */
	public void add(PageData pd)throws Exception;
	
	/**通过(角色ID和按钮ID)获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	
	/**列表(全部)左连接按钮表,查出安全权限标识
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAllBrAndQxname(PageData pd)throws Exception;
}	
