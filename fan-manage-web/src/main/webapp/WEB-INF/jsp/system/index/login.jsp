<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html >
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet" href="rest/static/login/bootstrap.min.css" />
<link rel="stylesheet" href="rest/static/login/css/camera.css" />
<link rel="stylesheet" href="rest/static/login/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="rest/static/login/matrix-login.css" />
<link href="rest/static/login/font-awesome.css" rel="stylesheet" />
<script type="text/javascript" src="rest/static/js/jquery-1.7.2.js"></script>

<script type="text/javascript">
if(self!=top){
	top.location = self.location;
}
</script>
<title>登陆</title>
</head>
<body>
<div style="width:100%;text-align: center;margin: 0 auto;position: absolute;">
	<!-- 登录 -->
	<div id="windows1">
		<div id="loginbox" >
			<form action="" method="post" name="loginForm" id="loginForm">
				<!-- logo图片 control-group是bootstrap 表单-->
				<div class="control-group ">
					<h3>
						<img src="rest/static/login/logo.png">
					</h3>					
				</div>
				<!-- 表单 -->
				<!-- 名字 -->
				<div class="control-group">
					<div class="controls">
						<div class="main_input_box">
							<span class="add-on bg_lg">
								<i><img height="37" src="rest/static/login/user.png" /></i>
							</span>
							<input type="text" name="loginname" id="loginname" value="" placeholder="请输入用户名" />
						</div>
					</div>
				</div>
				
				<!-- 密码 -->
				<div class="control-group">
					<div class="controls">
						<div class="main_input_box">
							<span class="add-on bg_lg">
								<i><img height="37" src="rest/static/login/suo.png" /></i>
							</span>
							<input type="password" name="password" id="password" placeholder="请输入密码" value=""/>
						</div>						
					</div>
				</div>
				
				<!-- 记住密码-->
				<div style="float:right;padding-right:10%;">
					<div style="float: left;margin-top:3px;margin-right:2px;">
						<font color="white">记住密码</font>
					</div>
					
					<div style="float: left;">
						<input name="form-field-checkbox" id ="saveid" type="checkbox" 
									onclick="" style="padding-top:0px;"/>
					</div>
				</div>
				
				<!-- 第四行 -->
				<div class="form-actions">
					<div style="width:86%;padding-left:8%;">
						<div style="float: left;padding-top:2px;">
							<i><img src="rest/static/login/yan.png" /></i>
						</div>
						<div style="float: left;" class="codediv">
							<input type="text" name="code" id="code"  class="login_code" style="height:16px; padding-top:4px;"/>
						</div>
						
						<div style="float: left;">
							<i><img style="height:22px;" id="codeImg" alt="点击更换" title="点击更换" src=""/></i>
						</div>
						
						<span class="pull-right" style="padding-right:3%;"><a href="javascript:changepage(1);" class="btn btn-success">注册</a></span>
						<span class="pull-right"><a onclick="severCheck();" class="flip-link btn btn-info" id="to-recover">登录</a></span>
					</div>
				</div>
			</form>
			<div class="controls">
				<div class="main_input_box">
					<font color="white">
						<span id="nameerr">Copyright © FANqq347057513 </span>
					</font>
				</div>
			</div>
		</div>
	</div>
	
	<div id="windows2" style="display: none;">
		<div id="loginbox">
			<form action="" method="post" name="loginForm" id="loginForm">
				<div class="control-group normal_text">
					<h3>
						<img src="rest/static/login/logo.png" alt="Logo" />
					</h3>
				</div>
				<div class="control-group">
					<div class="controls">
						<div class="main_input_box">
							<span class="add-on bg_lg">
							<i>用户</i>
							</span><input type="text" name="USERNAME" id="USERNAME" value="" placeholder="请输入用户名" />
						</div>
					</div>
				</div>
				<div class="control-group">
					<div class="controls">
						<div class="main_input_box">
							<span class="add-on bg_ly">
							<i>密码</i>
							</span><input type="password" name="PASSWORD" id="PASSWORD" placeholder="请输入密码" class="keypad" keypadMode="full" allowKeyboard="true" value=""/>
						</div>
					</div>
				</div>
				<div class="control-group">
					<div class="controls">
						<div class="main_input_box">
							<span class="add-on bg_ly">
							<i>重输</i>
							</span><input type="password" name="chkpwd" id="chkpwd" placeholder="请重新输入密码" class="keypad" keypadMode="full" allowKeyboard="true" value=""/>
						</div>
					</div>
				</div>
				<div class="control-group">
					<div class="controls">
						<div class="main_input_box">
							<span class="add-on bg_lg">
							<i>姓名</i>
							</span><input type="text" name="NAME" id="name" value="" placeholder="请输入姓名" />
						</div>
					</div>
				</div>
				<div class="control-group">
					<div class="controls">
						<div class="main_input_box">
							<span class="add-on bg_lg">
							<i>邮箱</i>
							</span><input type="text" name="EMAIL" id="EMAIL" value="" placeholder="请输入邮箱" />
						</div>
					</div>
				</div>
				<div class="form-actions">
					<div style="width:86%;padding-left:8%;">

						<div style="float: left;padding-top:2px;">
							<i><img src="rest/static/login/yan.png" /></i>
						</div>
						<div style="float: left;" class="codediv">
							<input type="text" name="rcode" id="rcode" class="login_code"
								style="height:16px; padding-top:4px;" />
						</div>
						<div style="float: left;">
							<i><img style="height:22px;" id="zcodeImg" alt="点击更换" title="点击更换" src="" /></i>
						</div>
						<span class="pull-right" style="padding-right:3%;"><a href="javascript:changepage(2);" class="btn btn-success">取消</a></span>
						<span class="pull-right"><a onclick="register();" class="flip-link btn btn-info" id="to-recover">提交</a></span>
					</div>
				</div>
			</form>
			<div class="controls">
				<div class="main_input_box">
					<font color="white"><span id="nameerr">Copyright © FHqq313596790 2100</span></font>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
	//加载完之后执行
	$(document).ready(function() {
		changeCode1();
		$("#codeImg").bind("click", changeCode1);
		$("#zcodeImg").bind("click", changeCode2);
	});
	
	function changeCode1() {
		$("#codeImg").attr("src", "/rest/code?t=" + genTimestamp());
	}
	function changeCode2() {
		$("#zcodeImg").attr("src", "/rest/code?t=" + genTimestamp());
	}
	
	function genTimestamp() {
		var time = new Date();
		return time.getTime();
	}
	//登陆
	function severCheck(){
		var loginname = $("#loginname").val();
		var password = $("#password").val();
		var code = loginname+","+password+","+$("#code").val();
		$.ajax({
			type:"POST",
			url:"rest/login_login",
			data:{KEYDATA:code,tm:new Date().getTime()},
			dataType:"json",
			success:function(data){
				if("success" == data.result){
					window.location.href="rest/main/index";
				}else{
					window.location.href="/"
				}
			}
		});
	}


	function changepage(value){
		if(value == 1){
			$("#windows1").hide();
			$("#windows2").show();
		}else{
			$("#windows2").hide();
			$("#windows1").show();
		}
	}

</script>
</body>
</html>