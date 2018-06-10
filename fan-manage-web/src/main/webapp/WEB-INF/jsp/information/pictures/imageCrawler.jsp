<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head lang="en">
<base href="<%=basePath%>">
<!-- jsp文件头和头部 -->
<!-- page specific plugin styles -->
<link rel="stylesheet" href="static/ace/css/colorbox.css" />
<%@ include file="../../system/index/top.jsp"%>
</head>
<body>
<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container">
	<!-- /section:basics/sidebar -->
	<div class="main-content">
		<div class="main-content-inner">
			<div class="page-content">
				<div class="hr hr-18 dotted hr-double"></div>
				<div class="row">
					<div class="col-xs-12">
					
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>