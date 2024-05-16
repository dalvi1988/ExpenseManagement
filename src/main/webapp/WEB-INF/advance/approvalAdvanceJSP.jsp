<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<head>

    <title>Expense Management System</title>
 	<script type="text/javascript" src=<spring:url value="/scripts/commonJS.js"/> ></script>

   <script type="text/javascript">
   var advanceList= ${advanceList};
   $(function () {
	   
	   function submitDetails(rowData,rowIndx){
		   $.ajax($.extend({}, ajaxObj, { 
             	context: grid,
         	    url: "approveRejectAdvance", 
         	    type: 'POST', 
         	    data: JSON.stringify(rowData),
         	 
         	    success: function(data) { 
         	    	if(data.serviceStatus=="SUCCESS"){
         	    		$(".alert").addClass("alert-success").text(data.message).show();
         	    		grid.deleteRow({ rowIndx: rowIndx, effect: true });
         	    	}
         	    	else{
         	    		$(".alert").addClass("alert-danger").text(data.message).show();
         	    		grid.rollback();
         	    	}
         	    	
         	    },
         	    error:function(data) {
         	    	$(".alert").addClass("alert-danger").text(data).show();
         	    }
		   }));
	   }
	   
       var colM = [
			{ title: "Employee Name", minWidth: 130, dataIndx: "employeeDTO", dataType:"String"},
           { title: "Purpose", width: 100, dataIndx: "purpose",
               filter: { type: 'textbox', condition: 'begin', listeners: ['keyup'] },
           },
           { title: "Advance Number", width: 120, dataIndx: "advanceNumber",
               filter: { type: 'textbox', condition: 'begin', listeners: ['keyup'] }
           }, 
           { title: "Amount", width: 100, dataIndx: "amount", align: "right",render: amountRenderer},
           { title: "For Event", width: 100, dataIndx: "eventDTO", },
           { title: "", dataIndx: "advanceDetailId",hidden:true},
           { title: "Previously Approved By", minWidth: 100, dataIndx: "processedByEmployeeDTO" },
           { title: "", editable: false, width: 165, sortable: false, render: function (ui) {
               return "<button type='button' class='approve_btn'>Approve</button>\
                   <button type='button' class='reject_btn'>Reject</button>";
               },
               postRender: function (ui) {
                   var rowIndx = ui.rowIndx,
                       grid = this,
                       $cell = grid.getCell(ui);
                 //reject button
                   $cell.find("button.reject_btn").button({ icons: { primary: 'ui-icon-closethick'} })
                   .unbind("click")
                   .bind("click", function (evt) {
                       
                	   var rowData = grid.getRowData({ rowIndx: rowIndx });
                       rowData.voucherStatusId = 4;
                       $( "#dialog-confirm" ).dialog({
                 	      resizable: false,
                 	      height: "auto",
                 	      width: 400,
                 	      modal: true,
                 	      buttons: {
                 	        "Continue": function() {
                 	        	rowData.rejectionComment=$("#comment").val();
                 	        	submitDetails(rowData,rowIndx);
                 	          $( this ).dialog( "close" );
                 	        },
                 	        Cancel: function() {
                 	          $( this ).dialog( "close" );
                 	        }
                 	      }
                 	 });
                       
                   });
                   //approver button
                   $cell.find("button.approve_btn").button({ icons: { primary: 'ui-icon-check'} })
                   .unbind("click")
                   .bind("click", function (evt) {
                       
                	   var rowData = grid.getRowData({ rowIndx: rowIndx });
                       rowData.voucherStatusId = 3;
                       submitDetails(rowData,rowIndx);
                   });
                   
               	}
           }
       ];

       var dataModel = {
               location: "local",
               sorting: "local",
               sortIndx: "advanceDetailId",
               sortDir: "up",
               data: advanceList
       }

       //define the grid.
       var obj = {
           wrap: false,
           hwrap: true,
           resizable: true,
           columnBorders: false,
           numberCell: { show: false },
           filterModel: { on: true, mode: "AND", header: true, type:"local" },
           trackModel: { on: true }, //to turn on the track changes.,
           collapsible:{ on : false,toggle: false },
           scrollModel: { autoFit: true },
           editor: { type: 'textbox', select: true, style: 'outline:none;' },
           rowBorders: true,
           selectionModel: {type: 'row', mode: 'single'},
           validation: { icon: 'ui-icon-info' },
          title: "<b>Advance For Approval</b>", 
           colModel:colM,
           postRenderInterval: -1, //synchronous post rendering.
           dataModel: dataModel
       };
       var grid = pq.grid("#grid_filter", obj);
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