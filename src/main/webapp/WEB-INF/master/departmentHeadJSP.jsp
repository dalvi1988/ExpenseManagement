<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<head>

    <title>Department Head Master</title>
    <link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/themes/base/jquery-ui.css" />
 	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>    
 	<script type="text/javascript" src=<spring:url value="/scripts/jquery-1.11.1.min.js"/> ></script>
 	<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/jquery-ui.min.js"></script>
    <!-- <script type="text/javascript" src=<spring:url value="/scripts/jquery-1.11.1.min.js"/> ></script> -->
    <script type="text/javascript" src=<spring:url value="/grid/pqgrid.min.js"/> ></script>
    <link rel="stylesheet" href=<spring:url value="/grid/pqgrid.min.css"/> />

   <script type="text/javascript">
   
   var departmentList=${departmentList};
   
   $(function () {
        var colM = [
            { title: "", minWidth: 27, width: 27, type: "detail", resizable: false, editable:false },
            { title: "Branch Code", width: 100, dataIndx: "branchCode" },
            { title: "Branch Name", width: 100, dataIndx: "branchName" },
            { title: "Active/Inactive", width: 100, dataType: "bool", align: "center", dataIndx: "status",
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
            detailModel: {
                cache: true,
                collapseIcon: "ui-icon-plus",
                expandIcon: "ui-icon-minus",
                init: function (ui) {
                    var rowData = ui.rowData,                        
                        detailobj = gridDetailModel( $(this), rowData ), //get a copy of gridDetailModel                        
                        $grid = $("<div></div>").pqGrid( detailobj ); //init the detail grid.

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
         var jsonToBeSend=new Object();
        jsonToBeSend["branchId"]="1";
    	
        var gridDetailModel = function( $gridMain, rowData ){
            return {
            	wrap: false,
                hwrap: false,
                resizable: true,
                columnBorders: false,
                sortable: false,
                numberCell: { show: false },
                track: true, //to turn on the track changes.
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
                        return { url: "/ExpenseManagement/departmentHead", data: "{\"branchId\":"+rowData.branchId+"}" };
                    },
                   
                    mimeType : 'application/json',
                     async: false,
               	    beforeSend: function(xhr) {   
                           xhr.setRequestHeader("Accept", "application/json; charset=UTF-8");
                           xhr.setRequestHeader("Content-Type", "application/json; charset=UTF-8");
                       },
                    error: function (data) {
                        $gridMain.pqGrid( 'rowInvalidate', { rowData: rowData });
                    }
                    //url = "/pro/orderdetails.php?orderId=" + orderID //for PHP
                },
                colModel: [
                    
                    { title: "Branch Id", dataType: "integer", dataIndx: "branchId", hidden:true, width: 80 },
                    { title: "Department", dataIndx: "departmentId", width: 150,
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
                    { title: "", editable: false, minWidth: 150, sortable: false, render: function (ui) {
                        return "<button type='button' class='edit_btn'>Edit</button>\
                            <button type='button' class='delete_btn'>Delete</button>";
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
                    debugger;
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
     	   $(".customMessage").text("");
     	   
            if (isEditing($grid)) {
                return false;
            }
            //append empty row in the first row.                            
            var rowData = { }; //empty row template
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
        	
        	debugger;
     	   $(".customMessage").text("");
     	   
            $grid.pqGrid("addClass", { rowIndx: rowIndx, cls: 'pq-row-edit' });
            $grid.pqGrid("editFirstCellInRow", { rowIndx: rowIndx });

            //change edit button to update button and delete to cancel.
            var $tr = $grid.pqGrid("getRow", { rowIndx: rowIndx }),
                $btn = $tr.find("button.edit_btn");
            debugger;
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
 	
               jsonToBeSend["branchName"] = rowData.branchName;
               jsonToBeSend["branchCode"] = rowData.branchCode;
               jsonToBeSend["status"] = rowData.status;
               url = "/SpringMVCSecruityMavenApp/addBranch";
               
               if (rowData[recIndx] == null || rowData[recIndx] == "") {
             	  //For new record
               }
               else {
             	  // For update
             	  jsonToBeSend["createdBy"] = rowData.createdBy;
               	  jsonToBeSend["createdDate"] = rowData.createdDate;
             	  jsonToBeSend["branchId"] = rowData.branchId;
               }
               $.ajax({ 
           	    url: url, 
           	    type: 'POST', 
           	    dataType: 'json', 
           	    data: JSON.stringify(jsonToBeSend),
           	    async: true,
           	    beforeSend: function(xhr) {                 
                       xhr.setRequestHeader("Accept", "application/json");
                       xhr.setRequestHeader("Content-Type", "application/json");
                   },
           	    success: function(data) { 
           	    	if(data.serviceStatus=="SUCCESS"){
 	          	    	var recIndx = $grid.pqGrid("option", "dataModel.recIndx");
 	                    if (rowData[recIndx] == null || rowData[recIndx] == "") {
 	                       rowData.branchId= data.branchId;
 	                    } 
 	          	    	$grid.pqGrid("removeClass", { rowIndx: rowIndx, cls: 'pq-row-edit' });
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
           	    
           	});
               
               
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
       
</body>
</html>