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
	   
	   function submitDetails(voucherIds,command){
		   $.ajax($.extend({}, ajaxObj, { 
             	context: $gridMain,
         	    url: "/ExpenseManagement/approveRejectExpense", 
         	    type: 'POST', 
         	    data: JSON.stringify(voucherIds),
         	 
         	    success: function(data) { 
         	    	if(data.serviceStatus=="SUCCESS"){
	          	    	var recIndx = $grid.pqGrid("option", "dataModel.recIndx");
	                    if (rowData[recIndx] == null || rowData[recIndx] == "") {
	                       rowData.branchId= data.branchId;
	                    } 
	          	    	$grid.pqGrid("removeClass", { rowIndx: rowIndx, cls: 'pq-row-edit' });
	          	    	$grid.pqGrid("commit");
         	    	}
         	    	else{
         	    		
         	    		$grid.pqGrid("rollback");
         	    	}
         	    	//$(".customMessage").text(data.message);
         	    	
         	    },
         	    error:function(data) { 
         	    	//$(".customMessage").text(data.message);
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
           { title: "Amount", width: 100, align: "right", dataType: "float", dataIndx: "amount" },
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
           url: "/ExpenseManagement/toBeApproveExpenseList",
           //url: "/pro/orders.php",//for PHP
           getData: function (dataJSON) {
               var data = dataJSON.data;
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
           toolbar: {
               items: [
                   { type: 'button', attr:"id='approveSelected'", icon: 'ui-icon-check', label: 'Approve selected', listeners: [
                       { "click": function (evt, ui) {
                    	   debugger;
                           var $grid = $(this).closest('.pq-grid'),
                           selarray = $grid.pqGrid('selection', { type: 'row', method: 'getSelection' }),
							ids = [];
	                       for (var i = 0, len = selarray.length; i < len; i++) {
	                           var rowData = selarray[i].rowData;
	                           rowData.voucherStatusId = 3;
	                           ids.push(rowData);
	                       }
	                                                                       
	                       submitDetails(ids);
                           //debugger;
                         }
                       }
                   	 ]
                   },
                   { type: 'button', attr:"id='rejectSelected'", icon: 'ui-icon-closethick', label: 'Reject selected', listeners: [
                         { "click": function (evt, ui) {
                        	 debugger;
                        	 var $grid = $(this).closest('.pq-grid'),
                             selarray = $grid.pqGrid('selection', { type: 'row', method: 'getSelection' }),
  							 ids = [];
  	                         for (var i = 0, len = selarray.length; i < len; i++) {
  	                           var rowData = selarray[i].rowData;
  								rowData.voucherStatusId = 4;
  	                           ids.push(rowData);
  	                         }
  	                                                                       
  	                       	 submitDetails(ids);
                            }
                         }
                      ]
                   }
               ]
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
                   id= [];
                   id.push(rowData);
                   submitDetails(id);
               });
               //edit button
               $grid.find("button.approve_btn").button({ icons: { primary: 'ui-icon-check'} })
               .unbind("click")
               .bind("click", function (evt) {
                   
                   var $tr = $(this).closest("tr"),
                       rowIndx = $grid.pqGrid("getRowIndx", { $tr: $tr }).rowIndx,
                       rowData = $grid.pqGrid("getRowData", { rowIndx: rowIndx });
                   rowData.voucherStatusId = 3;
                   id= [];
                   id.push(rowData);
                   submitDetails(id);
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
                       return { url: "/ExpenseManagement/expenseDetail", data: "{\"expenseHeaderId\":"+rowData.expenseHeaderId+"}" };
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