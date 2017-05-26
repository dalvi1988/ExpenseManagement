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
	
	
	/* var output = [];
	
	output.push('<option value="'+ -1 +'">'+ "--Select Event--" +'</option>');
	for (var key in eventList) {
	    output.push('<option value="'+ eventList[key].eventId +'">'+ eventList[key].eventName +'</option>');
	}

	$('#eventSelect').html(output.join('')); */

</script>
</head>
<body>
       
<form:form method="POST" modelAttribute="advance" class="form-horizontal">
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
        <label><form:checkbox path="isEvent" onclick="toggler('eventDiv');" value=""/>For Event</label>
      </div>
    </div>
  </div>
  
  <div class="form-group"> 
     <div class="col-sm-offset-2 col-sm-3" id="eventDiv" style="display: none" >
	  <!-- <select class="form-control" id="eventSelect">
	  </select> -->
	  <form:select class="form-control" path="eventId" >
	  <form:option value="NONE" label="--- Select Code ---" />
	  	<form:options items="${eventList}" itemValue="eventId" itemLabel="eventName"/>
	  </form:select>
	</div>
  </div>
  
  <div class="form-group"> 
    <div class="col-sm-offset-2 col-sm-10">
      <button type="submit" class="btn btn-default">Save as Draft</button>
      <button type="submit" class="btn btn-default">Send for Approval</button>
    </div>
  </div>
</form:form>
</body>
</html>