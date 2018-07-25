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
        <script type="text/javascript"src="<%=path %>/JqueryValidate/jquery-1.11.1.js"></script>
		<script type="text/javascript"src="<%=path %>/bootstrap-3.3.7/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="<%=path %>/JqueryValidate/jquery.validate.js"></script>
		<script type="text/javascript" src="<%=path %>/JqueryValidate/messages_zh.js"></script>
		<script type="text/javascript" src="<%=path %>/JqueryValidate/additional-methods.js"></script>
        <title>日计工页面</title>
        <style type="text/css">
            .width{
            	width:55px;
            }
        </style>
        <!-- <script type="text/javascript">
        	$(function(){
        		var oBtn=$("#setUpValue");
        		oBtn.on('click',function(){
        			var oIpt=$("#setValue").val();
        			var zd=$('#oSelect1 option:selected').val();
        			var oInputs=$("."+zd);
        			for(var i=0;i<oInputs.length;i++){
        				oInputs[i].value=oIpt;
        			}
        		});
        	});
        </script> -->
    </head>
    <body>
    	<div class="container">
    		<div class="row">
    			<form class="form-inline" action="<%=path%>/countDayWork/countDayWorkList" method="get">
    			  <input hidden="hidden" id="reviewDeptCode" value="${deptCode }"/>
				  <div class="form-group">
				    <label for="exampleInputName2">部门：</label>
				    <select id="oSelectDept" class="form-control" name="deptCode">
					</select>
				  </div>
    			  <div class="form-group">
    			  	<label for="dayTime">日期：</label>
    			  	<input type="date" name="day" id="dayTime" value="${oneDay }"/>
    			  	
     			  </div>
     			  
				  <div class="form-group">
				    <label for="oSelect">班次：</label>
				    <select id="oSelect" class="form-control" name="banci">
				    	<option value="日班" <c:if test="${'日班'==banci}">selected='selected'</c:if>>日班</option>
				    	<option value="前夜" <c:if test="${'前夜'==banci}">selected='selected'</c:if>>前夜</option>
				    	<option value="后夜" <c:if test="${'后夜'==banci}">selected='selected'</c:if>>后夜</option>
				    	<option value="日夜" <c:if test="${'日夜'==banci}">selected='selected'</c:if>>日夜</option>
				    	<option value="夜班" <c:if test="${'夜班'==banci}">selected='selected'</c:if>>夜班</option>
				    	<option value="日后" <c:if test="${'日后'==banci}">selected='selected'</c:if>>日后</option>
					</select>
				  </div>
				  
				  <button type="submit" class="btn btn-success">查询</button>
				</form>
    		</div>
    		<div class="row">
	    		<form id="updateItem" class="form-inline" action="<%=path%>/countDayWork/dayWorkUpdate" method="get">
	    			  <div class="form-group">
					    <label for="exampleInputName2">部门：</label>
					    <select id="SelectDept" class="form-control" name="deptCode">
						</select>
				  	  </div>
	    			  <div class="form-group">
	    			  	<label for="dayTime">日期：</label>
	    			  	<input type="date" name="day" value="${oneDay }"/>
     			  	  </div>
	    			  <div class="form-group">
				    	<label for="oSelect">班次：</label>
					    <select id="oSelect" class="form-control" name="banci">
					    <option value="riban" <c:if test="${'日班'==banci}">selected='selected'</c:if>>日班</option>
				    	<option value="qianye" <c:if test="${'前夜'==banci}">selected='selected'</c:if>>前夜</option>
				    	<option value="houye" <c:if test="${'后夜'==banci}">selected='selected'</c:if>>后夜</option>
				    	<option value="riye" <c:if test="${'日夜'==banci}">selected='selected'</c:if>>日夜</option>
				    	<option value="yeban" <c:if test="${'夜班'==banci}">selected='selected'</c:if>>夜班</option>
				    	<option value="rihou" <c:if test="${'日后'==banci}">selected='selected'</c:if>>日后</option>
					    	
						</select>
				  	  </div>
					  <div class="form-group">
					    <label for="oSelect">选择列：</label>
					    <select id="oSelect1" class="form-control" name="item">
					    	<option value="translocation">运转办理列</option>
					    	<option value="aload">装卸车数</option>
					    	<option value="brigades">旅发人数</option>
					    	<option value="passenger">客运收入</option>
					    	<option value="freight">货运收入</option>
					    	<option value="transport">运输收入</option>
						</select>
					  </div>
					  <div class="form-group">
	    			  	<label for="setValue">计工输入值</label>
	    			  	<input id="setValue" type="number" name="setValue"/>
	     			  </div>
					  <button type="submit" class="btn btn-success">计工</button>
				  </form>
    		</div>
    		<div class="row">
			    <table class="table table-bordered table-hover table-striped" contenteditable="true" id="userTable">
    				<thead style="background:#3399cc">
    					<tr class="bg-info">
