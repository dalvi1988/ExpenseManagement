<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<head>

<title>Advance Master</title>

<script type="text/javascript">
	function toggler(divId) {
	    $("#" + divId).toggle();
	}
	
	function submitData(command){
		$("#voucherStatusId").val(command);
		 $.ajax( { 
      	    url: "saveAdvance", 
      	    type: 'POST', 
      	    data: $("#form").serialize(),
      	 
      	    success: function(data) { 
      	    	if(data.serviceStatus=="SUCCESS"){
      	    		$(".alert").addClass("alert-success").text(data.message).show();
      	    	}
      	    	else{
      	    		$(".alert").addClass("alert-danger").text(data.message).show();
      	    		$grid.pqGrid("rollback");
      	    	}
      	    	
      	    },
      	    error:function(data) {
      	    	alert("error")
      	    }
		   });
	}
	

</script>
</head>
<body>
       
<form:form id="form" modelAttribute="advance" class="form-horizontal">
<h2 class="text-center">Request Advance</h2>
<br/>
  <div class="form-group row">
    <label class="control-label col-sm-2" for="amount">Amount:</label>
    <div class="col-sm-2 ">
      <form:input type="number" path="amount" class="form-control" id="amount" />
    </div>
  </div>
  <div class="form-group">
    <label class="control-label col-sm-2" for="purpose">Purpose:</label>
    <div class="col-sm-3"> 
      <form:input type="text" path="purpose" class="form-control" id="purpose" />
    </div>
  </div>
  <div class="form-group"> 
    <div class="col-sm-offset-2 col-sm-10">
      <div class="checkbox">
        <label><form:checkbox path="isEvent" onchange="toggler('eventDiv');"/>For Event</label>
      </div>
    </div>
  </div>
  
  <div class="form-group"> 
     <div class="col-sm-offset-2 col-sm-3" id="eventDiv" style="display: none" >
	  <form:select class="form-control" path="eventId" >
	  <form:option value="-1" label="--- Select Event ---" />
	  	<form:options items="${eventList}" itemValue="eventId" itemLabel="eventName"/>
	  </form:select>
	</div>
  </div>
  
  <div class="form-group"> 
    <div class="col-sm-offset-2 col-sm-10">
      <button type="button" class="btn btn-default" onclick="submitData(1)">Save as Draft</button>
      <button type="button" class="btn btn-default" onclick="submitData(2)">Send for Approval</button>
    </div>
  </div>
  <form:hidden id="voucherStatusId" path="voucherStatusId"></form:hidden>  
</form:form>
</body>
</html>