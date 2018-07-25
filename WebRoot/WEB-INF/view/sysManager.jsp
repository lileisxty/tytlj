<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
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
        <title>机构管理人员</title>
    </head>
    <body>
    	<div class="container">
    		<div class="row">
    			<form class="form-inline">
				  <div class="form-group">
				    <label for="exampleInputName2">部门：</label>
				    	<select id="oSelect" class="form-control">
						</select>
				    </div>
				  <button id="queryForKeyWord" type="button" class="btn btn-success">查询</button>
				</form>
    		</div>
    		<div class="row">
			    <table class="table table-bordered table-hover table-striped" id="userTable">
    				<thead style="background:#3399cc">
    					<tr class="bg-info">
    						<th>工号</th>
	    					<th>姓名</th>
	    					<th>职务</th>
	    					<th>部门</th>
	    					<th>绩效标准</th>
    					</tr>
    				</thead>
    				<tbody>
    					
    				</tbody>
				</table>
			</div>
			<div id="splid" class="row"></div>
		</div>
</body>
<script type="text/javascript">
 		var jsCommonCp=1;//当前页
 		var jsCommonLs=20;//每页显示条数
 		var jsCommonCol="";//显示
 		var jsCommonKw="";//查询关键字
 		var jsCommonPageSize;//总页数
 		$(function(){
 			//clearTable();
 			selectDataLoad();
/*  			dataLoad();
 */ 			var oBtn=document.getElementById("queryForKeyWord");
 			//页面查询触发事件
 			oBtn.onclick=function(){
 				jsCommonKw=$('#oSelect option:selected').val();
 				dataLoad();
 			}
 		});
 		//加载下拉框选项
 		function selectDataLoad(){
 			$.get(
 				"<%=path%>/sysManager/position",
 				function(data){
 					var oArray=eval(data);
 					for(var i=0;i<oArray.length;i++){
 	 					var department=oArray[i].department;
 	 					var deptCodeVal=oArray[i].deptCode;
 						$("#oSelect").append("<option value="+deptCodeVal+">"+department+"</option>");
 					}
 				},"json"
 			);
 		}
 		function createSplitBar(data){
 			clearBar();
 			colPageSize(data.allRowsCount);
 			previousPageBar();
 			addBar(1);
 			if(jsCommonPageSize>4){
 				var seed=3;
 	 			if(jsCommonCp>2*seed){//当前的页面已经超过了预计的内容
 	 				addDetailPageBar();//追加省略点
 	 				var startPage=jsCommonCp-seed;//设置页码的起点
 	 				for(var x=startPage;x<=jsCommonCp+seed;x++){
 	 					if(x<jsCommonPageSize){//分页条不能大于总页数
 	 						addBar(x);	
 	 					}
 	 				}
 	 				if(jsCommonCp+seed*2<jsCommonPageSize){
 	 					addDetailPageBar();
 	 				}
 	 			}else{
 	 				for(var i=2;i<=jsCommonCp+seed;i++){
 	 					if(i<=jsCommonPageSize){
 	 						addBar(i);
 	 					}
 	 	 			}
 	 				if(jsCommonCp+seed<=jsCommonPageSize){
 	 					addDetailPageBar();
 	 				}
 	 			}
 			}else{
 				//总页数在4页以内
 				for(var i=2;i<=jsCommonPageSize;i++){
 					addBar(i);
 				}
 			}
 			nextPageBar();
 		}
 		function addDetailPageBar(){
 			var liObj=$("<li><span>...</span></li>");
 			$("#pageControl").append(liObj);
 		}
 		function previousPageBar(){
 			var liObj=$("<li></li>");
 			if(jsCommonCp==1){
 				liObj.attr("class","disabled");
 				liObj.append("<span>上一页</span>");
 			}else{
 				var aObj=$("<a stype='cursor: pointer'>上一页</a>");
 				aObj.on("click",function(){
 					jsCommonCp--;
 					dataLoad();
 				});
 				liObj.append(aObj);
 			}
 			$("#pageControl").append(liObj);
 		}
 		
 		function nextPageBar(){
 			var liObj=$("<li></li>");
 			if(jsCommonCp==jsCommonPageSize){
 				liObj.attr("class","disabled");
 				liObj.append("<span>下一页</span>");
 			}else{
 				var aObj=$("<a stype='cursor: pointer'>下一页</a>");
 				aObj.on("click",function(){
 					jsCommonCp++;
 					dataLoad();
 				});
 				liObj.append(aObj);
 			}
 			$("#pageControl").append(liObj);
 		}
 		
 		function addBar(index){
 			var liObj=$("<li></li>");
 			if(jsCommonCp==index){
 				liObj.attr("class","active");
 				liObj.append("<span>"+index+"</span>");
 			}else{
 				var aObj=$("<a stype='cursor:pointer'>"+index+"</a>");
 				aObj.on("click",function(){
 					jsCommonCp=index;//改变当前所在页
 					dataLoad();
 				});
 				liObj.append(aObj);
 			}
 			$("#pageControl").append(liObj);
 			
 		}
 		function colPageSize(allRecorders){
 			if(allRecorders==0){
 				jsCommonPageSize=1;
 			}else{
 				jsCommonPageSize=parseInt((allRecorders+jsCommonLs-1)/jsCommonLs);
 			}
 		}
 		function clearBar(){
 			$("#splid").empty();
 			$("#splid").append("<ul id='pageControl' class='pagination'></ul>");
 		}
 		function dataLoad(){
 			$.get(
 				"<%=path%>/sysManager/list",
 				{"cp":jsCommonCp,"ls":jsCommonLs,"col":jsCommonCol,"kw":jsCommonKw},
 				function(data){
 					clearTable();
 					createSplitBar(data);
 					for(var i=0;i<data.entityList.length;i++){
 						addRow(data.entityList[i].userId,data.entityList[i].username,data.entityList[i].position,data.entityList[i].department
 							,data.entityList[i].salary);
 					}
 				},"json"
 			);
 		}
 		function addRow(userId,username,position,department,salary){
 			$("#userTable").append("<tr class='success'><td>"+userId+"</td><td>"+username+"</td><td>"+position+"</td><td>"+department+"</td><td>"+salary+"</td></tr>");
 		}
 		function clearTable(){
 			$("#userTable tr:gt(0)").remove();
 		}
 	</script>
</html>
