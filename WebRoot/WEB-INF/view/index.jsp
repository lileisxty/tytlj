<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>   

<%
	String path = request.getContextPath();
	HttpSession Session = request.getSession();
	String department = (String) Session.getAttribute("department");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
	    <link rel="stylesheet" href="<%=path %>/bootstrap-3.3.7/css/bootstrap.min.css">  
		<script type="text/javascript"src="<%=path %>/JqueryValidate/jquery-1.11.1.js"></script>
		<script type="text/javascript"src="<%=path %>/bootstrap-3.3.7/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="<%=path %>/JqueryValidate/jquery.validate.js"></script>
		<script type="text/javascript" src="<%=path %>/JqueryValidate/messages_zh.js"></script>
		<script type="text/javascript" src="<%=path %>/JqueryValidate/additional-methods.js"></script>
        <title>各站月派工情况</title>
        <style type="text/css">
            .width{
            	width:100px;
            }
        	
        </style>
    </head>
    <body>
    	<div class="container">
	    		<div class="row">
	    			<form id="uploadExcel" class="form-inline" action="<%=path %>/test/upload" method="post" enctype="multipart/form-data">
					<div class="form-group">
						<input type="file" class="form-control" id="excelFile" name="excelFile"
							   placeholder="请上传EXCEL格式文件" />
					</div>
					<input type="submit" class="btn btn-success" value="上传文件"/>
				</form>
	    		</div>
    		
		</div>
</body>
<script type="text/javascript">
$(function(){
	 /**
    验证上传文件的格式为excel
    **/
    var validatePing = $("#uploadExcel").validate({
        debug: true, //调试模式取消submit的默认提交功能   
        focusInvalid: false, //当为false时，验证无效时，没有焦点响应  
        onkeyup: false,   
        submitHandler: function(form){   //表单提交句柄,为一回调函数，带一个参数：form   
            alert("上传文件开始,耐心等待处理返回结果！");   
            form.submit();   //提交表单   
        },   
        rules:{
        	excelFile:{
                required:true,
                checkFile:true
            }
        }        
    });      
	
});
</script>
 		
 	
</html>