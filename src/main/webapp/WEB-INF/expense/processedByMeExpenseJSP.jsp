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
   
   $(function () {
	   
	   function submitDetails(rowData,rowIndx){
		   $.ajax($.extend({}, ajaxObj, { 
             	context: $gridMain,
         	    url: "approveRejectExpense", 
         	    type: 'POST', 
         	    data: JSON.stringify(rowData),
         	 
         	    success: function(data) { 
         	    	if(data.serviceStatus=="SUCCESS"){
         	    		$(".alert").addClass("alert-success").text(data.message).show();
         	    		 $gridMain.pqGrid("deleteRow", { rowIndx: rowIndx, effect: true });
         	    	}
         	    	else{
         	    		$(".alert").addClass("alert-danger").text(data.message).show();
         	    		$gridMain.pqGrid("rollback");
         	    	}
         	    	
         	    },
         	    error:function(data) {
         	    	$(".alert").addClass("alert-danger").text(data.message).show();
         	    }
		   }));
	   }
	   
       var colM = [
           { title: "", minWidth: 27, width: 27, type: "detail", resizable: false },
           { title: "Voucher Number",width:130, dataIndx: "voucherNumber" },
           { title: "For Event", width: 130, dataIndx: "eventDTO" },
           { title: "Employee Name", width: 120, dataIndx: "employeeDTO",
        	   filter: { type: 'textbox', condition: 'begin', listeners: ['keyup'] }
           },
           { title: "Purpose", width: 120, dataIndx: "purpose" },
           { title: "Start Date", width: 100, dataIndx: "startDate" },
		   { title: "End Date", width: 100, dataIndx: "endDate"},
		   { title: "Voucher Status", width: 100, dataIndx: "voucherStatusDTO"},
           { title: "Total Amount", width: 85, align: "right", dataType: "float", dataIndx: "totalAmount",render: amountRenderer },
		   { title: "Advance Amount", width: 85, align: "right", dataType: "float", dataIndx: "advanceAmount",render: amountRenderer},
           { title: "Processed Date", width: 200, dataIndx: "processedDate",
			   filter: { type: 'textbox', condition: "between", init: pqDatePicker, listeners: ['change'] },
           }
          
       ];

       var dataModel = {
           location: "remote",
           sorting: "local",            
           dataType: "JSON",
           method: "post",
           recIndx: "expenseHeaderId",
           rPPOptions: [1, 10, 20, 30, 40, 50, 100, 500, 1000],
           url: "processedByMeExpenseList",
           //url: "/pro/orders.php",//for PHP
           getData: function (dataJSON) {
               var data = dataJSON.data;
               if(data == ""){
            	   $gridMain.pqGrid("hideLoading");
               }
               //expand the first row.
               data[0]['pq_detail'] = { 'show': false };
               return { curPage: dataJSON.curPage, totalRecords: dataJSON.totalRecords, data: data };
           }
       }

       var $gridMain = $("div#grid_md").pqGrid({ 
           dataModel: dataModel,
           virtualX: true, virtualY: true,
           editable: false,
           colModel: colM, 
           rowBorders: true,
           scrollModel: {
               autoFit: true
           },
           height: '78%',
           filterModel: {type: 'local', on: true, mode: "AND", header: true },
           numberCell: { show: false },
           title: "<b>Processed By Me Expense Voucher</b>",                        
           resizable: true,
           freezeCols: 1, 
           selectionModel: {type: 'row', mode: 'single'},
           detailModel: {
               cache: true,
               collapseIcon: "ui-icon-plus",
               expandIcon: "ui-icon-minus",
               init: function (ui) {
                   
                   var rowData = ui.rowData,                        
                       detailobj = gridDetailModel( $(this), rowData ), //get a copy of gridDetailModel                        
                       $grid = $("<div></div>").pqGrid( detailobj ); //init the detail grid.

                   return $grid;
               }
           }
       });

       /* 
       * another grid in detail view.
       * returns a new copy of detailModel every time the function is called.
       * @param $gridMain {jQuery object}: reference to parent grid
       * @param rowData {Plain Object}: row data of parent grid
       */
       var gridDetailModel = function( $gridMain, rowData ){
           return {
               height: 130,
               pageModel: { type: "local", rPP: 5, strRpp: "" },
               dataModel: {
                   location: "remote",
                   sorting: "local",
                   dataType: "json",
                   method: "POST",
                   sortIndx: "expenseDetailId",
                   getUrl: function() {
                       return { url: "expenseDetail", data: "{\"expenseHeaderId\":"+rowData.expenseHeaderId+"}" };
                   },
                  
                   mimeType : 'application/json',
                   async: true,
              	   beforeSend: function(xhr) {   
                          xhr.setRequestHeader("Accept", "application/json; charset=UTF-8");
                          xhr.setRequestHeader("Content-Type", "application/json; charset=UTF-8");
                   },
                   error: function (data) {
                       $gridMain.pqGrid( 'rowInvalidate', { rowData: rowData });
                   }
               },
               colModel: [
                   { title: "Expense Detail Id", width: 80, dataIndx: "expenseDetailId", hidden:true},
                   { title: "Expense Name", width: 200, dataIndx: "expenseCategoryDTO" },
                   { title: "To Location", width: 200, dataIndx: "toLocation" },
                   { title: "From Location", width: 200, dataIndx: "fromLocation" },
		           { title: "Unit Price", width: "80", align: "center", dataIndx: "unit", dataType: "float"},
		           { title: "Amount", align: "center", width: 85, dataIndx: "amount", dataType: "float"},
		           { title: "Receipt", width: 200, dataIndx: "receipt" }
		        ],
               editable: false,              
               flexHeight: true,
               flexWidth: true,
               numberCell: { show: false },
               title: "Expense Details",
               showTop: true,
               showBottom: false
           };
       };
   });
   
   

</script>
</head>
<body>
  <div id="grid_md" style="margin:5px auto;"></div>
  <div id="dialog-confirm" style="display: none" title="Rejection Comment">
    <fieldset>
      <label for="name">Please enter rejection comments:</label>
      <input type="text" name="comment" id="comment" class="text ui-widget-content ui-corner-all">
    </fieldset>
  </div>
</body>
</html>