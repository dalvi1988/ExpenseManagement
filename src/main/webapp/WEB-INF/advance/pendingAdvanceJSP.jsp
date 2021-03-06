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
      
       //define colModel
       var colM = [
		
       { title: "Purpose", width: 100, dataIndx: "purpose",
           filter: { type: 'textbox', condition: 'begin', listeners: ['keyup'] },
       },
       { title: "Advance Number", width: 120, dataIndx: "advanceNumber",
           filter: { type: 'textbox', condition: 'begin', listeners: ['keyup'] }
       }, 
       { title: "Amount", width: 100, dataIndx: "amount", align: "right",render: amountRenderer},
       { title: "For Event", width: 100, dataIndx: "eventDTO", },
       { title: "Previously Approved By", minWidth: 120, dataIndx: "processedByEmployeeDTO" },
       { title: "Currently Pending At", minWidth: 100, dataIndx: "pendingAtEmployeeDTO", render: voucherStatusRenderer },
       { title: "", dataIndx: "advanceDetailId",hidden:true},
		];
       //define dataModel
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
           selectionModel: {type: 'row', mode: 'single'},
           filterModel: { on: true, mode: "AND", header: true },
           title: "Pending Advance Vouchers",
           resizable: true,
           numberCell: { show: false },
           columnBorders: true
       };

       var $grid = $("#grid_filter").pqGrid(obj);

   });
</script>
</head>
<body>
  <form id="form" action="expense" method="post">
  	<div id="grid_filter" style="margin: auto;"></div>
  </form>
       
</body>
</html>