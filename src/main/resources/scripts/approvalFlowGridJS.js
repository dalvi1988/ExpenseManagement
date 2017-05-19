 /* another grid in detail view.
       * returns a new copy of detailModel every time the function is called.*/
var getFunctionalGrid = function( rowData ){
       return {
       	   wrap: false,
           hwrap: false,
           resizable: true,
           columnBorders: false,
           sortable: false,
           numberCell: { show: false },
           title: "<b>Functional Approval Cycle</b>",
           track: true, //to turn on the track changes.
           //flexWidth: true,
           flexHeight: true,
           toolbar: {
               items: [
                   { type: 'button', icon: 'ui-icon-plus', label: 'Add Functional', listeners: [
                       { "click": function (evt, ui) {
                           var $grid = $(this).closest('.pq-grid');
                           addRow($grid,"FunctionalFlow");
                       }
                       }
                   ]
                   },
                   {
                       type: '</br><span style="color:red;font-weight:bold;font-size:20px" class="customMessage"></span>'
                   }
               ]
           },
          dataModel: {
               location: "remote",
               dataType: "json",
               method: "POST",
               recIndx: "branchId",
               getUrl: function() {
                   return { url: "fuctionalFlow", data: "{\"branchId\":"+rowData.branchId+"}" };
               },
               async: false,
          	   beforeSend: function(xhr) {   
                   xhr.setRequestHeader("Accept", "application/json; charset=UTF-8");
                   xhr.setRequestHeader("Content-Type", "application/json; charset=UTF-8");
               },
               error: function (data) {
                   $gridMain.pqGrid( 'rowInvalidate', { rowData: rowData });
               }
           },
           colModel: [
               
               { title: "Functional Approval Cycle ID", dataType: "integer", dataIndx: "flowId", hidden:true, width: 80 },
               { title: "Department", dataIndx: "departmentId", width: 150,
                    editor: {                    
                       type: "select",
                       prepend: { '': '--Select Department--' },
                       valueIndx: "departmentId",
                       labelIndx: "departmentName",
                       options: departmentList,
                       init: function(ui){
                       	   var $grid=$(this);
                    	   
                       	   ui.$cell.find("select").change(function( evt){
                       		   
                       		var jsonToBeSend=new Object();
                     	    jsonToBeSend["branchId"]=ui.rowData.branchId;
                     	    jsonToBeSend["departmentId"]=$(this).val();
                     	   
                           	   $.ajax($.extend({}, ajaxObj, { 
                                   	context: $gridMain,
                               	    url: "empUnderDeptBranchWithLevel", 
                               	    type: 'POST', 
                               	    data: JSON.stringify(jsonToBeSend),
                               	 
                               	    success: function(response) { 
                                        var level1= $grid.pqGrid( "getColumn",{ dataIndx: "level1" } );
                                        level1.editor.options=response.data;
                               	    	
                               	    	var level2= $grid.pqGrid( "getColumn",{ dataIndx: "level2" } );
                               	    	level2.editor.options=response.data;
                               	    	
                               	    	var level3= $grid.pqGrid( "getColumn",{ dataIndx: "level3" } );
                               	    	level3.editor.options=response.data;
                               	    },
                               	    error:function(data) { 
                               	    	empUnderDeptBranch="";
                               	    }
                               	    
                               	}));
                       	   });
                       	} 
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
     			   },
     			  validations: 
     				[
	                    { type: 'minLen', value: 1, msg: "Required" },
	                    { type: function (ui) {

	                    	//Code to validate duplicate approvalflow
	                    	  /*   departmentId = ui.value;
   		                        
			                    //make cell read only if country in this row is present in countries list.    
			                    if ($.inArray(departmentId, departmentList[0]) !== -1) {
			                        return false;
			                    }
			                    else {
			                        return true;
			                    } */
	                     return true;
   		                    }
	                    }
	                ]
               },
               { title: "No. Of Level", dataIndx: "noOfLevel", width: 50,
              	  editor: {                    
                        type: "select",
                        prepend: { '': '--Select--' },
                        options: ['1','2','3'],
                    } 
               },
               { title: "Level1 Approval", dataIndx: "level1", width: 100, 
            	   editable: function(ui){
						if(ui.rowData['noOfLevel'] >= 1)  
							return true;
						else 
							return false;
                   },
               	  editor: {                    
                         type: "select",
                         prepend: { '': '--Select--' },
                         valueIndx: "employeeId",
                         labelIndx: "fullName",
                         options: employeeList
                   },
                   render: function (ui) {
      			       var options = ui.column.editor.options,
      			           cellData = ui.cellData;
       			       for (var i = 0; i < options.length; i++) {
       			           var option = options[i];
       			           if (option.employeeId == ui.rowData.level1) {
       			               return option.fullName;
       			           } 
       			       }
      			   }
               },
               { title: "Level2 Approval", dataIndx: "level2", width: 100,
            	   editable: function(ui){
						if(ui.rowData['noOfLevel'] >= 2)  
							return true;
						else 
							return false;
                   },
                   editor: {                    
                       type: "select",
                       prepend: { '': '--Select--' },
                       valueIndx: "employeeId",
                       labelIndx: "fullName",
                       options: employeeList
                 },
                 render: function (ui) {
    			       var options = ui.column.editor.options,
    			           cellData = ui.cellData;
       			       for (var i = 0; i < options.length; i++) {
       			           var option = options[i];
       			           if (option.employeeId == ui.rowData.level2) {
       			               return option.fullName;
       			           } 
       			       }
    			   } 
               }, 
               { title: "Level3 Approval", dataIndx: "level3", width: 100, 
            	   editable: function(ui){
						if(ui.rowData['noOfLevel'] >= 3)  
							return true;
						else 
							return false;
                   },
                   editor: {                    
                       type: "select",
                       prepend: { '': '--Select--' },
                       valueIndx: "employeeId",
                       labelIndx: "fullName",
                       options: employeeList
                    },
                 	render: function (ui) {
    			       var options = ui.column.editor.options,
    			           cellData = ui.cellData;
       			       for (var i = 0; i < options.length; i++) {
       			           var option = options[i];
       			           if (option.employeeId == ui.rowData.level3) {
       			               return option.fullName;
       			           } 
       			       }
    			   } 
               },
               { title: "", width: 100, dataIndx: "status", hidden:true },
               { title: "", width: 100, dataIndx: "createdBy", hidden:true },
               { title: "", width: 100, dataIndx: "createdDate", hidden:true },
               { title: "", editable: false,dataIndx: "status", minWidth: 150, sortable: false, 
            	   render: function (ui) {
            		   
            		    if(ui.rowData['flowId'] == "" || ui.rowData['flowId'] == null){
                       		return "<button type='button' class='edit_btn'>Edit</button>\
                           			<button type='button' class='delete_btn'>Delete</button>";
            		    }
            		    else{
            		    	if(ui.cellData == true) return "<button type='button' class='edit_btn'>Deactivate</button>";
                            else return "<button type='button' class='delete_btn' disabled>Deactivated</button>";
            		    }
               	   }
               }

	        ],
           editable: true, 
          /* groupModel: {
               dataIndx: ["branchId"],
               dir: ["up"],
               title: ["{0} - {1} product(s)"],
               icon: [["ui-icon-triangle-1-se", "ui-icon-triangle-1-e"]]
           },       */
           scrollModel: {
               autoFit: true
           },
           selectionModel: {
               //type: 'cell'
               type: 'none'
           },
           hoverMode: 'cell',
           editModel: {
               //onBlur: 'validate',
               saveKey: $.ui.keyCode.ENTER
           },
           editor: { type: 'textbox', select: true, style: 'outline:none;' },
           validation: {
               icon: 'ui-icon-info'
           },
           pageModel: { type: "local" },
           cellBeforeSave: function (evt, ui) {
               var $grid = $(this);
               var isValid = $grid.pqGrid("isValid", ui);
               if (!isValid.valid) {
                   return false;
               }
           },
           refresh: function () {
               //debugger;
               var $grid = $(this);

               //delete button
               $grid.find("button.delete_btn").button({ icons: { primary: 'ui-icon-close'} })
               .unbind("click")
               .bind("click", function (evt) {
                   if (isEditing($grid)) {
                       return false;
                   }
                   var $tr = $(this).closest("tr"),
                       rowIndx = $grid.pqGrid("getRowIndx", { $tr: $tr }).rowIndx;
                   deleteRow(rowIndx, $grid);
               });
               
               //edit button
               $grid.find("button.edit_btn").button({ icons: { primary: 'ui-icon-pencil'} })
               .unbind("click")
               .bind("click", function (evt) {
                   if (isEditing($grid)) {
                       return false;
                   }
                   var $tr = $(this).closest("tr"),
                       rowIndx = $grid.pqGrid("getRowIndx", { $tr: $tr }).rowIndx;
                   editRow(rowIndx, $grid, "FunctionalFlow");
                   return false;
               });

               //rows which were in edit mode before refresh, put them in edit mode again.
               var rows = $grid.pqGrid("getRowsByClass", { cls: 'pq-row-edit' });
               if (rows.length > 0) {
                   var rowIndx = rows[0].rowIndx;
                   editRow(rowIndx, $grid, "FunctionalFlow");
               }
           },
           //make rows editable selectively.
           editable: function (ui) {
               var $grid = $(this);
               var rowIndx = ui.rowIndx;
               if ($grid.pqGrid("hasClass", { rowIndx: rowIndx, cls: 'pq-row-edit' }) == true) {
                   return true;
               }
               else {
                   return false;
               }
           }
       };
};





