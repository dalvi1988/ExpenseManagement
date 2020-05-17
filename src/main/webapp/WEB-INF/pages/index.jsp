<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>Expense Management System</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  
 	
 	<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/themes/base/jquery-ui.css" />
 <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
 <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/jquery-ui.min.js"></script>
    <script type="text/javascript" src=<spring:url value="/grid/pqgrid.min.js"/> ></script>
    <link rel="stylesheet" href=<spring:url value="/grid/pqgrid.min.css"/> />
 	<script type="text/javascript" src=<spring:url value="/scripts/commonJS.js"/> ></script>
 	
 	<link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/themes/cupertino/jquery-ui.css" rel="stylesheet">
 	<!-- <link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/themes/cupertino/jquery-ui.css" rel="stylesheet"> -->
  
  <!-- Bootstrap 3.3.6 -->
  <link rel="stylesheet" href=<spring:url value="/theme/bootstrap/css/bootstrap.min.css"/> />
  <!-- Font Awesome -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
  <!-- Ionicons -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href=<spring:url value="/theme/dist/css/AdminLTE.css"/> />
  <!-- AdminLTE Skins. Choose a skin from the css/skins
       folder instead of downloading all of them to reduce the load. -->
  <link rel="stylesheet" href=<spring:url value="/theme/dist/css/skins/skin-blue.min.css"/> />
  <!-- iCheck -->
  <link rel="stylesheet" href=<spring:url value="/theme/plugins/iCheck/flat/blue.css"/> />
  <!-- bootstrap wysihtml5 - text editor -->
  <link rel="stylesheet" href=<spring:url value="/theme/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css"/> />
<style type="text/css">
.ui-state-error, .ui-widget-content .ui-state-error, .ui-widget-header .ui-state-error {
    border: 1px solid #cd0a0a/*{borderColorError}*/;
    background: #fef1ec/*{bgColorError};url(images/ui-bg_glass_95_fef1ec_1x400.png)/*{bgImgUrlError}; 50%/*{bgErrorXPos}*/ 50%/*{bgErrorYPos}*/repeat-x/*{bgErrorRepeat}*/;
    color: #c71010;
}
</style>
	<script>
	
	$(document).ready(function(){
		$('a').click(function() {
			//$('.overlay').show();
			$(".alert").hide();
		});
	    
		$("ul.sidebar-menu li a").click(function() {
			activeInactiveMenu(this);
		});
 
	    $('.employeeDashboard').click();
	    
	});
	
		
	function logout(){
    	document.getElementById("logoutForm").submit();
    }
	</script>
