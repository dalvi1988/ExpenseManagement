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
	   
	   function submitDetails(voucherIds){
		   $.ajax($.extend({}, ajaxObj, { 
             	context: $grid,
         	    url: "payAdvance", 
         	    type: 'POST', 
         	    data: JSON.stringify(voucherIds),
         	 
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
         	    	$(".alert").addClass("alert-danger").text(data).show();
         	    }
		   }));
	   }
	   
       var colM = [
    	   { title: "Created Date", minWidth: 130, dataIndx: "createdDate", dataType:"String"},
           { title: "Purpose", width: 100, dataIndx: "purpose",
               filter: { type: 'textbox', condition: 'begin', listeners: ['keyup'] },
           },
           { title: "Advance Number", width: 120, dataIndx: "advanceNumber",
               filter: { type: 'textbox', condition: 'begin', listeners: ['keyup'] }
           }, 
           { title: "Amount", width: 100, dataIndx: "amount", align: "center"},
           { title: "For Event", width: 100, dataIndx: "eventDTO", },
           { title: "", dataIndx: "advanceDetailId",hidden:true},
           { title: "Previously Approved By", minWidth: 100, dataIndx: "approvedByEmployeeDTO" },
           { title: "", editable: false, width: 165, sortable: false, render: function (ui) {
               return "<button type='button' class='pay_btn'>Pay</button>";
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

       var obj = {
   		   resizable: true,
   		   scrollModel: {autoFit: true},
           dataModel: dataModel,
           colModel: colM,
           hwrap: false,
           pageModel: { type: "local", rPP: 10 },
           editable: false,
           selectionModel: { type: 'cell' },
           filterModel: { on: true, mode: "AND", header: true },
           title: "Saved Advance Vouchers",
           resizable: true,
           numberCell: { show: false },
           columnBorders: true,
           numberCell: { show: false },
           title: "<b>Vouchers For Approval</b>",                        
           resizable: true,
           freezeCols: 1,   
           selectionModel: { type: 'none', subtype:'incr', cbHeader:true, cbAll:true},
           
           refresh: function() {
        	 //debugger;
               var $grid = $(this);
               //edit button
               $grid.find("button.pay_btn").button({ icons: { primary: 'ui-icon-check'} })
               .unbind("click")
               .bind("click", function (evt) {
                   
                 
               });

           }
           
       };

       var $grid = $("#grid_filter").pqGrid(obj);
   });
   
   

</script>
</head>
<body>
  <div id="grid_filter" style="margin:5px auto;"></div>
</body>
</html>