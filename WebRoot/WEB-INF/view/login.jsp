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
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"> 
<meta name="viewport" content="width=device-width, initial-scale=1"> 
<title>太原车务段网络派计工系统</title>
<link rel="stylesheet" type="text/css" href="<%=path %>/loginPage/css/normalize.css" />
<link rel="stylesheet" type="text/css" href="<%=path %>/loginPage/css/demo.css" />
<!--必要样式-->
<link rel="stylesheet" type="text/css" href="<%=path %>/loginPage/css/component.css" />
<script src="<%=path %>/loginPage/js/html5.js"></script>
</head>
<body>
		<div class="container demo-1">
			<div class="content">
				<div id="large-header" class="large-header">
					<canvas id="demo-canvas"></canvas>
					<div class="logo_box">
						<h3>欢迎登录太原车务段网络派计工系统</h3>
						<form id="loginPageForm" action="<%=path%>/code/checkUser" method="post" name="form1">
							<div class="input_outer">
								<span class="u_user"></span>
								<input id="account" name="account" class="text" style="color: #FFFFFF !important" type="text" placeholder="请输入账户">
							</div>
							<div class="row">
								<span id="accountMsg"></span>
							</div>
							<div class="input_outer">
								<span class="us_uer"></span>
								<input id="password" name="password" class="text" style="color: #FFFFFF !important; position:absolute; z-index:100;"value="" type="password" placeholder="请输入密码">
							</div>
							<div class="row">
								<span id="passwordMsg"></span>
							</div>
							<div class="input_outer">
								<span class="us_uer"></span>
								<input id="code" name="code" class="text" style="color: #FFFFFF !important; position:absolute; z-index:100;"value="" type="text" placeholder="请输入驗證碼">
							</div>
							<div class="row">
								<span id="codeMsg"></span>
							</div>
							<div class="mb2"><input type="submit" class="act-but submit" style="color: #FFFFFF" value="提交"></intput></div>
<!-- 							<div class="mb2"><a id="post" class="act-but submit" href="javascript:;" style="color: #FFFFFF">登录</a></div>
 -->							<img style="float:right" id="imgCode" alt="" src="<%=path %>/code/getCode?p="+Math.random() />
						</form>
					</div>
				</div>
			</div>
		</div><!-- /container -->
		
		<div style="text-align:center;">
<p>太原车务段网络派计工系统V1.0</p>
</div>
<script src="<%=path%>/AdminLTE/plugins/jQuery/jquery-2.2.3.min.js"></script>
<script src="<%=path%>/AdminLTE/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=path %>/loginPage/js/TweenLite.min.js"></script>
<script src="<%=path %>/loginPage/js/EasePack.min.js"></script>
<script src="<%=path %>/loginPage/js/rAF.js"></script>
<script src="<%=path %>/loginPage/js/demo-1.js"></script>
<script src="<%=path%>/JqueryValidate/jquery.js"></script>
<script src="<%=path%>/JqueryValidate/jquery-1.11.1.js"></script>
<script src="<%=path%>/JqueryValidate/jquery.validate.js"></script>
<script src="<%=path%>/JqueryValidate/messages_zh.js"></script>
<script type="text/javascript">
	$(function(){
		/* $("#post").on("click",function(){
			$("#loginPageForm").submit();
		}); */
		
		$("#imgCode").on("click",function(){
			$(this).attr("src","<%=path %>/code/getCode?p="+Math.random());
		});
		$("#loginPageForm").validate({
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
				account:{
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
				account:"required",
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
