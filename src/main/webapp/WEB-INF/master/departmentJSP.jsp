<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<head>

    <title>Department/Cost Center/Project Master</title>
    
 	<script type="text/javascript" src=<spring:url value="/scripts/commonJS.js"/> ></script>
    <script type="text/javascript" src=<spring:url value="/grid-3.5.1/pqgrid.min.js"/> ></script>
    <link rel="stylesheet" href=<spring:url value="/grid-3.5.1/pqgrid.min.css"/> />

   <script type="text/javascript">
   //var data = [{ "departmentId": 1, "departmentCode": "Exxon Mobil", "departmentName": "339938.0" }];
   var logerBranchId=<sec:authentication property="principal.loginDTO.employeeDTO.branchDTO.branchId" />;
   var data= ${departmentList};
   var branchList= ${branchList};
   
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
    	   $(".customMessage").text("");
           var rows = grid.getRowsByClass({ cls: 'pq-row-edit' });
           if (rows.length > 0) {//already a row currently being edited.
               var rowIndx = rows[0].rowIndx;

               //focus on editor if any 
               grid.editFirstCellInRow({ rowIndx: rowIndx });
           }
           else {
               //append empty row in the first row.                            
               //append empty row in the first row.                            
           var rowData = { departmentCode: "", departmentName:"", status:true}; //empty row template
               var rowIndx = grid.addRow({ rowIndxPage: 0, rowData: rowData, checkEditable: false });

               //start editing the new row.
               editRow(rowIndx, grid, true);
           }
       }
       //called by delete button.
       function deleteRow(rowIndx, $grid) {
    	   $(".customMessage").text("");
    	   
       }
              
     //called by edit button.
       function editRow(rowIndx, grid, edit) {
    	   $(".customMessage").text("");
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

               jsonToBeSend["departmentName"] = rowData.departmentName;
               jsonToBeSend["departmentCode"] = rowData.departmentCode;
               jsonToBeSend["branchId"]=rowData.branchId;
               jsonToBeSend["status"] = rowData.status;
               url = "addDepartment";
               if (rowData[recIndx] == null || rowData[recIndx] == "") {
                   //add record.
                   type = 'add';
               }
               else {
                   type = 'update';
                   jsonToBeSend["createdBy"] = rowData.createdBy;
              	  jsonToBeSend["createdDate"] = rowData.createdDate;
            	  jsonToBeSend["departmentId"] = rowData.departmentId;
               }
               $.ajax($.extend({}, ajaxObj, {
                   context: grid,
                   url: url,
                   type: 'POST', 
                   data: JSON.stringify(jsonToBeSend),
                   success: function (response) {
                       if (type == 'add') {
                           rowData[recIndx] = response.departmentId;
                       }
                       //debugger;
                       grid.commit({ type: type, rows: [rowData] });
                       $(".customMessage").text(response.message);
                       grid.refreshRow({ rowIndx: rowIndx });
                   },
                   error:function(data) { 
                	   $(".customMessage").text(response.message);
     	    		}
               }));
           }
           else {
               grid.quitEditMode();
               grid.removeClass({ rowIndx: rowIndx, cls: 'pq-row-edit' });
               grid.refreshRow({ rowIndx: rowIndx });
               $(".customMessage").text(response.message);
           }
       }
       //define the grid.
       var obj = {
           height: '90%',
           wrap: false,
           hwrap: false,
           resizable: true,
           columnBorders: false,
           numberCell: { show: false },
           filterModel: { on: true, mode: "AND", header: true, type:"local" },
           trackModel: { on: true }, //to turn on the track changes.            
           toolbar: {
               items: [
                   { type: 'button', icon: 'ui-icon-plus', label: 'Add New Department', listeners: [
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
           title: "<h1><b>Department/Cost Center/Project Master</b></h1>",
           colModel: [
                   { title: "Department Id", dataType: "integer", dataIndx: "departmentId",hidden:true, editable: false, width: 80 },
                  { title: "Department Code", width: 140, dataType: "string", align: "right", dataIndx: "departmentCode",
                	  filter: { type: 'textbox',attr: 'placeholder="Search Department Code"', condition: 'contain', listeners: ['keyup'] },                      
                      validations: [
                          { type: 'minLen', value: 1, msg: "Required." },
                          { type: 'maxLen', value: 40, msg: "length should be <= 40" }
                      ]
                  },
                   { title: "Department Name", width: 165, dataType: "string", dataIndx: "departmentName",
                	  filter: { type: 'textbox',attr: 'placeholder="Search Department Name"', condition: 'contain', listeners: ['keyup'] },
                      validations: [
                          { type: 'minLen', value: 1, msg: "Required" },
                          { type: 'maxLen', value: 40, msg: "length should be <= 40" }
                      ]
                  },
                  { dataIndx: 'branchId', hidden:'true'},
                  { title: "Branch", dataIndx: "branchName", width: 150,
                	  filter: { type: "select",
          		        condition: 'equal',
          		        prepend: { '': '--All--' },
          		        listeners: ['change'],
          		        valueIndx: "branchName",
          		        labelIndx: "branchName",
          		        options: branchList,
          		        
          		      },
          		      editor: {                    
                          type: "select",
                          valueIndx: "branchId",
                          labelIndx: "branchName",
                          options: branchList,
                      } ,
                       render: function (ui) {
        			       var options = ui.column.editor.options,
        			           cellData = ui.cellData;
	       			       for (var i = 0; i < options.length; i++) {
	       			           var option = options[i];
	       			           if (option.branchId == ui.rowData.branchId) {
	       			        	ui.rowData.branchName=option.branchName;
	       			               return option.branchName;
	       			           } 
	       			       }
        			   },
        			   validations: [{ type: 'minLen', value: '1', msg: 'Branch Required' }]
                  },
                   { title: "Active/Inactive", width: 100, dataType: "bool", align: "center", dataIndx: "status",
                       editor: false,
                       filter: { type: "checkbox", condition: "equal", listeners: ['click'] },
                       type: 'checkbox',
                       validations: [{ type: 'nonEmpty', msg: 'Active or Inactive?'}]
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
	           recIndx: "departmentId",
	           sorting: "local",
	           sortIndx: "departmentName",
	           sortDir: "down",
	           data: data
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