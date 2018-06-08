<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" /> 
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="description" content="page description" /> 

<title>后台ace整合</title>

<!-- 引入外部文件 -->
<!-- bootstrap & fontawesome -->
<!-- <link rel="stylesheet" href="static/ace/css/bootstrap.css" /> -->
<!-- 应该是字体 -->
<!-- <link rel="stylesheet" href="static/ace/css/font-awesome.css" /> -->

<!-- <link rel="stylesheet" href="static/ace/css/ace-fonts.css" />
<link rel="stylesheet" href="static/ace/css/ace.css" class="ace-main-stylesheet" id="main-ace-style" />
<script src="static/ace/js/ace-extra.js"></script> -->

<script type="text/javascript" src="../static/js/jquery-3.2.1.js"></script>
<script type="text/javascript" src="../static/js/bootstrap.js"></script>

<link rel="stylesheet" type="text/css" href="../static/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="../static/css/bootstrap.css.map">
<link rel="stylesheet" type="text/css" href="../static/css/bootstrap-tab.css">
<!-- ACE模板 -->
<link rel="stylesheet" type="text/css" href="../static/css/ace.min.css">
<link rel="stylesheet" type="text/css" href="../static/css/ace-rtl.min.css">
<link rel="stylesheet" type="text/css" href="../static/css/ace-skins.min.css">
<link rel="stylesheet" type="text/css" href="../static/css/font-awesome.min.css">

<!-- ACE模板js-->
<script type="text/javascript" src="../static/js/ace-extra.min.js"></script>
<script type="text/javascript" src="../static/js/ace.min.js"></script>
<script type="text/javascript" src="../static/js/ace-elements.min.js"></script>
<!--封装-->
<script type="text/javascript" src="../static/js/sidebar-menu.js"></script>
<script type="text/javascript" src="../static/js/bootstrap-tab.js"></script>

<script type="text/javascript">
$(function () {
  $('#menu').sidebarMenu({
  data: [{
   id: '1',
   text: '系统设置',
   icon: 'icon-cog',
   url: '',
   menus: [{
   id: '11',
   text: '编码管理',
   icon: 'icon-glass',
   url: 'C:/Users/hanxiaofan/Desktop/testAce/test1.html'
   }]
  }, {
   id: '2',
   text: '基础数据',
   icon: 'icon-leaf',
   url: '',
   menus: [{
   id: '21',
   text: '基础特征',
   icon: 'icon-glass',
   url: 'C:/Users/hanxiaofan/Desktop/testAce/test4.html'
   }, {
   id: '22',
   text: '特征管理',
   icon: 'icon-glass',
   url: '/BasicData/Features/Index'
   }, {
   id: '23',
   text: '物料维护',
   icon: 'icon-glass',
   url: '/Model/Index'
   }, {
   id: '24',
   text: '站点管理',
   icon: 'icon-glass',
   url: '/Station/Index'
   }]
  }, {
   id: '3',
   text: '权限管理',
   icon: 'icon-user',
   url: '',
   menus: [{
   id: '31',
   text: '用户管理',
   icon: 'icon-user',
   url: '/SystemSetting/User'
   }, {
   id: '32',
   text: '角色管理',
   icon: 'icon-apple',
   url: '/SystemSetting/Role'
   }, {
   id: '33',
   text: '菜单管理',
   icon: 'icon-list',
   url: '/SystemSetting/Menu'
   }, {
   id: '34',
   text: '部门管理',
   icon: 'icon-glass',
   url: '/SystemSetting/Department'
   }]
  }, {
   id: '4',
   text: '订单管理',
   icon: 'icon-envelope',
   url: '',
   menus: [{
   id: '41',
   text: '订单查询',
   icon: 'icon-glass',
   url: '/Order/Query'
   }, {
   id: '42',
   text: '订单排产',
   icon: 'icon-glass',
   url: '/Order/PLANTPRODUCT'
   }, {
   id: '43',
   text: '订单撤排',
   icon: 'icon-glass',
   url: '/Order/cancelPRODUCT'
   }, {
   id: '44',
   text: '订单HOLD',
   icon: 'icon-glass',
   url: '/Order/hold'
   }, {
   id: '45',
   text: '订单删除',
   icon: 'icon-glass',
   url: '/Order/delete'
   }, {
   id: '47',
   text: '订单插单',
   icon: 'icon-glass',
   url: '/Order/insertorder'
   }, {
   id: '48',
   text: '订单导入',
   icon: 'icon-glass',
   url: '/Order/Import'
   }]
  }]
  });
 }); 
