<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<head>

<title>Advance Master</title>

<script type="text/javascript">
	var eventList= ${eventList};
	
	function toggler(divId) {
	    $("#" + divId).toggle();
	}
	
	
	var output = [];
	
	output.push('<option value="'+ -1 +'">'+ "--Select Event--" +'</option>');
	for (var key in eventList) {
	    output.push('<option value="'+ eventList[key].eventId +'">'+ eventList[key].eventName +'</option>');
	}

	$('#eventSelect').html(output.join(''));

</script>
</head>
<body>
       
<form class="form-horizontal">
<h2 class="text-center">Request Advance</h2>
<br/>
  <div class="form-group row">
    <label class="control-label col-sm-2" for="amount">Amount:</label>
    <div class="col-sm-2 ">
      <input type="number" class="form-control" id="amount" >
    </div>
  </div>
  <div class="form-group">
    <label class="control-label col-sm-2" for="purpose">Purpose:</label>
    <div class="col-sm-3"> 
      <input type="text" class="form-control" id="purpose" >
    </div>
  </div>
  <div class="form-group"> 
    <div class="col-sm-offset-2 col-sm-10">
      <div class="checkbox">
        <label><input type="checkbox" onclick="toggler('eventDiv');" value="">For Event</label>
      </div>
    </div>
  </div>
  
  <div class="form-group"> 
     <div class="col-sm-offset-2 col-sm-3" id="eventDiv" style="display: none" >
	  <select class="form-control" id="eventSelect">
	  </select>
	</div>
  </div>
  
  <div class="form-group"> 
    <div class="col-sm-offset-2 col-sm-10">
      <button type="submit" class="btn btn-default">Save as Draft</button>
      <button type="submit" class="btn btn-default">Send for Approval</button>
    </div>
  </div>
</form>
</body>
</html>