var getFinanceGrid = function( rowData ){
    return {
    	   wrap: false,
        hwrap: false,
        resizable: true,
        columnBorders: false,
        sortable: false,
        title: "<b>Finance Approval Cycle</b>",
        numberCell: { show: false },
        track: true, //to turn on the track changes.
        //flexWidth: true,
        flexHeight: true,
        toolbar: {
            items: [
                { type: 'button', icon: 'ui-icon-plus', label: 'Add Finance Flow', listeners: [
                    { "click": function (evt, ui) {
                        var $grid = $(this).closest('.pq-grid');
                        addRow($grid,"FinanceFlow");
                    }
                    }
                ]
                },
                {
                    type: '</br><span style="color:red;font-weight:bold;font-size:20px" class="customMessage"></span>'
                }
            ]
        },
       dataModel: {
            location: "remote",
            dataType: "json",
            method: "POST",
            recIndx: "branchId",
            getUrl: function() {
                return { url: "financeFlow", data: "{\"branchId\":"+rowData.branchId+"}" };
            },
            async: true,
       	   beforeSend: function(xhr) {   
                xhr.setRequestHeader("Accept", "application/json; charset=UTF-8");
                xhr.setRequestHeader("Content-Type", "application/json; charset=UTF-8");
            },
            error: function (data) {
                //$gridMain.pqGrid( 'rowInvalidate', { rowData: rowData });
            }
        },
        colModel: [
            
            { title: "Flow ID", dataType: "integer", dataIndx: "flowId", hidden:true, width: 80 },
            { title: "No. Of Level", dataIndx: "noOfLevel", width: 50,
           	  editor: {                    
                     type: "select",
                     prepend: { '': '--Select--' },
                     options: ['1','2','3'],
                 } 
            },
            { title: "Level1 Approval", dataIndx: "level1", width: 100, 
         	   editable: function(ui){
						if(ui.rowData['noOfLevel'] >= 1)  
							return true;
						else 
							return false;
                },
            	  editor: {                    
                      type: "select",
                      prepend: { '': '--Select--' },
                      valueIndx: "employeeId",
                      labelIndx: "fullName",
                      options: employeeList
                },
                render: function (ui) {
   			       var options = ui.column.editor.options,
   			           cellData = ui.cellData;
    			       for (var i = 0; i < options.length; i++) {
    			           var option = options[i];
    			           if (option.employeeId == ui.rowData.level1) {
    			               return option.fullName;
    			           } 
    			       }
   			   }
            },
            { title: "Level2 Approval", dataIndx: "level2", width: 100,
         	   editable: function(ui){
						if(ui.rowData['noOfLevel'] >= 2)  
							return true;
						else 
							return false;
                },
                editor: {                    
                    type: "select",
                    prepend: { '': '--Select--' },
                    valueIndx: "employeeId",
                    labelIndx: "fullName",
                    options: employeeList
              },
              render: function (ui) {
 			       var options = ui.column.editor.options,
 			           cellData = ui.cellData;
    			       for (var i = 0; i < options.length; i++) {
    			           var option = options[i];
    			           if (option.employeeId == ui.rowData.level2) {
    			               return option.fullName;
    			           } 
    			       }
 			   } 
            }, 
            { title: "Level3 Approval", dataIndx: "level3", width: 100, 
         	   editable: function(ui){
						if(ui.rowData['noOfLevel'] >= 3)  
							return true;
						else 
							return false;
                },
                editor: {                    
                    type: "select",
                    prepend: { '': '--Select--' },
                    valueIndx: "employeeId",
                    labelIndx: "fullName",
                    options: employeeList
                 },
              	render: function (ui) {
 			       var options = ui.column.editor.options,
 			           cellData = ui.cellData;
			       for (var i = 0; i < options.length; i++) {
			           var option = options[i];
			           if (option.employeeId == ui.rowData.level3) {
			               return option.fullName;
			           } 
			       }
 			   } 
            },
            { title: "", width: 100, dataIndx: "status", hidden:true },
            { title: "", width: 100, dataIndx: "createdBy", hidden:true },
            { title: "", width: 100, dataIndx: "createdDate", hidden:true },
            { title: "", editable: false,dataIndx: "status", minWidth: 150, sortable: false, 
         	   render: function (ui) {
         		  if(ui.rowData['flowId'] == "" || ui.rowData['flowId'] == null){
                		return "<button type='button' class='fin_edit_btn'>Edit</button>\
                    			<button type='button' class='fin_delete_btn'>Delete</button>";
         		    }
         		    else{
         		    	if(ui.cellData == true) return "<button type='button' class='fin_edit_btn'>Deactivate</button>";
                         else return "<button type='button' class='fin_delete_btn' disabled>Deactivated</button>";
         		    }
            	 }
            }

	        ],
        editable: true, 
       /* groupModel: {
            dataIndx: ["branchId"],
            dir: ["up"],
            title: ["{0} - {1} product(s)"],
            icon: [["ui-icon-triangle-1-se", "ui-icon-triangle-1-e"]]
        },       */
        scrollModel: {
            autoFit: true
        },
        selectionModel: {
            //type: 'cell'
            type: 'none'
        },
        hoverMode: 'cell',
        editModel: {
            //onBlur: 'validate',
            saveKey: $.ui.keyCode.ENTER
        },
        editor: { type: 'textbox', select: true, style: 'outline:none;' },
        validation: {
            icon: 'ui-icon-info'
        },
        pageModel: { type: "local" },
        cellBeforeSave: function (evt, ui) {
            var $grid = $(this);
            var isValid = $grid.pqGrid("isValid", ui);
            if (!isValid.valid) {
                return false;
            }
        },
        refresh: function () {
            //debugger;
            var $grid = $(this);

            //delete button
            $grid.find("button.fin_delete_btn").button({ icons: { primary: 'ui-icon-close'} })
            .unbind("click")
            .bind("click", function (evt) {
                if (isEditing($grid)) {
                    return false;
                }
                var $tr = $(this).closest("tr"),
                    rowIndx = $grid.pqGrid("getRowIndx", { $tr: $tr }).rowIndx;
                deleteRow(rowIndx, $grid);
            });
            
            //edit button
            $grid.find("button.fin_edit_btn").button({ icons: { primary: 'ui-icon-pencil'} })
            .unbind("click")
            .bind("click", function (evt) {
                if (isEditing($grid)) {
                    return false;
                }
                var $tr = $(this).closest("tr"),
                    rowIndx = $grid.pqGrid("getRowIndx", { $tr: $tr }).rowIndx;
                editRow(rowIndx, $grid,"FinanceFlow");
                return false;
            });

            //rows which were in edit mode before refresh, put them in edit mode again.
            var rows = $grid.pqGrid("getRowsByClass", { cls: 'pq-row-edit' });
            if (rows.length > 0) {
                var rowIndx = rows[0].rowIndx;
                editRow(rowIndx, $grid,"FinanceFlow");
            }
        },
        //make rows editable selectively.
        editable: function (ui) {
            var $grid = $(this);
            var rowIndx = ui.rowIndx;
            if ($grid.pqGrid("hasClass", { rowIndx: rowIndx, cls: 'pq-row-edit' }) == true) {
                return true;
            }
            else {
                return false;
            }
        }
    };
};



