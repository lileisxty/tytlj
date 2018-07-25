<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	String path = request.getContextPath();
	HttpSession Session = request.getSession();
	String department = (String) Session.getAttribute("department");
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
        <title>车站统计查询</title>
    </head>
    <body>
    	<div class="container">
    		<div class="row">
    			<form class="form-inline" action="<%=path%>/countQueryStation/queryData" method="post">
    			  <input hidden="hidden" id="reviewDeptCode" value="${deptCode }"/>
				  <div class="form-group">
				    <label for="exampleInputName2">部门：</label>
				    <select id="oSelectDept" class="form-control" name="deptCode">
					</select>
				  </div>
    			  <div class="form-group">
    			  	<label for="startTime">开始时间：</label>
    			  	<input type="date" name="startDay" id="startTime" value="${startDay }"/>
     			  </div>
				  <div class="form-group">
    			  	<label for="endTime">结束时间：</label>
    			  	<input type="date" name="endDay" id="endTime" value="${endDay }"/>
     			  </div>
				  <button type="submit" class="btn btn-success">查询</button>
				</form>
    		</div>
    		<div class="row">
			    <table class="table table-bordered table-hover table-striped" contenteditable="true" id="userTable">
    				<thead style="background:#3399cc">
    					<tr class="bg-info">
    						<th>车站</th>
    						
	    					<th bgcolor="#4EEE94">运转办理列(派)</th>
	    					<th bgcolor="#FF8C69">运转办理列(计)</th>
	    					<th bgcolor="#FFC125">运转办理列(百分比)</th>
	    					
	    					<th bgcolor="#4EEE94">装卸车数(派)</th>
	    					<th bgcolor="#FF8C69">装卸车数(计)</th>
	    					<th bgcolor="#FFC125">装卸车数(百分比)</th>
	    					
	    					<th bgcolor="#4EEE94">旅发人数(派)</th>
	    					<th bgcolor="#FF8C69">旅发人数(计)</th>
	    					<th bgcolor="#FFC125">旅发人数(百分比)</th>
	    					
	    					<th bgcolor="#4EEE94">客运收入(派)</th>
	    					<th bgcolor="#FF8C69">客运收入(计)</th>
	    					<th bgcolor="#FFC125">客运收入(百分比)</th>
	    					
	    					<th bgcolor="#4EEE94">货运收入(派)</th>
	    					<th bgcolor="#FF8C69">货运收入(计)</th>
	    					<th bgcolor="#FFC125">货运收入(百分比)</th>
	    					
	    					<th bgcolor="#4EEE94">运输收入(派)</th>
	    					<th bgcolor="#FF8C69">运输收入(计)</th>
	    					<th bgcolor="#FFC125">运输收入(百分比)</th>
    					</tr>
    				</thead>
    				<tbody>
    					<c:forEach items="${list}" var="row">
	    					<tr>
	    						<td>${row.station}</td>
    							
    							<td bgcolor="#4EEE94">${row.translocation_p }</td>
    							<td bgcolor="#FF8C69">${row.translocation_j }</td>
    							<td bgcolor="#FFC125">${row.translocation_b }%</td>
    							
    							<td bgcolor="#4EEE94">${row.load_p }</td>
    							<td bgcolor="#FF8C69">${row.load_j }</td>
    							<td bgcolor="#FFC125">${row.load_b }%</td>
    							
    							<td bgcolor="#4EEE94">${row.brigades_p }</td>
    							<td bgcolor="#FF8C69">${row.brigades_j }</td>
    							<td bgcolor="#FFC125">${row.brigades_b }%</td>
    							
    							<td bgcolor="#4EEE94">${row.passenger_p }</td>
    							<td bgcolor="#FF8C69">${row.passenger_j }</td>
    							<td bgcolor="#FFC125">${row.passenger_b }%</td>
    							
    							<td bgcolor="#4EEE94">${row.freight_p }</td>
    							<td bgcolor="#FF8C69">${row.freight_j }</td>
    							<td bgcolor="#FFC125">${row.freight_b }%</td>
    							
    							<td bgcolor="#4EEE94">${row.transport_p }</td>
    							<td bgcolor="#FF8C69">${row.transport_j }</td>
    							<td bgcolor="#FFC125">${row.transport_b }%</td>
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
	//加载部门select列
	selectDataLoad();
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
						$("#oSelectDept").append("<option selected='selected' value="+deptCode+">"+department+"</option>");
					}else{
						$("#oSelectDept").append("<option value="+deptCode+">"+department+"</option>");
					}
				}
			},"json"
		);
	}
	
</script>
</html>