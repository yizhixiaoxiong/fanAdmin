<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>div和ajax</title>
<script type="text/javascript" src="../static/js/jquery.js"></script>
</head>
<body>  
    <input type="text" name="txt" id="txt">  
    <input type="button" id="btn" value="获取">  
    <div style="border:1px solid black;width:100%;height:100%" id="div"></div>  
</body>  
<script>  
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
</html>