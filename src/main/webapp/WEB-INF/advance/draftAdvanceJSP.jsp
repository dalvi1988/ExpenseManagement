<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<head>

    <title>Expense Management System</title>

 	<script type="text/javascript" src=<spring:url value="/scripts/commonJS.js"/> ></script>

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
       { title: "", dataIndx: "advanceDetailId",hidden:true},
       { title: "", editable: false, minWidth: 70, sortable: false, render: function (ui) {
        	return "<button type='button' class='edit_btn' >Edit</button>";
       },
       postRender: function (ui) {
           var rowIndx = ui.rowIndx,
               grid = this,
               $cell = grid.getCell(ui);
           $cell.find(".edit_btn").button({ icons: { primary: 'ui-icon-pencil'} })
           .bind("click", function (evt) {
	             var rowData = grid.getRowData({ rowIndx: rowIndx });
	  	         $("#advanceDetailId").val(rowData.advanceDetailId);
	 	    	 $( this ).parent().addClass("active")
		         $('.content').load('advance?advanceDetailId='+rowData.advanceDetailId);
           });
       	}
       },
		];
       //define dataModel
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
           title: "Saved Advance Vouchers",
           colModel:colM,
           postRenderInterval: -1, //synchronous post rendering.
           dataModel: dataModel
       };
       var grid = pq.grid("#grid_editing", obj);

   });
</script>
</head>
<body>
  <form id="form" action="expense" method="post">
  	<div id="grid_editing" style="margin: auto;"></div>
  	<input name="advanceDetailId" id="advanceDetailId" type="text" hidden/>
  </form>
       
</body>
</html>