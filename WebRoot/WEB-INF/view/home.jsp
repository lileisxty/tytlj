<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
	String path = request.getContextPath();
	HttpSession Session = request.getSession();
 	String username = (String) Session.getAttribute("name");
 

 %>
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
  <link rel="stylesheet" href="<%=path %>/AdminLTE/bootstrap/css/bootstrap.min.css">
 
  <link rel="stylesheet" href="<%=path%>/AdminLTE/dist/css/AdminLTE.css">
  <!-- AdminLTE Skins. Choose a skin from the css/skins
       folder instead of downloading all of them to reduce the load. -->
  <link rel="stylesheet" href="<%=path %>/AdminLTE/dist/css/skins/_all-skins.min.css">
  <!-- iCheck -->
  <link rel="stylesheet" href="<%=path %>/AdminLTE/plugins/iCheck/flat/blue.css">
  <!-- Morris chart -->
  <link rel="stylesheet" href="<%=path %>/AdminLTE/plugins/morris/morris.css">
  <!-- jvectormap -->
  <link rel="stylesheet" href="<%=path %>/AdminLTE/plugins/jvectormap/jquery-jvectormap-1.2.2.css">
  <!-- Date Picker -->
  <link rel="stylesheet" href="<%=path %>/AdminLTE/plugins/datepicker/datepicker3.css">
  <!-- Daterange picker -->
  <link rel="stylesheet" href="<%=path %>/AdminLTE/plugins/daterangepicker/daterangepicker.css">
  <!-- bootstrap wysihtml5 - text editor -->
  <link rel="stylesheet" href="<%=path %>/AdminLTE/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css">
  <!-- jQuery 2.2.3 -->
  <script src="<%=path %>/AdminLTE/plugins/jQuery/jquery-2.2.3.min.js"></script>
  <script type="text/javascript">
  function changeFrameHeight(){
	    var ifm= document.getElementById("myiframe"); 
	    ifm.height=document.documentElement.clientHeight;
	}
	window.onresize=function(){  
	     changeFrameHeight();  
	} 
  </script>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

  <header class="main-header">
    <!-- Logo -->
    <a href="#" class="logo">
      <!-- mini logo for sidebar mini 50x50 pixels -->
      <span class="logo-mini"><fmt:message key="home_simple_title"/></span>
      <!-- logo for regular state and mobile devices -->
      <span class="logo-lg"><fmt:message key="home_simple_title"/></span>
    </a>
    <!-- Header Navbar: style can be found in header.less -->
    <nav class="navbar navbar-static-top">
      <!-- Sidebar toggle button-->
      <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
        <span class="sr-only">Toggle navigation</span>
      </a>
        <ul class="nav navbar-nav">
        </ul>
    </nav>
  </header>
  <!-- Left side column. contains the logo and sidebar -->
  <aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
      <!-- Sidebar user panel -->
      <div class="user-panel">
        <div class="pull-left image">
          <img src="<%=path %>/AdminLTE/dist/img/tl_logo.png" class="img-circle" alt="User Image">
        </div>
        <div class="pull-left info">
           <p>欢迎工号 <a href="<%=path %>/sysManager/changePwdPage" style="color:red" target="_blank"><%=username %></a> 用户!</p>
           <a href="<%=path%>/login/logout"><i class="fa fa-circle text-success"></i> <fmt:message key="logout"/></a>
        </div>
      </div>
      <!-- 左菜单列表 -->
      <ul class="sidebar-menu">
      	<!-- 系统管理 -->
        <li class="active treeview">
          <a href="#">
            <i class="glyphicon glyphicon-th-list"></i> <span><fmt:message key="systemManager"/></span>
            <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
          </a>       
          <ul class="treeview-menu">
          		<li><a href="<%=path %>/sysManager/index" target="home_right"><i class="fa fa-circle-o"></i><fmt:message key="dept_custom"/></a></li>
          		<shiro:hasRole name="admin">
          		<li><a href="<%=path %>/postPg/index" target="home_right"><i class="fa fa-circle-o"></i><fmt:message key="station_pg"/></a></li>
          		</shiro:hasRole>
          </ul>
        </li>
        <!-- 派工计工 -->
        <li class="active treeview">
          <a href="#">
            <i class="glyphicon glyphicon-th-list"></i> <span><fmt:message key="pic"/></span>
            <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
          </a>
          <ul class="treeview-menu">
          		<!-- 日派工 -->
          		<li><a href="<%=path %>/pic/dayWork" target="home_right"><i class="fa fa-circle-o"></i><fmt:message key="day_dispatched_workers"/></a></li>
				<!-- 日计工 -->
				<li><a href="<%=path %>/countDayWork/index" target="home_right"><i class="fa fa-circle-o"></i><fmt:message key="day_project_workers"/></a></li>
				<!-- 车站统计查询 -->
				<li><a href="<%=path %>/countQueryStation/index" target="home_right"><i class="fa fa-circle-o"></i><fmt:message key="statistical_query"/></a></li>
				<!-- 个人统计查询 -->
				<li><a href="<%=path %>/countQueryPerson/index" target="home_right"><i class="fa fa-circle-o"></i><fmt:message key="person_query"/></a></li>
          </ul>
        </li> 
        <!-- 二次分配 -->
         <li class="active treeview">
          <a href="#">
            <i class="glyphicon glyphicon-th-list"></i> <span><fmt:message key="distribution"/></span>
            <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
          </a>
          <ul class="treeview-menu">
          		<li><a href="<%=path %>/distribution/pieceworkliquidation" target="home_right"><i class="fa fa-circle-o"></i><fmt:message key="piecework_wages"/></a></li>
          		<shiro:hasRole name="admin">
          		<li><a href="<%=path %>/distribution/salaryExamine" target="home_right"><i class="fa fa-circle-o"></i><fmt:message key="salaryExamine"/></a></li>
          		</shiro:hasRole>
          </ul>
        </li>
        </ul>
    </section>
    <!-- /.sidebar -->
  </aside>

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
	  <iframe id="myiframe" width="100%" scrolling="yes" onload="changeFrameHeight()" frameborder="0" name="home_right">
	  
	  </iframe>
  </div>
  <!-- /.content-wrapper -->
  <footer class="main-footer">
    <div class="pull-right hidden-xs">
      <b>Version</b> 1.0
    </div>
    <strong><fmt:message key="system_name"/></strong> 
  </footer> 

  
  <!-- Add the sidebar's background. This div must be placed
       immediately after the control sidebar -->
  <div class="control-sidebar-bg"></div>