var getBranchGrid = function( rowData ){
    return {
    	   wrap: false,
        hwrap: false,
        resizable: true,
        columnBorders: false,
        sortable: false,
        title: "<b>Branch Approval Cycle</b>",
        numberCell: { show: false },
        track: true, //to turn on the track changes.
        //flexWidth: true,
        flexHeight: true,
        toolbar: {
            items: [
                { type: 'button', icon: 'ui-icon-plus', label: 'Add Branch Flow', listeners: [
                    { "click": function (evt, ui) {
                        var $grid = $(this).closest('.pq-grid');
                        addRow($grid,"BranchFlow");
                    }
                    }
                ]
                },
                {
                    type: '</br><span style="color:red;font-weight:bold;font-size:20px" class="customMessage"></span>'
                }
            ]
        },
       dataModel: {
            location: "remote",
            dataType: "json",
            method: "POST",
            recIndx: "branchId",
            getUrl: function() {
                return { url: "branchFlow", data: "{\"branchId\":"+rowData.branchId+"}" };
            },
            async: false,
       	   beforeSend: function(xhr) {   
                xhr.setRequestHeader("Accept", "application/json; charset=UTF-8");
                xhr.setRequestHeader("Content-Type", "application/json; charset=UTF-8");
            },
            error: function (data) {
                $gridMain.pqGrid( 'rowInvalidate', { rowData: rowData });
            }
        },
        colModel: [
            
            { title: "Flow ID", dataType: "integer", dataIndx: "flowId", hidden:true, width: 80 },
            { title: "No. Of Level", dataIndx: "noOfLevel", width: 50,
           	  editor: {                    
                     type: "select",
                     prepend: { '': '--Select--' },
                     options: ['1','2','3'],
                 } 
            },
            { title: "Level1 Approval", dataIndx: "level1", width: 100, 
         	   editable: function(ui){
						if(ui.rowData['noOfLevel'] >= 1)  
							return true;
						else 
							return false;
                },
            	  editor: {                    
                      type: "select",
                      prepend: { '': '--Select--' },
                      valueIndx: "employeeId",
                      labelIndx: "fullName",
                      options: employeeList
                },
                render: function (ui) {
   			       var options = ui.column.editor.options,
   			           cellData = ui.cellData;
    			       for (var i = 0; i < options.length; i++) {
    			           var option = options[i];
    			           if (option.employeeId == ui.rowData.level1) {
    			               return option.fullName;
    			           } 
    			       }
   			   }
            },
            { title: "Level2 Approval", dataIndx: "level2", width: 100,
         	   editable: function(ui){
						if(ui.rowData['noOfLevel'] >= 2)  
							return true;
						else 
							return false;
                },
                editor: {                    
                    type: "select",
                    prepend: { '': '--Select--' },
                    valueIndx: "employeeId",
                    labelIndx: "fullName",
                    options: employeeList
              },
              render: function (ui) {
 			       var options = ui.column.editor.options,
 			           cellData = ui.cellData;
    			       for (var i = 0; i < options.length; i++) {
    			           var option = options[i];
    			           if (option.employeeId == ui.rowData.level2) {
    			               return option.fullName;
    			           } 
    			       }
 			   } 
            }, 
            { title: "Level3 Approval", dataIndx: "level3", width: 100, 
         	   editable: function(ui){
						if(ui.rowData['noOfLevel'] >= 3)  
							return true;
						else 
							return false;
                },
                editor: {                    
                    type: "select",
                    prepend: { '': '--Select--' },
                    valueIndx: "employeeId",
                    labelIndx: "fullName",
                    options: employeeList
                 },
              	render: function (ui) {
 			       var options = ui.column.editor.options,
 			           cellData = ui.cellData;
			       for (var i = 0; i < options.length; i++) {
			           var option = options[i];
			           if (option.employeeId == ui.rowData.level3) {
			               return option.fullName;
			           } 
			       }
 			   } 
            },
            { title: "", width: 100, dataIndx: "status", hidden:true },
            { title: "", width: 100, dataIndx: "createdBy", hidden:true },
            { title: "", width: 100, dataIndx: "createdDate", hidden:true },
            { title: "", editable: false,dataIndx: "status", minWidth: 150, sortable: false, 
         	   render: function (ui) {
         		  if(ui.rowData['flowId'] == "" || ui.rowData['flowId'] == null){
                		return "<button type='button' class='branch_edit_btn'>Edit</button>\
                    			<button type='button' class='branch_delete_btn'>Delete</button>";
         		    }
         		    else{
         		    	if(ui.cellData == true) return "<button type='button' class='branch_edit_btn'>Deactivate</button>";
                         else return "<button type='button' class='branch_delete_btn' disabled>Deactivated</button>";
         		    }
            	 }
            }

	        ],
        /*editable: true, 
        groupModel: {
            dataIndx: ["branchId"],
            dir: ["up"],
            title: ["{0} - {1} product(s)"],
            icon: [["ui-icon-triangle-1-se", "ui-icon-triangle-1-e"]]
        },       */
        scrollModel: {
            autoFit: true
        },
        selectionModel: {
            //type: 'cell'
            type: 'none'
        },
        hoverMode: 'cell',
        editModel: {
            //onBlur: 'validate',
            saveKey: $.ui.keyCode.ENTER
        },
        editor: { type: 'textbox', select: true, style: 'outline:none;' },
        validation: {
            icon: 'ui-icon-info'
        },
        pageModel: { type: "local" },
        cellBeforeSave: function (evt, ui) {
            var $grid = $(this);
            var isValid = $grid.pqGrid("isValid", ui);
            if (!isValid.valid) {
                return false;
            }
        },
        refresh: function () {
            //debugger;
            var $grid = $(this);

            //delete button
            $grid.find("button.branch_delete_btn").button({ icons: { primary: 'ui-icon-close'} })
            .unbind("click")
            .bind("click", function (evt) {
                if (isEditing($grid)) {
                    return false;
                }
                var $tr = $(this).closest("tr"),
                    rowIndx = $grid.pqGrid("getRowIndx", { $tr: $tr }).rowIndx;
                deleteRow(rowIndx, $grid);
            });
            
            //edit button
            $grid.find("button.branch_edit_btn").button({ icons: { primary: 'ui-icon-pencil'} })
            .unbind("click")
            .bind("click", function (evt) {
                if (isEditing($grid)) {
                    return false;
                }
                var $tr = $(this).closest("tr"),
                    rowIndx = $grid.pqGrid("getRowIndx", { $tr: $tr }).rowIndx;
                editRow(rowIndx, $grid,"BranchFlow");
                return false;
            });

            //rows which were in edit mode before refresh, put them in edit mode again.
            var rows = $grid.pqGrid("getRowsByClass", { cls: 'pq-row-edit' });
            if (rows.length > 0) {
                var rowIndx = rows[0].rowIndx;
                editRow(rowIndx, $grid,"BranchFlow");
            }
        },
        //make rows editable selectively.
        editable: function (ui) {
            var $grid = $(this);
            var rowIndx = ui.rowIndx;
            if ($grid.pqGrid("hasClass", { rowIndx: rowIndx, cls: 'pq-row-edit' }) == true) {
                return true;
            }
            else {
                return false;
            }
        }
    };
};





