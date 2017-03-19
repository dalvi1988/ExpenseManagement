<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<head>

    <title>Approval Cycle</title>
    <meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1"> 	
 	<script type="text/javascript" src=<spring:url value="/scripts/jquery-1.11.1.min.js"/> ></script>
 	<link rel="stylesheet" href=<spring:url value="/jquery/jquery-ui.css"/> />
 	<script type="text/javascript" src=<spring:url value="/jquery/jquery-ui.min.js"/> ></script>
 	
 	<script type="text/javascript" src=<spring:url value="/scripts/commonJS.js"/> ></script>
    <script type="text/javascript" src=<spring:url value="/grid/pqgrid.min.js"/> ></script>
    <link rel="stylesheet" href=<spring:url value="/grid/pqgrid.min.css"/> />
    
   <script type="text/javascript">
   
   var departmentList=${departmentList};
   var employeeList=${employeeList};
   var empUnderDeptBranch="";
   
   $(function () {
	   
       function onTabsActive(evt, ui){
           //grid requires refresh whenever corresponding tab is refreshed.
           ui.newPanel.find(".pq-grid").pqGrid("refresh");
       };
       /**
       * does data binding of detail view.
       * @return: {jQuery object}
       */
       function initDetail( ui ) {
           var rowData = ui.rowData,                
               //get a copy of gridDetailModel
               detailobj = gridDetailModel( rowData ),
               //get markup of the detail template.                
               html = $("#tmpl").html(),
               //create new detail place holder
               $detail = $("<div></div>");

           for (var key in rowData) {
               var cellData = (rowData[key] == null) ? "" : rowData[key];
               html = html.replace("<#=" + key + "#>", cellData);
           }
           $detail.html(html);

           $detail.find(".pq-tabs").tabs().on("tabsactivate", onTabsActive);

           //append pqGrid in the 2nd tab.                
           $("<div></div>").appendTo($("#tabs-2", $detail)).pqGrid( detailobj ).data("branchId",rowData.branchId);
           
           
           return $detail;
       };

       var colM = [
           { title: "", minWidth: 27, width: 27, type: "detail", resizable: false, editable:false },
           { title: "Branch Code", minWidth:300, width: 100, dataIndx: "branchCode" },
           { title: "Branch Name", minWidth:300, width: 100, dataIndx: "branchName" },
           { title: "Active/Inactive", width: 50, dataType: "bool", align: "center", dataIndx: "status",
               editor: { type: "checkbox", style: "margin:3px 5px;" },
               render: function (ui) {
                   if(ui.cellData == true) return "Active";
                   else return "Inactive";
                	
               }
           }
       ];

       var dataModel = {
           location: "remote",
           sorting: "local",            
           dataType: "JSON",
           method: "POST",
           recIndx: "branchId",
           rPPOptions: [1, 10, 20, 30, 40, 50, 100, 500, 1000],
           url: "/ExpenseManagement/branchList",
           getData: function (dataJSON) {
           	var data = dataJSON;
               //expand the first row.
               data[0]['pq_detail'] = { 'show': true };
               return { curPage: dataJSON.curPage, totalRecords: dataJSON.totalRecords, data: data };
           }
       }
       var $gridMain = $("div#grid_md").pqGrid({
    	   width: 860, height: 500,
           dataModel: dataModel,
           virtualX: true, virtualY: true,
           editable: false,
           colModel: colM,
           wrap: false,
           hwrap: false,            
           numberCell: { show: false },
           title: "<b>Branch Department Head Master</b>",                        
           resizable: true,
           freezeCols: 1,            
           selectionModel: { type: 'cell' },
           detailModel: { init: initDetail }
       });

       /* another grid in detail view.
       * returns a new copy of detailModel every time the function is called.*/
       var gridDetailModel = function( rowData ){
           return {
           	   wrap: false,
               hwrap: false,
               resizable: true,
               columnBorders: false,
               sortable: false,
               numberCell: { show: false },
               track: true, //to turn on the track changes.
               //flexWidth: true,
               flexHeight: true,
               toolbar: {
                   items: [
                       { type: 'button', icon: 'ui-icon-plus', label: 'Add Product', listeners: [
                           { "click": function (evt, ui) {
                               var $grid = $(this).closest('.pq-grid');
                               addRow($grid);
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
              dataModel: {
                   location: "remote",
                   dataType: "json",
                   method: "POST",
                   recIndx: "branchId",
                   getUrl: function() {
                       return { url: "/ExpenseManagement/fuctionalFlow", data: "{\"branchId\":"+rowData.branchId+"}" };
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
	                               	    url: "/ExpenseManagement/empUnderDeptBranchWithLevel", 
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
                		   
                		    if(ui.rowData['flowId'] == ""){
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
                       editRow(rowIndx, $grid);
                       return false;
                   });

                   //rows which were in edit mode before refresh, put them in edit mode again.
                   var rows = $grid.pqGrid("getRowsByClass", { cls: 'pq-row-edit' });
                   if (rows.length > 0) {
                       var rowIndx = rows[0].rowIndx;
                       editRow(rowIndx, $grid);
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
       function addRow($grid) {
    	 
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
               //simulate click on edit button.
               $tr.find("button.edit_btn").click();
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
       function editRow(rowIndx, $grid) {
    	   $(".customMessage").text("");
    	   
    	   var rowData= $grid.pqGrid( "getRowData",{ rowIndx: rowIndx } );

    	   if(rowData.flowId == ""){
    		   
	           $grid.pqGrid("addClass", { rowIndx: rowIndx, cls: 'pq-row-edit' });
	           $grid.pqGrid("editFirstCellInRow", { rowIndx: rowIndx });
	
	           //change edit button to update button and delete to cancel.
	           var $tr = $grid.pqGrid("getRow", { rowIndx: rowIndx }),
	               $btn = $tr.find("button.edit_btn");
	           
	           $btn.button("option", { label: "Update", "icons": { primary: "ui-icon-check"} })
	               .unbind("click")
	               .click(function (evt) {
	                   evt.preventDefault();
	                   return update(rowIndx, $grid);
	               });
	           $btn.next().button("option", { label: "Cancel", "icons": { primary: "ui-icon-cancel"} })
	               .unbind("click")
	               .click(function (evt) {
	                   $grid.pqGrid("quitEditMode");
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
    			          	    url: "/ExpenseManagement/deactivateFlow", 
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
       function update(rowIndx, $grid) {
   	  
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
              var url,
                  rowData = $grid.pqGrid("getRowData", { rowIndx: rowIndx }),
                  recIndx = $grid.pqGrid("option", "dataModel.recIndx");
              $grid.pqGrid("removeClass", { rowIndx: rowIndx, cls: 'pq-row-edit' });
	
              jsonToBeSend["branchId"] = rowData.branchId;
              
              jsonToBeSend["departmentId"] = rowData.departmentId;
              jsonToBeSend["noOfLevel"] = rowData.noOfLevel;
              jsonToBeSend["level1"] = rowData.level1;
              jsonToBeSend["level2"] = rowData.level2;
              jsonToBeSend["level3"] = rowData.level3;
              jsonToBeSend["status"] =true;
              url = "/ExpenseManagement/addFunctionalFlow";
              
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
          	    		alert(data.flowId)
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
       	   
              $grid.pqGrid("quitEditMode");
              $grid.pqGrid("removeClass", { rowIndx: rowIndx, cls: 'pq-row-edit' });
              $grid.pqGrid("refreshRow", { rowIndx: rowIndx });
          }
       } 
       
   });

</script>
</head>
<body>
  <div id="grid_md" style="margin:5px auto;"></div>
    <script type="text/pq-template" id="tmpl">
    <div class="pq-tabs" style="width:100%;">
        <ul>
            <li><a href="#tabs-1">Functional Approval Cycle</a></li>
            <li><a href="#tabs-2">Finance Approval Cycle</a></li>            
            <li><a href="#tabs-3">Branch Level Approval Cycle</a></li>
        </ul>
        <div id="tabs-1">
			
        </div>
		<div id="tabs-2">
            
        </div>
        <div id="tabs-3">
            <p><b>Order Date:</b> <#=OrderDate#></p>
            <p><b>Required Date:</b> <#=RequiredDate#></p>
            <p><b>Shipped Date:</b> <#=ShippedDate#></p>
        </div>
    </div>
	
</script>   
	<div id="dialog-confirm" style="display: none" title="Are you sure to deactivate workflow.">
  		<p><span class="ui-icon ui-icon-alert"  style="float:left; margin:12px 12px 20px 0;"></span>Once workflow deactivate, then you wont able to activate.\n</p>
	</div>
</body>
</html>