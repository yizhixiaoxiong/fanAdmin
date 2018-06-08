package com.fan.manage.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fan.manage.pojo.User;

import serviceUtils.Jurisdiction;
import common.utils.Const;

/**
 * SpringMVC的访问拦截
 * 作用：拦截菜单，进行权限验证，在菜单内拦截以防止直接输入菜单进入
 * @author hanxiaofan
 *
 */
public class LoginHandlerInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		//String path = request.getServletPath();	//返回的的是工程路径之后的部分，也就是http://127.0.0.1：8082/工程名/(之后的部分，也就是菜单)	
		String path = request.getRequestURI();	
		if(path.matches(Const.NO_INTERCEPTOR_PATH)){//如果匹配这些，直接放行
			return true;
		}else{
			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
			if(user != null){
				path = path.substring(1,path.length());	//去掉路径的"/"
				path = path.substring(path.indexOf("/")+1, path.length());
				
				boolean b = Jurisdiction.hasJurisdiction(path);	//访问校验权限
				if(!b){	//如果没有，则跳转
					response.sendRedirect(request.getContextPath()+Const.LOGIN);//getContextPath() /工程名
				}
				return b;
			}else{
				//登陆过滤
				response.sendRedirect(request.getContextPath() + Const.LOGIN);
				return false;
			}
		}
	}
}