$(function () {
    //固定左边菜单的高度
    $('#sidebar').height($(window).height() - 80);
}); 

</script>
<style type="text/css">
html {
	width: 100%;
	overflow: hidden;
}
/* 用来固定左边菜单的高度的css */
#sidebar {
	overflow-x: hidden;
	overflow-y: auto;
}
</style>

</head>
<body>
	<!-- head 使用组件 navbar-->
	<%-- <jsp:include page="head1.jsp"></jsp:include> --%>
	<%@include file="head2.jsp" %>

	<div class="main-container" id="main-container">
		<script type="text/javascript">
			try{ace.settings.check('main-container' , 'fixed')}catch(e){}
		</script>
		<div class="main-container-inner">
			<a class="menu-toggler" id="menu-toggler" href="#"> <span
				class="menu-text"></span>
			</a>
			
			<!-- 左侧菜单组件 -->
			<jsp:include page="left.jsp"></jsp:include>
			<!-- 左侧菜单完结 -->

			<div class="main-content">
				<div class="breadcrumbs" id="breadcrumbs">
					<script type="text/javascript">
						try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
					</script>
					
					<!-- tab 窗口 -->
					<ul class="breadcrumb">
						<li><i class="icon-home home-icon"></i> <a href="#">首页</a></li>
						<li class="active">控制台 (这部分待定)</li>
					</ul>
					<!-- tab 标签 -->
					
					<!-- .breadcrumb -->
					
					<!-- 右侧搜索功能 -->
					<div class="nav-search" id="nav-search">
						<form class="form-search">
							<span class="input-icon"> <input type="text"
								placeholder="Search ..." class="nav-search-input"
								id="nav-search-input" autocomplete="off" /> <i
								class="icon-search nav-search-icon"></i>
							</span>
						</form>
					</div>
					<!-- #nav-search -->
				</div>

				
				<!-- 主内容显示 -->
				<div class="page-content">
					<div class="row">
						<div class="col-xs-12" style="padding-left: 5px;">
							<ul class="nav nav-tabs" role="tablist">
								<li class="active"><a href="#Index" role="tab"
									data-toggle="tab">首页</a></li>
							</ul>
							<div class="tab-content" style="height: 100%;">
								<div role="tabpanel" class="tab-pane active" id="Index"></div>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="ace-settings-container" id="ace-settings-container">
				<div class="btn btn-app btn-xs btn-warning ace-settings-btn"
					id="ace-settings-btn">
					<i class="icon-cog bigger-150"></i>
				</div>

				<div class="ace-settings-box" id="ace-settings-box">
					<div>
						<div class="pull-left">
							<select id="skin-colorpicker" class="hide">
								<option data-skin="default" value="#438EB9">#438EB9</option>
								<option data-skin="skin-1" value="#222A2D">#222A2D</option>
								<option data-skin="skin-2" value="#C6487E">#C6487E</option>
								<option data-skin="skin-3" value="#D0D0D0">#D0D0D0</option>
							</select>
						</div>
						<span>&nbsp; 选择皮肤</span>
					</div>

					<div>
						<input type="checkbox" class="ace ace-checkbox-2"
							id="ace-settings-navbar" /> <label class="lbl"
							for="ace-settings-navbar"> 固定导航条</label>
					</div>

					<div>
						<input type="checkbox" class="ace ace-checkbox-2"
							id="ace-settings-sidebar" /> <label class="lbl"
							for="ace-settings-sidebar"> 固定滑动条</label>
					</div>

					<div>
						<input type="checkbox" class="ace ace-checkbox-2"
							id="ace-settings-breadcrumbs" /> <label class="lbl"
							for="ace-settings-breadcrumbs">固定面包屑</label>
					</div>

					<div>
						<input type="checkbox" class="ace ace-checkbox-2"
							id="ace-settings-rtl" /> <label class="lbl"
							for="ace-settings-rtl">切换到左边</label>
					</div>

					<div>
						<input type="checkbox" class="ace ace-checkbox-2"
							id="ace-settings-add-container" /> <label class="lbl"
							for="ace-settings-add-container"> 切换窄屏 <b></b>
						</label>
					</div>
				</div>
			</div>

		</div>
	</div>

</body>
</html>