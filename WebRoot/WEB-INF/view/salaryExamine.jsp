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
        
        <link rel="stylesheet" href="<%=path%>/bootstrap-3.3.7/css/bootstrap.min.css" />
        <link rel="stylesheet" href="<%=path%>/bootstrap-3.3.7/css/bootstrap-theme.min.css" />
        <script type="text/javascript" src="<%=path%>/JqueryValidate/jquery-2.2.3.min.js" ></script>
        <script type="text/javascript" src="<%=path%>/bootstrap-3.3.7/js/bootstrap.min.js" ></script>
        <script type="text/javascript" src="<%=path%>/JqueryValidate/jquery.js" ></script>
        <script type="text/javascript" src="<%=path%>/JqueryValidate/jquery-1.11.1.js" ></script>
        <script type="text/javascript" src="<%=path%>/JqueryValidate/jquery.validate.js" ></script>
        <script type="text/javascript" src="<%=path%>/JqueryValidate/messages_zh.js" ></script>
        <title>计件工资</title>
        <style type="text/css">
            .width{
            	width:70px;
            }
            tbody input{
            	BACKGROUND-COLOR: transparent;
            }
        </style>
    </head>
    <body>
    	<div class="container-fluid">
    		<div class="row">
    			
    			<form class="form-inline" action="<%=path%>/distribution/salaryExamineList" method="get">
    			  <div class="form-group">
    			  	<label for="dayTime">时间：</label>
    			  	<input type="month" name="month" id="dayTime" value="${month }"/>    			  	
     			  </div>
				  <div class="form-group">
				  	<input id="reviewDeptCode" value="${deptCode }" hidden="hidden">
				    <label for="oSelect">部门：</label>
				    <select id="oSelect" class="form-control" name="deptCode">
					</select>
				  </div>
				  <button type="submit" class="btn btn-success">查询</button>
				  <a id="export" class="btn btn-success" href="#">导出</a>
				</form>
    		</div>
    		<div class="row">
			    <table class="table table-bordered table-hover table-striped" contenteditable="true" id="userTable">
    				<thead style="background:#3399cc">
    					<tr class="bg-info">
    						<th>工号</th>
	    					<th>姓名</th>
	    					<th>职务</th>
	    					<th>绩效薪标准</th>
	    					<th>系数</th>
	    					<th colspan="2">运转办理列</th>
	    					<th colspan="2">装卸车数</th>
	    					<th colspan="2">旅发人数</th>
	    					<th colspan="2">客运收入</th>
	    					<th colspan="2">货运收入</th>
	    					<th colspan="2">运输收入</th>
	    					<th colspan="4">基本上线</th>
	    					<th colspan="2">日常考核</th>
	    					<th>应发工资</th>
	    					<th>操作</th>
    					</tr>
    				</thead>
    				<tbody>
    					<c:forEach items="${list}" var="row">
	    					<tr class='success'>   
	    					 	<form action="<%=path%>/distribution/salaryDetailDelete?id=${row.id }&month=${row.month}&deptCode=${row.deptCode}" method="POST">
		   							<td>${row.employeeId }</td>
		   							<td>${row.employeeName }</td>
		   							<td>${row.employeeJob }</td>
		   							<td>${row.baseSalary }</td>
		   							<td>${row.coefficient }</td>
		   							
		    						<td>${row.translocation }</td>
		    						<td>${row.tran_price }</td>
		    						
		    						<td>${row.load }</td>
		    						<td>${row.load_price }</td>
		    						
		    						<td>${row.brigades }</td>
		    						<td>${row.brig_price }</td>
		    						
		    						<td>${row.passenger }</td>
		    						<td>${row.pass_price }</td>
		    						
		    						<td>${row.freight }</td>
		    						<td>${row.frei_price }</td>
		    						
		   							<td>${row.transport }</td>
		   							<td>${row.trans_price }</td>
		   							
		   							<td>${row.examine }</td>
		   							<td>${row.bgRed }</td>
		   							<td>${row.red }</td>
		   							<td>${row.yellow }</td>
		   							<td>${row.assessment }</td>
		   							<td>${row.assessmentValue }</td>
		   							
		   							<td>${row.payroll }</td>
		   							<td><button type="submit" class="btn btn-primary btn-sm active">退回</button></td>
	   							</form>
	  						</tr>
    					</c:forEach>
    				</tbody>
				</table>
			</div>
			<div id="splid" class="row"></div>
		</div>
</body>
<script type="text/javascript">
 		$(function(){
 			selectDataLoad();
 		});
 		$("#export").click(function(){
 			var month=$("#dayTime").val();
 			var deptCode=$("#oSelect").val();
 			window.open("<%=path%>/distribution/exportSalary?&month="+month+"&deptCode="+deptCode, "_blank");
 		});
 		//加载下拉框选项
 		function selectDataLoad(){
 			$.get(
 				"<%=path%>/sysManager/position",
 				function(data){
 					var code=$("#reviewDeptCode").val();
 					var oArray=eval(data);
 					for(var i=0;i<oArray.length;i++){
 	 					var department=oArray[i].department;
 	 					var deptCode=oArray[i].deptCode;
 	 					if(deptCode==code){
 	 						$("#oSelect").append("<option selected='selected' value="+deptCode+">"+department+"</option>");
 	 					}else{
 	 						$("#oSelect").append("<option value="+deptCode+">"+department+"</option>");
 	 					}
 					}
 				},"json"
 			);
 		}
 		</script>
</html>