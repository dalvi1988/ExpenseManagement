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
    	   { title: "Employee Name", width: 120, dataIndx: "employeeDTO" },
	       { title: "Purpose", width: 100, dataIndx: "purpose"},
	       { title: "Voucher Number", minWidth: 200, dataIndx: "voucherNumber"}, 
	       { title: "Start date", minWidth: 80, dataIndx: "startDate", dataType:"String"},
		   { title: "End Date", minWidth: 80, dataIndx: "endDate"},
	       { title: "Total Amount", width: 70, dataIndx: "totalAmount", align: "center",render: amountRenderer
		   },
	       { title: "Advance Amount", width: 85, align: "right", dataType: "float", dataIndx: "advanceAmount",
        	   render: function (ui) {                        
                   var cellData = ui.cellData;
                   if (cellData != null) {
                       return "&#8377;" + parseFloat(ui.cellData).toFixed(2);
                   }
                   else {
                       return "";
                   }
               }
		   },
	       { title: "", dataIndx: "expenseHeaderId",hidden:true},
		];
       //define dataModel
       var dataModel = {
           location: "local",
           sorting: "local",
           sortIndx: "expenseHeaderId",
           sortDir: "up",
           data: expenseHeaderList
       }
       var obj = {
   		   resizable: true,
   		   scrollModel: {
                  autoFit: true
           },
           height:'90%',
           dataModel: dataModel,
           colModel: colM,
           hwrap: false,
           pageModel: { type: "local", rPP: 10 },
           editable: false,
           selectionModel: {type: 'row', mode: 'single'},
           title: "Fetched Accounting Entries",
           resizable: true,
           numberCell: { show: false },
           columnBorders: true,           
       };

       var $grid = $("#grid_filter").pqGrid(obj);

   });
</script>
</head>
<body>
  	<div id="grid_filter" style="margin: auto;"></div>
</body>
</html>