<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<head>

    <title>Employee Master</title>
    <script type="text/javascript" src=<spring:url value="/scripts/commonJS.js"/> ></script>
    <script type="text/javascript" src=<spring:url value="/grid/pqgrid.min.js"/> ></script>
    <link rel="stylesheet" href=<spring:url value="/grid/pqgrid.min.css"/> />
    
    <!--script tag used to define detail template-->

   <script type="text/javascript">
   var logerBranchId=<sec:authentication property="principal.loginDTO.employeeDTO.branchDTO.branchId" />;
   var employeeList= ${employeeList};
   var reportingMgrList=${employeeList};
   var branchList= ${branchList};
   var departmentList=${departmentList};
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
              // detailobj = gridDetailModel( rowData ),
               //get markup of the detail template.                
               html = $("#tmpl").html(),
               //create new detail place holder
               $detail = $("<div></div>");

		   if(typeof rowData["employeeId"] != "undefined"){
	           for (var key in rowData) {
	               var cellData = (rowData[key] == null) ? "" : rowData[key];
	               html = html.replace("<#=" + key + "#>", cellData);
	           }
	           $detail.html(html);
	
	           $detail.find(".pq-tabs").tabs().on("tabsactivate", onTabsActive);
       		}

           /* //append pqGrid in the 2nd tab.                
           $("<div></div>").appendTo($("#tabs-2", $detail)).pqGrid( detailobj ); */

           return $detail;
       };
       
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
       //called by add button in toolbar.
       function addRow($grid) {
    	   $(".alert").hide();
    	   
           if (isEditing($grid)) {
               return false;
           }
           //append empty row in the first row.                            
           var rowData = { employeeId:"", firstName: "", lastName:"",gender:"M",branchId:logerBranchId,departmentId:null, status:true}; //empty row template
           $grid.pqGrid("addRow", { rowIndxPage: 0, rowData: rowData });

           var $tr = $grid.pqGrid("getRow", { rowIndxPage: 0 });
           if ($tr) {
               //simulate click on edit button.
               $tr.find("button.edit_btn").click();
           }
       }
       //called by delete button.
       function deleteRow(rowIndx, $grid) {
    	   $(".alert").hide();
    	   
           $grid.pqGrid("addClass", { rowIndx: rowIndx, cls: 'pq-row-delete' });
           var rowData = $grid.pqGrid("getRowData", { rowIndx: rowIndx });
           var ans = window.confirm("Are you sure to delete row No " + (rowIndx + 1) + "?");

           if (ans) {
        	   var jsonToBeSend=new Object();
               $grid.pqGrid("deleteRow", { rowIndx: rowIndx, effect: true });

               var data = $grid.pqGrid("getRecId", { rowIndx: rowIndx });
               jsonToBeSend["employeeId"] = data;
               $.ajax($.extend({}, ajaxObj, {
            	   type: 'DELETE', 
                   context: $grid,
                   url: "delete",
                   //url: "/pro/products.php?pq_delete=1",//for PHP
                   data: JSON.stringify(jsonToBeSend),
            	   success: function(data) { 
                      this.pqGrid("commit");
                      this.pqGrid("refreshDataAndView");
                   },
                   error: function () {
                       //debugger;
                       /* this.pqGrid("removeClass", { rowData: rowData, cls: 'pq-row-delete' });
                       this.pqGrid("rollback"); */
                   }
               }));
           }
           else {
               $grid.pqGrid("removeClass", { rowIndx: rowIndx, cls: 'pq-row-delete' });
           }
       }
       //called by edit button.
       function editRow(rowIndx, $grid) {
    	   $(".alert").hide();
    	   
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
            	  //For new record
              }
              else {
            	  // For update
            	  jsonToBeSend["createdBy"] = rowData.createdBy;
              	  jsonToBeSend["createdDate"] = rowData.createdDate;
            	  jsonToBeSend["employeeId"] = rowData.employeeId;
              }
              $.ajax($.extend({}, ajaxObj, { 
            	context: $grid,
          	    url: url, 
          	    type: 'POST', 
          	    data: JSON.stringify(jsonToBeSend),
          	    success: function(data) { 
          	    	if(data.serviceStatus=="SUCCESS"){
	          	    	var recIndx = $grid.pqGrid("option", "dataModel.recIndx");
	                    if (rowData[recIndx] == null || rowData[recIndx] == "") {
	                       rowData.employeeId= data.employeeId;
	                    } 
	          	    	$grid.pqGrid("removeClass", { rowIndx: rowIndx, cls: 'pq-row-edit' });
	          	    	$grid.pqGrid("refreshRow", { rowIndx: rowIndx });
	          	    	$grid.pqGrid("commit");
	          	    	$(".alert").addClass("alert-success").text(data.message).show();
          	    	}
          	    	else{
          	    		//$grid.pqGrid("rollback");
          	    		$grid.pqGrid("addClass", { rowIndx: rowIndx, cls: 'pq-row-edit' });
           				$grid.pqGrid("editFirstCellInRow", { rowIndx: rowIndx });
          	    		$(".alert").addClass("alert-danger").text(data.message).show();
          	    	}
          	    	
          	    },
          	    error:function(data) { 
          	    	$grid.pqGrid("addClass", { rowIndx: rowIndx, cls: 'pq-row-edit' });
                    $grid.pqGrid("editFirstCellInRow", { rowIndx: rowIndx });
          	    	$(".alert").addClass("alert-danger").text(data.message).show();
          	    }
          	    
          	}));
              
              
          }
          else {
       	   
              $grid.pqGrid("quitEditMode");
              $grid.pqGrid("removeClass", { rowIndx: rowIndx, cls: 'pq-row-edit' });
              $grid.pqGrid("refreshRow", { rowIndx: rowIndx });
          }
       }
       //define the grid.
       $gridMain = $("div#grid_md").pqGrid({  
   		   resizable: true,
      	   scrollModel: {autoFit: true},
           height: '90%',
           wrap: false,
           columnBorders: false,
           sortable: true,
           numberCell: { show: false },
           track: true, //to turn on the track changes.
           filterModel: { header: true, type: 'local' },
           toolbar: {
               items: [
                   { type: 'button', icon: 'ui-icon-plus', label: 'Add New Employee', listeners: [
                       { "click": function (evt, ui) {
                           var $grid = $(this).closest('.pq-grid');
                           addRow($grid);
                           //debugger;
                       }
                       }
                   ]
                   }
               ]
           },
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
           title: "<h1><b>Employee Master</b></h1>",

           colModel: [
        	   { title: "", minWidth: 27, width: 27, type: "detail", resizable: false, editable: false /* no need to mention dataIndx */ },
                  { title: "Id", dataType: "integer", dataIndx: "employeeId", editable: false, hidden: true},
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
                  { title: "Branch", dataIndx: "branchId", width: 150,
                	  filter: { type: "select",
          		        condition: 'equal',
          		        prepend: { '': '--All--' },
          		        listeners: ['change'],
          		        valueIndx: "branchId",
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
	       			               return option.branchName;
	       			           } 
	       			       }
        			   }   
                  },
                  { title: "Department", dataIndx: "departmentId", width: 150,
                	  filter: { type: "select",
            		        condition: 'equal',
            		        prepend: { '': '--All--' },
            		        valueIndx: "departmentId",
            		        labelIndx: "departmentName",
            		        listeners: ['change'],
              		        options: departmentList
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
        			   },
        			   validations: [
                           { type: 'nonEmpty', msg: "Required" }
                       ]
                  },
                  { title: "Reporting Manager", dataIndx: "reportingMgr", minWidth: 150,
                	  filter: { type: "select",
          		        condition: 'equal',
          		        prepend: { '': '--All--' },
          		        valueIndx: "employeeId",
          		        labelIndx: "fullName",
          		        listeners: ['change'],
          		      	options: reportingMgrList
          		      },
                	  editor: {                    
                          type: "select",
                          valueIndx: "employeeId",
                          labelIndx: "fullName",
                          groupIndx: "branchName",
                          options: reportingMgrList,
                      },
                      render: function (ui) {		            
      		            var options = ui.column.editor.options,
                              cellData = ui.cellData;
      		            for (var i = 0; i < options.length; i++) {
      		                var option = options[i];
      		                if (option.employeeId == ui.rowData.reportingMgr) {
      		                    return option.fullName;
      		                }
      		            }
      		        },
	      		      validations: [
	                      { type: 'nonEmpty', msg: "Required" }
	                  ]
                  },
                  { title: "Active/Inactive", width: 100, dataType: "bool", align: "center", dataIndx: "status",
                	  filter: { type: "checkbox", subtype: 'triple', condition: "equal", listeners: ['click'] },
                      editor: { type: "checkbox", style: "margin:3px 5px;" },
                      render: function (ui) {
                          if(ui.cellData == true) return "Active";
                          else return "Inactive";
                       	
                      }
                  },
                 
                  { title: "", width: 0, dataIndx: "createdBy", hidden:true },
                  { title: "", width: 0, dataIndx: "createdDate", hidden:true },
                  { title: "", editable: false, minWidth: 180, sortable: false, render: function (ui) {
                      return "<button type='button' class='edit_btn'>Edit</button>\
                          <button type='button' class='delete_btn'>Delete</button>";
                  }
                  }
          ],
           dataModel: {
        	   location: "remote",
               sorting: "local",            
               dataType: "JSON",
               method: "GET",
               sortIndx: "firstName",
               sortDir: "down",
               url: "employeeList",
               getData: function (dataJSON) {
               	var data = dataJSON;
                   //expand the first row.
                   data[0]['pq_detail'] = { 'show': true };
                   return { curPage: dataJSON.curPage, totalRecords: dataJSON.totalRecords, data: data };
               }
           },
           pageModel: { type: "local" },
           cellBeforeSave: function (evt, ui) {
               var $grid = $(this);
               var isValid = $grid.pqGrid("isValid", ui);
               if (!isValid.valid) {
                   return false;
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
           },
           detailModel: { init: initDetail }
       });
       
       //use refresh & refreshRow events to display jQueryUI buttons and bind events.
       $gridMain.on('pqgridrefresh pqgridrefreshrow', function () {
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
       }); 

       $gridMain.pqGrid( "filter", {
		    oper: 'add', 
		    data: [{dataIndx: 'branchId', value:logerBranchId}] 
		});
	   //$gridMain.pqGrid("refresh");

   });


