<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html >
<html lang="en">
<head>
<base href="<%=basePath%>">

<!-- 下拉框 -->
<link rel="stylesheet" href="static/ace/css/chosen.css" />
<%@ include file="../../system/index/top.jsp" %>
<!-- 日期框 -->
<link rel="stylesheet" href="static/ace/css/datepicker.css" />
</head>
<body>
<!-- file	管理 -->
<div class="main-container" id="main-container">
	<!-- /section:basics/sidebar -->
	<div class="main-content">
		<div class="main-content-inner">
			<div class="page-content">
				<div class="row">
					<div class="col-xs-12">
						<form action="rest/fhfile/list" method="post" name="Form" id="Form">
							<table style="margin-top:5px;">
								<tr>
									<td>
										<div class="nav-search">
											<span class="input-icon">
												<input type="text" placeholder="这里输入关键词" class="nav-search-input" id="nav-search-input" autocomplete="off" name="keywords" value="${pd.keywords }" placeholder="这里输入关键词"/>
												<i class="ace-icon fa fa-search nav-search-icon"></i>
											</span>
										</div>
									</td>
									<!-- 检索 -->
									<c:if test="${QX.cha == 1 }">
									<td style="vertical-align: top;padding-left:2px ">
										<a class="btn btn-light btn-xs" onclick="tosearch();"  title="检索">
											<i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i>
										</a>
									</td>
									</c:if>
								</tr>
							</table>
							
							<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">
								<thead>
									<tr>
										<th class="center" style="width:35px;">
											<label class="pos-rel">
												<input type="checkbox" class="ace" id="zcheckbox"/>
												<span class="lbl"></span>
											</label>
										</th>
										<th class="center" style="width:50px;">序号</th>
										<th class="center">文件名</th>
										<th class="center">上传者</th>
										<td class='center'>源文件</td>
										<th class="center">备注</th>
										<th class="center">上传时间</th>
										<th class="center">文件大小</th>
										<th class="center">下载次数</th>
										<th class="center">操作</th>
									</tr>
								</thead>
								<tbody>
									<!-- 开始循环 -->
									<c:choose>
										<c:when test="${not empty fileList }">
											<c:forEach items="${fileList}" var="var" varStatus="vs">												
												<tr>
													<td class='center'>
														<label class="pos-rel"><input type='checkbox' name='ids' value="${var.FHFILE_ID}" class="ace" /><span class="lbl"></span></label>
													</td>
													<td class='center'>${vs.index+1}</td>
													<td class='center'>
														<img style="margin-top: -3px;" alt="${var.NAME}" src="static/images/extension/${var.fileType}.png">
														${var.NAME}
														&nbsp;
														<c:if test="${var.fileType == 'tupian' }">
															<a style="cursor:pointer;" onmouseover="showTU('uploadFiles/uploadFile/${var.FILEPATH}','yulantu${vs.index+1}');" onmouseout="hideTU('yulantu${vs.index+1}');">[预览]</a>
														</c:if>
														<c:if test="${var.fileType == 'pdf' }"><a style="cursor:pointer;" onclick="goViewPdf('${var.NAME}${fn:substring(var.FILEPATH ,19,fn:length(var.FILEPATH))}','${var.FHFILE_ID}');">[预览]</a></c:if>
														<c:if test="${var.fileType == 'wenben' }"><a style="cursor:pointer;" onclick="goViewTxt('${var.NAME}${fn:substring(var.FILEPATH ,19,fn:length(var.FILEPATH))}','${var.FHFILE_ID}','gbk');">[预览]</a></c:if>
														<div class="yulantu" id="yulantu${vs.index+1}"></div>
													</td>
													<td class='center'>${var.USERNAME}</td>
													<td class='center'>${var.OLDNAME}</td>
													<td class='center'>${var.BZ}</td>
													<td class='center' style="width:150px;">${var.CTIME}</td>
													<td class='center' style="width:100px;">${var.FILESIZE}&nbsp;KB</td>
													<td class='center' style="width:100px;" id="${var.FHFILE_ID}">${var.DOWNLOAD}&nbsp;次</td>
													<td class='center' style="width:150px;">
														<c:if test="${QX.edit != 1 && QX.del != 1 }">
														<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
														</c:if>
														<div class="hidden-sm hidden-xs btn-group">
															<c:if test="${QX.edit == 1 }">
															<a class="btn btn-xs btn-success" title="下载" onclick="window.location.href='<%=basePath%>rest/fhfile/download?FHFILE_ID=${var.FHFILE_ID}'">
																<i class="ace-icon fa fa-cloud-download bigger-120" title="下载"></i>
															</a>
															</c:if>
															<c:if test="${QX.del == 1 }">
															<a class="btn btn-xs btn-danger" onclick="del('${var.FHFILE_ID}','${var.NAME}');">
																<i class="ace-icon fa fa-trash-o bigger-120" title="删除"></i>
															</a>
															</c:if>
														</div>
														<div class="hidden-md hidden-lg">
															<div class="inline pos-rel">
																<button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
																	<i class="ace-icon fa fa-cog icon-only bigger-110"></i>
																</button>
					
																<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
																	<c:if test="${QX.edit == 1 }">
																	<li>
																		<a style="cursor:pointer;" onclick="window.location.href='<%=basePath%>rest/fhfile/download?FHFILE_ID=${var.FHFILE_ID}'" class="tooltip-success" data-rel="tooltip" title="下载">
																			<span class="green">
																				<i class="ace-icon fa fa-cloud-download bigger-120"></i>
																			</span>
																		</a>
																	</li>
																	</c:if>
																	<c:if test="${QX.del == 1 }">
																	<li>
																		<a style="cursor:pointer;" onclick="del('${var.FHFILE_ID}','${var.NAME}');" class="tooltip-error" data-rel="tooltip" title="删除">
																			<span class="red">
																				<i class="ace-icon fa fa-trash-o bigger-120"></i>
																			</span>
																		</a>
																	</li>
																	</c:if>
																</ul>
															</div>
														</div>
													</td>
												</tr>
											</c:forEach>
											<c:if test="${QX.cha == 0 }">
												<tr>
													<td colspan="100" class="center">您无权查看</td>
												</tr>
											</c:if>
										</c:when>
										<c:otherwise>
											<tr class="main_info">
												<td colspan="100" class="center" >没有相关数据</td>
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
											<a class="btn btn-mini btn-success" onclick="add();">新增</a>
											</c:if>
											<c:if test="${QX.del == 1 }">
											<a class="btn btn-mini btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除" ><i class='ace-icon fa fa-trash-o bigger-120'></i></a>
											</c:if>
										</td>
										<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
									</tr>
								</table>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- 页面底部js¨ -->
