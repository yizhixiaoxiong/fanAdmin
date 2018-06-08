<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>

<%@include file="top.jsp" %>
</head>
<body>
<%@include file="head.jsp" %>

<div class="main-container" id="main-container">
	<script type="text/javascript">
		try{ace.settings.check('main-container' , 'fixed')}catch(e){}
	</script>

	<div class="main-container-inner">
		<a class="menu-toggler" id="menu-toggler" href="#">
			<span class="menu-text"></span>
		</a>

		<%@include file="left.jsp" %>
		
		<div class="main-content">
			<!-- <div class="breadcrumbs" id="breadcrumbs">
				<script type="text/javascript">
					try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
				</script>

				<ul class="breadcrumb">
					<li>
						<i class="icon-home home-icon"></i>
						<a href="#">Home</a>
					</li>

					<li>
						<a href="#">Other Pages</a>
					</li>
					<li class="active">Blank Page</li>
				</ul>.breadcrumb

				<div class="nav-search" id="nav-search">
					<form class="form-search">
						<span class="input-icon">
							<input type="text" placeholder="Search ..." class="nav-search-input" id="nav-search-input" autocomplete="off" />
							<i class="icon-search nav-search-icon"></i>
						</span>
					</form>
				</div>#nav-search
			</div> -->

			<div class="page-content">
				<div class="row">
					<div>
						<!-- 理解：iframe在加载的时候访问tab.do方法，然后在返回一个页面，也就是tab.jsp页面 -->
						<!-- <iframe name="mainFrame" id="mainFrame" frameborder="0" src="tab" style="margin:0 auto;width:100%;height:100%;"></iframe> -->
						<input type="text" name="txt" id="txt">  
    					<input type="button" id="btn" value="获取"> 
						<div  id="div"></div> 
					</div>
				</div><!-- /.row -->
			</div><!-- /.page-content -->
		</div><!-- /.main-content -->

		<div class="ace-settings-container" id="ace-settings-container">
			<div class="btn btn-app btn-xs btn-warning ace-settings-btn" id="ace-settings-btn">
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
					<span>&nbsp; Choose Skin</span>
				</div>

				<div>
					<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-navbar" />
					<label class="lbl" for="ace-settings-navbar"> Fixed Navbar</label>
				</div>

				<div>
					<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-add-container" />
					<label class="lbl" for="ace-settings-add-container">
						Inside
						<b>.container</b>
					</label>
				</div>
			</div>
		</div><!-- /#ace-settings-container -->
	</div><!-- /.main-container-inner -->

	<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
		<i class="icon-double-angle-up icon-only bigger-110"></i>
	</a>
</div><!-- /.main-container -->

<%@include file="foot.jsp" %>
<script type="text/javascript">
function cmainFrame(){
	var hmain = document.getElementById("div");
 	var bheight = document.documentElement.clientHeight;
 	hmain.style.width = "100%";
	hmain.style.height=(bheight-109)+"px";
	alert(bheight-120);
	alert("加载main");
}
cmainFrame();
window.onresize = function(){
	cmainFrame();
}

$("#btn").click(function(){  
	alert(txt);
    $.ajax({  
        type:"GET",  
        url:"http://127.1.1.1:8082/rest/login/"+$("#txt").val(),  
        dataType:'text',  
        error: function(){alert('error');},  
        success:function(data){  
            $("#div").html(data);  
        }  
    });  
}); 
</script>
</body>
</html>