//called by add button in toolbar.
function addRow($grid, gridName) {

    var branchId = $grid.data( 'branchId' );
    $(".customMessage").text("");
	   
    if (isEditing($grid)) {
        return false;
    }
    //append empty row in the first row.
    var rowData = {branchId:branchId,flowId:"",status:true }; //empty row template
    $grid.pqGrid("addRow", { rowIndxPage: 0, rowData: rowData });

    var $tr = $grid.pqGrid("getRow", { rowIndxPage: 0 });
    if ($tr) {
    	
    	if(gridName == "FinanceFlow"){
    		//simulate click on edit button.
            $tr.find("button.fin_edit_btn").click();
        }
        else if(gridName == "FunctionalFlow"){
        	alert("here")
        	//simulate click on edit button.
            $tr.find("button.edit_btn").click();
        }
        else if(gridName == "BranchFlow"){
        	//simulate click on edit button.
            $tr.find("button.branch_edit_btn").click();
       }
    	
    }
}


//to check whether any row is currently being edited.
function isEditing($grid) {
    var rows = $grid.pqGrid("getRowsByClass", { cls: 'pq-row-edit' });
    if (rows.length > 0) {
        //focus on editor if any 
        $grid.find(".pq-editor-focus").focus();
        return true;
    }
    return false;
}

