 /* another grid in detail view.
       * returns a new copy of detailModel every time the function is called.*/this
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
                       { "click": function () {
                           addRow(this, rowData, "FunctionalFlow");
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
                   gridMain.rowInvalidate({ rowData: rowData });
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
            		   if(typeof ui.rowData != "undefined"){
							if(ui.rowData['noOfLevel'] >= 1)  
								return true;
							else 
								return false;
            		   }
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
            		   if(typeof ui.rowData != "undefined"){
							if(ui.rowData['noOfLevel'] >= 2)  
								return true;
							else 
								return false;
            		   }
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
            		   if(typeof ui.rowData != "undefined"){
							if(ui.rowData['noOfLevel'] >= 3)  
								return true;
							else 
								return false;
            		   }
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
            		    	if(ui.cellData == true) return "<button type='button' class='delete_btn'>Deactivate</button>";
                            else return "<button type='button' class='delete_btn' disabled>Deactivated</button>";
            		    }
               	   },
               	   postRender: function (ui) {
                        var rowIndx = ui.rowIndx,
                            grid = this,
                            $cell = grid.getCell(ui);

			               //delete button
			               $cell.find("button.delete_btn").button({ icons: { primary: 'ui-icon-close'} })
			               .unbind("click")
			               .bind("click", function () {
							   if (isEditing(grid)) {
			                       return false;
			                   }
			                   deleteRow(rowIndx, grid);
			               });
			               
			               //edit button
			               $cell.find("button.edit_btn").button({ icons: { primary: 'ui-icon-pencil'} })
			               .unbind("click")
			               .bind("click", function () {
			                   if (isEditing(grid)) {
			                       return false;
			                   }
			                   editRow(rowIndx, grid,"FunctionalFlow", true);
			               });
			
			               //if it has edit class, then edit the row.
                            if (grid.hasClass({ rowData: ui.rowData, cls: 'pq-row-edit' })) {
                                editRow(rowIndx, grid,"FunctionalFlow");
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
           //make rows editable selectively.
           editable: function (ui) {
                return this.hasClass({ rowIndx: ui.rowIndx, cls: 'pq-row-edit' });
            },
            postRenderInterval: -1,
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
                    { "click": function () {
                        addRow(this,rowData, "FinanceFlow");
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
         		   if(typeof ui.rowData != "undefined"){
						if(ui.rowData['noOfLevel'] >= 1)  
							return true;
						else 
							return false;
         		   }
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
         		  if(typeof ui.rowData != "undefined"){
						if(ui.rowData['noOfLevel'] >= 2)  
							return true;
						else 
							return false;
         		  }
						
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
         		  if(typeof ui.rowData != "undefined"){
						if(ui.rowData['noOfLevel'] >= 3)  
							return true;
						else 
							return false;
         		  }
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
         		    	if(ui.cellData == true) return "<button type='button' class='fin_delete_btn'>Deactivate</button>";
                         else return "<button type='button' class='fin_delete_btn' disabled>Deactivated</button>";
         		    }
            	 },
               	   postRender: function (ui) {
                        var rowIndx = ui.rowIndx,
                            grid = this,
                            $cell = grid.getCell(ui);

			               //delete button
		               $cell.find("button.fin_delete_btn").button({ icons: { primary: 'ui-icon-close'} })
		               .unbind("click")
		               .bind("click", function () {
						   if (isEditing(grid)) {
		                       return false;
		                   }
		                   deleteRow(rowIndx, grid);
		               });
		               
		               //edit button
		               $cell.find("button.fin_edit_btn").button({ icons: { primary: 'ui-icon-pencil'} })
		               .unbind("click")
		               .bind("click", function () {
		                   if (isEditing(grid)) {
		                       return false;
		                   }
		                   editRow(rowIndx, grid,"FinanceFlow", true);
		               });
		
		               //if it has edit class, then edit the row.
                        if (grid.hasClass({ rowData: ui.rowData, cls: 'pq-row-edit' })) {
                            editRow(rowIndx, grid,"FinanceFlow");
                        }
                   }
            }

	        ],

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
        //make rows editable selectively.
        editable: function (ui) {
            return this.hasClass({ rowIndx: ui.rowIndx, cls: 'pq-row-edit' });
        },
        postRenderInterval: -1,
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
                    { "click": function () {
                        addRow(this,rowData, "BranchFlow");
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
         		  if(typeof ui.rowData != "undefined"){
						if(ui.rowData['noOfLevel'] >= 1)  
							return true;
						else 
							return false;
         		  }
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
         		  if(typeof ui.rowData != "undefined"){
						if(ui.rowData['noOfLevel'] >= 2)  
							return true;
						else 
							return false;
         		  }
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
         		  if(typeof ui.rowData != "undefined"){
						if(ui.rowData['noOfLevel'] >= 3)  
							return true;
						else 
							return false;
         		  }
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
         		    	if(ui.cellData == true) return "<button type='button' class='branch_delete_btn'>Deactivate</button>";
                         else return "<button type='button' class='branch_delete_btn' disabled>Deactivated</button>";
         		    }
            	 },
               	   postRender: function (ui) {
                        var rowIndx = ui.rowIndx,
                            grid = this,
                            $cell = grid.getCell(ui);

			               //delete button
		               $cell.find("button.branch_delete_btn").button({ icons: { primary: 'ui-icon-close'} })
		               .unbind("click")
		               .bind("click", function () {
						   if (isEditing(grid)) {
		                       return false;
		                   }
		                   deleteRow(rowIndx, grid);
		               });
		               
		               //edit button
		               $cell.find("button.branch_edit_btn").button({ icons: { primary: 'ui-icon-pencil'} })
		               .unbind("click")
		               .bind("click", function () {
		                   if (isEditing(grid)) {
		                       return false;
		                   }
		                   editRow(rowIndx, grid,"BranchFlow", true);
		               });
		
		               //if it has edit class, then edit the row.
                        if (grid.hasClass({ rowData: ui.rowData, cls: 'pq-row-edit' })) {
                            editRow(rowIndx, grid,"BranchFlow");
                        }
                   }
            }

	        ],
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
        //make rows editable selectively.
        editable: function (ui) {
            return this.hasClass({ rowIndx: ui.rowIndx, cls: 'pq-row-edit' });
        },
        postRenderInterval: -1
    };
};





