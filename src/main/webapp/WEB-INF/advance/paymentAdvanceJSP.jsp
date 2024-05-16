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
               },
               postRender: function (ui) {
                   var rowIndx = ui.rowIndx,
                       grid = this,
                       $cell = grid.getCell(ui);
                   $cell.find(".pay_btn").button({ icons: { primary: 'ui-icon-circle-check'} })
                   .bind("click", function (evt) {
        	             var rowData = grid.getRowData({ rowIndx: rowIndx });
    	        	     rowData.moduleName="Advance";
    	                 rowData.voucherId=rowData.advanceDetailId;
    	                 $.ajax($.extend({}, ajaxObj, { 
    	                  	context: grid,
    	              	    url: "makePayment", 
    	              	    type: 'POST', 
    	              	    data: JSON.stringify(rowData),
    	              	 
    	              	    success: function(data) { 
    	              	    	if(data.serviceStatus=="SUCCESS"){
    	              	    		$(".alert").addClass("alert-success").text(data.message).show();
    	              	    		 grid.deleteRow({ rowIndx: rowIndx, effect: true });
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
           title: "<b>Advances For Payment</b>", 
           colModel:colM,
           postRenderInterval: -1, //synchronous post rendering.
           dataModel: dataModel
       };
       var grid = pq.grid("#grid_editing", obj);

   });
   
   

</script>
</head>
<body>
  <div id="grid_editing" style="margin:5px auto;"></div>
</body>
</html>