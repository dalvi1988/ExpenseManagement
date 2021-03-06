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
			{ title: "", dataIndx: "state", maxWidth: 30, minWidth: 30, align: "center",
			    cb: { header: true, all: true },
			    type: 'checkBoxSelection', cls: 'ui-state-default', resizable: false, sortable: false, editable: false
			},
			{ title: "expenseHeaderId", width: 100, dataType: "integer", hidden:true, dataIndx: "expenseHeaderId" },
			{ title: "Employee Name", width: 120, dataIndx: "employeeDTO" },
	       { title: "Purpose", width: 100, dataIndx: "purpose"},
	       { title: "Voucher Number", minWidth: 180, dataIndx: "voucherNumber"}, 
	       { title: "Start date", minWidth: 80, dataIndx: "startDate", dataType:"String"},
		   { title: "End Date", minWidth: 80, dataIndx: "endDate"},
	       { title: "Total Amount", width: 70, dataIndx: "totalAmount", align: "center",render: amountRenderer
		   },
	       { title: "Advance Amount", width: 70, align: "right", dataType: "float", dataIndx: "advanceAmount",
        	   render: function (ui) {                        
                   var cellData = ui.cellData;
                   if (cellData != null) {
                       return "&#8377;" + parseFloat(ui.cellData).toFixed(2);
                   }
                   else {
                       return "";
                   }
               }
		   }
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
           height:'98%',
           toolbar: {
               cls: 'pq-toolbar-export',
               items: [{
                       type: 'button',
                       label: "Export to XLSX",
                       icon: 'ui-icon-document',
                       listeners: [{
                           "click": function (evt) {
                        	   var $grid = $(this).closest('.pq-grid'),
                               selarray = $grid.pqGrid('selection', { type: 'row', method: 'getSelection' }),
	   							ids = [];
                        	   if(selarray.length> 0){
		                           for (var i = 0, len = selarray.length; i < len; i++) {
		                               var rowData = selarray[i].rowData;
		                               ids.push(rowData.expenseHeaderId);
		                               $grid.pqGrid( "deleteRow",  { rowIndx: selarray[i].rowIndx });
		                           }
		                           $("#expenseHeaderIds").val(ids);
		        	       		   $( "#form" ).submit();
                        	   }else{
                        		   alert("Please select expenses to export for accounting entry.")
                        	   }
	        	       		   
                           }
                       }]
               }
               ]
           },
           dataModel: dataModel,
           colModel: colM,
           hwrap: false,
           pageModel: { type: "local", rPP: 15 },
           editable: false,
           selectionModel: {type: null},
           title: "Accounting Entries",
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
  	<form id="form" action="exportAccountingEntry" method="POST" >
 		<input type="hidden" id="expenseHeaderIds" name="expenseHeaderIds"></input>
 	  </form>
</body>
</html>
