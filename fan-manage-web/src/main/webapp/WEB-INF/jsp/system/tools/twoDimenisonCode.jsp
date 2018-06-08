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
<base href="<%=basePath %>">
<!-- jsp文件头和头部 -->
<%@ include file="../index/top.jsp"%>
<link href="plugins/uploadify/uploadify.css" rel="stylesheet" type="text/css">
<!-- 上传插件 -->
<script type="text/javascript" src="static/ace/js/jquery.js"></script>
<script type="text/javascript" src="plugins/uploadify/swfobject.js"></script>
<script type="text/javascript" src="plugins/uploadify/jquery.uploadify.v2.1.4.min.js"></script>
<!-- 二维码 -->
<script type="text/javascript" src="static/js/jquery.qrcode.min.js"></script>
</head>
<body>
<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="row">
						<div class="col-xs-12">
						<br/>
							<div class="">
								<div style="margin-top: 5px;">
									<span class="input-icon">
										<input type="text" id="encoderContent" title="输入内容" value="https://www.baidu.com" style="width:300px;"/>
										<i class="ace-icon fa fa-edit"></i>
									</span>
								</div>
								
								<div style="margin-top: 15px;">
									<div style="width:280px;float: left;">
										<div class="widget-box" >
											<div class="widget-header widget-header-flat widget-header-small" style="height: 46px;">
												<div style="float: left;margin-top: 10px;font-size: 16px;">RQCode生成二维码</div>
												<button class="btn btn-app btn-light btn-xs" onclick="createRQCode();" style="float: right;margin-right: 15px;">
													<i class="ace-icon fa fa-print"></i>
												</button>
												<div style="clear:both;"></div>
												<div class="widget-toolbar no-border"></div>
											</div>
											<div class="widget-body">
												<div class="widget-main">
												<img id="encoderImgQRCodeId" cache="false" src="<%=basePath%>static/images/default.png" width="256px" height="261px;"/>
												</div><!--/widget-main-->
											</div><!--/widget-body-->
										</div>
									</div>
									
									<div style="width:280px;float: left;">
										<div class="widget-box" >
											<div class="widget-header widget-header-flat widget-header-small" style="height: 46px;">
												<div style="float: left;margin-top: 10px;font-size: 16px;">zxing生成二维码</div>
												<button class="btn btn-app btn-light btn-xs" onclick="createZxing();" style="float: right;margin-right: 15px;">
													<i class="ace-icon fa fa-print"></i>
												</button>
												<div style="clear:both;"></div>
												
												<!-- <button class="btn btn-app btn-light btn-xs" onclick="createTwoD();">
													<i class="ace-icon fa fa-print"></i>
												</button> -->
												<div class="widget-toolbar no-border"></div>
											</div>
											<div class="widget-body">
												<div class="widget-main">
												<img id="encoderImgZxingId" cache="false" src="<%=basePath%>static/images/default.png" width="256px" height="261px;"/>
												</div><!--/widget-main-->
											</div><!--/widget-body-->
										</div>
									</div>
									
									<div style="width:280px;float: left;">
										<div class="widget-box" >
											<div class="widget-header widget-header-flat widget-header-small" style="height: 46px;">
												<div style="float: left;margin-top: 10px;font-size: 14px;">jQuery-rqcode.js生成二维码</div>
												<button class="btn btn-app btn-light btn-xs" onclick="createJQRQCode();" style="float: right;margin-right: 15px;">
													<i class="ace-icon fa fa-print"></i>
												</button>
												<div style="clear:both;"></div>
												
												<!-- <button class="btn btn-app btn-light btn-xs" onclick="createTwoD();">
													<i class="ace-icon fa fa-print"></i>
												</button> -->
												<div class="widget-toolbar no-border"></div>
											</div>
											<div class="widget-body">
												<div class="widget-main">
													<img id="encoderJQRCodeImgId" cache="false" src="<%=basePath%>static/images/default.png" width="256px" height="261px;"/>
													<div id="qrcode" style="width:256px;height:261px;border: 2px solid blue;padding-left: 22px;padding-top: 24px ;" hidden="hidden"></div>
												</div><!--/widget-main-->
											</div><!--/widget-body-->
										</div>
									</div>
									<div style="width:399px;float:right;">
										<div class="widget-box">
											<div class="widget-header widget-header-flat widget-header-small">
												<h5>
													解析二维码
												</h5>
												<div class="widget-toolbar no-border">
												</div>
											</div>
											<div class="widget-body">
											 <div class="widget-main">
												<div>
													<textarea id="readContent" title="解析结果" placeholder="显示解析结果" class="autosize-transition span12" style="width:375px;height:233px;">
													</textarea>
												</div>
												<div>
													<div style="float: left;" id="tipsTwo">
														<input type="file" name="TP_URL" id="uploadify1" keepDefaultStyle = "true"/>
													</div>
													<div><a class="btn btn-mini btn-success" onclick="uploadTwo();">解析</a></div>
												</div>
											 </div><!--/widget-main-->
											</div><!--/widget-body-->
										</div><!--/widget-box-->
									</div>
									<div style="clear:both;"></div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
