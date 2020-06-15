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
	       { title: "Total Amount", width: 100, dataIndx: "totalAmount", align: "center",render: amountRenderer},
	       { title: "Rejected By", minWidth: 120, dataIndx: "processedByEmployeeDTO",render:voucherStatusRenderer },
	       { title: "Rejection Comment", minWidth: 100, dataIndx: "rejectionComment" },
	       { title: "", dataIndx: "expenseHeaderId",hidden:true},
	       { title: "", editable: false, minWidth: 70, sortable: false, render: function (ui) {
        	return "<button type='button' class='edit_btn' >Edit</button>";
      }}
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
           height: '78%',
           dataModel: dataModel,
           colModel: colM,
           hwrap: false,
           pageModel: { type: "local", rPP: 10 },
           editable: false,
           selectionModel: {type: 'row', mode: 'single'},
           title: "Rejected Expense Vouchers",
           resizable: true,
           numberCell: { show: false },
           columnBorders: true,       
           refresh: function(){
	    	    $("#grid_filter").find("button.edit_btn").button({ icons: { primary: 'ui-icon-pencil'} })
	           .unbind("click")
	           .bind("click", function (evt) {
	        	     var $tr = $(this).closest("tr");
	                 var obj = $grid.pqGrid("getRowIndx", { $tr: $tr });
	                 var rowIndx = obj.rowIndx;
	                 var rowData = $grid.pqGrid("getRowData", { rowIndx: rowIndx })
	        	     $("#expenseHeaderId").val(rowData.expenseHeaderId);
           	    	 $( this ).parent().addClass("active")
         	         $('.content').load('expense?expenseHeaderId='+rowData.expenseHeaderId);
	                 //$("#form").submit();
	           }); 
	       }    
       };

       var $grid = $("#grid_filter").pqGrid(obj);

   });
</script>
</head>
<body>
  	<div id="grid_filter" style="margin: auto;"></div>
  	<input name="expenseHeaderId" id="expenseHeaderId" type="text" hidden/>
</body>
</html>