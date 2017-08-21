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
	       { title: "Voucher Number", width: 120, dataIndx: "voucherNumber"}, 
	       { title: "Start date", minWidth: 130, dataIndx: "startDate", dataType:"String"},
		   { title: "End Date", minWidth: 130, dataIndx: "endDate"},
	       { title: "Total Amount", width: 100, dataIndx: "totalAmount", align: "right",render: amountRenderer},
		   { title: "Advance Amount", width: 85, align: "right", dataType: "float", dataIndx: "advanceAmount",render: amountRenderer },
	       { title: "", dataIndx: "expenseHeaderId",hidden:true},
	       { title: "", dataIndx: "payAmount",hidden:true},
	       { title: "", editable: false, minWidth: 100, sortable: false, render: function (ui) {
	    	   if( ui.rowData.advanceAmount !=null){
		    	   if(ui.rowData.advanceAmount == ui.rowData.totalAmount){
		    		   ui.rowData.amount=0;
		    		   return "<button type='button' class='pay_btn' style='background:yellow; color: white'>Pay "+ui.rowData.amount+"</button>";
		    	   }
		    	   else if(ui.rowData.advanceAmount > ui.rowData.totalAmount){
		    		   ui.rowData.amount=ui.rowData.advanceAmount - ui.rowData.totalAmount
		        		return "<button type='button' class='pay_btn' style='background:#48DD11; color: white' >Receive "+ui.rowData.amount+"</button>";
		    	   }
		    	   else if(ui.rowData.advanceAmount < ui.rowData.totalAmount){
		    		   ui.rowData.amount= ui.rowData.totalAmount -ui.rowData.advanceAmount;
		        		return "<button type='button' class='pay_btn' style='background:red; color: white'>Pay "+ui.rowData.amount+"</button>";
		    	   }
	       	  }
	    	   else{
	    		   ui.rowData.amount=ui.rowData.totalAmount;
	        		return "<button type='button' class='pay_btn' style='background:red; color: white' >Pay "+ui.rowData.totalAmount+"</button>";
	    	   }
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
           dataModel: dataModel,
           colModel: colM,
           hwrap: false,
           pageModel: { type: "local", rPP: 10 },
           editable: false,
           selectionModel: {type: 'row', mode: 'single'},
           title: "Excpens Voucher Payment Desk",
           resizable: true,
           numberCell: { show: false },
           columnBorders: true,   
           refresh: function(){
	    	    $("#grid_filter").find("button.pay_btn").button({ icons: { primary: 'ui-icon-check'}})
	           .unbind("click")
	           .bind("click", function (evt) {
	        	     var $tr = $(this).closest("tr");
	                 var obj = $grid.pqGrid("getRowIndx", { $tr: $tr });
	                 var rowIndx = obj.rowIndx;
	                 var rowData = $grid.pqGrid("getRowData", { rowIndx: rowIndx })
	        	     rowData.moduleName="Expense";
	                 rowData.voucherId=rowData.expenseHeaderId;
	                 $.ajax($.extend({}, ajaxObj, { 
	                  	context: $grid,
	              	    url: "makePayment", 
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
	              	    	$(".alert").addClass("alert-danger").text(data.message).show();
	              	    }
	     		   }));
	           }); 
	       }
       };

       var $grid = $("#grid_filter").pqGrid(obj);

   });
</script>
</head>
<body>
  	<div id="grid_filter" style="margin: auto;"></div>
</body>
</html>