<script type="text/javascript">
	$(top.hangge());
	//获取路径
	var locat = (window.location+'').split('/'); 
	$(function(){
		if('tool'== locat[4]){
			locat =  locat[0]+'//'+locat[2]+'/'+locat[3];
		}else{
			locat =  locat[0]+'//'+locat[2]+'/'+locat[3]+'/'+locat[4];
		};
	});

	//上传二维码
	$(document).ready(function(){
		var str = '';
	 		$("#uploadify").uploadify({
	 			'buttonImg'	: "<%=basePath %>rest/static/images/twoDimensonCode.png",
	            'uploader': '<%=basePath %>rest/plugins/uploadify/uploadify.swf',
	            'script': '<%=basePath %>rest/tool/fileUpload',
	            'cancelImg': '<%=basePath %>rest/plugins/uploadify/cancel.png',
	            'folder':'upload',
	            'queueId':"fileQueue",
	    		'queueSizeLimit':1,//限制上传文件的数量
	            'auto':false,
	            'multi':true,
	            'fileExt':'*.jpg;*.gif;*.png',
	    		'fileDesc':'Please choose(.JPG, .GIF, .PNG)',
	            'fileDataName':'Filedata',
	            'simUploadLimit':2,//同时运行上传的进程数量
	    		'buttonText':"files",
	    		'method'	:	"GET",
	            'onComplete':function(event,queueId,fileObj,response,data){
	    			str = response.trim();//单个上传完毕执行
	    		},
	    		'onAllComplete' : function(event,data) {
	    			//alert(str);	//全部上传完毕执行
	    			readContent(str);
	        	},
	        	'onSelect' : function(event, queueId, fileObj){
	        		$("#hasTp1").val("ok");
	        	}
       		});
	});
	
	//解析
	function uploadTwo(){
		$('#uploadify1').uploadifyUpload();
	}
	
	//生成二维码
	function createRQCode(){
		var content = $("#encoderContent").val();
		if($("#encoderContent").val() == ""){
			$("encoderContent").tips({
				side:3,
	            msg:'输入内容',
	            bg:'#AE81FF',
	            time:2
			});
			$("#encoderContent").focus();
			return false;
		}
		$("#encoderImgQRCodeId").attr("src",locat+"/static/images/jzx.gif");
		$.ajax({
			type:"POST",
			url: locat+'/tool/createRQCode',
			data: {tm:new Date().getTime(),content:content},
			dataType: 'json',
			cache:false,
			success:function(data){
				if("success" == data.result){
					$("#encoderContent").tips({
						side:1,
			            msg:'生成成功',
			            bg:'#75C117',
			            time:3
			     });
				$("#encoderImgQRCodeId").attr("src",locat+"/uploadFiles/twoDimensionCode/"+ data.encoderImgId); 
				}else{
					$("#encoderContent").tips({
						side:3,
			            msg:'生成失败,后台有误',
			            bg:'#FF5080',
			            time:10
			     });
				 $("#encoderImgQRCodeId").attr("src",locat+"/uploadFiles/twoDimensionCode/default.png");
				 return;
				}
			}
		})
	}
	
	//生成Zxing二维码
	function createZxing(){
		var content = $("#encoderContent").val();
		if($("#encoderContent").val() == ""){
			$("encoderContent").tips({
				side:3,
	            msg:'输入内容',
	            bg:'#AE81FF',
	            time:2
			});
			$("#encoderContent").focus();
			return false;
		}
		$("#encoderImgZxingId").attr("src",locat+"/static/images/jzx.gif");
		$.ajax({
			type:"POST",
			url: locat+'/tool/createZxing',
			data: {tm:new Date().getTime(),content:content},
			dataType: 'json',
			cache:false,
			success:function(data){
				if("success" == data.result){
					$("#encoderContent").tips({
						side:1,
			            msg:'生成成功',
			            bg:'#75C117',
			            time:3
			     });
				$("#encoderImgZxingId").attr("src",locat+"/uploadFiles/twoDimensionCode/"+ data.encoderImgId); 
				}else{
					$("#encoderContent").tips({
						side:3,
			            msg:'生成失败,后台有误',
			            bg:'#FF5080',
			            time:10
			     });
				 $("#encoderImgZxingId").attr("src",locat+"/uploadFiles/twoDimensionCode/default.png");
				 return;
				}
			}
		})
	}
	
	//生成二维码
	function createJQRQCode(){
		$("#encoderJQRCodeImgId").hide();
		$("#qrcode").qrcode({
		 	render: "canvas", //也可以替换为table
		    width: 200,
		    height: 200,
		    foreground: "#C00",
		    background: "#FFF",
		    text: $("#encoderContent").val()
		});
		$("#qrcode").show();
	}
	
	//解析QRCode
	function uploadTwo(){
		$("").submit();
	}
</script>

<!-- 页面底部js¨ -->
<%@ include file="../index/foot.jsp"%>
<!-- ace scripts -->
<script src="static/ace/js/ace/ace.js"></script>
<!--提示框-->
<script type="text/javascript" src="static/js/jquery.tips.js"></script>
</body>
</html>