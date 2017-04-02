<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<head>

    <title>Expense Management System</title>
    <link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/themes/base/jquery-ui.css" />
 	<script type="text/javascript" src=<spring:url value="/scripts/jquery-1.11.1.min.js"/> ></script>
 	<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/jquery-ui.min.js"></script>
 	<script type="text/javascript" src=<spring:url value="/scripts/commonJS.js"/> ></script>
    <script type="text/javascript" src=<spring:url value="/grid/pqgrid.min.js"/> ></script>
    <link rel="stylesheet" href=<spring:url value="/grid/pqgrid.min.css"/> />

   <script type="text/javascript">
   var expenseHeaderList= ${expenseHeaderList};
   $(function () {
      
       //define colModel
       var colM = [
		
       { title: "Title", width: 100, dataIndx: "title",
           filter: { type: 'textbox', condition: 'begin', listeners: ['keyup'] },
       },
       
       { title: "Voucher Number", width: 120, dataIndx: "voucherNumber",
           filter: { type: 'textbox', condition: 'begin', listeners: ['keyup'] }
       },
        { title: "", editable: false, minWidth: 70, sortable: false, render: function (ui) {
         	return "<button type='button' class='edit_btn' >Edit</button>";
       }}, 
       { title: "Start date", minWidth: 130, dataIndx: "startDate", dataType:"String",
       },
	   { title: "End Date", minWidth: 190, dataIndx: "endDate"},
       { title: "Purpose", width: 100, dataIndx: "purpose", align: "center"},
       { title: "", dataIndx: "expenseHeaderId",hidden:true}
		];
       //define dataModel
       var dataModel = {
           location: "local",
           sorting: "local",
           sortIndx: "expenseHeaderId",
           sortDir: "up",
           data: expenseHeaderList
       }
       var obj = { width: 800, height: 500,
           dataModel: dataModel,
           colModel: colM,
           hwrap: false,
           pageModel: { type: "local", rPP: 50 },
           editable: false,
           selectionModel: { type: 'cell' },
           filterModel: { on: true, mode: "AND", header: true },
           title: "Saved Expense Vouchers",
           resizable: true,
           numberCell: { show: false },
           columnBorders: true,           
           freezeCols: 3,            
	       refresh: function(){
	    	    $("#grid_filter").find("button.edit_btn").button({ icons: { primary: 'ui-icon-pencil'} })
	           .unbind("click")
	           .bind("click", function (evt) {
	        	     var $tr = $(this).closest("tr");
	                 var obj = $grid.pqGrid("getRowIndx", { $tr: $tr });
	                 var rowIndx = obj.rowIndx;
	                 var rowData = $grid.pqGrid("getRowData", { rowIndx: rowIndx })
	        	     $("#expenseHeaderId").val(rowData.expenseHeaderId);
	                 $("#form").submit();
	           }); 
	       }
       };

       var $grid = $("#grid_filter").pqGrid(obj);

   });
</script>
</head>
<body>
  <form id="form" action="/ExpenseManagement/expense" method="post">
  	<div id="grid_filter" style="margin: auto;"></div>
  	<input name="expenseHeaderId" id="expenseHeaderId" type="text" hidden/>
  </form>
       
</body>
</html>