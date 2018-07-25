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
        <title>日派工页面</title>
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
    			<form class="form-inline" action="<%=path%>/pic/dayWorkList" method="post">
    			  <div class="form-group">
    			  	<label for="exampleInputName2">时间：</label>
    			  	<input type="date" name="day" id="dayTime" value="${oneDay }"/>
    			  	<input hidden="hidden" id="reviewDeptCode" value="${deptCode }"/>
    			  	
     			  </div>
				  <div class="form-group">
				    <label for="exampleInputName2">部门：</label>
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
<!-- 	    					<td>部门</td>
 -->	    					<th>职务</th>
	    					<th>班次</th>
	    					<th>派工岗位</th>
	    					<th>运转办理列</th>
	    					<th>装卸车数</th>
	    					<th>旅发人数</th>
	    					<th>客运收入</th>
	    					<th>货运收入</th>
	    					<th>运输收入</th>
	    					<th>操作</th>
    					</tr>
    				</thead>
    				<tbody>
    					<c:forEach items="${list}" var="row">
    					<input hidden="hidden" class="flag" value="${row.isWork }">
    					<tr class='success'>
    						<form action="<%=path%>/pic/dayWork/save" method="POST">
    							<input hidden="hidden" name="id" value="${row.id }">
    							<input hidden="hidden" name="deptCode" value="${row.deptCode }">
    							<input hidden="hidden" id="isWork" value="${row.isWork }">
    							<input hidden="hidden" name="date" value="${row.date }">
    							<td><input style="border-style:none" type="text" name="userId" value="${row.userId }" readonly="readonly" class="width userId"></td>
    							<td><input style="border-style:none" type="text" name="username" value="${row.username }" readonly="readonly" class="width username"></td>
    							<td><input style="border-style:none" type="text" name="position" value="${row.position }" readonly="readonly" class="width position"></td>
    							<td>
	    							<select class="banci" name="banci" value="${row.banci }">
	    								<option value="日班" <c:if test="${'日班'==row.banci}">selected='selected'</c:if>>日班</option>
	    								<option value="前夜" <c:if test="${'前夜'==row.banci}">selected='selected'</c:if>>前夜</option>
	    								<option value="后夜" <c:if test="${'后夜'==row.banci}">selected='selected'</c:if>>后夜</option>
	    								<option value="日夜" <c:if test="${'日夜'==row.banci}">selected='selected'</c:if>>日夜</option>
	    								<option value="日后" <c:if test="${'日后'==row.banci}">selected='selected'</c:if>>日后</option>
	    								<option value="夜班" <c:if test="${'夜班'==row.banci}">selected='selected'</c:if> >夜班</option>
	    							</select>
    							</td>
    							<td>
	    							<select class="postWalk" name="postWalk" value="${row.postWalk }">
	    								<option value="${row.position }" <c:if test="${row.position==row.postWalk}">selected='selected'</c:if>>${row.position }</option>
	    								<option value="值班站长" <c:if test="${'值班站长'==row.postWalk}">selected='selected'</c:if>>值班站长</option>
	    								<option value="车站值班员" <c:if test="${'车站值班员'==row.postWalk}">selected='selected'</c:if>>车站值班员</option>
	    								<option value="助理值班员" <c:if test="${'助理值班员'==row.postWalk}">selected='selected'</c:if>>助理值班员</option>
	    								<option value="调车长" <c:if test="${'调车长'==row.postWalk}">selected='selected'</c:if>>调车长</option>
	    								<option value="调车区长" <c:if test="${'调车区长'==row.postWalk}">selected='selected'</c:if>>调车区长</option>
	    								<option value="连接员" <c:if test="${'连接员'==row.postWalk}">selected='selected'</c:if> >连接员</option>
	    								<option value="调车指导" <c:if test="${'调车指导'==row.postWalk}">selected='selected'</c:if>>调车指导</option>
	    								
	    								<option value="扳道员" <c:if test="${'扳道员'==row.postWalk}">selected='selected'</c:if>>扳道员</option>
	    								<option value="客运值班员" <c:if test="${'客运值班员'==row.postWalk}">selected='selected'</c:if>>客运值班员</option>
	    								<option value="售票员" <c:if test="${'售票员'==row.postWalk}">selected='selected'</c:if>>售票员</option>
	    								<option value="客运员" <c:if test="${'客运员'==row.postWalk}">selected='selected'</c:if>>客运员</option>
	    								<option value="货运值班员" <c:if test="${'货运值班员'==row.postWalk}">selected='selected'</c:if> >货运值班员</option>
	    								<option value="货运员" <c:if test="${'货运员'==row.postWalk}">selected='selected'</c:if>>货运员
	    								</option>
	    							</select>
    							</td>
	    						<td><input style="border-style:none" type="number" name="translocation" value="${row.translocation }" class="width translocation" readonly="readonly"></td>
	    						<td><input style="border-style:none" type="number" name="load" value="${row.load }" class="width load" readonly="readonly"></td>
	    						<td><input style="border-style:none" type="number" name="brigades" value="${row.brigades }" class="width brigades" readonly="readonly"></td>
	    						<td><input style="border-style:none" type="number" name="passenger" value="${row.passenger }" class="width passenger" readonly="readonly"></td>
	    						<td><input style="border-style:none" type="number" name="freight" value="${row.freight }" class="width freight" readonly="readonly"></td>
    							<td><input style="border-style:none" type="number" name="transport" value="${row.transport }" class="width transport" readonly="readonly"></td>
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
 			selectDataLoad();
 			var oBtn=document.getElementById("queryForKeyWord");
 			
 			var oBanci=$(".banci");
 			for(var i=0;i<oBanci.length;i++){
 				oBanci[i].index=i;
 			}
 			//给select标签绑定事件 
 			$(".banci").unbind().bind('change', function() {
 				 var index=this.index;
 				 var translocation;
				 var load;
				 var brigades;
				 var passenger;
				 var freight;
				 var transport;
 				 var banci=this.value;
 				 var day=$("#dayTime").val();
 				 var departCode=$('#oSelect option:selected').val();//获取部门编码
 				 $.get(
 		 				"<%=path%>/pic/banciWork",
 		 				{"day":day,"deptCode":departCode,"banci":banci},
 		 				function(data){
 		 					var oArray=eval(data);
 		 					translocation=oArray.translocation;
 		 					load=oArray.load;
							brigades=oArray.brigades;
							passenger=oArray.passenger;
							freight=oArray.freight;
							transport=oArray.transport;
							changeOtherValue(translocation,load,brigades,passenger,freight,transport,index);
 		 					/* for(var i=0;i<oArray.length;i++){
 		 	 					var department=oArray[i].department;
 		 	 					var deptCode=oArray[i].deptCode;
 		 						$("#oSelect").append("<option value="+deptCode+">"+department+"</option>");
 		 					} */
 		 				},"json"
 		 			);
 			});
 			//console.log(oBanci.length);
 			//页面查询触发事件
 			/* oBtn.onclick=function(){
 	 			var dayContent=$('#dayTime').val();
 				departCode=$('#oSelect option:selected').val();//获取部门编码
 				console.log("部门："+departCode+"时间："+dayContent);
 				dataLoad(dayContent,departCode);
 			}; */
 			//已经派过单的颜色提示
 			var translocations=$(".translocation");
			var loads=$(".load");
			var brigadeses=$(".brigades");
			var passengers=$(".passenger");
			var freights=$(".freight");
			var transports=$(".transport");
			var userId=$(".userId");
			var username=$(".username");
			var position=$(".position"); 
			
 			var oTrs=$(".success");
 			var oFlag=$(".flag");
 			for(var i=0;i<oTrs.length;i++){
 				console.log(oFlag[i].value);
 				if(oFlag[i].value=='1'){
 					
 					userId[i].style.background='#7FFF00';
 					username[i].style.background='#7FFF00';
 					position[i].style.background='#7FFF00';
 					oTrs[i].style.background='#7FFF00';
 					translocations[i].style.background='#7FFF00';
 					loads[i].style.background='#7FFF00';
 					brigadeses[i].style.background='#7FFF00';
 					passengers[i].style.background='#7FFF00';
 					freights[i].style.background='#7FFF00';
 					transports[i].style.background='#7FFF00';
 				}
 			}
 			
 		});
 		//修改其他input标签中的值
 		function changeOtherValue(translocation,load,brigades,passenger,freight,transport,index){
			console.log("change"+translocation+"--"+load+"--"+brigades+"--"+passenger+"--"+freight+"--"+transport+"--"+index);
			var translocations=$(".translocation");
			var loads=$(".load");
			var brigadeses=$(".brigades");
			var passengers=$(".passenger");
			var freights=$(".freight");
			var transports=$(".transport");
			translocations[index].value=translocation;
			loads[index].value=load;
			brigadeses[index].value=brigades;
			passengers[index].value=passenger;
			freights[index].value=freight;
			transports[index].value=transport;
 		}
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