<!--     						<th>工号</th>
 -->	    					<th>姓名</th>
<!-- 	    					<th>职务</th>
 -->	    					<th>班次</th>
	    					<th>派工岗位</th>
	    					<th>时间</th>
	    					<th>运转办理列(派)</th>
	    					<th>运转办理列(计)</th>
	    					<th>装卸车数(派)</th>
	    					<th>装卸车数(计)</th>
	    					<th>旅发人数(派)</th>
	    					<th>旅发人数(计)</th>
	    					<th>客运收入(派)</th>
	    					<th>客运收入(计)</th>
	    					<th>货运收入(派)</th>
	    					<th>货运收入(计)</th>
	    					<th>运输收入(派)</th>
	    					<th>运输收入(计)</th>
	    					
	    					<th>操作</th>
    					</tr>
    				</thead>
    				<tbody>
    					<c:forEach items="${list}" var="row">
    					<tr class='success'>
    						<form action="<%=path%>/countDayWork/saveOrUpdate" method="POST">
    							<input hidden="hidden" name="id" value="${row.id }">
    							<input hidden="hidden" name="deptCode" value="${row.deptCode }">
    						    <input type="text" hidden="hidden" name="userId" value="${row.userId }" readonly="readonly" class="width">
     							<input type="text" hidden="hidden" name="position" value="${row.position }" readonly="readonly" class="width">
     							<td><input style="border-style:none;background-color: transparent" type="text" name="username" value="${row.username }" readonly="readonly" class="width"></td>
     							<td><input style="border-style:none;background-color: transparent" type="text" name="banci" value="${row.banci }" readonly="readonly" class="width"></td>
    							<td><input style="border-style:none;background-color: transparent" type="text" name="postWalk" value="${row.postWalk }" readonly="readonly" class="width"></td>
    							<td><input style="border-style:none;background-color: transparent" type="text" name="date" value="${row.date }" readonly="readonly" class="width"></td>
    							
    							<td><input style="border-style:none;background-color: transparent" type="number" name="translocation_p" value="${row.translocation_p }" class="width translocation" readonly="readonly"></td>
    							<td><input type="number" name="translocation" value="${row.translocation }" class="width translocation"></td>
    							<td><input style="border-style:none;background-color: transparent" type="number" name="load_p" value="${row.load_p }" class="width load" readonly="readonly"></td>
    							<td><input type="number" name="load" value="${row.load }" class="width load"></td>
    							<td><input style="border-style:none;background-color: transparent" type="number" name="brigades_p" value="${row.brigades_p }" class="width brigades" readonly="readonly"></td>
    							<td><input type="number" name="brigades" value="${row.brigades }" class="width brigades"></td>
    							<td><input style="border-style:none;background-color: transparent" type="number" name="passenger_p" value="${row.passenger_p }" class="width passenger" readonly="readonly"></td>
    							<td><input type="number" step="0.01" name="passenger" value="${row.passenger }" class="width passenger"></td>
    							<td><input style="border-style:none;background-color: transparent" type="number" name="freight_p" value="${row.freight_p }" class="width freight" readonly="readonly"></td>
    							<td><input type="number" step="0.01" name="freight" value="${row.freight }" class="width freight"></td>
    							<td><input style="border-style:none;background-color: transparent" type="number" name="transport_p" value="${row.transport_p }" class="width transport" readonly="readonly"></td>
    							<td><input type="number" step="0.01" name="transport" value="${row.transport }" class="width transport"></td>
    							
    							<td><input class="btn btn-success" type="submit" value="提交"></td>
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
	//加载部门select列
	selectDataLoad();
	selectDataLoad2();

	 /**
    验证上传文件的格式为excel
    **/
    var validatePing = $("#updateItem").validate({
        debug: true, //调试模式取消submit的默认提交功能   
        focusInvalid: false, //当为false时，验证无效时，没有焦点响应  
        onkeyup: false,   
        submitHandler: function(form){   //表单提交句柄,为一回调函数，带一个参数：form   
            form.submit();   //提交表单   
        },   
        rules:{
        	setValue:{
                required:true,
            }
        }        
    });      

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
	//加载下拉框选项
	function selectDataLoad2(){
		$.get(
			"<%=path%>/sysManager/position",
			function(data){
				var code=$("#reviewDeptCode").val();
				var oArray=eval(data);
				for(var i=0;i<oArray.length;i++){
					var department=oArray[i].department;
					var deptCode=oArray[i].deptCode;
					if(deptCode==code){
						$("#SelectDept").append("<option selected='selected' value="+deptCode+">"+department+"</option>");
					}else{
						$("#SelectDept").append("<option value="+deptCode+">"+department+"</option>");
					}
				}
			},"json"
		);
	}
	
</script>
</html>