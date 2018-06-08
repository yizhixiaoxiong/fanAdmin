package com.fan.manage.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import common.utils.CommonUtils;

/**
 * 页面跳转控制器
 * @author hanxiaofan
 *
 */
@RequestMapping("page")
@Controller
public class PageController {
	
	@RequestMapping(value="{pageName}",method=RequestMethod.GET)
	public String toPage(@PathVariable("pageName") String pageName,HttpServletRequest sq){
		StringBuffer reqUrl = sq.getRequestURL();
		String ip = CommonUtils.getClientIP(sq);
		//将来包装在user用户里面
		System.out.println("请求路径为："+reqUrl+"\t"+"请求ip为"+ip);
		return "/last/"+pageName;
	}

}
