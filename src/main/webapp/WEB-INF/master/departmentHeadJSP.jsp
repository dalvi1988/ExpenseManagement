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
            title: "<b>Shipping Orders</b>",                        
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
    	
        jsonToBeSend["message"] = "hello";
        jsonToBeSend["success"] = "true";
        jsonToBeSend["id"] = "1"; 
        var gridDetailModel = function( $gridMain, rowData ){
            return {
                height: 130,
                //pageModel: { type: "local", rPP: 5, strRpp: "" },
                dataModel: {
                    location: "remote",
                    dataType: "json",
                    url: "/ExpenseManagement/departmentHead", 
                    method: "get",
                    //postData: JSON.stringify(jsonToBeSend),
                    mimeType : 'application/json',
                    postData:{"branchId":rowData.branchId},
                     async: true,
               	    beforeSend: function(xhr) {   
                           xhr.setRequestHeader("Accept", "application/json; charset=UTF-8");
                           xhr.setRequestHeader("Content-Type", "application/json; charset=UTF-8");
                       },
                    error: function (data) {
                    	alert("error"+data)
                        $gridMain.pqGrid( 'rowInvalidate', { rowData: rowData });
                    },
                    success: function(data){
                    	alert(data)
                    }
                    //url = "/pro/orderdetails.php?orderId=" + orderID //for PHP
                },
                colModel: [
                    { title: "BranchName", width: 80, dataIndx: "branchId" },
                    { title: "BranchName", width: 80, datatype:"integer", dataIndx: "branchId" }

		        ],
                editable: false,/* 
                groupModel: {
                    dataIndx: ["branchId"],
                    dir: ["up"],
                    title: ["{0} - {1} product(s)"],
                    icon: [["ui-icon-triangle-1-se", "ui-icon-triangle-1-e"]]
                },       */          
                flexHeight: true,
                flexWidth: true,
                numberCell: { show: false },
                title: "Order Details",
                showTop: false,
                showBottom: false
            };
        };
    });

</script>
</head>
<body>
  <div id="grid_md" style="margin:5px auto;"></div>
       
</body>
</html>