//called by edit button.
function editRow(rowIndx, $grid, gridName) {
	   $(".customMessage").text("");
	   
	   var rowData= $grid.pqGrid( "getRowData",{ rowIndx: rowIndx } );

	   if(rowData.flowId == "" || rowData.flowId == null){
		   
        $grid.pqGrid("addClass", { rowIndx: rowIndx, cls: 'pq-row-edit' });
        $grid.pqGrid("editFirstCellInRow", { rowIndx: rowIndx });

        if(gridName == "FinanceFlow"){
	        //change edit button to update button and delete to cancel.
	        var $tr = $grid.pqGrid("getRow", { rowIndx: rowIndx }),
	            $btn = $tr.find("button.fin_edit_btn");
        }
        else if(gridName == "FunctionalFlow"){
        	 //change edit button to update button and delete to cancel.
	        var $tr = $grid.pqGrid("getRow", { rowIndx: rowIndx }),
	            $btn = $tr.find("button.edit_btn");
        }
        else if(gridName == "BranchFlow"){
        	 //change edit button to update button and delete to cancel.
	        var $tr = $grid.pqGrid("getRow", { rowIndx: rowIndx }),
	            $btn = $tr.find("button.branch_edit_btn");
        }
        $btn.button("option", { label: "Update", "icons": { primary: "ui-icon-check"} })
            .unbind("click")
            .click(function (evt) {
                evt.preventDefault();
                return update(rowIndx, $grid,gridName);
            });
        $btn.next().button("option", { label: "Cancel", "icons": { primary: "ui-icon-cancel"} })
            .unbind("click")
            .click(function (evt) {
                //$grid.pqGrid("quitEditMode");
                $grid.pqGrid("removeClass", { rowIndx: rowIndx, cls: 'pq-row-edit' });
                $grid.pqGrid("refreshRow", { rowIndx: rowIndx });
                $grid.pqGrid("rollback");
            });
	   }
	   else{
		   $( "#dialog-confirm" ).dialog({
			      resizable: false,
			      height: "auto",
			      width: 400,
			      modal: true,
			      text: "Are you sure to deactivate workflow?",
			      buttons: {
			        "Deactivate": function() {
			          $( this ).dialog( "close" );
			          
			          $.ajax($.extend({}, ajaxObj, { 
			            	context: $gridMain,
			          	    url: "deactivateFlow", 
			          	    type: 'POST', 
			          	    data: "{\"flowId\":"+rowData.flowId+"}",
			          	    success: function(response) {
			          	    	var data=response.data;
			          	    	if(data.serviceStatus=="SUCCESS"){
				          	    	var recIndx = $grid.pqGrid("option", "dataModel.recIndx");
				                    rowData.status= data.status;
				          	    	$grid.pqGrid("commit");
			          	    	}
			          	    	else{
			          	    		$grid.pqGrid("rollback");
			          	    	}
			          	    	$(".customMessage").text(data.message);
			          	    	
			          	    },
			          	    error:function(data) { 
			          	    	$(".customMessage").text(data.message);
			          	    }
			          }));
			        },
			        Cancel: function() {
			          $( this ).dialog( "close" );
			        }
			      }
			});
	   }
}




