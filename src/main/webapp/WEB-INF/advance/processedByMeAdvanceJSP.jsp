<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<head>

    <title>Expense Management System</title>
 	<script type="text/javascript" src=<spring:url value="/scripts/commonJS.js"/> ></script>
    <script type="text/javascript" src=<spring:url value="/grid/pqgrid.min.js"/> ></script>
    <link rel="stylesheet" href=<spring:url value="/grid/pqgrid.min.css"/> />

   <script type="text/javascript">
   var advanceList= ${advanceList};
   $(function () {
	   
	   function submitDetails(rowData,rowIndx){
		   $.ajax($.extend({}, ajaxObj, { 
             	context: $grid,
         	    url: "approveRejectAdvance", 
         	    type: 'POST', 
         	    data: JSON.stringify(rowData),
         	 
         	    success: function(data) { 
         	    	if(data.serviceStatus=="SUCCESS"){
         	    		$(".alert").addClass("alert-success").text(data.message).show();
         	    		$grid.pqGrid("deleteRow", { rowIndx: rowIndx, effect: true });
         	    	}
         	    	else{
         	    		$(".alert").addClass("alert-danger").text(data.message).show();
         	    		$grid.pqGrid("rollback");
         	    	}
         	    	
         	    },
         	    error:function(data) {
         	    	$(".alert").addClass("alert-danger").text(data).show();
         	    }
		   }));
	   }
	   
       var colM = [
			{ title: "Employee Name", minWidth: 130, dataIndx: "employeeDTO", dataType:"String",
				filter: { type: 'textbox', condition: 'begin', listeners: ['keyup'] }
			},
           { title: "Purpose", width: 100, dataIndx: "purpose",
               filter: { type: 'textbox', condition: 'begin', listeners: ['keyup'] },
           },
           { title: "Advance Number", width: 120, dataIndx: "advanceNumber",
               filter: { type: 'textbox', condition: 'begin', listeners: ['keyup'] }
           }, 
           { title: "Amount", width: 100, dataIndx: "amount", align: "right", render: amountRenderer },
           { title: "For Event", width: 100, dataIndx: "eventDTO", },
           { title: "Voucher Status", width: 100, dataIndx: "voucherStatusDTO"},
           { title: "", dataIndx: "advanceDetailId",hidden:true},
           { title: "Processed Date", minWidth: 200, dataIndx: "processedDate",
        	   filter: { type: 'textbox', condition: "between", init: pqDatePicker, listeners: ['change'] },
           }
           
       ];

       var dataModel = {
               location: "local",
               sorting: "local",
               sortIndx: "advanceDetailId",
               sortDir: "up",
               data: advanceList
       }

       var obj = {
    		   resizable: true,
    		   scrollModel: {
                   autoFit: true
               },
           dataModel: dataModel,
           colModel: colM,
           hwrap: false,
           pageModel: { type: "local", rPP: 10 },
           editable: false,
           filterModel: { on: true, mode: "AND", header: true },
           resizable: true,
           numberCell: { show: false },
           columnBorders: true, 
           numberCell: { show: false },
           title: "<b>Processed By Me Advances</b>",                        
           resizable: true,
           
       };

       var $grid = $("#grid_filter").pqGrid(obj);
   });
   
  

</script>
</head>
<body>
  <div id="grid_filter" style="margin:5px auto;"></div>
  
  <div id="dialog-confirm" style="display: none" title="Rejection Comment">
    <fieldset>
      <label for="name">Please enter rejection comments:</label>
      <input type="text" name="comment" id="comment" class="text ui-widget-content ui-corner-all">
    </fieldset>
  </div>
	
</body>
</html>