//called by add button in toolbar.
function addRow(grid,data, gridName) {
	debugger;
    var branchId = data.branchId;
    $(".customMessage").text("");
	   
    var rows = grid.getRowsByClass({ cls: 'pq-row-edit' });
    
    if (rows.length > 0) {//already a row currently being edited.
        var rowIndx = rows[0].rowIndx;

        //focus on editor if any 
        grid.editFirstCellInRow({ rowIndx: rowIndx });
    }
    else {
        //append empty row in the first row.
    	var rowData = {branchId:branchId,flowId:"",status:true };//empty row template
        var rowIndx = grid.addRow({ rowIndxPage: 0, rowData: rowData, checkEditable: false });

        //start editing the new row.
        editRow(rowIndx, grid, gridName, true);
    }
   
}


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


//called by edit button.
function editRow(rowIndx, grid, gridName, edit) {
	debugger;
	    $(".customMessage").text("");
	   
	    grid.addClass({ rowIndx: rowIndx, cls: 'pq-row-edit' });

        if (edit) grid.editFirstCellInRow({ rowIndx: rowIndx });

        //change edit button to update button and delete to cancel.
        var $tr = grid.getRow({ rowIndx: rowIndx });

        if(gridName == "FinanceFlow"){
	        //change edit button to update button and delete to cancel.
            var $btn = $tr.find("button.fin_edit_btn");
            $btn.button("option", { label: "Update", "icons": { primary: "ui-icon-check"} })
            .unbind("click")
            .click(function (evt) {
                //evt.preventDefault();
                return update(rowIndx, grid, gridName);
            });
             $btn.next().button("option", { label: "Cancel", "icons": { primary: "ui-icon-cancel"} })
            .unbind("click")
            .click(function (evt) {
                grid.quitEditMode();
                grid.removeClass({ rowIndx: rowIndx, cls: 'pq-row-edit' })
                grid.rollback();
            });
           
        }
        else if(gridName == "FunctionalFlow"){
        	 //change edit button to update button and delete to cancel.
            var $btn = $tr.find("button.edit_btn");
            $btn.button("option", { label: "Update", "icons": { primary: "ui-icon-check"} })
            .unbind("click")
            .click(function (evt) {
                //evt.preventDefault();
                return update(rowIndx, grid, gridName);
            });
             $btn.next().button("option", { label: "Cancel", "icons": { primary: "ui-icon-cancel"} })
            .unbind("click")
            .click(function (evt) {
                grid.quitEditMode();
                grid.removeClass({ rowIndx: rowIndx, cls: 'pq-row-edit' })
                grid.rollback();
            });
        }
        else if(gridName == "BranchFlow"){
        	 //change edit button to update button and delete to cancel.
	        var $btn = $tr.find("button.branch_edit_btn");
            $btn.button("option", { label: "Update", "icons": { primary: "ui-icon-check"} })
            .unbind("click")
            .click(function (evt) {
                //evt.preventDefault();
                return update(rowIndx, grid, gridName);
            });
             $btn.next().button("option", { label: "Cancel", "icons": { primary: "ui-icon-cancel"} })
            .unbind("click")
            .click(function (evt) {
                grid.quitEditMode();
                grid.removeClass({ rowIndx: rowIndx, cls: 'pq-row-edit' })
                grid.rollback();
            });
        }
	   
}




