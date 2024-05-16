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
      /*  { title: "Voucher Number", width: 120, dataIndx: "voucherNumber",
           filter: { type: 'textbox', condition: 'begin', listeners: ['keyup'] }
       },  */
       { title: "", minWidth: 10, width: 10, type: "detail", resizable: false },
       { title: "Start date", minWidth: 130, dataIndx: "startDate", dataType:"String",
       },
	   { title: "End Date", minWidth: 190, dataIndx: "endDate"},
       { title: "Purpose", width: 100, dataIndx: "purpose", align: "center"},
       { title: "For Event", width: 150, dataIndx: "eventDTO" },
       { title: "Advance Number", width: 150, dataIndx: "advanceDTO" },
       { title: "Total Amount", width: 85, align: "right", dataType: "float", dataIndx: "totalAmount",
        	   render: function (ui) {                        
                   var cellData = ui.cellData;
                   if (cellData != null) {
                       return "&#8377;" + parseFloat(ui.cellData).toFixed(2);
                   }
                   else {
                       return "";
                   }
               }
		   },
       
       { title: "", dataIndx: "expenseHeaderId",hidden:true},
       { title: "", editable: false, minWidth: 70, sortable: false, render: function (ui) {
        	return "<button type='button' class='edit_btn' >Edit</button>";
      },
		postRender: function (ui) {
          var rowIndx = ui.rowIndx,
              grid = this,
              $cell = grid.getCell(ui);

          //delete button
          $cell.find(".edit_btn").button({ icons: { primary: 'ui-icon-pencil'} })
          .bind("click", function (evt) {
                  
        	  $(".alert").hide();
     	     var $tr = $(this).closest("tr");
              var rowData = grid.getRowData({ rowIndx: rowIndx })
     	     $("#expenseHeaderId").val(rowData.expenseHeaderId);
    	    	 $( this ).parent().addClass("active")
  	         $('.content').load('expense?expenseHeaderId='+rowData.expenseHeaderId);
          });
		 }
       },
	];
       //define dataModel
       var dataModel = {
           location: "local",
           sorting: "local",
           sortIndx: "expenseHeaderId",
           sortDir: "down",
           data: expenseHeaderList
       }
       var gridMain = pq.grid("#grid_md",{
        	dataModel: dataModel,
            virtualX: true, virtualY: true,
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
            title: "Draft Expense Vouchers",  
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
  <form id="form" action="expense" method="post">
  	<div id="grid_md" style="margin: auto; height: 100%;"></div>
  	<input name="expenseHeaderId" id="expenseHeaderId" type="text" hidden/>
  </form>
       
</body>
</html>