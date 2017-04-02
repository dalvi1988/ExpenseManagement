<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<head>

    <title>Expense Request</title>
    <script type="text/javascript" src=<spring:url value="/scripts/jquery-1.11.1.min.js"/> ></script>
 	<link rel="stylesheet" href=<spring:url value="/jquery/jquery-ui.css"/> />
 	<script type="text/javascript" src=<spring:url value="/jquery/jquery-ui.min.js"/> ></script>
 	
 	<script type="text/javascript" src=<spring:url value="/scripts/commonJS.js"/> ></script>
    <script type="text/javascript" src=<spring:url value="/grid/pqgrid.min.js"/> ></script>
    <link rel="stylesheet" href=<spring:url value="/grid/pqgrid.min.css"/> />
    

<script type="text/javascript">


var expenseDetailList=${expenseDetailList};

$(function () {
    
	$( "#startDate" ).datepicker({
	    maxDate: "Now",
	    dateFormat: "dd-MM-yy",
	    changeMonth: true,
	    changeYear: true,
	    onSelect: function (dateText,inst) {
	
	  	 $( "#endDate" ).datepicker( "option", "disabled", false );
	  	  $( "#endDate" ).datepicker( "option", "minDate", dateText);
	    }
	  });
	  
	  
	$( "#endDate" ).datepicker({
		minDate: $( "#startDate" ).val(),
		maxDate: "Now",
		dateFormat: "dd-MM-yy",
		changeMonth: true,
		changeYear: true,
		onClose: function (dateText,inst) {
			$( "#purpose" ).focus();
		}
	});
	
	var dateEditor = function (ui) {
	    var $inp = ui.$cell.find("input"),
	        $grid = $(this),
	        validate = function (that) {
	            var valid = $grid.pqGrid("isValid", { dataIndx: ui.dataIndx, value: $inp.val() }).valid;
	            if (!valid) {
	                that.firstOpen = false;
	            }
	        };

	    //initialize the editor
	    $inp
	    .on("input", function (evt) {
	        validate(this);
	    })
	    .datepicker({
	    	minDate: $( "#startDate" ).val(),
			maxDate: $( "#endDate" ).val(),
	    	dateFormat: "dd-MM-yy",
	        changeMonth: true,
	        changeYear: true,
	        showAnim: '',
	        onSelect: function () {
	            this.firstOpen = true;
	        },
	        beforeShow: function (input, inst) {
	            return !this.firstOpen;
	        },
	        onClose: function () {
	            this.focus();
	        }
	    });
	}
	
	function addNewRow(){
		var rowData = { }; //empty row
        var rowIndx = $grid.pqGrid("addRow", { rowData: rowData });
        $grid.pqGrid("goToPage", { rowIndx: rowIndx });
	}
	
    //called when accept changes button is clicked.
    function acceptChanges() {
        //attempt to save editing cell.
        //debugger;
        if (grid.saveEditCell() === false) {
            return false;
        }

        var isDirty = grid.isDirty();
        if (isDirty) {
            //validate the new added rows.                
            var addList = grid.getChanges().addList;
            for (var i = 0; i < addList.length; i++) {
            	debugger;
                var rowData = addList[i];
                 if(typeof rowData.file =="undefined" || rowData.file == ""){
                	$("#filesDiv").append("<input type='file'name='addedFiles'/>");
                }
                else{ 
                	$("#filesDiv").append(rowData.file);
                }
                var isValid = grid.isValid({ "rowData": rowData }).valid;
                if (!isValid) {
                    return;
                }
            }
            var changes = grid.getChanges({ format: "byVal" });
            
            $("#data").val(JSON.stringify(changes));
            
            $("#form").submit();
          }
    }
    var obj = {
        width: 700,
        height: 400,
        wrap: false,
        hwrap: false,
        resizable: true,
        rowBorders: false,
        numberCell: { show: false },
        track: true, //to turn on the track changes.
        flexHeight: true,
        toolbar: {
            items: [
				{
				    type:function (){
				    	 $('.pq-toolbar').append($('#headerToolbar').show());
				    } 
				},
                { type: 'button', icon: 'ui-icon-plus', label: 'Add new Line', listeners: [
                    { "click": function (evt, ui) {
                        addNewRow();
                    }
                    }
                ]
                },
                { type: 'button', icon: 'ui-icon-disk', label: 'Accept Changes', style: 'margin:0px 5px;', listeners: [
                    { "click": function (evt, ui) {
                        acceptChanges();
                    }
                    }
                ]
                },
                { type: 'button', icon: 'ui-icon-cancel', label: 'Reject Changes', listeners: [
                    { "click": function (evt, ui) {
                        $grid.pqGrid("rollback");
                    }
                    }
                ]
                },
                { type: 'separator' },
                { type: 'button', icon: 'ui-icon-cart', label: 'Get Changes', style: 'margin:0px 5px;', listeners: [
                    { "click": function (evt, ui) {
                        var changes = $grid.pqGrid("getChanges");
                        try {
                            console.log(changes);
                        }
                        catch (ex) { }
                        alert("Please see the log of changes in your browser console.");
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
            type: ''
        },
        hoverMode: 'cell',
        editModel: {
            saveKey: $.ui.keyCode.ENTER
        },
        title: "<b>Expense Voucher</b>",

        colModel: [
            { title: "Expense ID", dataType: "integer", dataIndx: "expenseDetailId", editable: false },
            { title: "Date", width: "150", dataIndx: "date",
		        editor: {
		            type: 'textbox',
		            init: dateEditor
		        },
		        render: function (ui) {
		            var cellData = ui.cellData;
		            if (cellData) {
		                return $.datepicker.formatDate('dd-MM-yy', new Date(cellData));
		            }
		            else {
		                return "";
		            }
		        },
		        validations: [
                    { type: 'regexp', value: '^[0-9]{2}-[A-Za-z]{3,10}-[0-9]{4}$', msg: 'Not in dd-MMM-yyy format' }
                ]
		    }, 
            { title: "Location From", width: 140, dataType: "string", align: "right", dataIndx: "fromLocation"},
            { title: "Location To", width: 100, dataType: "String", align: "right", dataIndx: "toLocation"},
            { title: "Description", width: 100, dataType: "String", align: "right", dataIndx: "description"},
             { title: "Amount", width: 100, dataType: "float", align: "right", dataIndx: "amount",
                validations: [{ type: 'gt', value: 0.5, msg: "should be > 0.5"}],
                render: function (ui) {                        
                    var cellData = ui.cellData;
                    if (cellData != null) {
                        return "&#8377;" + parseFloat(ui.cellData).toFixed(2);
                    }
                    else {
                        return "";
                    }
                }
            },
            { title: "Receipt/Document",editable:false, dataIndx: "file", minWidth: 200, sortable: false, 

            	  render:function (ui) {
            		 
            		 if(typeof ui.cellData == "undefined"){
            		   return "<input type='file' name='file' class='file'/>";
            		  }
            		 else{
            			 var fullPath=ui.cellData.val();
           			     var startIndex = (fullPath.indexOf('\\') >= 0 ? fullPath.lastIndexOf('\\') : fullPath.lastIndexOf('/'));
           			     var filename = fullPath.substring(startIndex);
           			     if (filename.indexOf('\\') === 0 || filename.indexOf('/') === 0) {
           			        filename = filename.substring(1);
           			     } 
            			 return "<a href="+fullPath+">"+ filename+"</a>";
            		 }  
	            }  
            },
            { title: "", editable: false, minWidth: 83, dataIndx: "delButton", sortable: false, render: function (ui) {
                return "<button type='button' class='delete_btn' >Delete</button>";
            }
            }
        ],
        dataModel: {                
            dataType: "JSON",
            location: "local",
            recIndx: "expenseDetailId",
            data: expenseDetailList
        },
        //save the cell when cell loses focus.
        quitEditMode: function (evt, ui) {                
            if (evt.keyCode != $.ui.keyCode.ESCAPE) {
                $grid.pqGrid("saveEditCell");
            }
        },
        refresh: function () {

        	 $("#grid_editing").find("input.file").button().bind("change", function (evt){
        		 //debugger;
        		 var $tr = $(this).closest("tr");
                 var obj = $grid.pqGrid("getRowIndx", { $tr: $tr });
                 var rowIndx = obj.rowIndx;
                 var rowData = $grid.pqGrid("getRowData", { rowIndx: rowIndx })
        		 var clone = $(this).clone();
        	     rowData.file = clone.attr('name', 'addedFiles');
				 $grid.pqGrid("refreshColumn",{dataIndx:"file"})
        	});  
            $("#grid_editing").find("button.delete_btn").button({ icons: { primary: 'ui-icon-scissors'} })
            .unbind("click")
            .bind("click", function (evt) {
                var $tr = $(this).closest("tr");
                var obj = $grid.pqGrid("getRowIndx", { $tr: $tr });
                var rowIndx = obj.rowIndx;
                $grid.pqGrid("addClass", { rowIndx: rowIndx, cls: 'pq-row-delete' });

                var ans = window.confirm("Are you sure to delete row No " + (rowIndx + 1) + "?");

                if (ans) {
                    $grid.pqGrid("deleteRow", { rowIndx: rowIndx, effect: true, complete: function () {
                        $grid.pqGrid("removeClass", { rowIndx: rowIndx, cls: 'pq-row-delete' });
                    }
                    });
                }
                else {
                    $grid.pqGrid("removeClass", { rowIndx: rowIndx, cls: 'pq-row-delete' });
                }
            });
        },
        cellBeforeSave: function (evt, ui) {
            var isValid = grid.isValid(ui);
            if (!isValid.valid) {
                evt.preventDefault();
                return false;
            }
        }
    };
    var $grid = $("#grid_editing").pqGrid(obj);
    
    //get instance of the grid.
    var grid = $grid.data("paramqueryPqGrid");
        
});


</script>
</head>
<body>
 	<form:form id="form" action="saveExpense" method="POST" enctype="multipart/form-data" modelAttribute="ExpenseHeaderDTO">
 	    <div id="headerToolbar" >
 	    	<table style="width: 600px;">
			<tbody>
			<tr>
			<td style="width: 75px;"><form:label path="startDate">Start Date:</form:label></td>
			<td style="width: 181px;"> <form:input path="startDate" readonly="true"/> </td>
			<td style="width: 75px;"><form:label path="endDate">End Date:</form:label></td>
			<td style="width: 262px;"><form:input path="endDate" readonly="true"/> </td>
			</tr>
			<tr>
			<td style="width: 75px;"><form:label path="title">Title:</form:label></td>
			<td style="width: 181px;"><form:input path="title"/></td>
			<td style="width: 75px;"><form:label path="purpose">Purpose:</form:label></td>
			<td style="width: 262px;"><form:textarea style="margin-left: 0px; margin-right: 0px; width: 165px;" cols="" path="purpose" id="purpose" rows=""></form:textarea></td>
			
			</tr>
			</tbody>
			</table>
 	    </div>
        <div id="grid_editing" style="margin: auto;"></div>
 		<input type="hidden" id="data" name="data"/>
 		<div id="filesDiv" style="border: medium; display: none;"></div>
 		<form:hidden path="expenseHeaderId"/>  
    </form:form>
    
     
</body>
</html>