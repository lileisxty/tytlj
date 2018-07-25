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
        <style type="text/css">
        	span{
        	color:red
        	}
        </style>
        <title>工资详细页面</title>
	    <script type="text/javascript">
	        window.onload=function(){
	            $(".radio1").unbind().bind('click', function() {
	                $("#span1").html(300);
	                $("#span2").html(200);
	                $("#span3").html(100);
	                $("#examineType").val("基本");
	            });
	            $(".radio2").unbind().bind('click', function() {
	                $("#span1").html(500);
	                $("#span2").html(400);
	                $("#span3").html(300);
	                $("#examineType").val("上线");
	            });
                var payroll=$("#payroll").val();
	            $(".salary").unbind().bind("change",function(){
	                var vSpan1=  $("#span1").html();
	                var vSpan2=  $("#span2").html();
	                var vSpan3=  $("#span3").html();
	                var oOne=$("#one").val();
	                var oTwo=$("#two").val();
	                var oThree=$("#three").val();
	                var baseSalary=$("#baseSalary").val();
	                var percent=$("#four").val();
	                var examine=vSpan1*oOne+vSpan2*oTwo+vSpan3*oThree;
	                var assessmentValue=parseInt(percent*baseSalary/100);;
	                var salary=payroll-examine-assessmentValue;
	                $("#payroll").val(salary);
	                $("#examine").val(examine);
	                $("#assessmentValue").val(assessmentValue);
	            });
	            
	        };
	    </script>
</head>
<body>
<div class="container">
    <div class="row">
        <form class="form-horizontal" action="<%=path%>/distribution/saveSalary" method="post">
        	<input type="text" name="employeeId" value="${salary.employeeId }" hidden="hidden"/>
			<input type="text" name="coefficient" value="${salary.coefficient }" hidden="hidden"/>
			<input type="text" name="translocation" value="${salary.translocation }" hidden="hidden"/>
			<input type="text" name="tran_price" value="${salary.tran_price }" hidden="hidden"/>
			<input type="text" name="load" value="${salary.load }" hidden="hidden"/>
			<input type="text" name="load_price" value="${salary.load_price }" hidden="hidden"/>
			<input type="text" name="brigades" value="${salary.brigades }" hidden="hidden"/>
			<input type="text" name="brig_price" value="${salary.brig_price }" hidden="hidden"/>
			<input type="text" name="passenger" value="${salary.passenger }" hidden="hidden"/>
			<input type="text" name="pass_price" value="${salary.pass_price }" hidden="hidden"/>
			<input type="text" name="freight" value="${salary.freight }" hidden="hidden"/>
			<input type="text" name="frei_price" value="${salary.frei_price }" hidden="hidden"/>
			<input type="text" name="transport" value="${salary.transport }" hidden="hidden"/>
			<input type="text" name="trans_price" value="${salary.trans_price }" hidden="hidden"/>
        	<input type="text" name="deptCode" value="${salary.deptCode }" hidden="hidden"/>
        	<input type="text" name="month" value="${salary.month }" hidden="hidden"/>
        	<input type="text" id="assessmentValue" name="assessmentValue"hidden="hidden"/>
        	<input type="text" id="examineType" name="examineType"hidden="hidden"/>
        	
        	<input type="text" id="examine" name="examine" hidden="hidden"/>
        	
        	<div class="form-group">
                <label>姓名:</label>
                <input type="text" name="employeeName" value="${salary.employeeName }" readonly="readonly">
            </div>
        	<div class="form-group">
                <label>职务:</label>
                <input type="text" name="employeeJob" value="${salary.employeeJob }" readonly="readonly">
            </div>
            <div class="form-group">
                <label>绩效薪标准:</label>
                <input type="text" id="baseSalary" name="baseSalary" value="${salary.baseSalary }" readonly="readonly">
            </div>
            <div class="form-group">
                <div class="radio">
                    <label>
                        <input type="radio" name="optradio" value="1" class="radio1">基本:
                    </label>
                </div>

                <div class="radio">
                    <label>
                        <input type="radio" name="optradio" value="2" class="radio2">上线:
                    </label>
                </div>
            </div>
            <br>
            <div class="form-group">
                <label>大红:</label>
                <span id="span1"></span>
                <input type="number" id="one" name="bgRed" class="form-control salary" min="0" max="5" value="0">
                <label>红:</label>
                <span id="span2"></span>
                <input type="number" id="two" name="red" class="form-control salary" min="0" max="5" value="0">
                <label>黄:</label>
                <span id="span3"></span>
                <input type="number" id="three" name="yellow" class="form-control salary" min="0" max="5" value="0">
            </div>
            <!-- examine -->
            <div class="form-group">
                <label>其它考核:</label>
                <input type="number" name="assessment" id="four" class="form-control salary" min="0" max="100" value="0">
            </div>
            <div class="form-group">
                <label>考核原因:</label>
                <input type="text" name="remark" class="form-control"/>
            </div>
            <div class="form-group">
                <label>实发工资:</label>
                <input type="text" id="payroll" name="payroll" class="form-control" value="${salary.payroll }" readonly="readonly">
            </div>
			<button type="submit" class="btn btn-lg btn-success">提交</button>
        </form>
    </div>
</div>
</body>
</html>