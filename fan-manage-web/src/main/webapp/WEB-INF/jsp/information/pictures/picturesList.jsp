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
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>${pd.SYSNAME}</title>
<meta name="description" content="" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

<link rel="stylesheet" href="static/ace/css/bootstrap.min.css" />
<link rel="stylesheet" href="static/ace/css/font-awesome.css" />
<link rel="stylesheet" href="static/ace/css/ace-fonts.css" />
<link rel="stylesheet" href="static/ace/css/ace.css" class="ace-main-stylesheet" id="main-ace-style" />
<script src="static/ace/js/ace-extra.js"></script>
<!-- 图片插件 -->
<link rel="stylesheet" media="screen" type="text/css" href="plugins/zoomimage/css/zoomimage.css" />
<link rel="stylesheet" media="screen" type="text/css" href="plugins/zoomimage/css/custom.css" />
<script type="text/javascript" src="plugins/zoomimage/js/jquery.js"></script>
<script type="text/javascript" src="plugins/zoomimage/js/eye.js"></script>
<script type="text/javascript" src="plugins/zoomimage/js/utils.js"></script>
<script type="text/javascript" src="plugins/zoomimage/js/zoomimage.js"></script>
<script type="text/javascript" src="plugins/zoomimage/js/layout.js"></script>
</head>
<body class="no-skin">

	<!-- 图片列表 -->
	<div class="main-container" id="main-container">
		<div class="main-content">
			<div class="main-content-inner">
				<form action="rest/pictures/list" method="post" name="Form" id="Form">
					<!-- 头部检索部分 -->
					<table style="margin-top:5px;">
						<tr>
							<td>
								<div class="nav-search">
									<span class="input-icon">
										<input autocomplete="off" class="nav-search-input"  id="nav-search-input" type="text" name="keyword"  value="${pd.keyword}" placeholder="这里输入关键词" />
										<i class="ace-icon fa fa-search nav-search-icon"></i>
									</span>
								</div>
							</td>
							<c:if test="${QX.cha == 1}">
								<td style="vertical-align:top;padding-left:2px;">
									<a class="btn btn-light btn-xs" onclick="searchs();"  title="检索">
										<i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i>
									</a>
								</td>
							</c:if>
						</tr>
					</table>
					
					<table id="simple-table" class="table table-striped table-bordered table-hover"  style="margin-top:0px;">
						<thead>
							<tr>
								<th class="center" onclick="selectAll()" style="width:35px;">
									<label>
										<input type="checkbox" id="zcheckbox" class="ace" />
										<span class="lbl"></span>
									</label>
								</th>
								<th class="center" style="width:50px;">序号</th>
								<th class="center">图片</th>
								<th class="center">标题</th>
								<th class="center">ID</th>
								<th class="center">创建时间</th>
								<th class="center">属于</th>
								<th class="center">图片管理处上传</th>
								<th class="center">操作</th>
							</tr>
						</thead>
						<tbody>
						<!-- 开始循环 -->
						<c:choose>
							<c:when test="${not empty varList}">
								<c:if test="${QX.cha == 1 }"><!-- 有查的权限 -->
								<c:forEach items="${varList}" var="var" varStatus="vs">
									<tr>
										<td class='center' style="width:30px;">
											<label>
												<input type='checkbox' name='ids' class="ace" value="${var.PICTURES_ID}" />
												<span class="lbl"></span>
											</label>
										</td>
										<td class='center' style="width:30px;">${vs.index+1}</td>
										<td class="center">
											<a href="<%=basePath%>rest/pictures/IoReadImage/${var.PATH}" title="${var.TITLE}" class="bwGal">
												<img src="<%=basePath%>rest/pictures/IoReadImage/${var.PATH}" value="${var.PATH}" alt="${var.TITLE}" width="100" id="pic">
											</a>
										</td>
										<td class='center'>${var.TITLE}</td>
										<td class='center'>${var.PICTURES_ID}</td>
										<td class="center">${var.CREATETIME}</td>
										<td class="center">${var.MASTER_ID}</td>
										<td class="center">${var.BZ}</td>
										<td class="center" style="width:130px;">
										<c:if test="${QX.edit != 1 && QX.del != 1 }">
										<span class="label label-large label-grey arrowed-in-right arrowed-in">
											<i class="ace-icon fa fa-lock" title="无权限"></i>
										</span>	
										</c:if>
										<c:if test="${QX.edit == 1 }">
											<a style="cursor:pointer;" class="green" onclick="edit('${var.PICTURES_ID}');" title="编辑">
												<i class="ace-icon fa fa-pencil bigger-130"></i>
											</a>
											</c:if>
											&nbsp;
											<c:if test="${QX.del == 1 }">
											<a style="cursor:pointer;" class="red" onclick="del('${var.PICTURES_ID}','${var.PATH}');" title="删除">
												<i class="ace-icon fa fa-trash-o bigger-130"></i>
											</a>
											</c:if>
										</td>
									</tr>
								</c:forEach>
								</c:if>
								<c:if test="${QX.cha == 0 }">
									<tr>
										<td colspan="100" class="center">您无权查看</td>
									</tr>
								</c:if>
							</c:when>
							<c:otherwise>
								<tr>
									<td colspan="100" class="center">没有数据</td>
								</tr>
							</c:otherwise>
						</c:choose>
						</tbody>
					</table>
					
					<div class="page-header position-relative">
					<table style="width:100%;">
						<tr>
							<td style="vertical-align:top;">
								<c:if test="${QX.add == 1 }">
								<a class="btn btn-sm btn-success" onclick="add();">新增</a>
								</c:if>
								<c:if test="${QX.del == 1 }">
								<a title="批量删除" class="btn btn-sm btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" ><i class='ace-icon fa fa-trash-o bigger-120'></i></a>
								</c:if>
							</td>
							<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
						</tr>
					</table>
					</div>
				</form>
			</div>
		</div>
		
		<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
			<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>
	</div>
	
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- 提示窗口 -->
	<script type="text/javascript">
		$(top.hangge());
		
		$("a.bwGal").zoomimage({
			border: 20,
			centered: true,
			hideSource: true
		});
		
		//全选按钮
		function selectAll(){
			var checkList = document.getElementsByName ("ids");
			if(document.getElementById("zcheckbox").checked){
				for(var i = 0;checkList.length;i++){
					checkList[i].checked = true;
				}
			}else{
				for(var i = 0;checkList.length;i++){
					checkList[i].checked = false;
				}
			}
		}
		
		//新增
		function add(){
		 	top.jzts();
			var diag = new top.Dialog();
			diag.Drag=true;
			diag.Title ="新增";
			diag.URL = '<%=basePath%>rest/pictures/goAdd';
			diag.Width = 800;
		 	diag.Height = 490;
		 	diag.CancelEvent = function(){
	 			if('${page.currentPage}' == '0'){
					 top.jzts();
					 setTimeout("self.location=self.location",100);
				 }else{
					 nextPage(${page.currentPage});
				 }
	 			diag.close();
		 	};
	 	 	diag.show();
		}
		//编辑
		function edit(Id){
			top.jzts();
			var diag = new top.Dialog();
			diag.Drag=true;
			diag.Title ="编辑";
			diag.URL = '<%=basePath%>rest/pictures/goEdit?PICTURES_ID='+Id;
			diag.Width = 800;
		 	diag.Height = 490;
		 	diag.CancelEvent = function(){
		 		if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 nextPage(${page.currentPage});
				}
		 		diag.close();
		 	};
		 	 diag.show();
		}
		//删除
		//参数：id和路径
		function del(Id,path){
			if(confirm("确当删除？")){
				top.jzts();
				var url = "<%=basePath%>rest/pictures/delete?PICTURES_ID="+Id+"&PATH="+path+"&tm="+new Date().getTime();
				$.get(url,function(data){
					alert(data);
					data = JSON.parse(data);
					if("success" == data.result){
						nextPage(${page.currentPage});
					}else{
						alert("删除失败");
					}
				});
			}
		}
		//批量删除
		//目标:获取ids
		function makeAll(msg){
			if(confirm("确定删除全部？")){
				var checked = document.getElementsByName("ids");	//所有的checked
				var str = '';
				for (var i = 0; i < checked.length; i++) {
					if(checked[i].checked){			//如果被选中
						if( str == ''){
							str += checked[i].value;
						}else{
							str += ','+checked[i].value;
						}
					}
				}
				if(str==''){
					alert("您没有选择任何内容!"); 
					return;
				}else{
					if(msg == '确定要删除选中的数据吗?'){
						top.jzts();
						$.ajax({
							type:"post",
							url: '<%=basePath%>rest/pictures/deleteAll?tm='+new Date().getTime(),
					    	data: {DATA_IDS:str},
							dataType:'json',
							cache:false,
							success:function(data){
								if("success" == data.result){
									setTimeout("self.location=self.location",100);
									nextPage(${page.currentPage});
								}
							}
						});
					}
				}
			}
		}
	</script>
</body>

</html>