<%@ include file="../../system/index/foot.jsp"%>

<!-- 删除时确认窗口 -->
<script src="static/ace/js/bootbox.js"></script>
<!-- ace scripts -->
<script src="static/ace/js/ace/ace.js"></script>
<!-- 下拉框 -->
<script src="static/ace/js/chosen.jquery.js"></script>
<!--提示框-->
<script type="text/javascript" src="static/js/jquery.tips.js"></script>


<script type="text/javascript">
$(top.hangge());//关闭加载状态
//检索
function tosearch(){
	top.jzts();
	$("#Form").submit();
}
//复选框
$(function(){
	var active_class='active';
	$('#simple-table > thead > tr > th input[type=checkbox]').eq(0).on('click',function(){	//绑定一个click方法
		var th_checked = this.checked;
		$(this).closest('table').find("tbody > tr").each(function(){
			var row = this;
			if(th_checked){	//如果选中，则增加样式
				$(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
			}else{
				$(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
			}
		});
	});
});

//新增
function add(){
	 top.jzts();
	 var diag = new top.Dialog();
	 diag.Drag=true;
	 diag.Title ="新增";
	 diag.URL = '<%=basePath%>rest/fhfile/goAdd';
	 diag.Width = 460;
	 diag.Height = 310;
	 diag.Modal = true;				//有无遮罩窗口
	 diag.ShowMaxButton = true;		//最大化按钮
     diag.ShowMinButton = true;		//最小化按钮
	 diag.CancelEvent = function(){ //关闭事件
		 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
			 if('${page.currentPage}' == '0'){
				 top.jzts();
				 setTimeout("self.location=self.location",100);
			 }else{
				 nextPage(${page.currentPage});
			 }
		}
		diag.close();
	 };
	 diag.show();
}

//根据id删除
function del(id,NAME){
	bootbox.confirm("要删除["+NAME+"]吗？",function(result){
		if(result){
			var url = '<%=basePath%>rest/fhfile/delete?FHFILE_ID='+id+'&tm='+new Date().getTime();
			$.get(url,function(data){
				data = JSON.parse(data);
				if("success" == data.result){
					nextPage(${page.currentPage});
				}else{
					alert("删除失败");
				}
			})
		}
	});
}

//批量操作
function makeAll(msg){
	bootbox.confirm("确定要删除["+msg+"]",function(result){
		if(result){
			var str = '';
			var checkedList = document.getElementsByName("ids");
			for(var i = 0;i<checkedList.length;i++){
				if(checkedList[i].checked){
					if(str == ''){
						str += checkedList[i].value;
					}else{
						str += ","+checkedList[i].value;
					}
				}
			}
			
			if(str == ''){
				bootbox.dialog({
					message: "<span class='bigger-110'>您没有选择任何内容!</span>",
					buttons: 			
					{ "button":{ "label":"确定", "className":"btn-sm btn-success"}}
				});
				$("#zcheckbox").tips({
					side:1,
		            msg:'点这里全选',
		            bg:'#AE81FF',
		            time:8
		        });
				return;
			}else{
				if(msg == '确定要删除选中的数据吗?'){
					top.jzts();
					$.ajax({
						type: "POST",
						url: '<%=basePath%>rest/fhfile/deleteAll?tm='+new Date().getTime(),
				    	data: {IDS:str},
						dataType:'json',
						cache: false,
						success: function(data){
							if("success" == data.result){
								setTimeout("self.location=self.location",100);
								nextPage(${page.currentPage});
							}else{
								alert(data.result);
							}
						}
					})
				}
			}
		}
	})
} 

//预览pdf
function goViewPdf(fileName,Id){
	var diag = new top.Dialog();
	diag.Drag=true;
	diag.Title =fileName;
	diag.URL = '<%=basePath%>rest/fhfile/goViewPdf?FHFILE_ID='+Id;
	diag.Width = 1000;
	diag.Height = 600;
	diag.Modal = false;				//有无遮罩窗口
	diag. ShowMaxButton = true;		//最大化按钮
	diag.ShowMinButton = true;		//最小化按钮
	diag.CancelEvent = function(){ 	//关闭事件
	diag.close();
	};
	diag.show();
}

//预览txt,java,php,等文本文件页面
function goViewTxt(fileName,Id,encoding){
	var diag = new top.Dialog();
	diag.Drag=true;
	diag.Title =fileName;
	diag.URL = '<%=basePath%>rest/fhfile/goViewTxt?FHFILE_ID='+Id+'&encoding='+encoding;
	diag.Width = 1000;
	diag.Height = 608;
	diag.Modal = false;				//有无遮罩窗口
	diag.ShowMinButton = true;		//最小化按钮
	diag.CancelEvent = function(){ 	//关闭事件
	diag.close();
	};
	diag.show();
}



</script>
</body>
</html>