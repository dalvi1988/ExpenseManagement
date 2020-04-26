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

   var expenseHeaderList= ${expenseHeaderList};
   $(function () {
       //define colModel
       var colM = [
	       { title: "Purpose", width: 100, dataIndx: "purpose"},
	       { title: "Voucher Number", width: 120, dataIndx: "voucherNumber"}, 
	       { title: "Start date", minWidth: 130, dataIndx: "startDate", dataType:"String"},
		   { title: "End Date", minWidth: 130, dataIndx: "endDate"},
	       { title: "Total Amount", width: 100, dataIndx: "totalAmount", align: "center", render: amountRenderer},
	       { title: "Previously Approved By", minWidth: 120, dataIndx: "processedByEmployeeDTO" },
	       { title: "Currently Pending At", minWidth: 100, dataIndx: "pendingAtEmployeeDTO",render: voucherStatusRenderer },
	       { title: "View Approval Flow", dataIndx: "employeeId", render: viewApprovalFlowRenderer},
	       { title: "", dataIndx: "expenseHeaderId", hidden:true},
		];
       //define dataModel
       var dataModel = {
           location: "local",
           sorting: "local",
           sortIndx: "totalAmount",
           sortDir: "up",
           data: expenseHeaderList
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
           selectionModel: {type: 'row', mode: 'single'},
           title: "Pending Expense Vouchers",
           resizable: true,
           numberCell: { show: false },
           columnBorders: true,           
       };

       var $grid = $("#grid_filter").pqGrid(obj);

   });
   
   
   function showApprovalFlow( employeeId, expenseHeaderId){
	   $("#dialog-confirm").load("viewVoucherApprovalFlow?employeeId="+employeeId+"&expenseHeaderId="+expenseHeaderId);
	   
	   $( "#dialog-confirm" ).dialog({
		      resizable: false,
		      height: 500,
		      width: 1100,
		      modal: true,
		      buttons: {
		        Cancel: function() {
		          $( this ).dialog( "close" );
		        }
		      }
		    });
   }
</script>
</head>
<body>
  	<div id="grid_filter" style="margin: auto;"></div>
  	
  <div id="dialog-confirm"  style="display: none;" title="Approval Flow">
    
  </div>
</body>
</html>