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
    	<div class="container">
    		<div class="row">
    			
    			<form class="form-inline" action="<%=path%>/distribution/salaryList" method="get">
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
	    					<!-- <th>上线</th>
	    					<th>日常考核</th>
	    					<th>备注</th> -->
	    					<th>应发工资</th>
	    					<th>操作</th>
    					</tr>
    				</thead>
    				<tbody>
    					<c:forEach items="${list}" var="row">
	    					<tr class='success'>   
	    					 	<form action="<%=path%>/distribution/salaryDetail?employeeId=${row.employeeId }&employeeName=${row.employeeName }&employeeJob=${row.employeeJob }&baseSalary=${row.baseSalary }&coefficient=${row.coefficient }&translocation=${row.translocation }&tran_price=${row.tran_price }&brigades=${row.brigades }&brig_price=${row.brig_price }&passenger=${row.passenger }&pass_price=${row.pass_price }&freight=${row.freight }&frei_price=${row.frei_price }&transport=${row.transport }&trans_price=${row.trans_price }&payroll=${row.payroll }&month=${row.month}&deptCode=${row.deptCode}" method="POST">
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
		   							
		   							<td>${row.payroll }</td>
		   							<td><button type="submit" class="btn btn-primary btn-sm active">详细</button></td>
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
 			/* var examine=$(".examine");
 			for(var i=0;i<examine.length;i++){
 				examine[i].index=i;
 			}
 			
 			var payroll=0;
 			//给select标签绑定事件 
 			$(".examine").unbind().bind('change', function() {
 				var index=this.index;
 				var payrolls=$('.payroll');
 				var examine=this.value;//获取当前上线应扣除的工资数
 				if(payroll==0){
 	 				payroll=payrolls[index].value;//原始的实发工资
 				}
 				payrolls[index].value=payroll-examine;
 			});
 			
 			//获取初始的工资信息
 			var oldpayrolls=$(".oldpayroll");
 	 		var assessment=$(".assessment");
 	 		for(var i=0;i<assessment.length;i++){
 	 			assessment[i].index=i;
 	 		}
 	 		//绩效工资乘以百分比作为扣掉工资
 	 		$(".assessment").unbind().bind('change',function(){
 	 			var index=this.index;
 	 			var temp=this.value;//当前百分比
 	 			var oldpayroll=oldpayrolls[index].value;//原始工资信息
 	 			var jiandiaoSalary=oldpayroll*(temp/100);
 	 			var payrolls=$('.payroll');
 	 			payrolls[index].value=payrolls[index].value-jiandiaoSalary;
 	 			
 	 		});
 		}); */
 		
 		
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