</head>
<body class="hold-transition skin-blue sidebar-mini" style="height:100%">
<div class="wrapper" style="height:100%">

  <header class="main-header">
    <!-- Logo -->
    <a href="#" class="logo">
      <!-- mini logo for sidebar mini 50x50 pixels -->
      <span class="logo-mini"><b>EMS</b></span>
      <!-- logo for regular state and mobile devices -->
      <span class="logo-lg"><b>Expense</b>Management</span>
    </a>
    <!-- Header Navbar: style can be found in header.less -->
    <nav class="navbar navbar-static-top">
      <!-- Sidebar toggle button-->
      <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
        <span class="sr-only">Toggle navigation</span>
      </a>
      <form:form action="logout" method="post" id="logoutForm">
      
      <div class="navbar-custom-menu">
        <ul class="nav navbar-nav">
         <!-- User Account: style can be found in dropdown.less -->
          <li class="dropdown user user-menu">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
              <img src="theme/dist/img/user2-160x160.jpg" class="user-image" alt="User Image">
              <span class="hidden-xs"><sec:authentication property="principal.loginDTO.employeeDTO.fullName" /></span>
            </a>
         </li>
          <!-- Control Sidebar Toggle Button -->
          <li>
                <a href="#" class=" btn-flat" onclick="logout();">Sign out</a>
          </li>
        </ul>
      </div>
      </form:form>
    </nav>
  </header>
  <!-- Left side column. contains the logo and sidebar -->
  <aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
      <!-- Sidebar user panel -->
      <div class="user-panel">
        <div class="pull-left image">
          <img src="theme/dist/img/user2-160x160.jpg" class="img-circle" alt="User Image">
        </div>
        <div class="pull-left info">
          <h4><sec:authentication property="principal.loginDTO.employeeDTO.branchDTO.companyDTO.companyName" /></h4>
          <h5>(<sec:authentication property="principal.loginDTO.employeeDTO.branchDTO.branchName" />)</h5>
        </div>
      </div>
      <!-- sidebar menu: : style can be found in sidebar.less -->
      <ul class="sidebar-menu">
        <li class="header">MAIN NAVIGATION</li>
        
        <li>
          <a href="#" class="employeeDashboard">
            <i class="fa fa-dashboard"></i> <span>Dashboard</span>
          </a>
        </li>
        
        <li>
          <a href="#" class="expense">
            <i class="fa fa-calendar"></i> <span>Create New Expense</span>
          </a>
        </li>
        <li>
          <a href="#" class="viewDraftExpense">
            <i class="fa fa-calendar"></i> <span>Draft Expenses</span>
          </a>
        </li>
        
        <li class="treeview">
          <a href="#">
            <i class="fa fa-th"></i> <span>Expense Voucher Status</span>
            <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
          </a>
          <ul class="treeview-menu">
            <li><a href="#" class="pendingExpense"><i class="fa fa-circle-o"></i>Pending Expenses</a></li>
            <li><a href="#" class="rejectedExpense"><i class="fa fa-circle-o"></i>Rejected Expenses</a></li>
            <li><a href="#" class="pendingExpensesAtPaymentDesk"><i class="fa fa-circle-o"></i>Expenses at Payment Desk</a></li>
            <li><a href="#" class="paidExpense"><i class="fa fa-circle-o"></i>Paid Expenses</a></li>
            <li><a href="#" class="processedByMeExpense"><i class="fa fa-circle-o"></i>Processed Expense By Me</a></li>
          </ul>
        </li>
        
        <li class="treeview">
          <a href="#">
            <i class="fa fa-th"></i> <span>Advances</span>
            <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
          </a>
          <ul class="treeview-menu">
            <li><a href="#" class="advance"><i class="fa fa-circle-o"></i>Request Advance</a></li>
            <li><a href="#" class="viewDraftAdvance"><i class="fa fa-circle-o"></i>Draft Advance</a></li>
            <li><a href="#" class="pendingAdvance"><i class="fa fa-circle-o"></i>Pending Advance</a></li>
            <li><a href="#" class="rejectedAdvance"><i class="fa fa-circle-o"></i>Rejected Advance</a></li>
            <li><a href="#" class="pendingAdvanceAtPaymentDesk"><i class="fa fa-circle-o"></i>Advances at Payment Desk</a></li>
            <li><a href="#" class="paidAdvances"><i class="fa fa-circle-o"></i>Paid Advances</a></li>
            <li><a href="#" class="processedByMeAdvances"><i class="fa fa-circle-o"></i>Processed Advances By Me</a></li>
          </ul>
        </li>
        
        <sec:authorize access="hasAnyRole('ADMIN_ROLE','SUPER_ADMIN')" var="isAuthorizeAny">
        <li class="treeview">
          <a href="#">
            <i class="fa fa-edit"></i>
            <span>Master</span>
            <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
          </a>
          <ul class="treeview-menu">
            <li><a href="#" class="employee"><i class="fa fa-circle-o"></i> Employee Master</a></li>
            <li><a href="#" class="departmentHead"><i class="fa fa-circle-o"></i> Department Head Master</a></li>
            <li><a href="#" class="branch"><i class="fa fa-circle-o"></i> Branch Master</a></li>
            <li><a href="#" class="event"><i class="fa fa-circle-o"></i> Event Master</a></li>
            <li><a href="#" class="expenseCategory"><i class="fa fa-circle-o"></i> Expense Category Master</a></li>
            
          </ul>
        </li>
        <li>
          <a href="#" class="approvalFlow">
            <i class="fa fa-calendar"></i> <span>Approval Flow Master</span>
          </a>
        </li>
        </sec:authorize>
         <li>
          <a href="#" class="toBeApproveExpense">
            <i class="fa fa-calendar"></i> <span>Expense For Approval</span>
          </a>
        </li>
        <li>
          <a href="#" class="toBeApproveAdvance">
            <i class="fa fa-calendar"></i> <span>Advance For Approval</span>
          </a>
        </li>
        
        <sec:authorize access="hasAnyRole('ADMIN_ROLE')" var="isAuthorizeAny">
        <li class="treeview">
          <a href="#">
            <i class="fa fa-edit"></i>
            <span>Payment Desk</span>
            <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
          </a>
          <ul class="treeview-menu">
            <li><a href="#" class="paymentDeskExpenses"><i class="fa fa-circle-o"></i> Expense Voucher</a></li>
            <li><a href="#" class="paymentDeskAdvance"><i class="fa fa-circle-o"></i> Advance Voucher</a></li>
            
          </ul>
        </li>
        </sec:authorize>
        
        <sec:authorize access="hasAnyRole('SUPER_ADMIN')" var="isAuthorizeAny">
         <li class="treeview">
          <a href="#">
            <i class="fa fa-edit"></i>
            <span>Super Admin</span>
            <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>

          </a>
          <ul class="treeview-menu">
            <li><a href="#" class="company"><i class="fa fa-circle-o"></i> Company Master</a></li>
            <li><a href="#" class="department"><i class="fa fa-circle-o"></i> Department Master</a></li>
          </ul>
        </li>
        </sec:authorize>
        
        <!-- <li class="treeview">
          <a href="#">
            <i class="fa fa-folder"></i> <span>Examples</span>
            <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
          </a>
          <ul class="treeview-menu">
            <li><a href="pages/examples/invoice.html"><i class="fa fa-circle-o"></i> Invoice</a></li>
            <li><a href="pages/examples/profile.html"><i class="fa fa-circle-o"></i> Profile</a></li>
            <li><a href="pages/examples/login.html"><i class="fa fa-circle-o"></i> Login</a></li>
            <li><a href="pages/examples/register.html"><i class="fa fa-circle-o"></i> Register</a></li>
            <li><a href="pages/examples/lockscreen.html"><i class="fa fa-circle-o"></i> Lockscreen</a></li>
            <li><a href="pages/examples/404.html"><i class="fa fa-circle-o"></i> 404 Error</a></li>
            <li><a href="pages/examples/500.html"><i class="fa fa-circle-o"></i> 500 Error</a></li>
            <li><a href="pages/examples/blank.html"><i class="fa fa-circle-o"></i> Blank Page</a></li>
            <li><a href="pages/examples/pace.html"><i class="fa fa-circle-o"></i> Pace Page</a></li>
          </ul>
        </li> -->
        
      </ul>
    </section>
    <!-- /.sidebar -->
  </aside>

  <!-- Content Wrapper. Contains page content -->
  
  <div class="box content-wrapper">
    <div class="alert alert-dismissible " style="display: none" >
           
    </div>

    <!-- Main content -->
    <section class=" content">
    
    </section>
     
     <div class="overlay" >
        <i class="fa fa-refresh fa-spin"></i>
      </div>
    <!-- /.content -->
    </div>
  <!-- /.content-wrapper -->
  
  <footer class="main-footer">
    <div class="pull-right hidden-xs">
      <b>Version</b> 1.0.0
    </div>
    <strong>Copyright &copy; 2016-2018 <a href="http://finsoftsolution.com">Finsoft Solution</a>.</strong> All rights
    reserved.
  </footer>
  
  
