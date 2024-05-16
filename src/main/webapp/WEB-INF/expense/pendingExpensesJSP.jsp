<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<head>

<title>Expense Management System</title>

<script type="text/javascript" src=<spring:url value="/scripts/commonJS.js"/> ></script>
<script type="text/javascript" src=<spring:url value="/grid-3.5.1/pqgrid.min.js"/> ></script>
<link rel="stylesheet" href=<spring:url value="/grid-3.5.1/pqgrid.min.css"/> />

<script type="text/javascript">

   var expenseHeaderList= ${expenseHeaderList};
   $(function () {
       //define colModel
       var colM = [
			{ title: "", minWidth: 3, width: 3, type: "detail", resizable: false },
	       { title: "Purpose", width: 120, dataIndx: "purpose"},
	       { title: "Voucher Number", width: 150, dataIndx: "voucherNumber"}, 
	       { title: "Start date", width: 90, dataIndx: "startDate", dataType:"String"},
		   { title: "End Date", width: 90, dataIndx: "endDate"},
	       { title: "Total Amount", width: 60, dataIndx: "totalAmount", align: "center", render: amountRenderer},
	       { title: "Previously Approved By", minWidth: 90, dataIndx: "processedByEmployeeDTO" },
	       { title: "Currently Pending At", minWidth: 100, dataIndx: "pendingAtEmployeeDTO",render: voucherStatusRenderer },
	       { title: "View Approval Flow", width: 100, dataIndx: "employeeId", render: viewApprovalFlowRenderer},
	       { title: "", dataIndx: "expenseHeaderId", hidden:true},
		];
       //define dataModel
       var dataModel = {
           dataType: "JSON",
           location: "local",
           recIndx: "expenseHeaderId",
           sorting: "local",
           sortIndx: "expenseHeaderId",
           sortDir: "down",
           data: expenseHeaderList
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
              numberCell: { show: false },
              resizable: true,
              freezeCols: 1, 
              title: "Pending Expense Vouchers",  
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
   
   
   function showApprovalFlow( employeeId, expenseHeaderId){
	   $("#dialog-confirm").load("viewVoucherApprovalFlow?employeeId="+employeeId+"&expenseHeaderId="+expenseHeaderId);
 
	   $( "#dialog-confirm" ).dialog({
		      resizable: false,
		      height: 500,
		      width: 1000,
		      modal: true,
		      buttons: {
		        Cancel: function() {
		          $( this ).dialog( "close" );
		        }
		      }
		});
   }
</script>
</head>
<body>
  	<div id="grid_md" style="margin: auto;"></div>
  	
  <div id="dialog-confirm"  style="display: none;" title="Approval Flow">
    
  </div>
</body>
</html>
