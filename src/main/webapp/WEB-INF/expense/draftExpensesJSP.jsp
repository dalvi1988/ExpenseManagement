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
      /*  { title: "Voucher Number", width: 120, dataIndx: "voucherNumber",
           filter: { type: 'textbox', condition: 'begin', listeners: ['keyup'] }
       },  */
        
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
      }},
		];
       //define dataModel
       var dataModel = {
           location: "local",
           sorting: "local",
           sortIndx: "expenseHeaderId",
           sortDir: "down",
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
           selectionModel: { type: 'none', subtype:'incr', cbHeader:true, cbAll:true},    
           filterModel: { on: true, mode: "AND", header: true },
           title: "Draft Expense Voucher",
           resizable: true,
           numberCell: { show: false },
           columnBorders: true,           
	       refresh: function(){
	    	    $("#grid_filter").find("button.edit_btn").button({ icons: { primary: 'ui-icon-pencil'} })
	           .unbind("click")
	           .bind("click", function (evt) {
	        	   $(".alert").hide();
	        	     var $tr = $(this).closest("tr");
	                 var obj = $grid.pqGrid("getRowIndx", { $tr: $tr });
	                 var rowIndx = obj.rowIndx;
	                 var rowData = $grid.pqGrid("getRowData", { rowIndx: rowIndx })
	        	     $("#expenseHeaderId").val(rowData.expenseHeaderId);
           	    	 $( this ).parent().addClass("active")
         	         $('.content').load('expense?expenseHeaderId='+rowData.expenseHeaderId);
	                 //$("#form").submit();
	           }); 
	       }
       };

       var $grid = $("#grid_filter").pqGrid(obj);

   });
</script>
</head>
<body>
  <form id="form" action="expense" method="post">
  	<div id="grid_filter" style="margin: auto;"></div>
  	<input name="expenseHeaderId" id="expenseHeaderId" type="text" hidden/>
  </form>
       
</body>
</html>