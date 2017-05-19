<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>Expense Management System</title>
  
   <link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/themes/base/jquery-ui.css" />
 <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
 <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/jquery-ui.min.js"></script>
 	
 	
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <!-- Bootstrap 3.3.6 -->
  <link rel="stylesheet" href=<spring:url value="/theme/bootstrap/css/bootstrap.min.css"/> />
  <!-- Font Awesome -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
  <!-- Ionicons -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href=<spring:url value="/theme/dist/css/AdminLTE.min.css"/> />
  <!-- iCheck -->
  <link rel="stylesheet" href=<spring:url value="/theme/plugins/iCheck/square/blue.css"/> />

  <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
</head>
<body class="hold-transition login-page">
<div class="login-box">
  <div class="login-logo">
    <a href="">Expense & Event Management System</a>
  </div>
 
  <!-- /.login-logo -->
  <div  class="login-box-body ">
    <c:if test="${not empty error}">
		<div class="alert alert-danger alert-dismissible">${error}</div>
	</c:if>
	<c:if test="${not empty message}">
		<div class="alert alert-success alert-dismissible">${message}</div>
	</c:if>
    <p class="login-box-msg">Sign in to start your session</p>

		

    <form name='loginForm' action="login" method="post">
      <div class="form-group has-feedback">
        <input name="username" type="email" class="form-control" placeholder="Email">
        <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <input name="password" type="password" class="form-control" placeholder="Password">
        <span class="glyphicon glyphicon-lock form-control-feedback"></span>
      </div>
        
       <div class="col-xs-4">
         <button type="submit" class="btn btn-primary btn-block btn-flat">Sign In</button>
       </div>
    </form>

    <a href="#" onclick="forgotPassword()">I forgot my password</a><br>

  </div>
  <!-- /.login-box-body -->
  
  <div style="display: none"  class="register-box-body">

	<p class="login-box-msg">Forgot Password</p>
	
    <form action="login/forgotPassword" method="post">
      <div class="form-group has-feedback">
        <input name="emailId" class="form-control" placeholder="Email">
        
        <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
      </div>
      <div class="col-xs-4">
        <button type="submit" class="btn btn-primary btn-block btn-flat">Submit</button>
      </div>
    </form>
	<a href="#" onclick="login()">Log In</a><br>
  <!-- /.form-box -->
  </div>
  
  
  
  <!-- /.form-box -->
</div>
<!-- /.register-box -->
  

<!-- jQuery 2.2.3 -->
<script type="text/javascript" src=<spring:url value="/theme/plugins/jQuery/jquery-2.2.3.min.js"/> ></script>
<!-- Bootstrap 3.3.6 -->
<script type="text/javascript" src=<spring:url value="/theme/bootstrap/js/bootstrap.min.js"/> ></script>
<!-- iCheck -->
<script type="text/javascript" src=<spring:url value="/theme/plugins/iCheck/icheck.min.js"/> ></script>
<script>
  $(function () {
    $('input').iCheck({
      checkboxClass: 'icheckbox_square-blue',
      radioClass: 'iradio_square-blue',
      increaseArea: '20%' // optional
    });
    
    $(".login-box-body").show();
  });
  
  function forgotPassword(){
	  $(".login-box-body").hide();
	  $(".register-box-body").show();
	  
  }
  
  function login(){
	  $(".login-box-body").show();
	  $(".register-box-body").hide();
	  
  }
</script>
</body>
</html>
