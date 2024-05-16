<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<head>

<title>Expense Management System</title>

<script type="text/javascript" src=<spring:url value="/scripts/commonJS.js"/> ></script>

<script type="text/javascript">
   var expenseHeaderList= ${expenseHeaderList};
   $(function () {
       //define colModel
       var colM = [
			{ 
                dataIndx: "state",
                align: "center",
                title: "<label><input type='checkbox' />&nbsp;Select</label>",
                cb: { header: true, select: true, all: true },
                type: 'checkBoxSelection',
                cls: 'ui-state-default', 
                dataType: 'bool',
                editor: false
            },
			{ title: "expenseHeaderId", editable:false, width: 100, dataType: "integer", hidden:true, dataIndx: "expenseHeaderId" },
			{ title: "Employee Name", editable:false, width: 120, dataIndx: "employeeDTO" },
	       { title: "Purpose",editable:false, width: 100, dataIndx: "purpose"},
	       { title: "Voucher Number",editable:false, minWidth: 180, dataIndx: "voucherNumber"}, 
	       { title: "Start date", editable:false, minWidth: 80, dataIndx: "startDate", dataType:"String"},
		   { title: "End Date", editable:false, minWidth: 80, dataIndx: "endDate"},
	       { title: "Total Amount", editable:false, width: 70, dataIndx: "totalAmount", align: "center",render: amountRenderer
		   },
	       { title: "Advance Amount", editable:false, width: 70, align: "right", dataType: "float", dataIndx: "advanceAmount",
        	   render: amountRenderer
		   }
		];
       //define dataModel
       var dataModel = {
           location: "local",
           sorting: "local",
           sortIndx: "expenseHeaderId",
           sortDir: "up",
           data: expenseHeaderList
       }
       var obj = {
       			resizable: true,
       			virtualX: true, virtualY: true,
               scrollModel: { autoFit: true },
               numberCell: {show: false},
               selectionModel: { type: null },
               pasteModel: { on: false },
               height: '90%',
               colModel:colM,
               hwrap: true,
	           title: "Accounting Entries",
	           columnBorders: true, 
	           collapsible:{ on : false,toggle: false },
               toolbar: {
               		cls: 'pq-toolbar-export',
                   items: [{
                       type: 'button',
                       label: 'Export to Excel File',
                       icon: 'ui-icon-document',
                       listener: function () {
                                                   
                        var selarray = this.selection({ type: 'row', method: 'getSelection' }),
   						ids = [];
                        debugger;
   						if(selarray.length > 0){
                           for (var i = 0, len = selarray.length; i < len; i++) {
                               var rowData = selarray[i].rowData;
                               ids.push(rowData.expenseHeaderId);
                               this.deleteRow({ rowIndx: selarray[i].rowIndx });
                           }
                           $("#expenseHeaderIds").val(ids);
		        	       $( "#form" ).submit();      
                       }
                       else{
                        	alert("Please select expenses to export for accounting entry.");
                       }                    
                   }
                   }]
               },
               dataModel: dataModel
           };
           $("#grid_selection_checkbox").pqGrid(obj);

   });
</script>
</head>
<body>
  	<div id="grid_selection_checkbox" style="margin: auto;"></div>
  	<form id="form" action="exportAccountingEntry" method="POST" >
 		<input type="hidden" id="expenseHeaderIds" name="expenseHeaderIds"></input>
 	  </form>
</body>
</html>
