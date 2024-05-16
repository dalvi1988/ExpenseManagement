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
			{ title: "", minWidth: 10, width: 10, type: "detail", resizable: false },
	       { title: "Purpose", width: 100, dataIndx: "purpose"},
	       { title: "Voucher Number", width: 120, dataIndx: "voucherNumber"}, 
	       { title: "Start date", minWidth: 130, dataIndx: "startDate", dataType:"String"},
		   { title: "End Date", minWidth: 130, dataIndx: "endDate"},
	       { title: "Total Amount", width: 100, dataIndx: "totalAmount", align: "right",render: amountRenderer},
		   { title: "Advance Amount", width: 85, align: "right", dataType: "float", dataIndx: "advanceAmount",render: amountRenderer},
	       { title: "", dataIndx: "expenseHeaderId",hidden:true},
		];
       //define dataModel
       var dataModel = {
           location: "local",
           sorting: "local",
           sortIndx: "expenseHeaderId",
           sortDir: "up",
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
              title: "Pending Expense Voucher At Payment Desk",
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
  	<div id="grid_md" style="margin: auto;"></div>
</body>
</html>