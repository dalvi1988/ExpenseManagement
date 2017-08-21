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
           { title: "", editable: false, width: 165, sortable: false, render: function (ui) {
               return "<button type='button' class='pay_btn' style='background:red; color: white'>Pay "+ui.rowData.amount+"</button>";
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
           selectionModel: {type: 'row', mode: 'single'},
           filterModel: { on: true, mode: "AND", header: true },
           resizable: true,
           numberCell: { show: false },
           columnBorders: true,
           numberCell: { show: false },
           title: "<b>Advances For Payment</b>",                        
           resizable: true,
           selectionModel: { type: 'none', subtype:'incr', cbHeader:true, cbAll:true},
           
           refresh: function() {
        	 //debugger;
               var $grid = $(this);
               //edit button
               $grid.find("button.pay_btn").button({ icons: { primary: 'ui-icon-check'} })
               .unbind("click")
               .bind("click", function (evt) {
            	   var $tr = $(this).closest("tr");
	                 var obj = $grid.pqGrid("getRowIndx", { $tr: $tr });
	                 var rowIndx = obj.rowIndx;
	                 var rowData = $grid.pqGrid("getRowData", { rowIndx: rowIndx })
	        	     rowData.moduleName="Advance";
	                 rowData.voucherId=rowData.advanceDetailId;
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
  <div id="grid_filter" style="margin:5px auto;"></div>
</body>
</html>