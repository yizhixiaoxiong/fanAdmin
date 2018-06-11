<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html >
<html lang="en">
<head>
<base href="<%=basePath%>">
<!-- 下拉框 -->
<link rel="stylesheet" href="static/ace/css/chosen.css" />
<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>
<script type="text/javascript" src="static/ace/js/jquery.js"></script>
<!-- 上传插件 -->
<link href="plugins/uploadify/uploadify.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="plugins/uploadify/jquery.uploadify.min.js"></script>
<!-- 上传插件 -->
<script type="text/javascript">
var jsessionid = "<%=session.getId()%>";  //勿删，uploadify兼容火狐用到
</script>
</head>
<body>

<!-- /section:basics/sidebar -->
<div class="main-content">
	<div class="main-content-inner">
		<div class="page-content">
			<div class="row">
				<div class="col-xs-12">
				
				<form action="rest/fhfile/${msg }" name="Form" id="Form" method="post" enctype="multipart/form-data">
						<input type="hidden" value="no" id="hasTp1" />
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">文件名:</td>
								<td><input type="text" name="NAME" id="NAME" value="" maxlength="30" placeholder="这里输入文件名" title="文件名" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;" id="FILEPATHn">文件:</td>
								<td>
									<input type="file" name="File_name" id="uploadify1" keepDefaultStyle = "true"/>
									<input type="hidden" name="FILEPATH" id="FILEPATH" value=""/>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注:</td>
								<td><input type="text" name="BZ" id="BZ" value="" maxlength="100" placeholder="这里输入备注" title="备注" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="text-align: center;" colspan="10">
									<a class="btn btn-mini btn-primary" onclick="save()">保存</a>
									<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
								</td>
							</tr>
						</table>
						</div>
						<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/>
						<img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
					</form>
   					 
				</div>
			</div>
		</div>
	</div>
</div>

<!-- 页面底部js¨ -->
<%@ include file="../../system/index/foot.jsp"%>
<!-- 下拉框 -->
<script src="static/ace/js/chosen.jquery.js"></script>
<!-- 日期框 -->
<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
<!--提示框-->
<script type="text/javascript" src="static/js/jquery.tips.js"></script>

<script type="text/javascript">
	$(top.hangge());
	
	//保存
	function save(){
		if($("#NAME").val()==""){
			$("#NAME").tips({
				side:3,
	            msg:'请输入文件名',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#NAME").focus();
		return false;
		}
		if($("#hasTp1").val()=="no"){
			$("#FILEPATHn").tips({
				side:2,
		        msg:'请选择文件',
		        bg:'#AE81FF',
		        time:2
		    });
		return false;
		}
		if($("#BZ").val()==""){
			$("#BZ").tips({
				side:3,
	            msg:'请输入备注',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#BZ").focus();
		return false;
		}
		$('#uploadify1').uploadify('upload', '*');
	}
	
	$(function () {
        $("#uploadify1").uploadify({
            //指定swf文件
            'swf': '<%=basePath%>plugins/uploadify/uploadify.swf',
            //后台处理的页面
            'uploader': '<%=basePath%>rest/fhfile/uploadFile',
            'progressData' : 'speed',
            //按钮显示的文字
            'buttonImage'	: '<%=basePath%>static/images/fileup.png',
            //显示的高度和宽度，默认 height 30；width 120
            //'height': 15,
            //'width': 80,
            //上传文件的类型  默认为所有文件    'All Files'  ;  '*.*'
            //在浏览窗口底部的文件类型下拉菜单中显示的文本
            'fileTypeDesc': 'Image Files',
            'queueSizeLimit':3,
            //允许上传的文件后缀
            //'fileTypeExts': '*.gif; *.jpg; *.png',
            'cancel': '<%=basePath%>plugins/uploadify/uploadify-cancel.png',
            //上传文件页面中，你想要用来作为文件队列的元素的id, 默认为false  自动生成,  不带#
            //'queueID': 'fileQueue',
            //选择文件后自动上传
            'auto': false,
            //设置为true将允许多文件上传
            'multi': true,
            'overrideEvents': ['onSelectError','onDialogClose'],
            'onUploadSuccess' : function(file,data,response) {	//每成功一次上传触发
            	data =JSON.parse(data);	//后台返回json
            	$("#FILEPATH").val(data.name);	//文件名称即为数据库的路径
             },
            'onQueueComplete' : function(queueData) {	//	队列中的文件上传之后触发
            	$("#Form").submit();
				$("#zhongxin").hide();
				$("#zhongxin2").show();
             }, 
            'onCancel' : function(file) {
                 alert('取消上传');
             },
             'onSelect' : function(file) {
            	 $("#hasTp1").val("ok");
             },
             'onSelectError':function(file,errorCode){	//选择文件错误的时候触发
            	  switch (errorCode) {  
                  case -100:  
                      alert("上传的文件数量已经超出系统限制的" + $('#uploadify1').uploadify('settings', 'queueSizeLimit') + "个文件！");  
                      break;  
                  case -110:  
                      alert("文件 [" + file.name + "] 大小超出系统限制的" + $('#uploadify1').uploadify('settings', 'fileSizeLimit') + "大小！");  
                      break;  
                  case -120:  
                      alert("文件 [" + file.name + "] 大小异常！");  
                      break;  
                  case -130:  
                      alert("文件 [" + file.name + "] 类型不正确！");  
                      break;  
              }  
              return false;
             }
        });
        
      /*   $("#upload").on("click",function(){
        	alert("点击事件");
        	if($("#NAME").val()==""){
    			$("#NAME").tips({
    				side:3,
    	            msg:'请输入文件名',
    	            bg:'#AE81FF',
    	            time:2
    	        });
    			$("#NAME").focus();
    		return false;
    		}
    		if($("#hasTp1").val()=="no"){
    			$("#FILEPATHn").tips({
    				side:2,
    		        msg:'请选择文件',
    		        bg:'#AE81FF',
    		        time:2
    		    });
    		return false;
    		}
    		if($("#BZ").val()==""){
    			$("#BZ").tips({
    				side:3,
    	            msg:'请输入备注',
    	            bg:'#AE81FF',
    	            time:2
    	        });
    			$("#BZ").focus();
    		return false;
    		}
    		$('#uploadify1').uploadifyUpload();
        }); */
    });
	
</script>
</body>
</html>