</script>
</head>
<body>
	<b></b>
 <div id="grid_md" style="margin: auto;">
</div>
       <script type="text/pq-template" id="tmpl">
    <div class="pq-tabs" style="width:700px;">
        <ul>
            <li><a href="#tabs-1">Additional Details</a></li>
			<li><a href="#tabs-2">Address</a></li>
            <li><a href="#tabs-3">Bank Details</a></li>
        </ul>
        <div id="tabs-1">
            <p><b>Employee Id:</b> <#=employeeId#></p>
            <p><b>Designation:</b> <select name="Designation" id="designation">
  <option value="volvo">Volvo</option>
  <option value="saab">Saab</option>
  <option value="mercedes">Mercedes</option>
  <option value="audi">Audi</option>
</select></p>
            <p><b>Employee Code:</b> <input type="text" id="employeeCode" name="employeeCode"></p>
            <p><b>Shipping Postal Code:</b> <#=ShipPostalCode#></p>
        </div>
		<div id="tabs-2">
            <p><b>Shipping Address:</b> <#=ShipAddress#></p>
            <p><b>Shipping City:</b> <#=ShipCity#></p>
            <p><b>Shipping Region:</b> <#=ShipRegion#></p>
            <p><b>Shipping Postal Code:</b> <#=ShipPostalCode#></p>
        </div>
        <div id="tabs-3">
            <p><b>Order Date:</b> <#=OrderDate#></p>
            <p><b>Required Date:</b> <#=RequiredDate#></p>
            <p><b>Shipped Date:</b> <#=ShippedDate#></p>
        </div>
    </div>
</script>
    
</body>
</html>
