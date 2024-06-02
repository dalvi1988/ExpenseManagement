<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<head>

    <title>Expense Management</title>
   
 	<script type="text/javascript" src=<spring:url value="/scripts/commonJS.js"/> ></script>
    <script type="text/javascript" src=<spring:url value="/grid-3.5.1/pqgrid.min.js"/> ></script>
    <link rel="stylesheet" href=<spring:url value="/grid-3.5.1/pqgrid.min.css"/> />

   <script type="text/javascript">
   var expenseCategoryList= ${expenseCategoryList};
   $(function () {

	   //to check whether any row is currently being edited.
       function isEditing(grid) {
           var rows = grid.getRowsByClass({ cls: 'pq-row-edit' });
           if (rows.length > 0) {
               var rowIndx = rows[0].rowIndx;
               grid.goToPage({ rowIndx: rowIndx });
               //focus on editor if any 
               grid.editFirstCellInRow({ rowIndx: rowIndx });
               return true;
           }
           return false;
       }
      
       //called by add button in toolbar.
       function addRow(grid) {
    	   $(".alert").hide();
           var rows = grid.getRowsByClass({ cls: 'pq-row-edit' });
           if (rows.length > 0) {//already a row currently being edited.
               var rowIndx = rows[0].rowIndx;

               //focus on editor if any 
               grid.editFirstCellInRow({ rowIndx: rowIndx });
           }
           else {
               //append empty row in the first row.                            
             var rowData = { expenseCategoryId:"", status:true,unitRequired :false, amount:0.0}; //empty row template
              var rowIndx = grid.addRow({ rowIndxPage: 0, rowData: rowData, checkEditable: false });

               //start editing the new row.
               editRow(rowIndx, grid, true);
           }
       }
       //called by delete button.
       function deleteRow(rowIndx, $grid) {
    	   $(".alert").hide();
    	   
       }
              
     //called by edit button.
       function editRow(rowIndx, grid, edit) {
    	   $(".alert").hide();
           grid.addClass({ rowIndx: rowIndx, cls: 'pq-row-edit' });

           if (edit) grid.editFirstCellInRow({ rowIndx: rowIndx });

           //change edit button to update button and delete to cancel.
           var $tr = grid.getRow({ rowIndx: rowIndx }),
               $btn = $tr.find("button.edit_btn");
           $btn.button("option", { label: "Update", "icons": { primary: "ui-icon-check"} })
               .unbind("click")
               .click(function (evt) {
                   //evt.preventDefault();
                   return update(rowIndx, grid);
               });
           $btn.next().button("option", { label: "Cancel", "icons": { primary: "ui-icon-cancel"} })
               .unbind("click")
               .click(function (evt) {
                   grid.quitEditMode();
                   grid.removeClass({ rowIndx: rowIndx, cls: 'pq-row-edit' });
                   grid.refreshRow();
                   grid.rollback();
               });
       }
       
       //called by update button.
       function update(rowIndx, grid) {

           if (grid.saveEditCell() == false) {
               return false;
           }

           if (!grid.isValid({ rowIndx: rowIndx, focusInvalid: true }).valid) {
               return false;
           }

           if (grid.isDirty()) {
           	var jsonToBeSend=new Object();
               var url,
                   rowData = grid.getRowData({ rowIndx: rowIndx }),
                   recIndx = grid.option("dataModel.recIndx"),
                   type;

               grid.removeClass({ rowIndx: rowIndx, cls: 'pq-row-edit' });

               jsonToBeSend["expenseName"] = rowData.expenseName;
              jsonToBeSend["glCode"] = rowData.glCode;
              jsonToBeSend["status"] = rowData.status;
              jsonToBeSend["locationRequired"] = rowData.locationRequired;
              jsonToBeSend["unitRequired"] = rowData.unitRequired;
              jsonToBeSend["limitIncrease"] = rowData.limitIncrease;
              jsonToBeSend["amount"] = rowData.amount;
              url = "addExpenseCategory";
              
               if (rowData[recIndx] == null || rowData[recIndx] == "") {
                   //add record.
                   type = 'add';
               }
               else {
                   type = 'update';
                   jsonToBeSend["createdBy"] = rowData.createdBy;
              	  jsonToBeSend["createdDate"] = rowData.createdDate;
            	  jsonToBeSend["expenseCategoryId"] = rowData.expenseCategoryId;
               }
               $.ajax($.extend({}, ajaxObj, {
                   context: grid,
                   url: url,
                   type: 'POST', 
                   data: JSON.stringify(jsonToBeSend),
                   success: function (response) {
                       if (type == 'add') {
                           rowData[recIndx] = response.expenseCategoryId;
                       }
                       //debugger;
                       grid.commit({ type: type, rows: [rowData] });
                       $(".alert").addClass("alert-success").text(response.message).show();
                       grid.refreshRow({ rowIndx: rowIndx });
                   },
      	    		error:function(data) { 
      	    			$(".alert").addClass("alert-danger").text(response.message).show();
      	    		}
               }));
           }
           else {
               grid.quitEditMode();
               grid.removeClass({ rowIndx: rowIndx, cls: 'pq-row-edit' });
               grid.refreshRow({ rowIndx: rowIndx });
               $(".alert").addClass("alert-danger").text(response.message).show();
           }
       }
       //define the grid.
       var obj = {
           height: '90%',
           wrap: false,
           hwrap: true,
           resizable: true,
           columnBorders: false,
           numberCell: { show: false },
           filterModel: { on: true, mode: "AND", header: true, type:"local" },
           trackModel: { on: true }, //to turn on the track changes.            
           toolbar: {
               items: [
                   { type: 'button', icon: 'ui-icon-plus', label: 'Add New Expense Category', listeners: [
                       { "click": function (evt, ui) {
                           addRow(this);
                           //debugger;
                       }
                       }
                   ]
                   },
                   {
                       type: '</br><span style="color:red;font-weight:bold;font-size:20px" class="customMessage"></span>'
                   }
               ]
           },
           collapsible:{ on : false,toggle: false },
           scrollModel: { autoFit: true },
           editor: { type: 'textbox', select: true, style: 'outline:none;' },
           validation: { icon: 'ui-icon-info' },
           title: "<h1><b>Expense Category Master</b></h1>",
           colModel: [
                      { title: "Expense Category ID", dataType: "integer", dataIndx: "expenseCategoryId",hidden:true},
                      { title: "Expense Name", width: 140, dataType: "string", align: "right", dataIndx: "expenseName",
                          filter: { type: 'textbox',attr: 'placeholder="Search Expense Name"', condition: 'contain', listeners: ['keyup'] },
                          validations: [
                              { type: 'minLen', value: 1, msg: "Required." },
                              { type: 'maxLen', value: 25, msg: "length should be <= 25" }
                          ]
                      },
                      { title: "GL Code", width: 165, dataType: "string", dataIndx: "glCode",
                    	  filter: { type: 'textbox', condition: 'contain',attr: 'placeholder="Search GL code"', listeners: ['keyup'] },
                          validations: [
                              { type: 'minLen', value: 1, msg: "Required" },
                              { type: 'maxLen', value: 40, msg: "length should be <= 40" }
                          ]
                      },
                      { title: "Location Required", width: 100, dataType: "bool", align: "center", dataIndx: "locationRequired",
                          editor: false, type: 'checkbox'
                      },
                      { title: "Unit Required", width: 100, dataType: "bool", align: "center", dataIndx: "unitRequired",
                    	  editor: false, type: 'checkbox',
                        	  init: function(ui){
                            	   ui.$cell.change(function(){
                            		   ui.rowData['amount']="";
                            		   grid.refreshCell( { rowIndx: ui.rowIndx, dataIndx: 'amount' } );
                            	   });
                              }
                      },
                      { title: "Amount", width: 100, dataType: "float", align: "right", dataIndx: "amount",
                          validations: [
                              //{ type: 'gt', value: 0, msg: "should be > 0" },
                              { type: function(ui){
                            	  debugger;
                            	  	if(ui.rowData['unitRequired'] == true){
                            	  		if(ui.value<=0 || ui.value== "NaN" ||ui.value ==""){
                            	  		    ui.msg= "should be > 0";
                            	  			return false;
                            	  		}
                            	  	}else{
                            	  		return true;
                            	  	}
                            	  	
                              	 }
                              }
                          ],
    	               	  
                          render: function (ui) {
                        	  debugger;
                        	  if(ui.rowData['unitRequired'] == true)  
    								return ""+parseFloat(ui.cellData).toFixed(2)+ " per Unit";
    							else{
    								if(typeof ui.cellData != "undefined" && ui.cellData != null)
    									return ""+parseFloat(ui.cellData).toFixed(2);
    							}
                          }
                      },
                      { title: "Amount Limit Increased?", width: 130, dataType: "bool", align: "center", dataIndx: "limitIncrease",
                    	  filter: { type: "checkbox", subtype: 'triple', condition: "equal", listeners: ['click'] },
                    	  editor: false, type: 'checkbox'
                      },
                      { title: "Active/Inactive", width: 100, dataType: "bool", align: "center", dataIndx: "status",
                    	  filter: { type: "checkbox",  condition: "equal", listeners: ['click'] },
                          editor: false, type: "checkbox"
                      },
                      { title: "", width: 100, dataIndx: "createdBy", hidden:true },
                      { title: "", width: 100, dataIndx: "createdDate", hidden:true },
                      { title: "", editable: false, minWidth: 200, sortable: false,
	                       render: function (ui) {
	                           return "<button type='button' class='edit_btn'>Edit</button>\
	                               <button type='button' class='delete_btn' >Delete</button>";
	                       },
	                       postRender: function (ui) {
	                           var rowIndx = ui.rowIndx,
	                               grid = this,
	                               $cell = grid.getCell(ui);

	                           //delete button
	                           $cell.find(".delete_btn").button({ icons: { primary: 'ui-icon-close'} })
	                           .bind("click", function (evt) {
	                               deleteRow(rowIndx, grid);
	                           });

	                           //edit button
	                           $cell.find(".edit_btn").button({ icons: { primary: 'ui-icon-pencil'} })
	                           .bind("click", function (evt) {
	                               if (isEditing(grid)) {
	                                   return false;
	                               }
	                               editRow(rowIndx, grid, true);
	                           });

	                           //if it has edit class, then edit the row.
	                           if (grid.hasClass({ rowData: ui.rowData, cls: 'pq-row-edit' })) {
	                               editRow(rowIndx, grid);
	                           }
	                       }
	                   }
              ],
           postRenderInterval: -1, //synchronous post rendering.
           dataModel: {
	           dataType: "JSON",
	           location: "local",
	           recIndx: "expenseCategoryId",
	           sorting: "local",
	           sortIndx: "expenseCategoryId",
	           sortDir: "down",
	           data: expenseCategoryList
           },
           //make rows editable based upon the class.
           editable: function (ui) {
               return this.hasClass({ rowIndx: ui.rowIndx, cls: 'pq-row-edit' });
           },
           create: function (evt, ui) {
               this.widget().pqTooltip();
           }
       };
       var grid = pq.grid("#grid_editing", obj);
   });

</script>
</head>
<body>
  <div id="grid_editing" style="margin: auto;">
</div>
       
</body>
</html>