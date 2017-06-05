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
	   
	   function submitDetails(rowData){
		   $.ajax($.extend({}, ajaxObj, { 
             	context: $gridMain,
         	    url: "approveRejectExpense", 
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
         	    	alert("error")
         	    }
		   }));
	   }
	   
       var colM = [
		   { title: "", dataIndx: "state", width: 20, minWidth:20, align: "center", type:'checkBoxSelection', cls: 'ui-state-default', resizable: false, sortable:false },
           { title: "", minWidth: 27, width: 27, type: "detail", resizable: false },
           { title: "Voucher Number",minWidth:250, dataIndx: "voucherNumber" },
           { title: "Employee Name", minWidth: 100, dataIndx: "employeeDTO" },
           { title: "Title", width: 120, dataIndx: "title" },
           { title: "Start Date", width: 100, dataIndx: "startDate" },
		   { title: "End Date", width: 100, dataIndx: "endDate"},
           { title: "Total Amount", width: 100, align: "right", dataType: "float", dataIndx: "totalAmount" },
           { title: "Previously Approved By", minWidth: 100, dataIndx: "approvedByEmployeeDTO" },
           { title: "", editable: false, width: 165, sortable: false, render: function (ui) {
               return "<button type='button' class='approve_btn'>Approve</button>\
                   <button type='button' class='reject_btn'>Reject</button>";
               }
           }
       ];

       var dataModel = {
           location: "remote",
           sorting: "local",            
           dataType: "JSON",
           method: "post",
           recIndx: "expenseHeaderId",
           rPPOptions: [1, 10, 20, 30, 40, 50, 100, 500, 1000],
           url: "toBeApproveExpenseList",
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
           wrap: false,
           hwrap: false,  
           rowBorders: true,
           scrollModel: {
               autoFit: true
           },
          
           numberCell: { show: false },
           title: "<b>Vouchers For Approval</b>",                        
           resizable: true,
           freezeCols: 1,   
           selectionModel: { type: 'none', subtype:'incr', cbHeader:true, cbAll:true},  
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
           },
           selectChange: function (evt, ui) {
               console.log('selectChange', ui);
               if( ui.rows.length == 0){
					$("#approveSelected").prop( "disabled", true );
					$("#rejectSelected").prop( "disabled", true );
			   }
               else{
            	   $("#approveSelected").prop( "disabled", false );
					$("#rejectSelected").prop( "disabled", false );
               }
           },
           refresh: function() {
        	 //debugger;
               var $grid = $(this);

               //delete button
               $grid.find("button.reject_btn").button({ icons: { primary: 'ui-icon-closethick'} })
               .unbind("click")
               .bind("click", function (evt) {
                   
                   var $tr = $(this).closest("tr"),
                       rowIndx = $grid.pqGrid("getRowIndx", { $tr: $tr }).rowIndx,
                   rowData = $grid.pqGrid("getRowData", { rowIndx: rowIndx });
                   rowData.voucherStatusId = 4;
                   submitDetails(rowData);
               });
               //edit button
               $grid.find("button.approve_btn").button({ icons: { primary: 'ui-icon-check'} })
               .unbind("click")
               .bind("click", function (evt) {
                   
                   var $tr = $(this).closest("tr"),
                       rowIndx = $grid.pqGrid("getRowIndx", { $tr: $tr }).rowIndx,
                       rowData = $grid.pqGrid("getRowData", { rowIndx: rowIndx });
                   rowData.voucherStatusId = 3;
                   submitDetails(rowData);
               });

               $("#approveSelected").prop( "disabled", true );
				$("#rejectSelected").prop( "disabled", true );
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
                   { title: "Expense Name", width: 80, dataIndx: "expenseCategoryDTO" },
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
               showTop: false,
               showBottom: false
           };
       };
   });
   
   

</script>
</head>
<body>
  <div id="grid_md" style="margin:5px auto;"></div>
</body>
</html>