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
<!DOCTYPE html >
<html lang="en">
<head>
<base href="<%=basePath%>">
<!-- 引入头部文件 -->
<%@ include file="../../system/index/top.jsp"%>

</head>
<body class="no-skin">
	<!-- 图片编辑 -->
	
	<div class="main-container" id="main-container">
		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">
				<form action="rest/pictures/${msg}" name="Form" id="Form" method="post" enctype="multipart/form-data">
					<input type="hidden" name="PICTURES_ID" id="PICTURES_ID" value="${pd.PICTURES_ID}"/>
					
					<div id="zhongxin" >
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:50px;text-align: right;padding-top: 13px;">标题</td>
								<td><input type="text" name="TITLE" id="TITLE" value="${pd.TITLE}" maxlength="32" style="width:99%;" placeholder="这里输入标题" title="标题"/></td>
							</tr>
							<tr>
								<td style="width:50px;text-align: right;padding-top: 13px;">图片:</td>
								<td>
									<c:if test="${pd == null || pd.PATH == null || pd.PATH == ''}">
										<!-- 上传图片 -->
										<input type="file" id="tp" name="tp" onchange="fileType(this)"/>
									</c:if>
									<c:if test="${pd != null && pd.PATH != '' && pd.PATH != null }">
										<!-- 展示图片 -->
										<a href="<%=basePath%>rest/pictures/IoReadImage/${pd.PATH}" title="${pd.TITLE}" class="bwGal">
												<img src="<%=basePath%>rest/pictures/IoReadImage/${pd.PATH}" value="${pd.PATH}" alt="${pd.TITLE}" width="100" id="pic">
											</a>
										<input type="button" class="btn btn-mini btn-danger" value="删除" onclick="delP('${pd.PATH}','${pd.PICTURES_ID }');"/>
										<input type="hidden" name="tpz" id="tpz" value="${pd.PATH }"/>
									</c:if>
								</td>
							</tr>
							<tr>
								<td style="width:50px;text-align: right;padding-top: 13px;">属于:</td>
								<td><input type="text" name="MASTER_ID" id="MASTER_ID" value="${pd.MASTER_ID}" maxlength="32" placeholder="这里输入属于" title="属于"/></td>
							</tr>
							<tr>
								<td style="width:50px;text-align: right;padding-top: 13px;">备注:</td>
								<td><input type="text" name="BZ" id="BZ" value="${pd.BZ}" maxlength="32" style="width:95%;" placeholder="这里输入备注" title="备注"/></td>
							</tr>
							<tr>
								<td colspan="2" style="text-align: center;" colspan="2">
									<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
									<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
								</td>
							</tr>
						</table>
					</div>
					
					<div id="zhongxin2" class="center" style="display: none;"><br/><br/><br/><br/><br/>
						<img src="static/images/jiazai.gif"><br/><h4 class="lighter block green">提交中...</h4>
					</div>
				</form>
			</div>
		</div>
	</div>
	
<!-- 页面底部js¨ -->
<%@ include file="../../system/index/foot.jsp"%>
<!-- ace scripts -->
<script src="static/ace/js/ace/ace.js"></script>
<!-- 上传控件 -->
<script src="static/ace/js/ace/elements.fileinput.js"></script>
<!--提示框-->
<script type="text/javascript" src="static/js/jquery.tips.js"></script>	

<script type="text/javascript">
$(top.hangge());

function fileType(){
	
}

//保存
function save(){
	if($("#TITLE").val() ==""){
		$("#TITLE").tips({
			side:3,
            msg:'请输入标题',
            bg:'#AE81FF',
            time:2
		});
		$("TITLE").focus();
		return false;
	}
	
	if($("#MASTER_ID").val() ==""){
		$("#MASTER_ID").tips({
			side:3,
            msg:'请输入属于',
            bg:'#AE81FF',
            time:2
		});
		$("#MASTER_ID").focus();
		return false;
	}
	if($("#BZ").val() ==""){
		$("#BZ").tips({
			side:3,
            msg:'请输入备注',
            bg:'#AE81FF',
            time:2
		});
		$("#BZ").focus();
		return false;
	}
	
	$("#Form").submit();
	$("#zhongxin").hide();
	$("#zhongxin2").show();
	
}

function delP(){
	
}

</script>
</body>
</html>