<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<head>

    <title>Fuuntional Approval Cycle</title>
    <link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/themes/base/jquery-ui.css" />
 	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>    
 	<script type="text/javascript" src=<spring:url value="/scripts/jquery-1.11.1.min.js"/> ></script>
 	<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/jquery-ui.min.js"></script>
    <!-- <script type="text/javascript" src=<spring:url value="/scripts/jquery-1.11.1.min.js"/> ></script> -->
    <script type="text/javascript" src=<spring:url value="/grid/pqgrid.min.js"/> ></script>
    <link rel="stylesheet" href=<spring:url value="/grid/pqgrid.min.css"/> />

   <script type="text/javascript">
   //var data = [{ "departmentId": 1, "departmentCode": "Exxon Mobil", "departmentName": "339938.0" }];
   $(function () {
        var colM = [
            { title: "", minWidth: 27, width: 27, type: "detail", resizable: false, editable:false },
            { title: "Branch Name", width: 100, dataIndx: "branchId" },
            { title: "Customer Name", width: 120, dataIndx: "ContactName" },
            { title: "Order ID", width: 100, dataIndx: "OrderID", dataType: "integer", editable: false },
		    { title: "Order Date", width: "100", dataIndx: "OrderDate", dataType: "date" },
		    { title: "Required Date", width: 100, dataIndx: "RequiredDate", dataType: "date" },
		    { title: "Shipped Date", width: 100, dataIndx: "ShippedDate", dataType: "date" },
		    { title: "Shipping Via", width: 100, dataIndx: "ShipVia" },
            { title: "Freight", width: 100, align: "right", dataType: "float", dataIndx: "Freight" },
            { title: "Shipping Name", width: 160, dataIndx: "ShipName" },
            { title: "Shipping Address", width: 200, dataIndx: "ShipAddress" },
            { title: "Shipping City", width: 100, dataIndx: "ShipCity" },
            { title: "Shipping Region", width: 120, dataIndx: "ShipRegion" },
            { title: "Shipping Postal Code", width: 140, dataIndx: "ShipPostalCode" }
        ];

        var dataModel = {
            location: "remote",
            sorting: "local",            
            dataType: "JSON",
            method: "GET",
            recIndx: "OrderID",
            rPPOptions: [1, 10, 20, 30, 40, 50, 100, 500, 1000],
            url: "/pro/orders/get",
            //url: "/pro/orders.php",//for PHP
            getData: function (dataJSON) {
                var data = dataJSON.data;
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
        var gridDetailModel = function( $gridMain, rowData ){
            return {
                height: 130,
                pageModel: { type: "local", rPP: 5, strRpp: "" },
                dataModel: {
                    location: "remote",
                    sorting: "local",
                    dataType: "jsonp",
                    method: "GET",
                    sortIndx: "ProductName",
                    error: function () {
                        $gridMain.pqGrid( 'rowInvalidate', { rowData: rowData });
                    },
                    url: "/pro/orderdetails/get?orderId=" + rowData.OrderID
                    //url = "/pro/orderdetails.php?orderId=" + orderID //for PHP
                },
                colModel: [
                    { title: "Order ID", width: 80, dataIndx: "OrderID" },
                    { title: "Product ID", width: 80, dataType: "integer", dataIndx: "ProductID" },
                    { title: "Product Name", width: 200, dataIndx: "ProductName" },
		            { title: "Unit Price", width: "80", align: "center", dataIndx: "UnitPrice", dataType: "float",
		                summary: {
		                    type: ["sum"],
		                    title: ["<b style='font-weight:bold;'>Total Price:</b> ${0}"]
		                }
		            },
		            { title: "Quantity", align: "center", width: 85, dataIndx: "Quantity", dataType: "integer",
		                summary: {
		                    type: ["sum"],
		                    title: ["<b style='font-weight:bold;'>Total Qty:</b> {0}"]
		                }
		            },
		            { title: "Discount", width: 80, align: "center", dataIndx: "Discount", dataType: "float" }
		        ],
                editable: false,
                groupModel: {
                    dataIndx: ["OrderID"],
                    dir: ["up"],
                    title: ["{0} - {1} product(s)"],
                    icon: [["ui-icon-triangle-1-se", "ui-icon-triangle-1-e"]]
                },                
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