<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	String path = request.getContextPath();
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
        <title>修改密码</title>
    </head>
    <body>
    	<div class="container">
    		<div class="row">
    			<form id="changePwd" class="form-inline" action="<%=path %>/sysManager/savePwd" method="post" enctype="multipart/form-data">
				<div class="form-group">
					<input type="password" class="form-control" id="newPassword" name="newPassword"
						   placeholder="请输入密码" />
				</div>
				<input type="submit" class="btn btn-primary" value="保存密码"/>
			</form>
    		</div>
		</div>
</body>
<script type="text/javascript">
$(function(){
	 /**
    验证上传文件的格式为excel
    **/
    var validatePwd = $("#changePwd").validate({
        debug: true, //调试模式取消submit的默认提交功能   
        focusInvalid: false, //当为false时，验证无效时，没有焦点响应  
        onkeyup: false,   
        submitHandler: function(form){   //表单提交句柄,为一回调函数，带一个参数：form   
            form.submit();   //提交表单   
        },   
        rules:{
        	newPassword:{
                required:true
            }
        }        
    }); 
	
});
</script>
</html>