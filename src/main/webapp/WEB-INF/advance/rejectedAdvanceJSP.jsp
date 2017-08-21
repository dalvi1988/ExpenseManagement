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
      
       //define colModel
       var colM = [
		
       { title: "Purpose", width: 100, dataIndx: "purpose",
           filter: { type: 'textbox', condition: 'begin', listeners: ['keyup'] },
       },
       { title: "Advance Number", width: 120, dataIndx: "advanceNumber",
           filter: { type: 'textbox', condition: 'begin', listeners: ['keyup'] }
       }, 
       { title: "Amount", width: 100, dataIndx: "amount", align: "right",render: amountRenderer},
       { title: "For Event", width: 100, dataIndx: "eventDTO", },
       { title: "Rejected By", minWidth: 120, dataIndx: "processedByEmployeeDTO" },
       { title: "Rejection Comment", width: 100, dataIndx: "rejectionComment"},
       { title: "", dataIndx: "advanceDetailId",hidden:true},
       { title: "", editable: false, minWidth: 70, sortable: false, render: function (ui) {
        	return "<button type='button' class='edit_btn' >Edit</button>";
      }},
		];
       //define dataModel
       var dataModel = {
           location: "local",
           sorting: "local",
           sortIndx: "advanceDetailId",
           sortDir: "up",
           data: advanceList
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
           selectionModel: {type: 'row', mode: 'single'},
           filterModel: { on: true, mode: "AND", header: true },
           title: "Rejected Vouchers",
           resizable: true,
           numberCell: { show: false },
           columnBorders: true,           
	       refresh: function(){
	    	    $("#grid_filter").find("button.edit_btn").button({ icons: { primary: 'ui-icon-pencil'} })
	           .unbind("click")
	           .bind("click", function (evt) {
	        	     var $tr = $(this).closest("tr");
	                 var obj = $grid.pqGrid("getRowIndx", { $tr: $tr });
	                 var rowIndx = obj.rowIndx;
	                 var rowData = $grid.pqGrid("getRowData", { rowIndx: rowIndx })
	        	     $("#advanceDetailId").val(rowData.advanceDetailId);
           	    	 $( this ).parent().addClass("active")
         	         $('.content').load('advance?advanceDetailId='+rowData.advanceDetailId);
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
  	<input name="advanceDetailId" id="advanceDetailId" type="text" hidden/>
  </form>
       
</body>
</html>