//called by update button.
function update(rowIndx, grid, gridName) {
  
    if (grid.saveEditCell() == false) {
        return false;
    }

    if (!grid.isValid({ rowIndx: rowIndx, focusInvalid: true }).valid) {
        return false;
    }

    if (grid.isDirty()) {
        var url,
            rowData = grid.getRowData({ rowIndx: rowIndx }),
            recIndx = grid.option("dataModel.recIndx"),
            type;

	   
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
     	  type = 'add';
       }
       else {
     	  // For update
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
   	    success: function(data) { 
   	    	if(data.serviceStatus=="SUCCESS"){
       	    	var recIndx = grid.option("dataModel.recIndx");
       	    	if (type == 'add') {
                   rowData.flowId = data.flowId;
                }
                rowData.status=data.status;
             //$grid.pqGrid( "refreshRow", { rowIndx: recIndx} );
       	    	//$grid.pqGrid("removeClass", { rowIndx: rowIndx, cls: 'pq-row-edit' });
       	    	grid.commit();
   	    	}
   	    	else{
   	    		grid.rollback();
   	    	}
   	    	$(".customMessage").text(data.message);
   	    	grid.quitEditMode();
	        grid.removeClass({ rowIndx: rowIndx, cls: 'pq-row-edit' });
	        grid.refreshRow({ rowIndx: rowIndx });
   	    },
   	    error:function(data) { 
   	    	$(".customMessage").text(data.message);
   	    }
   	    
   	}));
   }
   else {
        grid.quitEditMode();
        grid.removeClass({ rowIndx: rowIndx, cls: 'pq-row-edit' });
        grid.refreshRow({ rowIndx: rowIndx });
   }
} 

function deleteRow(rowIndx, grid){
	debugger;
	var rowData = grid.getRowData({ rowIndx: rowIndx });
	   $( "#dialog-confirm" ).dialog({
		      resizable: false,
		      height: "auto",
		      width: 400,
		      modal: true,
		      text: "Are you sure to deactivate workflow? ",
		      buttons: {
		        "Deactivate": function() {
		          $( this ).dialog( "close" );
		          
		          $.ajax($.extend({}, ajaxObj, { 
		            	context: grid,
		          	    url: "deactivateFlow", 
		          	    type: 'POST', 
		          	    data: "{\"flowId\":"+rowData.flowId+"}",
		          	    success: function(response) {
		          	    	var data=response.data;
		          	    	if(data.serviceStatus=="SUCCESS"){
			          	    	var recIndx = grid.option("dataModel.recIndx");
			                    rowData.status= data.status;
			          	    	grid.commit();
		          	    	}
		          	    	else{
		          	    		grid.rollback();
		          	    	}
		          	    	$(".customMessage").text(data.message);
		          	    	grid.quitEditMode();
					        grid.removeClass({ rowIndx: rowIndx, cls: 'pq-row-edit' });
					        grid.refreshRow({ rowIndx: rowIndx });
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