</div>
<!-- ./wrapper -->

<!-- jQuery 2.2.3 -->
<script src="<%=path %>/AdminLTE/plugins/jQuery/jquery-2.2.3.min.js"></script>
<!-- <script>
  $.widget.bridge('uibutton', $.ui.button);
</script> -->

<!-- Bootstrap 3.3.6 -->
<script src="<%=path %>/AdminLTE/bootstrap/js/bootstrap.min.js"></script>
<!-- Morris.js charts -->
<!-- Sparkline -->
<script src="<%=path %>/AdminLTE/plugins/sparkline/jquery.sparkline.min.js"></script>
<!-- jvectormap -->
<script src="<%=path %>/AdminLTE/plugins/jvectormap/jquery-jvectormap-1.2.2.min.js"></script>
<script src="<%=path %>/AdminLTE/plugins/jvectormap/jquery-jvectormap-world-mill-en.js"></script>
<!-- jQuery Knob Chart -->
<script src="<%=path %>/AdminLTE/plugins/knob/jquery.knob.js"></script>
<!-- daterangepicker -->
<script src="<%=path %>/AdminLTE/plugins/daterangepicker/daterangepicker.js"></script>
<!-- datepicker -->
<script src="<%=path %>/AdminLTE/plugins/datepicker/bootstrap-datepicker.js"></script>
<!-- Bootstrap WYSIHTML5 -->
<script src="<%=path %>/AdminLTE/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.all.min.js"></script>
<!-- Slimscroll -->
<script src="<%=path %>/AdminLTE/plugins/slimScroll/jquery.slimscroll.min.js"></script>
<!-- FastClick -->
<script src="<%=path %>/AdminLTE/plugins/fastclick/fastclick.js"></script>
<!-- AdminLTE App -->
<script src="<%=path %>/AdminLTE/dist/js/app.min.js"></script>
 <!-- AdminLTE for demo purposes -->
<script src="<%=path %>/AdminLTE/dist/js/demo.js"></script>

</body>
</html>
