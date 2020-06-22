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
	   var formatCurrency = $.paramquery.formatCurrency,
	   groupIndx = ["employeeDTO"],
	   colM = [
    	   { title: "Employee Name", minWidth: 100, dataIndx: "employeeDTO" },
	       { title: "Purpose", width: 100, dataIndx: "purpose"},
	       { title: "Voucher Number", minWidth: 200, dataIndx: "voucherNumber"}, 
	       { title: "Start date", minWidth: 80, dataIndx: "startDate", dataType:"String"},
		   { title: "End Date", minWidth: 80, dataIndx: "endDate"},
	       { title: "Total Amount", minWidth: 80, dataIndx: "totalAmount", dataType: "float", align: "center",
			   render: amountRenderer,
               summary:{ 
                   type: ["sum"], 
                   title: [ "Total Amount: {0}" ] 
               }
		   },
	       { title: "Advance Amount", width: 85, align: "right", dataType: "float", dataIndx: "advanceAmount",
        	   render: amountRenderer
		   },
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
       
       var groupModel = {
               dataIndx: groupIndx,
               collapsed: [false],
               dir: ["up"],
               titleCls: ['darkblue'],
               //summaryCls: ['totalAmount'],
               icon: ["circle-plus"]
           };
       
       var obj = {
   		   resizable: true,
   		   scrollModel: {
                  autoFit: true
           },
           height: '90%',
           dataModel: dataModel,
           colModel: colM,
           groupModel: groupModel,
           hwrap: false,
           pageModel: { type: "local", rPP: 10 },
           editable: false,
           selectionModel: {type: 'row', mode: 'single'},
           title: "Expense MIS",
           numberCell: { show: false },
           columnBorders: true, 
           virtualY: true,
       };
      
	     //bind the click event of button
	       /* $("button:contains('Toggle grouping')").click(function (evt) {
	           var $grid = $("#grid_group_rows"), 
	               groupModel = $grid.pqGrid('option', 'groupModel');
	           if (groupModel) {
	               var data = $grid.pqGrid('option', "dataModel").data;
	               for (var i = 0; i < data.length; i++) {
	                   //show all collapsed rows
	                   data[i].pq_hidden = false;
	               }
	               $grid.pqGrid('option', "groupModel", null).pqGrid('refreshView');
	           }
	           else {
	               $grid.pqGrid('option', "groupModel", obj.groupModel).pqGrid('refreshView');
	           }
	       }); */
       var $grid = $("#grid_filter").pqGrid(obj);

   });
</script>
</head>
<body>
  	<div id="grid_filter" style="margin: auto;"></div>
</body>
</html>