//called by update button.
function update(rowIndx, $grid, gridName) {
  
   if ($grid.pqGrid("saveEditCell") == false) {
       return false;
   }
   var isValid = $grid.pqGrid("isValid", { rowIndx: rowIndx }).valid;
   
   if (!isValid) {
       return false;
   }
   var isDirty = $grid.pqGrid("isDirty");
   //var isDirty = true;
   if (isDirty) {
	   
	   var jsonToBeSend=new Object();
	   if(gridName == "FinanceFlow"){
		   jsonToBeSend["isBranchFlow"]=false;
		   
       }
       else if(gridName == "FunctionalFlow"){
    	   jsonToBeSend["isBranchFlow"]=false;
       }
       else if(gridName == "BranchFlow"){
    	   jsonToBeSend["isBranchFlow"]=true;
       }
	   
       var url,
           rowData = $grid.pqGrid("getRowData", { rowIndx: rowIndx }),
           recIndx = $grid.pqGrid("option", "dataModel.recIndx");
       $grid.pqGrid("removeClass", { rowIndx: rowIndx, cls: 'pq-row-edit' });

       jsonToBeSend["branchId"] = rowData.branchId;
       jsonToBeSend["flowType"]=gridName;
       jsonToBeSend["departmentId"] = rowData.departmentId;
       jsonToBeSend["noOfLevel"] = rowData.noOfLevel;
       jsonToBeSend["level1"] = rowData.level1;
       jsonToBeSend["level2"] = rowData.level2;
       jsonToBeSend["level3"] = rowData.level3;
       jsonToBeSend["status"] =true;
       url = "addFunctionalFlow";
       
       if (rowData[recIndx] == null || rowData[recIndx] == "") {
     	  //For new record
       }
       else {
     	  // For update
     	  jsonToBeSend["createdBy"] = rowData.createdBy;
       	  jsonToBeSend["createdDate"] = rowData.createdDate;
     	  jsonToBeSend["deptHeadId"] = rowData.deptHeadId;
       }
       $.ajax($.extend({}, ajaxObj, { 
     	context: $gridMain,
   	    url: url, 
   	    type: 'POST', 
   	    data: JSON.stringify(jsonToBeSend),
   	    success: function(data) { 
   	    	if(data.serviceStatus=="SUCCESS"){
       	    	var recIndx = $grid.pqGrid("option", "dataModel.recIndx");
                rowData.flowId= data.flowId;
                rowData.status=data.status;
             //$grid.pqGrid( "refreshRow", { rowIndx: recIndx} );
       	    	//$grid.pqGrid("removeClass", { rowIndx: rowIndx, cls: 'pq-row-edit' });
       	    	$grid.pqGrid("commit");
   	    	}
   	    	else{
   	    		
   	    		$grid.pqGrid("rollback");
   	    	}
   	    	$(".customMessage").text(data.message);
   	    	
   	    },
   	    error:function(data) { 
   	    	$(".customMessage").text(data.message);
   	    }
   	    
   	}));
   }
   else {
	   
       //$grid.pqGrid("quitEditMode");
       $grid.pqGrid("removeClass", { rowIndx: rowIndx, cls: 'pq-row-edit' });
       $grid.pqGrid("refreshRow", { rowIndx: rowIndx });
   }
} 
