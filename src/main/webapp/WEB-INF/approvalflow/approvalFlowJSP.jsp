<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<head>

    <title>Approval Cycle</title>
    <meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1"> 	
 
 	
 	<script type="text/javascript" src=<spring:url value="/scripts/commonJS.js"/> ></script>
 	<script type="text/javascript" src=<spring:url value="/scripts/approvalFlowGridJS.js"/> ></script>
    
   <script type="text/javascript">
   
   var departmentList=${departmentList};
   var employeeList=${employeeList};
   var empUnderDeptBranch="";
   var $gridMain;
   
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
               detailobj = getFunctionalGrid( rowData ),
               
               functionalGrid = getFinanceGrid( rowData ),
               
               branchGrid = getBranchGrid( rowData ),
               
               //get markup of the detail template.                
               html = $("#tmpl").html(),
               //create new detail place holder
               $detail = $("<div></div>");
           

           $detail.html(html);
 
           $detail.find(".pq-tabs").tabs().on("tabsactivate", onTabsActive);

           //append finance pqGrid in the 2nd tab.                
           $("<div></div>").appendTo($("#tabs-2", $detail)).pqGrid( detailobj ).data("branchId",rowData.branchId);
           
         //append  fuctional pqGrid in the 1st tab.                
           $("<div></div>").appendTo($("#tabs-1", $detail)).pqGrid( functionalGrid ).data("branchId",rowData.branchId);
           
         
         //append  fuctional pqGrid in the 1st tab.                
           $("<div></div>").appendTo($("#tabs-3", $detail)).pqGrid( branchGrid ).data("branchId",rowData.branchId);
         
           return $detail;
       };


     //initialize main grid
      var gridMain = pq.grid("#grid_md",{            
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
           title: "<b>Approval Flow Cycle Details</b>",                
           resizable: true,            
           detailModel: { init: initDetail }
       });
             
        
   });

</script>
</head>
<body>
  <div id="grid_md" style="margin:5px auto;"></div>
    <script type="text/pq-template" id="tmpl">
    <div class="pq-tabs" style="width:100%;">
        <ul>
            <li><a href="#tabs-1">Finance Approval Cycle</a></li>
            <li><a href="#tabs-2">Functional Approval Cycle</a></li>            
            <li><a href="#tabs-3">Branch Level Approval Cycle</a></li>
        </ul>
        <div id="tabs-1">
			
        </div>
		<div id="tabs-2">
            
        </div>
        <div id="tabs-3">
        </div>
    </div>
	
</script>   
	<div id="dialog-confirm" style="display: none" title="Are you sure to deactivate workflow.">
  		<p><span class="ui-icon ui-icon-alert"  style="float:left; margin:12px 12px 20px 0;"></span>Once workflow deactivate, then you wont able to activate.\n</p>
	</div>
</body>
</html>