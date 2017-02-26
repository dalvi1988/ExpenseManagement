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
            { title: "Branch Name", width: 100, dataIndx: "branchName" }
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
                height: 130,
                wrap: false,
                hwrap: false,
                resizable: true,
                columnBorders: false,
                sortable: false,
                numberCell: { show: true },
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
                    { title: "BranchName",hidden:true, width: 80, dataIndx: "branchId" },
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
                editable: true,/* 
                groupModel: {
                    dataIndx: ["branchId"],
                    dir: ["up"],
                    title: ["{0} - {1} product(s)"],
                    icon: [["ui-icon-triangle-1-se", "ui-icon-triangle-1-e"]]
                },       */          
                flexHeight: false,
                flexWidth: true,
                numberCell: { show: false },
                title: "Department and their manager under this branch",
                showTop: true,
                showBottom: true
            };
        };
    });

</script>
</head>
<body>
  <div id="grid_md" style="margin:5px auto;"></div>
       
</body>
</html>