</div>
<!-- ./wrapper -->

<!-- Resolve conflict in jQuery UI tooltip with Bootstrap tooltip -->
<script>
  $.widget.bridge('uibutton', $.ui.button);
</script>

<!-- Bootstrap 3.3.6 -->
<script type="text/javascript" src=<spring:url value="/theme/bootstrap/js/bootstrap.min.js"/> ></script>
<!-- Morris.js charts -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/raphael/2.1.0/raphael-min.js"></script>
<script type="text/javascript" src=<spring:url value="/theme/plugins/morris/morris.min.js"/> ></script>
<!-- Sparkline -->
<script type="text/javascript" src=<spring:url value="/theme/plugins/sparkline/jquery.sparkline.min.js"/> ></script>
<!-- jvectormap -->
<script type="text/javascript" src=<spring:url value="/theme/plugins/jvectormap/jquery-jvectormap-1.2.2.min.js"/> ></script>
<script type="text/javascript" src=<spring:url value="/theme/plugins/jvectormap/jquery-jvectormap-world-mill-en.js"/> ></script>

<!-- Bootstrap WYSIHTML5 -->
<script src=""></script>
<script type="text/javascript" src=<spring:url value="/theme/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.all.min.js"/> ></script>
<!-- Slimscroll -->
<script type="text/javascript" src=<spring:url value="/theme/plugins/slimScroll/jquery.slimscroll.min.js"/> ></script>
<!-- FastClick -->
<script type="text/javascript" src=<spring:url value="/theme/plugins/fastclick/fastclick.js"/> ></script>
<!-- ChartJS 1.0.1 -->
<!-- <script type="text/javascript" src=<spring:url value="/theme/plugins/chartjs/Chart.min.js"/> ></script> -->

<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.6.0/Chart.min.js"></script>
<!-- AdminLTE App -->
<script type="text/javascript" src=<spring:url value="/theme/dist/js/app.min.js"/> ></script>
</body>
</html>
