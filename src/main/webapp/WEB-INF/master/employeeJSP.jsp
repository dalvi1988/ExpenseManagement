<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<head>

    <title>Employee Master</title>  
    <link rel="stylesheet" href=<spring:url value="/grid-3.5.1/pqgrid.min.css"/> />
    <script type="text/javascript" src=<spring:url value="/grid-3.5.1/pqgrid.min.js"/> ></script>
    <script type="text/javascript" src=<spring:url value="/scripts/commonJS.js"/> ></script>
    <link rel="Stylesheet" href=<spring:url value="/grid-3.5.1/select/pqselect.min.css"/> >
<script type="text/javascript" src=<spring:url value="/grid-3.5.1/select/pqselect.min.js"/> ></script>

   <script type="text/javascript">
   var logerBranchId=<sec:authentication property="principal.loginDTO.employeeDTO.branchDTO.branchId" />;
   var employeeList= ${employeeList};
   var reportingMgrList=${employeeList};
   var branchList= ${branchList};
   var departmentList=${departmentList};
   
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
               var rowData = { employeeId:"", firstName: "", lastName:"",gender:"M",branchId:null, branchName:null, departmentId:null,departmentName:null, status:true}; //empty row template
               var rowIndx = grid.addRow({ rowIndxPage: 0, rowData: rowData, checkEditable: false });

               //start editing the new row.
               editRow(rowIndx, grid, true);
           }
       }
       //called by delete button.
       function deleteRow(rowIndx, grid) {
           
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

			  jsonToBeSend["firstName"] = rowData.firstName;
              jsonToBeSend["lastName"] = rowData.lastName;
              jsonToBeSend["branchId"] = rowData.branchId;
              jsonToBeSend["departmentId"] = rowData.departmentId;
              jsonToBeSend["status"] = rowData.status;
              jsonToBeSend["reportingMgr"] = rowData.reportingMgr;
              jsonToBeSend["gender"] = rowData.gender;
              jsonToBeSend["emailId"] = rowData.emailId;
              url = "addEmployee";
               if (rowData[recIndx] == null || rowData[recIndx] == "") {
                   //add record.
                   type = 'add';
               }
               else {
                   type = 'update';
                   jsonToBeSend["createdBy"] = rowData.createdBy;
              	  jsonToBeSend["createdDate"] = rowData.createdDate;
            	  jsonToBeSend["employeeId"] = rowData.employeeId;
               }
               $.ajax($.extend({}, ajaxObj, {
                   context: grid,
                   url: url,
                   type: 'POST', 
                   data: JSON.stringify(jsonToBeSend),
                   success: function (response) {
                       if (type == 'add') {
                           rowData[recIndx] = response.employeeId;
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
           hwrap: false,
           resizable: true,
           columnBorders: false,
           numberCell: { show: false },
           filterModel: { on: true, mode: "AND", header: true },
           trackModel: { on: true }, //to turn on the track changes.            
           toolbar: {
               items: [
                   {
                       type: 'button',
                       icon: 'ui-icon-plus',
                       label: 'Add New Employee',
                       listener: function () {
                           addRow(this);
                       }
                   }
               ]
           },
           collapsible:{ on : false,toggle: false },
           scrollModel: { autoFit: true },
           editor: { type: 'textbox', select: true, style: 'outline:none;' },
           validation: { icon: 'ui-icon-info' },
           title: "<b>Employee Details</b>",
           colModel: [
                   { title: "Employee ID", dataType: "integer", dataIndx: "employeeId", editable: false, width: 80 , hidden: true},
                   { title: "First Name", width: 130, dataType: "string", dataIndx: "firstName",
         	  			filter: { type: 'textbox',attr: 'placeholder="Search First Name"', condition: 'contain', listeners: ['keyup'] },
                	    validations: [
                          { type: 'minLen', value: 1, msg: "Required." },
                          { type: 'maxLen', value: 20, msg: "length should be <= 20" }
                        ]
                  },
                   { title: "Last Name", width: 130, dataType: "string", dataIndx: "lastName",
                	  filter: { type: 'textbox',attr: 'placeholder="Search Last Name"', condition: 'contain', listeners: ['keyup'] },
                      validations: [
                          { type: 'minLen', value: 1, msg: "Required" },
                          { type: 'maxLen', value: 40, msg: "length should be <= 20" }
                      ]
                  },
                    { title: "Gender", minWidth: 55, dataIndx: "gender",
                	  filter: { type: "select",
          		        condition: 'equal',
          		        prepend: { '': '--All--' },
          		        listeners: ['change'],
          		        options: ['M','F'],
          		      },
                      editor: {
                    	  options: ['M','F'], 
                    	  type: function (ui) {
                             // debugger;
                              var str = "",
                                  options = ui.column.editor.options;
                              $(options).each(function (i, option) {
                                  var checked = '';
                                  if (option == ui.cellData) {
                                      checked = 'checked = checked';
                                  }
                                  str += "<input type='radio' " + checked + " name='" + ui.dataIndx + "' style='margin-left:5px;' value='" + option + "'>  " + option;
                              });
                              ui.$cell.append("<div class='pq-editor-focus' tabindex='0' style='padding:5px;'>" + str + "</div>");
                          },
                          getData: function (ui) {
                              return $("input[name='" + ui.dataIndx + "']:checked").val();
                          }
                    	  
                      }
                  },
                   { title: "Email ID", width: 200, dataType: "string", dataIndx: "emailId",
                	  filter: { type: 'textbox',attr: 'placeholder="Search Email ID"', condition: 'contain', listeners: ['keyup'] },
                      validations: [
                          { type: 'nonEmpty', msg: "Required" }
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
          		        options: branchList
          		      },
          		      editor: {                    
                          type: "select",
                          valueIndx: "branchId",
                          labelIndx: "branchName",
                          options: branchList
                      },
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
                    { dataIndx: "departmentId", hidden:'true' },
                    { title: "Department", dataIndx: "departmentName", width: 150,
                	  filter: { type: "select",
            		        condition: 'equal',
            		        prepend: { '': '--All--' },
            		        valueIndx: "departmentName",
            		        labelIndx: "departmentName",
            		        listeners: ['change'],
              		        options: departmentList
            		    },
                	  editor: {                    
                          type: "select",
		            	  valueIndx: "departmentId",
		           		  labelIndx: "departmentName",		            		            
		            	  options: departmentList
                      } ,
                       render: function (ui) {
        			       var options = ui.column.editor.options,
        			           cellData = ui.cellData;
	       			       for (var i = 0; i < options.length; i++) {
	       			           var option = options[i];
	       			           if (option.departmentId == ui.rowData.departmentId) {
	       			        		ui.rowData.departmentName=option.departmentName;
	       			               return option.departmentName;
	       			           } 
	       			       }
        			   },
        			   validations: [{ type: 'minLen', value: '1', msg: 'Department Required' }]
                  },
                  { dataIndx: "reportingMgr", hidden:'true' },
                  { title: "Reporting Manager", dataIndx: "reportingMgrName", minWidth: 150,
                	  filter: { type: "select",
          		        condition: 'equal',
          		        prepend: { '': '--All--' },
          		        valueIndx: "fullName",
          		        labelIndx: "fullName",
          		        listeners: ['change'],
          		      	options: reportingMgrList,
	          		      init: function () {
	                          $(this).pqSelect({ checkbox: true, radio: true, width: '98%', height:'20' });
	                      }
          		      },
                	  editor: {                    
                          type: "select",
                          init: function (ui) {
      		                ui.$cell.find("select").pqSelect();
      		             },
                          mapIndices: {"fullName": "reportingMgrName", "employeeId": "reportingMgr"},
                          labelIndx: "fullName",
                          valueIndx: "employeeId",
                          options: reportingMgrList,
                      },
                      render: function (ui) {	
      		            var options = ui.column.editor.options,
                              cellData = ui.cellData;
      		            for (var i = 0; i < options.length; i++) {
      		                var option = options[i];
      		                if (option.employeeId == ui.rowData.reportingMgr) {
      		                	ui.rowData.reportingMgrName=option.fullName;
      		                    return option.fullName;
      		                }
      		            }
      		        },
	                  validations: [{ type: 'minLen', value: '1', msg: 'Reporting Manager Required' }]

                  },
                   { title: "Active/Inactive", width: 100, dataType: "bool", align: "center", dataIndx: "status",
                       editor: false,
                       filter: { type: "checkbox", condition: "equal", listeners: ['click'] },
                       type: 'checkbox',
                       validations: [{ type: 'nonEmpty', msg: 'Active or Inactive?'}]
                   },
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
	           recIndx: "employeeId",
	           sorting: "local",
	           sortIndx: "firstName",
	           sortDir: "down",
	           data: employeeList
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
		/* Disable default branch name
		grid.filter( {
		    oper: 'add', 
		    data: [{dataIndx: 'branchId', value:logerBranchId}] 
		});
	   grid.refresh();*/
	   
   });
</script>
</head>
<body>
	<b></b>
 <div id="grid_editing" style="margin: auto;">
</div>
       
</body>
</html>
