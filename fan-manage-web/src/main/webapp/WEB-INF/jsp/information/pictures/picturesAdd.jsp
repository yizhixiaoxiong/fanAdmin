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
<html lang="en">
<head>
<base href="<%=basePath%>">
<%@ include file="../../system/index/top.jsp"%>
<!-- 上传插件 -->
<link rel="stylesheet" type="text/css" href="plugins/webuploader/webuploader.css" />
<link rel="stylesheet" type="text/css" href="plugins/webuploader/style.css" />
</head>
<body class="no-skin">

	<!-- 弹窗上传图片 -->
	<div class="main-container" id="main-container">
		<div class="main-content">
			<div class="main-content-inner">
				<div id="wrapper">
					<div id="container">
						<!--头部，相册选择和格式选择-->
						<div id="uploader">
							<div class="queueList">
			                    <div id="dndArea" class="placeholder">
			                        <div id="filePicker"></div>
			                        <p>或将照片拖到这里，单次最多可选300张</p>
			                    </div>
			                </div>
			                <!-- 当加入图片的时候，调整样式 -->
			                <div class="statusBar" style="display:none;">
			                    <div class="progress">
			                        <span class="text">0%</span>
			                        <span class="percentage"></span>
			                    </div><div class="info"></div>
			                    <div class="btns">
			                        <div id="filePicker2"></div>
			                        <div class="uploadBtn">开始上传</div>
			                    </div>
			                </div>
						</div>
					</div>				
				</div>
				</div>
			</div>
		</div>
		
	
	<%@include file="../../system/index/foot.jsp" %>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- webuploader上传插件js -->
   	<script type="text/javascript" src="plugins/webuploader/webuploader.js"></script>
   	<script type="text/javascript" src="plugins/webuploader/upload.js"></script>
   	<script type="text/javascript">
		$(top.hangge());
	</script>
</body>
</html>