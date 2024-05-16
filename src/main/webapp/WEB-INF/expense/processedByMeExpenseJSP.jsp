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
            	   gridMain.hideLoading();
               }
               //expand the first row.
               data[0]['pq_detail'] = { 'show': false };
               return { curPage: dataJSON.curPage, totalRecords: dataJSON.totalRecords, data: data };
           }
       }

       var gridMain = pq.grid("#grid_md",{
       	dataModel: dataModel,
           virtualX: true, virtualY: true,
           height: '95%',
           editable: false,
           collapsible:{ on : false,toggle: false },
           colModel: colM, 
           rowBorders: true,
           selectionModel: {type: 'row', mode: 'single'},
           scrollModel: {
               autoFit: true
           },
           filterModel: {type: 'local', on: true, mode: "AND", header: true },
           numberCell: { show: false },
           resizable: true,
           freezeCols: 1, 
          title: "<b>Processed By Me Expense Voucher</b>",   
           postRenderInterval: -1,
           detailModel: {
                cache: true,
               collapseIcon: "ui-icon-plus",
               expandIcon: "ui-icon-minus",
               init: function (ui) {                    
                    var rowData = ui.rowData,                        
                       detailobj = gridDetailModel(  rowData ), //get a copy of gridDetailModel                        
                       $grid = $("<div></div>").pqGrid( detailobj ); //init the detail grid.
                       $grid.data( 'expenseHeaderId' , rowData.expenseHeaderId);
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
     var gridDetailModel = function( rowData ){
         return {
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
                     gridMain.pqGrid( 'rowInvalidate', { rowData: rowData });
                 }
             },
             colModel: [
                 { title: "Expense Detail Id", width: 80, dataIndx: "expenseDetailId", hidden:true},
                 { title: "Expense Name", width: 200, dataIndx: "expenseCategoryDTO" },
                 { title: "To Location", width: 200, dataIndx: "toLocation" },
                 { title: "From Location", width: 200, dataIndx: "fromLocation" },
		           { title: "Unit Price", width: "80", align: "center", dataIndx: "unit", dataType: "float"},
		           { title: "Amount", align: "center", width: 85, dataIndx: "amount", dataType: "float"},
		           { title: "Receipt", width: 200, dataIndx: "fileName", 
		        	   render: function(ui){
		        		   //return "<a href='https://docs.google.com/viewer?embedded=true&url=getFile?fileName="+ui.cellData+"&voucherId="+$(this).data('expenseHeaderId')+"' target='_blank' class='embed'>"+ui.cellData+"</a>";
		        		   if(ui.cellData != null)
		        		   		return "<a href='getFile?fileName="+ui.cellData+"&voucherId="+rowData.expenseHeaderId+"' target='_blank' class='embed'>"+ui.cellData+"</a>";
		        	   }
		        	   
		           }
		        ],
		        editable: false, 
             height: 'flex',                
             numberCell: { show: false },
             scrollModel: {autoFit: true},
             showTop: false

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