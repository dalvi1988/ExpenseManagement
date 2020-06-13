<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>

    <title>Branch Master</title>

<script type="text/javascript">

function submitReset(){
	$.ajax( { 
  	//context: $grid,
	    url: "resetPassword", 
	    type: 'POST', 
	    data: $('#form').serialize(),
	    success: function(data) { 
	    	if(data.serviceStatus=="SUCCESS"){
	    		$(".alert").addClass("alert-success").text(data.message).show();
	    	}
	    	else{
	    		$(".alert").addClass("alert-danger").text(data.message).show();
	    	}
	    },
	    error:function(data) { 
	    	$(".alert").addClass("alert-danger").text(data.message).show();
	    }
	    
	});
  
}
</script>
</head>
<body class="hold-transition login-page">
<div class="login-box">
		<div class="box box-info">
            <div class="box-header with-border">
              <h3 class="box-title">Reset Password</h3>
            </div>
            <!-- /.box-header -->
            <!-- form start -->
            <c:if test="${not empty error}">
				<div class="alert alert-danger alert-dismissible">${error}</div>
			</c:if>
			<c:if test="${not empty message}">
				<div class="alert alert-success alert-dismissible">${message}</div>
			</c:if>
            <form action="resetPassword" id="form" class="form-horizontal" method="POST">
              <div class="box-body">
                <div class="form-group">
                  <label for="oldPassword" class="col-sm-2 control-label">Old Password</label>

                  <div class="col-sm-10">
                    <input type="password" name="oldPassword" class="form-control" id="oldPassword" placeholder="Old Password">
                  </div>
                </div>
                <div class="form-group">
                  <label for="newPassword" class="col-sm-2 control-label">New Password</label>

                  <div class="col-sm-10">
                    <input type="password" name="newPassword" class="form-control" id="newPassword" placeholder="New Password">
                  </div>
                </div>
                <div class="form-group">
                  <label for="confirmPassword" class="col-sm-2 control-label">Confirm Password</label>

                  <div class="col-sm-10">
                    <input type="password" name="confirmPassword" class="form-control" id="confirmPassword" placeholder="Confirm Password">
                  </div>
                </div>
              </div>
              <!-- /.box-body -->
              <div class="box-footer">
                <button type="button" onclick="submitReset()" class="btn btn-info pull-right">Submit</button>
              </div>
              <!-- /.box-footer -->
            </form>
          </div>
</div>
</body>
</html>