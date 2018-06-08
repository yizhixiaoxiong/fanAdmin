<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html >
<html lang="en">
<base href="<%=basePath%>">
<!-- 下拉框 -->
<link rel="stylesheet" href="static/ace/css/chosen.css" />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="../index/top.jsp" %>
</head>
<body>
<!-- userList -->
<div class="main-container" id="main-container">
	<!-- /section:basics/sidebar -->
	<div class="main-content">
		<div class="main-content-inner">
			<div class="page-content">
				<div class="row">
					<div class="col-xs-12">
						<form action="rest/user/listUsers" method="post" name="userForm" id="userForm">
						<!-- 头部检索部分 -->
						<table style="margin-top:5px;">
							<tr>
								<td>
									<div class="nav-search">
										<span class="input-icon">
											<input type="text" placeholder="请输入要查找的关键字" class="nav-search-input" id="nav-search-input" autocomplete="off"/>
											<i class="ace-icon fa fa-search nav-search-icon"></i>
										</span>
									</div>
								</td>
								<td style="padding-left:2px;"><input class="span10 date-picker" name="lastLoginStart" id="lastLoginStart"  value="${pd.lastLoginStart}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="开始日期" title="最近登录开始"/></td>
								<td style="padding-left:2px;"><input class="span10 date-picker" name="lastLoginEnd" id="lastLoginEnd"  value="${pd.lastLoginEnd}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="结束日期" title="最近登录结束"/></td>
								<td style="vertical-align:top;padding-left:2px;">
									<select class="chosen-select form-control" name="ROLE_ID" id="role_id" data-placeholder="请选择角色" style="vertical-align:top;width: 120px;">
									<option value=""></option>
									<option value="">全部</option>
									<c:forEach items="${roleList}" var = "role">
										<option value="${role.ROLE_ID}" <c:if test="${pd.ROLE_ID == role.ROLE_ID }">selected</c:if>>${role.ROLE_NAME }</option>
									</c:forEach>
								  	</select>
								</td>
								<td style="vertical-align: top;padding-left:2px;" >
									<a class="btn btn-light btn-xs" onclick="searchs()" title="检索" >
										<!-- 图标 -->
										<i id="" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i>
									</a>
								<td style="vertical-align:top;padding-left:2px;">
									<a class="btn btn-light btn-xs" onclick="toExcel();" title="导出到EXCEL">
										<i id="nav-search-icon" class="ace-icon fa fa-download bigger-110 nav-search-icon blue">
										</i>
									</a>
								</td>
								<td style="vertical-align:top;padding-left:2px;">
									<a class="btn btn-light btn-xs" onclick="fromExcel();" title="从EXCEL导入">
										<i id="nav-search-icon" class="ace-icon fa fa-cloud-upload bigger-110 nav-search-icon blue"></i>
									</a>
								</td>
								</td>
								<td></td>
								<td></td>
							</tr>
							
						</table>
						
						
						
						<!-- 列表部分 -->
						<table id="sample-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">
							<thead>
								<tr>
									<th class="center" style="width:35px;">
										<label>
											<input type="checkbox" class="ace" />
											<span class="lbl"></span>
										</label>
									</th>
									<th class="center" style="width:50px;">序号</th>
									<th class="center">编号</th>
									<th class="center">用户名</th>
									<th class="center">姓名</th>
									<th class="center">角色</th>
									<th class="center">邮箱</th>
									<th class="center">最近登录</th>
									<th class="center">上次登录IP</th>
									<th class="center">操作</th>
								</tr>
							</thead>			
							<tbody>
								<c:choose>
									<c:when test="${not empty userList}">
										<c:forEach items="${userList}" var="user" varStatus="vs">
											<tr>
												<td class="center">
													<c:if test="${user.USERNAME == 'admin'}">
														<label>
															<input type="checkbox" class="ace" disabled="disabled" />
															<span class="lbl"></span>
														</label>
													</c:if> 
											
													<c:if test="${user.USERNAME != 'admin'}">
														<label>
															<input type="checkbox" class="ace" name="ids" values="${user.USER_ID }" id="${user.EMAIL}" alt="${user.PHONE }" title="${user.USERNAME }" />
															<span class="lbl"></span>
														</label>
													</c:if>
												</td>
												<td class="center">${vs.index+1}</td>
												<td class="center">${user.NUMBER }</td>
												<td class="center">
													<a onclick="viewUser('${user.USERNAME}')" style="cursor:pointer;">${user.USERNAME }</a>
												</td>
												<td class="center">${user.NAME }</td>
												<td class="center">${user.ROLE_NAME }</td>
												<td class="center">
													<a title="发送电子邮件" style="text-decoration:none;cursor:pointer;" onclick="sendEmail('${user.EMAIL }');">
														${user.EMAIL }&nbsp;
														<i class="ace-icon fa fa-envelope-o"></i>
													</a>
												</td>
												<td class="center">${user.LAST_LOGIN}</td>
												<td class="center">${user.IP}</td>
												<td class="center">
													<div class="hidden-sm hidden-xs btn-group">
														<a class="btn btn-xs btn-info" title='发送站内信' onclick="sendFhsms('${user.USERNAME }');">
															<i class="ace-icon fa fa-envelope-o bigger-120" title="发送站内信"></i>
														</a>
														<a class="btn btn-xs btn-warning" title='发送短信' onclick="sendSms('${user.PHONE }');">
															<i class="ace-icon fa fa-envelope-o bigger-120" title="发送短信"></i>
														</a>
														<a class="btn btn-xs btn-success" title="编辑" onclick="editUser('${user.USER_ID}');">
															<i class="ace-icon fa fa-pencil-square-o bigger-120" title="编辑"></i>
														</a>
														<a class="btn btn-xs btn-danger" onclick="delUser('${user.USER_ID }','${user.USERNAME }');">
															<i class="ace-icon fa fa-trash-o bigger-120" title="删除"></i>
														</a>
													</div>
												</td>
											</tr>						
										</c:forEach>		
									</c:when>
									<c:otherwise>
									<tr class="main_info">
										<td colspan="10" class="center">没有相关数据</td>
									</tr>
								</c:otherwise>
								</c:choose>
							</tbody>		
						</table>
						<table style="width:100%;">
							<tr>
								<td style="vertical-align:top;">
									<a class="btn btn-mini btn-success" onclick="add();">新增</a>
									<a title="批量发送站内信" class="btn btn-mini btn-info" onclick="makeAll('确定要给选中的用户发送站内信吗?');"><i class="ace-icon fa fa-envelope-o bigger-120"></i></a>
									<a title="批量发送电子邮件" class="btn btn-mini btn-primary" onclick="makeAll('确定要给选中的用户发送邮件吗?');"><i class="ace-icon fa fa-envelope bigger-120"></i></a>
									<a title="批量发送短信" class="btn btn-mini btn-warning" onclick="makeAll('确定要给选中的用户发送短信吗?');"><i class="ace-icon fa fa-envelope-o bigger-120"></i></a>
									<a title="批量删除" class="btn btn-mini btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" ><i class='ace-icon fa fa-trash-o bigger-120'></i></a>
								</td>
								<td style="vertical-align:top;">
									<div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">
										${page.pageStr}
									</div>
								</td>
							</tr>
						</table>
						</form>
						</div>
					<!-- /.col -->
				</div>
				<!-- /.row -->
			</div>
			<!-- /.page-content -->
		</div>
	</div>
	<!-- /.main-content -->
	<!-- 返回顶部 -->
	<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
		<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
	</a>
