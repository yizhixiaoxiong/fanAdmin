package com.fan.manage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fan.manage.pojo.Role;
import com.fan.manage.service.ButtonRightsService;
import com.fan.manage.service.ButtonService;
import com.fan.manage.service.RoleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.utils.PageData;
import common.utils.Tools;

/**
 * 按钮控制
 * @author hanxiaofan
 *
 */
@Controller
@RequestMapping("buttonrights")
public class ButtonRightsController extends BaseController {
	
	@Resource(name = "buttonRightsService")
	private ButtonRightsService buttonRightsService;
	@Resource(name = "roleService")
	private RoleService roleService;
	@Resource(name = "buttonService")
	private ButtonService buttonService;
	
	@RequestMapping("/list")
	public ModelAndView buttonList() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		//type字段，用来切换视图
		String type = pd.getString("type");
		type = Tools.isEmpty(type) ? "0" : type;
		//如果前台没有传递角色id，则设置默认id
		if(pd.getString("ROLE_ID") == null || "".equals(pd.getString("ROLE_ID").trim())){
			pd.put("ROLE_ID", "1");//设置默认的角色id=1
		}
		
		PageData fpd = new PageData();
		fpd.put("ROLE_ID", "0");	//查找所有的角色组() 第一列
		
		List<Role> roleList = roleService.listAllRolesByPId(fpd);	//查找父id为0的组(系统管理组,会员组等)
		List<Role> roleList_z = roleService.listAllRolesByPId(pd);	//查找父id为1的角色(系统管理组下的角色)
		List<PageData> buttonlist = buttonService.listAll(pd);		//列出所有按钮
		List<PageData> roleFhbuttonlist = buttonRightsService.listAll(pd);	//列出所有的角色按钮关联数据
		pd = roleService.findObjectById(pd);
		mv.addObject("pd", pd);
		mv.addObject("roleList", roleList);
		mv.addObject("roleList_z", roleList_z);
		mv.addObject("buttonlist", buttonlist);
		mv.addObject("roleFhbuttonlist", roleFhbuttonlist);
		
		if("2".equals(type)){
			mv.setViewName("system/buttonrights/buttonrightsListC");
		}else{
			mv.setViewName("system/buttonrights/buttonrightsList");
		}
		return mv;
	}
	/**
	 * 更新按钮状态
	 * 参数：	ROLE_ID 	角色id
	 *  	BUTTON_ID	按钮id
	 * 		guid		时间戳
	 * 查找表 sys_role_fhbutton
	 * 返回json
	 * @throws Exception 
	 */
	@RequestMapping("/upRb")
	@ResponseBody
	public String updateRolebuttonrightd() throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String errInfo = "success";
		if( null != buttonRightsService.findById(pd)){	//关联表中是否存在，true:删除(前端显示否)/false:新增(前端显示是)
			buttonRightsService.delete(pd);//如果能查到，则删除
		}else{
			pd.put("RB_ID", this.get32UUID());
			buttonRightsService.add(pd);
		}
		map.put("result", errInfo);
		ObjectMapper mapper = new ObjectMapper();  
		String jsonfromMap =  mapper.writeValueAsString(map);
		return jsonfromMap;
	}
}
