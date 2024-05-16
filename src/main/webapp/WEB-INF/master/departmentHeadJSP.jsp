<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<head>

    <title>Department Head Master</title>
    
 	<script type="text/javascript" src=<spring:url value="/scripts/commonJS.js"/> ></script>
    <script type="text/javascript" src=<spring:url value="/grid-3.5.1/pqgrid.min.js"/> ></script>
    <link rel="stylesheet" href=<spring:url value="/grid-3.5.1/pqgrid.min.css"/> />

   <script type="text/javascript">
   var logerBranchId=<sec:authentication property="principal.loginDTO.employeeDTO.branchDTO.branchId" />;
   var departmentList=${departmentList};
   var employeeList=${employeeList};
   
   $(function () {
	   

      //initialize main grid
        $("#grid_md").pqGrid({             
            width: '100%', height: '58%',
            flexHeight: true,
            virtualX: true, virtualY: true,
            wrap: false,hwrap: false, 
            dataModel: {
                location: "remote",
                sorting: "local",            
                dataType: "JSON",
                method: "POST",
                recIndx: "branchId",
                rPPOptions: [1, 10, 20, 30, 40, 50, 100, 500, 1000],
                url: "branchList",
                getData: function (dataJSON) {
                	var data = dataJSON;
                    //expand the first row.
                    data[0]['pq_detail'] = { 'show': true };
                    return { curPage: dataJSON.curPage, totalRecords: dataJSON.totalRecords, data: data };
                }
            },
            editable: false,
            collapsible:{ on : false,toggle: false },
            colModel: [
				{ title: "", minWidth: 27, width: 27, type: "detail", resizable: false, editable:false },
				{ title: "Branch Code", width: 100, dataIndx: "branchCode" },
				{ title: "Branch Name", width: 100, dataIndx: "branchName"},
				{ title: "Active/Inactive", width: 100, dataType: "bool", align: "center", dataIndx: "status",
				    editor: { type: "checkbox", style: "margin:3px 5px;" },
				    render: function (ui) {
				        if(ui.cellData == true) return "Active";
				        else return "Inactive";
				     	
				    }
				}
            ],
            numberCell: { show: false },
            title: "<b>Department/Cost Center/Project Master Head Master</b>",              
            resizable: true,            
            detailModel: {
                cache: true,
                init: function (ui) {                    
                     var rowData = ui.rowData,                        
                        detailobj = gridDetailModel( $(this), rowData ), //get a copy of gridDetailModel                        
                        $grid = $("<div></div>").pqGrid( detailobj ); //init the detail grid.
                        $grid.data( 'branchId' , rowData.branchId);
                    return $grid;
                }
            }
        });
        

        /* 
        * another grid in detail view.
        * returns a new copy of detailModel every time the function is called.
        * @param $gridMain {jQuery object}: reference to parent grid
        * @param rowData {Plain Object}: row data of parent grid
        */
    	
        var gridDetailModel = function( gridMain, rowData ){
            return {
            	height: 'flex',
            wrap: false,
            hwrap: false,
            resizable: true,
            columnBorders: false,
            numberCell: { show: false },
            trackModel: { on: true }, //to turn on the track changes.       
            collapsible:{ on : false,toggle: false },
            toolbar: {
                items: [
                    {
                        type: 'button',
                        icon: 'ui-icon-plus',
                        label: 'Add New Department Head under {'+rowData.branchName+'} branch',
                        listener: function () {
                            addRow(this,rowData);
                        }
                    },
                     {
                         type: '</br><span style="color:red;font-weight:bold;font-size:20px" class="customMessage"></span>'
                     }
                ]
            },
            scrollModel: { autoFit: true },
            editor: { type: 'textbox', select: true, style: 'outline:none;' },
            validation: { icon: 'ui-icon-info' },
            colModel: [
                  {dataIndx: "departmentId", hidden:true},
                  { title: "Department", dataIndx: "departmentName", width: 150,
                	  editable: function(ui){
                		  debugger;
                		  if(ui.rowData.deptHeadId == undefined ){
                			  return true;
                		  }else{
                			  return false;
                		  }
                		},
                        editor: {                    
                           type: "select",
                           valueIndx: "departmentId",
                           labelIndx: "departmentName",
                           options: departmentList,
                           
                       } ,
                        render: function (ui) {
         			       var options = ui.column.editor.options,
         			           cellData = ui.cellData;
 	       			       for (var i = 0; i < options.length; i++) {
 	       			           var option = options[i];
 	       			           if (option.departmentId == ui.rowData.departmentId) {
 	       			               return option.departmentName;
 	       			           } 
 	       			       }
         			   }   
                   },
                   {dataIndx: "employeeId", hidden:true},
                   { title: "Department Head Name", dataIndx: "fullName", width: 150,
                        editor: {                    
                           type: "select",
                           valueIndx: "employeeId",
                           labelIndx: "fullName",
                           options: employeeList,
                           
                       } ,
                        render: function (ui) {
         			       var options = ui.column.editor.options,
         			           cellData = ui.cellData;
 	       			       for (var i = 0; i < options.length; i++) {
 	       			           var option = options[i];
 	       			           if (option.employeeId == ui.rowData.employeeId) {
 	       			               return option.fullName;
 	       			           } 
 	       			       }
         			   }   
                   },
                   { title: "Active/Inactive", width: 100, dataType: "bool", align: "center", dataIndx: "status",
                       editor: { type: "checkbox", style: "margin:3px 5px;" },
                       render: function (ui) {
                           if(ui.cellData == true) return "Active";
                           else return "Inactive";
                        	
                       }
                   },
                   { title: "Department Head Id", dataType: "integer", dataIndx: "deptHeadId", hidden:true, width: 80 },
                   { title: "", width: 100, dataIndx: "createdBy", hidden:true },
                   { title: "", width: 100, dataIndx: "createdDate", hidden:true },
                   { title: "", editable: false, minWidth: 165, sortable: false,
                       render: function (ui) {
                           return "<button type='button' class='edit_btn'>Edit</button>\
                               <button type='button' class='delete_btn'>Delete</button>";
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
                 location: "remote",
                 dataType: "json",
                 method: "POST",
                 recIndx: "deptHeadId",
                 getUrl: function() {
                     return { url: "departmentHeadList", data: "{\"branchId\":"+rowData.branchId+"}" };
                 },
                
                 mimeType : 'application/json',
                  async: true,
            	    beforeSend: function(xhr) {   
                        xhr.setRequestHeader("Accept", "application/json; charset=UTF-8");
                        xhr.setRequestHeader("Content-Type", "application/json; charset=UTF-8");
                    },
                 error: function (data) {
                     $gridMain.pqGrid( 'rowInvalidate', { rowData: rowData });
                 }
                 //url = "/pro/orderdetails.php?orderId=" + orderID //for PHP
             },
            //make rows editable based upon the class.
            editable: function (ui) {
                return this.hasClass({ rowIndx: ui.rowIndx, cls: 'pq-row-edit' });
            },
            create: function (evt, ui) {
                this.widget().pqTooltip();
            }
            };
        };
       
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
	       function addRow(grid,rowData) {
	       debugger;
	          var branchId = rowData.branchId;
	    	   $(".alert").hide();
	           var rows = grid.getRowsByClass({ cls: 'pq-row-edit' });
	           if (rows.length > 0) {//already a row currently being edited.
	               var rowIndx = rows[0].rowIndx;

	               //focus on editor if any 
	               grid.editFirstCellInRow({ rowIndx: rowIndx });
	           }
	           else {
	               //append empty row in the first row.                            
	            var rowData = {branchId:branchId, status:true };
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

	            jsonToBeSend["branchId"] = rowData.branchId;
               jsonToBeSend["departmentId"] = rowData.departmentId;
               jsonToBeSend["employeeId"] = rowData.employeeId;
               jsonToBeSend["status"] = rowData.status;
               url = "addDepartmentHead";
	               if (rowData[recIndx] == null || rowData[recIndx] == "") {
	                   //add record.
	                   type = 'add';
	               }
	               else {
	                   type = 'update';
	                   jsonToBeSend["createdBy"] = rowData.createdBy;
	              	  jsonToBeSend["createdDate"] = rowData.createdDate;
	            	  jsonToBeSend["deptHeadId"] = rowData.deptHeadId;
	               }
	               $.ajax($.extend({}, ajaxObj, {
	                   context: grid,
	                   url: url,
	                   type: 'POST', 
	                   data: JSON.stringify(jsonToBeSend),
	                   success: function (response) {
	                	   debugger;
	                	   if(response.serviceStatus == "BUSINESS_VALIDATION_FAILURE"){
	                		   $(".alert").addClass("alert-danger").text(response.message).show();
	                	   }
	                	   else{
		                       if (type == 'add') {
		                           rowData[recIndx] = response.deptHeadId;
		                       }
		                       //debugger;
		                       grid.commit({ type: type, rows: [rowData] });
		                       $(".alert").addClass("alert-success").text(response.message).show();
		                       grid.refreshRow({ rowIndx: rowIndx });
	                	   }
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
        

    });
   
   

</script>
</head>
<body>
  <div id="grid_md" style="margin:5px auto;"></div>
       
</body>
</html>