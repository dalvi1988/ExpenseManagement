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
  
  <!-- Bootstrap 3.3.6 -->
  <link rel="stylesheet" href=<spring:url value="/theme/bootstrap/css/bootstrap.min.css"/> />
  <!-- Font Awesome -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
  <!-- Ionicons -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href=<spring:url value="/theme/dist/css/AdminLTE.min.css"/> />
  <!-- AdminLTE Skins. Choose a skin from the css/skins
       folder instead of downloading all of them to reduce the load. -->
  <link rel="stylesheet" href=<spring:url value="/theme/dist/css/skins/skin-blue.min.css"/> />
  <!-- iCheck -->
  <link rel="stylesheet" href=<spring:url value="/theme/plugins/iCheck/flat/blue.css"/> />
  <!-- bootstrap wysihtml5 - text editor -->
  <link rel="stylesheet" href=<spring:url value="/theme/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css"/> />

	<script>
	
	$(document).ready(function(){
		
		$('a').click(function() {
			$(".alert").hide();
		});
		
	    $('.branchMaster').click(function(){
	    	$( this ).parent().addClass("active")
	        $('.content').load('branch');
	     });
	    
	    $('.employeeMaster').click(function(){
	    	$( this ).parent().addClass("active")
	        $('.content').load('employee');
	     });
	    
	    $('.companyMaster').click(function(){
	    	$( this ).parent().addClass("active")
	        $('.content').load('company');
	     });
	    
	    $('.departmentHeadMaster').click(function(){
	    	$( this ).parent().addClass("active")
	        $('.content').load('departmentHead');
	     });
	    
	    $('.departmentMaster').click(function(){
	    	$( this ).parent().addClass("active")
	        $('.content').load('department');
	     });
	    
	    $('.expenseCategoryMaster').click(function(){
	    	$( this ).parent().addClass("active")
	        $('.content').load('expenseCategory');
	     });
	    
	    $('.approvalFlowMaster').click(function(){
	    	$( this ).parent().addClass("active")
	        $('.content').load('approvalFlow');
	     });
	    
	    $('.voucherForApproval').click(function(){
	    	$( this ).parent().addClass("active")
	        $('.content').load('toBeApproveExpense');
	     });
	    
	    $('.createExpense').click(function(){
	    	$( this ).parent().addClass("active")
	        $('.content').load('expense');
	     });
	    
	    $('.draftExpense').click(function(){
	    	$( this ).parent().addClass("active")
	        $('.content').load('viewDraftExpense');
	     });
	    
	    $('.pendingExpense').click(function(){
	    	$( this ).parent().addClass("active")
	        $('.content').load('pendingExpense');
	     });
	    
	    $('.eventMaster').click(function(){
	    	$( this ).parent().addClass("active")
	        $('.content').load('event');
	     });
	    
	    $('.requestAdvance').click(function(){
	    	$( this ).parent().addClass("active")
	        $('.content').load('advance');
	     });
	    
	    $('.draftAdvance').click(function(){
	    	$( this ).parent().addClass("active")
	        $('.content').load('viewDraftAdvance');
	     });
	    
	    $('.createExpense').click();
	    
	});
	function logout(){
    	document.getElementById("logoutForm").submit();
    }
	</script>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

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
          <p><sec:authentication property="principal.loginDTO.employeeDTO.fullName" /></p>
          <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
        </div>
      </div>
      <!-- sidebar menu: : style can be found in sidebar.less -->
      <ul class="sidebar-menu">
        <li class="header">MAIN NAVIGATION</li>
        
        <li>
          <a href="#" class="createExpense">
            <i class="fa fa-calendar"></i> <span>Create New Expense</span>
          </a>
        </li>
        <li>
          <a href="#" class="draftExpense">
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
            <li><a href="#" class="paymentDeskExpense"><i class="fa fa-circle-o"></i>Expenses at Payment Desk</a></li>
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
            <li><a href="#" class="requestAdvance"><i class="fa fa-circle-o"></i>Request Advance</a></li>
            <li><a href="#" class="draftAdvance"><i class="fa fa-circle-o"></i>Draft Advance</a></li>
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
            <li><a href="#" class="employeeMaster"><i class="fa fa-circle-o"></i> Employee Master</a></li>
            <li><a href="#" class="departmentHeadMaster"><i class="fa fa-circle-o"></i> Department Head Master</a></li>
            <li><a href="#" class="departmentMaster"><i class="fa fa-circle-o"></i> Department Master</a></li>
            <li><a href="#" class="branchMaster"><i class="fa fa-circle-o"></i> Branch Master</a></li>
            <li><a href="#" class="eventMaster"><i class="fa fa-circle-o"></i> Event Master</a></li>
            
            
          </ul>
        </li>
        <li>
          <a href="#" class="approvalFlowMaster">
            <i class="fa fa-calendar"></i> <span>Approval Flow Master</span>
          </a>
        </li>
        </sec:authorize>
         <li>
          <a href="#" class="voucherForApproval">
            <i class="fa fa-calendar"></i> <span>Voucher For Approval</span>
          </a>
        </li>
        
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
            <li><a href="#" class="companyMaster"><i class="fa fa-circle-o"></i> Country Master</a></li>
             <li><a href="#" class="expenseCategoryMaster"><i class="fa fa-circle-o"></i> Expense Category Master</a></li>
          </ul>
        </li>
        </sec:authorize>
        
        <li class="treeview">
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
        </li>
        
      </ul>
    </section>
    <!-- /.sidebar -->
  </aside>

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <div class="alert alert-dismissible " style="display: none" >
           
    </div>

    <!-- Main content -->
    <section class="content">
    <div class="overlay" width="100%">
        <i class="fa fa-refresh fa-spin"></i>
      </div>
    </section>
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
<!-- AdminLTE App -->
<script type="text/javascript" src=<spring:url value="/theme/dist/js/app.min.js"/> ></script>
</body>
</html>
