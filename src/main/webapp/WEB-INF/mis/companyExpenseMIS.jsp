<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<head>

<title>Expense Management System</title>

<script type="text/javascript" src=<spring:url value="/scripts/commonJS.js"/> ></script>
<script type="text/javascript" src=<spring:url value="/gridNew/pqgrid.min.js"/> ></script>
<link rel="stylesheet" href=<spring:url value="/gridNew/pqgrid.min.css"/> />
<link rel="Stylesheet" href=<spring:url value="/gridNew/select/pqselect.min.css"/> >
<script type="text/javascript" src=<spring:url value="/grid/select/pqselect.min.js"/> ></script>

<script type="text/javascript">
   var expenseHeaderList= ${expenseHeaderList};
   var branchList= ${branchList};
   var employeeList=${employeeList};
   var branchList= ${branchList};
   var departmentList=${departmentList}
   
   $(function () {
	   var formatCurrency = $.paramquery.formatCurrency,
	   groupIndx = ["branchName","departmentName","employeeDTO"],
	   colM = [
		   { title: "Branch", width: 100, dataIndx: "branchName",
			   filter: { type: "select",
     		        condition: 'equal',
     		        prepend: { '': '--All--' },
     		        listeners: ['change'],
     		        valueIndx: "branchName",
     		        labelIndx: "branchName",
     		        options: branchList,
     		       init: function () {
	                      $(this).pqSelect({checkbox:false});
	                  }
     		      }
		   },
	       { title: "Department", width: 100, dataIndx: "departmentName",
			   filter: { type: "select",
   		        condition: 'equal',
   		        prepend: { '': '--All--' },
   		        valueIndx: "departmentName",
   		        labelIndx: "departmentName",
   		        listeners: ['change'],
     		        options: departmentList,
     		       init: function () {
	                      $(this).pqSelect({checkbox:false});
	                  }
   		      },
			},
    	   { title: "Employee Name", minWidth: 100, dataIndx: "employeeDTO",
				filter: { type: "select",
      		        condition: 'equal',
      		        prepend: { '': '--All--' },
      		        valueIndx: "fullName",
      		        labelIndx: "fullName",
      		        listeners: ['change'],
      		      	options: employeeList,
	      		      init: function () {
	                      $(this).pqSelect({checkbox:false});
	                  }
      		      }
      		},
	      
	       { title: "Voucher Number", minWidth: 240, dataIndx: "voucherNumber",
	    	 
      		},
	       { title: "Status", minWidth: 150, dataIndx: "voucherStatusDTO",
	    	   filter: { type: 'select',
	                condition: 'contain',
	                valueIndx: "voucherStatusDTO",
	                labelIndx: "voucherStatusDTO",
	                prepend: { '': '--All--' },
	                listeners: ['change'],
	                options: [
	                    { voucherStatusDTO: "Paid"},
	                    { voucherStatusDTO: "Pending" },
	                    { voucherStatusDTO: "Rejected" },
	                    { voucherStatusDTO: "Approved" }
	                    
	                ],
	                init: function () {
	                    $(this).pqSelect({checkbox:true});
	                }
	            }
      		},
	       { title: "Start date", minWidth: 80, dataIndx: "startDate", dataType:"String"},
		   { title: "End Date", minWidth: 80, dataIndx: "endDate"},
	       { title: "Total Amount", minWidth: 80, dataIndx: "totalAmount", dataType: "float", align: "center",render:amountRenderer,
               summary:{ 
                   type: ["sum","sum","sum"], 
                   title: [
                       function (ui) {
                           return ui.prevRowData[groupIndx[0]] + " Total: &#8377;" + formatCurrency(ui.rowData.totalAmount);
                       },
                       function (ui) {
                           return ui.prevRowData[groupIndx[1]] + " Total: &#8377;" + formatCurrency(ui.rowData.totalAmount);
                       },
                       function (ui) {
                           return ui.prevRowData[groupIndx[2]] + " Total: &#8377;" + formatCurrency(ui.rowData.totalAmount);
                       }
                   ]
               },
		   },
	       { title: "Advance Amount", minWidth: 80, align: "right", dataType: "float", dataIndx: "advanceAmount"
		   },
	       { title: "", dataIndx: "expenseHeaderId",hidden:true}
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
               collapsed: [false, false,true],
               title: [
                   "<b style='font-weight:bold;'>{0} ({1} vouchers)</b>",
                   "<b style='font-weight:bold;'>{0} ({1} vouchers)</b>",
                   "<b style='font-weight:bold;'>{0} ({1} vouchers)</b>"
               ],
               dir: ["up", "up","up"],
               titleCls: ['darkblue','blue','red'],
               summaryCls: ["branchName","departmentName","employeeDTO"]
           };
       
       var obj = {
   		   resizable: true,
   		   scrollModel: {
                  autoFit: true
           },
           height:'95%',
           dataModel: dataModel,
           colModel: colM,
           groupModel: groupModel,
           //hwrap: false,
           editable: false,
           selectionModel: {type: 'row', mode: 'single'},
           title: "Expense MIS ",
           numberCell: { show: false },
           columnBorders: true, 
           virtualY: true,
           filterModel: { on: true, mode: "AND", header: true },
           toolbar: {
               cls: 'pq-toolbar-export',
               items: [{
                       type: 'button',
                       label: "Export to Excel",
                       icon: 'ui-icon-document',
                       listeners: [{
                           "click": function (evt) {
                               $("#grid_filter").pqGrid("exportExcel", { url: "exportMIS", sheetName: "sheet" });
                               //$("#grid_filter").pqGrid("exportCsv", { url: "exportMIS"});
                           }
                       }]
               }]
           }
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