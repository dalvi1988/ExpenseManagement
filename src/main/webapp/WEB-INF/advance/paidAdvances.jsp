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
           columnBorders: true,
           numberCell: { show: false },
           filterModel: { on: true, mode: "AND", header: true, type:"local" },
           trackModel: { on: true }, //to turn on the track changes.,
           collapsible:{ on : false,toggle: false },
           scrollModel: { autoFit: true },
           editor: { type: 'textbox', select: true, style: 'outline:none;' },
           rowBorders: true,
           selectionModel: {type: 'row', mode: 'single'},
           validation: { icon: 'ui-icon-info' },
           title: "Paid Advance Vouchers",
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
  </form>
       
</body>
</html>