<%@ page language="java" import="java.util.*"
	contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="Pragma" content="no-cache">
  <title><fmt:message key="home_title"/></title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <link rel="shortcut icon" type="image/x-ico" href="<%=path %>/AdminLTE/dist/img/chinamobile.ico"/> 
  <!-- Bootstrap 3.3.6 -->
  <link rel="stylesheet" href="<%=path%>/AdminLTE/bootstrap/css/bootstrap.min.css">
  <link rel="stylesheet" href="<%=path%>/AdminLTE/dist/css/AdminLTE.css">
  <!-- iCheck -->
  <link rel="stylesheet" href="<%=path%>/AdminLTE/plugins/iCheck/square/blue.css">
  <style type="text/css">
  	body{
  		background-color: #red 
  	}
  	
  </style>
</head>
<body class="hold-transition login-page">
<div class="login-box">
  <!-- /.login-logo -->
  <div class="login-box-body">
    <p class="login-box-msg"><fmt:message key="welcome_login" /></p>

    <form id="loginForm" action="<%=path%>/code/checkUser" method="post">
      <div class="form-group has-feedback">
        <input id="userId" type="text" class="form-control" name="account" placeholder="<fmt:message key="username"/>">
        <span class="glyphicon glyphicon-user form-control-feedback "></span>
        <span id="userIdMsg"></span>
      </div>
      <div class="form-group has-feedback">
        <input id="password" type="password" class="form-control" name="password" placeholder="<fmt:message key="password"/>">
        <span class="glyphicon glyphicon-lock form-control-feedback"></span>
        <span id="passwordMsg"></span>
      </div>
      <div class="row">
      	<div class="col-xs-12">
      	    <input id="code" type="text" class="form-control" name="code" placeholder="<fmt:message key="code"/>">
      	</div>
      </div>
      <div class="row">
      	<div class="col-xs-12">
      		<img style="float:right" id="imgCode" alt="" src="<%=path %>/code/getCode?p="+Math.random()">
      	</div>
      </div>
      <div class="row">
      	<div class="col-xs-12">
      		<span id="codeMsg"></span>
      	</div>
      </div>
      <div class="row">
        <!-- /.col -->
        <div class="col-xs-12">
          <button type="submit" class="btn btn-primary btn-block btn-flat"><fmt:message key="login"/></button>
        </div>
        <!-- /.col -->
      </div>
    </form>
  </div>
  <!-- /.login-box-body -->
</div>
<!-- /.login-box -->

<!-- jQuery 2.2.3 -->
<script src="<%=path%>/AdminLTE/plugins/jQuery/jquery-2.2.3.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="<%=path%>/AdminLTE/bootstrap/js/bootstrap.min.js"></script>
<!-- iCheck -->
<script src="<%=path%>/AdminLTE/plugins/iCheck/icheck.min.js"></script>
<script src="<%=path%>/JqueryValidate/jquery.js"></script>
<script src="<%=path%>/JqueryValidate/jquery-1.11.1.js"></script>
<script src="<%=path%>/JqueryValidate/jquery.validate.js"></script>
<script src="<%=path%>/JqueryValidate/messages_zh.js"></script>
<script type="text/javascript">
	$(function(){
		$("#imgCode").on("click",function(){
			$(this).attr("src","<%=path %>/code/getCode?p="+Math.random());
		});
		$("#loginForm").validate({
			debug:true,
			submitHandler:function(form){
				form.submit();
			},
			errorPlacement : function(error,element){
				var spanName=element.attr("id")+"Msg";
				if(spanName.indexOf(".")!=-1){//如果id里面包含点
					spanName = spanName.replace(".","\\.");
				}
				$("#"+spanName).append(error);
			},
			messages:{
				userId:{
					required:"用户名不能为空"
				},
				password:{
					required:"密码不能为空"
				},
				code:{
					required:"验证码不能为空",
					rangelength: $.validator.format("验证码的长度为4位"),
					remote:"验证码输入错误"
				}
			},
			rules:{
				userId:"required",
				password:"required",
				code:{
					required:true,
					rangelength:[4,4],
					remote:{
						url:"<%=path %>/code/chcekCode",
						type:"post",
						dataType:"text",
						data:{
							code:function(){
								return $("#code").val();
							}
						},
						dataFilter:function(data,type){
							console.log(data);
							if("true"==data){
								return true;
							}else{
								$("#code").val("");
								$("#imgCode").attr("src","<%=path %>/code/getCode?p="+new Date().getMilliseconds().toString());
								return false;
							}
						}
					}
				}
			}
			
		});
	});
</script>
</body>
</html>
