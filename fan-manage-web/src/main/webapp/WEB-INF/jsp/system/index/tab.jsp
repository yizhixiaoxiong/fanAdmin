<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
	<script type="text/javascript" src="../static/js/jquery-1.7.2.js"></script>
	<script type="text/javascript" src="../plugins/tab/js/framework.js"></script>
	<link href="../plugins/tab/css/import_basic.css" rel="stylesheet" type="text/css"/>
	<link  rel="stylesheet" type="text/css" id="skin" prePath="../plugins/tab/" /><!--默认相对于根目录路径为../，可添加prePath属性自定义相对路径，如prePath="<%=request.getContextPath()%>"-->
	<script type="text/javascript" charset="utf-8" src="../plugins/tab/js/tab.js"></script>
	</head>
<body>
<div id="tab_menu"></div>
<div style="width:100%;">
	<div id="page" style="width:100%;height:100%;"></div>
</div>		
</body>
<script type="text/javascript">
//讲解这部分脚本，首先，声明了一个变量tab，加载页面的时候执行一个自动函数，new 一个tabView，然后加载一个tab菜单。当点击左侧菜单的时候
//执行一个函数siMenu(head.js)中，在那里执行top.mainFrame.tabAddHandler(id,MENU_NAME,MENU_URL);
function tabAddHandler(mid,mtitle,murl){
	tab.update({
		id :mid,
		title :mtitle,
		url :murl,
		isClosed :true
	});
	tab.add({
		id :mid,
		title :mtitle,
		url :murl,
		isClosed :true
	});

	tab.activate(mid);
}
 var tab;	
$( function() {
	 tab = new TabView( {
		containerId :'tab_menu',	//tab菜单
		pageid :'page',				//对应个iframeid
		cid :'tab1',				//tabid
		position :"top"				//
	});
	tab.add( {
		id :'tab1_index1',
		title :"主页",
		url :"login_default",
		isClosed :false
	});
	/**tab.add( {
		id :'tab1_index1',
		title :"主页",
		url :"/per/undoTask!gettwo",
		isClosed :false
	});
	**/
});

function cmainFrameT(){
	var hmainT = document.getElementById("page");
	var bheightT = document.documentElement.clientHeight;
	hmainT .style.width = '100%';
	hmainT .style.height = (bheightT  - 41) + 'px';
}
cmainFrameT();
window.onresize=function(){  
	cmainFrameT();
};

</script>
</html>

