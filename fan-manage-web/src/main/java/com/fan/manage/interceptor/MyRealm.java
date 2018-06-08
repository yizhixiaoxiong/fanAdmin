package com.fan.manage.interceptor;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class MyRealm extends AuthorizingRealm{
	
	/**
	 * 登录信息和用户验证信息验证
	 * 验证当前用户
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		//得到用户名
		String username = (String) token.getPrincipal();
		//得到密码
		String password = new String((char[])token.getCredentials());
		 if(null != username && null != password){
			 return new SimpleAuthenticationInfo(username, password, getName());
		 }else{
			 return null;
		 }
	}
	
	/**
	 * 在验证完用户之后，授予权限。
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		System.out.println("========2");
		return null;
	}

	

}