</div>
<%@include file="../index/foot.jsp" %>
<!-- 提示窗口 -->
<script src="static/ace/js/bootbox.js"></script>
<script src="/static/ace/js/ace/ace.js"></script>
<script type="text/javascript">
//清除加载效果
$(top.hangge());

//检索
function searchs(){
	$("#userForm").submit();
}


//渲染完页面执行
$(function(){
	//日期
	
	//下拉框数据
	
	//复选框全选择
	var active_class = 'active';
	$("#sample-table > thead > tr > th input[type=checkbox]").eq(0).on('click',function(){//找到table头部的复选框绑定 一个事件
		var th_checked = this.checked;		//this就是这个复选框元素	
		$(this).closest('table').find('tbody > tr').each(function(){
			var row = this;	//指向头部复选框
			if(th_checked){
				$(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked',true);
			}else{
				$(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
			}
			
		});
	});
})

//新增
function add(){
		var diag = new top.Dialog();//一个模态框
		 diag.Drag=true;
		 diag.Title ="新增";
		 diag.URL = '<%=basePath%>/rest/user/goAddU';
		 diag.Width = 469;
		 diag.Height = 510;
		 diag.CancelEvent = function(){ //关闭事件，执行的函数
			 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){//saveResult中的div
				if('$(page.currentPage)' == '0'){
					top.jzts();
					setTimeout("self.location = self.location",100);
				}else{
					nextPage(${page.currentPage});
				}
			 } 
			 diag.close();
		 };
		 diag.show();
	}

//删除id和USERNAME
function delUser(userId,USERNAME){
	bootbox.confirm("确定要删除["+USERNAME+"]吗?",function(result){
		if(result){
			var url = "<%=basePath%>rest/user/deleteU?USER_ID="+userId+"&tm="+new Date().getTime();
			$.get(url,function(data){
				alert(data);
				data = JSON.parse(data);
				//TODU
				if("success" == data.result)
					window.location.href="rest/user/listUsers";
			});
		}
	})
}

//修改
function editUser(user_id){
	 var diag = new top.Dialog();
	 diag.Drag=true;
	 diag.Title ="资料";
	 diag.URL = '<%=basePath%>rest/user/goEditU?USER_ID='+user_id;
	 diag.Width = 469;
	 diag.Height = 510;
	 diag.CancelEvent = function(){ //关闭事件
		diag.close();
	 };
	 diag.show();
}

//查看 用户
function viewUser(USERNAME){
	if('admin' == USERNAME){
		bootbox.dialog({
			message: "<span class='bigger-110'>不能查看admin用户!</span>",
			buttons:
			{"button":{"label":"确定","className":"btn-sm btn-success"}}
		});
		return;
	}
	
	 var diag = new top.Dialog();
	 diag.Drag=true;
	 diag.Title ="资料";
	 diag.URL = '<%=basePath%>rest/user/view?USERNAME='+USERNAME;
	 diag.Width = 469;
	 diag.Height = 380;
	 diag.CancelEvent = function(){ //关闭事件
			diag.close();
		 };
		 diag.show();
}

//批量操作用户
function makeAll(msg){
	bootbox.confirm(msg, function(result) {
		if(result){	//选择true
			var str = '';		//删除保存的数据
			var emstr = '';		//发送邮件
			var phones = '';	//发送短信
			var username = '';	//发送站内信
			var ids = document.getElementsByName("ids");
			for (var i = 0; i < ids.length; i++) {
				//如果元素被选中
				if(ids[i].checked){
					if(str == '') str += ids[i].value;
					else  str += ","+ids[i].value;
				}
			}
			if('' == str){	//如果数据为空
				bootbox.dialog({
					message: "<span class='bigger-110'>您没有选择任何内容!</span>",
					buttons: 			
					{ "button":{ "label":"确定", "className":"btn-sm btn-success"}}
				});
				$("#zcheckbox").tips({
					side:3,
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
						url: '<%=basePath%>rest/user/deleteAllU?tm='+new Date().getTime(),
				    	data: {USER_IDS:str},
						dataType:'json',
						//beforeSend: validateData,
						cache: false,
						success: function(data){
							window.location.href="rest/main/index";
						}
					});
				}
			}
			
		}
		
	});
}
</script>
</body>
</html>