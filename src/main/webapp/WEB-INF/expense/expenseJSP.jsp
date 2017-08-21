<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<head>

 <script type="text/javascript" src=<spring:url value="/scripts/commonJS.js"/> ></script>
 <script type="text/javascript" src=<spring:url value="/grid/pqgrid.min.js"/> ></script>
 <link rel="stylesheet" href=<spring:url value="/grid/pqgrid.min.css"/> />

<style type="text/css">
div.pq-grid tr td.disabled{    
    text-shadow: 0 1px 0 #fff;
    background:#ddd;
    border:0;
}
tr td.beige{        
    background:beige;
}
</style>

<script type="text/javascript">


var expenseDetailList=${expenseDetailList};
var expenseCategoryList =${expenseCategoryList};
var advanceList =${advanceList};
var $summary = "";
var totalData,settlementData;
var grid;
$(function () {
	
		//calculate sum of 3rd and 4th column.
	function calculateSummary() {
		var arrayData=[];
			var revenueTotal = 0,
			profitTotal = 0,
			advanceAmount=$("#advanceAmount").val();
			arrayData = $grid? $grid.pqGrid( "option" , "dataModel.data" ): expenseDetailList;
		    if(arrayData != null){
			    for (var i = 0; i < arrayData.length; i++) {
			        var row = arrayData[i];
			        if(row["amount"] != null)
			        	revenueTotal += parseFloat(row["amount"]);
			    }
		    }
		    totalData = { description: "<b>Total Amount</b>",  amount: revenueTotal ,receipt:"", delButton:"", pq_rowcls: 'green' };
		    if($("input[name='isAdvance']:checked").val() == "on"){
			    settlementData = { description: "<b>Pending Settment Amount</b>",  amount: revenueTotal-advanceAmount,receipt:"", delButton:"", pq_rowcls: 'green' };
		    	
		    }

	}
	function disableFieldRenderer(ui) {
        var grid = $(this).pqGrid('getInstance').grid,
            rowData = ui.rowData,
            rowIndx = ui.rowIndx,
            dataIndx = ui.dataIndx;

        if (grid.isEditableCell({ rowIndx: rowIndx, dataIndx: dataIndx }) == false) {
            //inject disabled class into read only cells.                
            grid.addClass({ rowIndx: rowIndx, dataIndx: dataIndx, cls: 'disabled' });
        }
        else {
            grid.removeClass({ rowIndx: rowIndx, dataIndx: dataIndx, cls: 'disabled' });
        }
    };
    
	function resetDate(){
        var data =  grid.pqGrid("option", "dataModel.data");
        if(data !=null){
          for (var i = 0; i < data.length; i++) {
              /* $grid.pqGrid( "updateRow", {rowIndx: i, row: { 'date':''}} );
              $grid.pqGrid("refresh");  */
          }
        }
	}
	function jsonParseForAutoComplete(data,value,id) {
	    var rows = [];
	    var rowData = null;
	    var dataLength = data.length;
	    for (var i = 0; i < dataLength; i++) {
	        rowData = data[i];
	        rows[i] = {
	            label: rowData[value],
	            value: rowData[id],
	        	row : rowData
	        };
	    }
	    return rows;
	}
	
	var autoCompleteEditor = function (ui) {
        var $inp = ui.$cell.find("input"),
        asignCategoryDetails = function (that,data) {
        	var rowData = $grid.pqGrid("getRowData", { rowIndx: ui.rowIndx });
            rowData.locationRequired = data.row.locationRequired;
            if(data.row.locationRequired == false){
            	rowData.fromLocation="";
            	rowData.toLocation="";
            	$grid.pqGrid( "addClass", {rowIndx: ui.rowIndx, dataIndx: 'fromLocation', cls: 'disabled'} );
            	$grid.pqGrid( "addClass", {rowIndx: ui.rowIndx, dataIndx: 'toLocation', cls: 'disabled'} );
            }
            else{
            	$grid.pqGrid( "removeClass", {rowIndx: ui.rowIndx, dataIndx: 'fromLocation', cls: 'disabled'} );
            	$grid.pqGrid( "removeClass", {rowIndx: ui.rowIndx, dataIndx: 'toLocation', cls: 'disabled'} );
            }
            rowData.unitRequired = data.row.unitRequired;
            rowData.limitIncrease = data.row.limitIncrease;
            if(data.row.unitRequired == false){
            	rowData.unit="";
            	$grid.pqGrid( "addClass", {rowIndx: ui.rowIndx, dataIndx: 'unit', cls: 'disabled'} );
            }
            else{
            	$grid.pqGrid( "removeClass", {rowIndx: ui.rowIndx, dataIndx: 'unit', cls: 'disabled'} );
            }
            //$grid.pqGrid( "removeClass", {rowIndx: ui.rowIndx, dataIndx: 'amount', cls: 'disabled'} );
            rowData.amountPerUnit= data.row.amount; 
            
            rowData.expenseCategoryId =data.value;
           // $grid.pqGrid("refresh");
        };
        //initialize the editor
        $inp.autocomplete({
            source: function(request, response) {
                var rows = jsonParseForAutoComplete(expenseCategoryList,"expenseName","expenseCategoryId");
                return response(rows);
            },
            selectItem: { on: true }, //custom option
            highlightText: { on: true }, //custom option
            minLength: 0,
            select: function(event, ui) {
				event.preventDefault();
				$(this).val(ui.item.label);
				
				asignCategoryDetails(this,ui.item);
			},
            focus: function(event,ui){
            	event.preventDefault();
				$(this).val(ui.item.label);
            }
        }).focus(function () {
            //open the autocomplete upon focus                
            $(this).autocomplete("search", "");
        });
    }
	
	$( "#startDate" ).datepicker({
	    maxDate: "Now",
	    dateFormat: "dd-MM-yy",
	    buttonImage: "/images/calendar.gif",
	    changeMonth: true,
	    changeYear: true,
	    onSelect: function (dateText,inst) {
	    	//resetDate();
	  	 $( "#endDate" ).datepicker( "option", "disabled", false );
	  	  $( "#endDate" ).datepicker( "option", "minDate", dateText);
	    }
	  });
	  
	$( "#endDate" ).datepicker({
		minDate: $( "#startDate" ).val(),
		maxDate: "Now",
		dateFormat: "dd-MM-yy",
		buttonImage: "images/calendar.gif",
		changeMonth: true,
		changeYear: true,
		onClose: function (dateText,inst) {
			$( "#purpose" ).focus();
		},
		onSelect: function (dateText,inst) {
	    	//resetDate();
	    }
	});
	$( "#startDate" ).datepicker( "setDate", "Now" );
	$( "#endDate" ).datepicker( "setDate", "Now" );
	
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
		
		var rowData = {date:$("#startDate").val(), locationRequired :false,unitRequired:false }; //empty row
        var rowIndx = $grid.pqGrid("addRow", { rowData: rowData });
        //$grid.pqGrid( "addClass", {rowIndx: rowIndx, dataIndx: 'amount', cls: 'disabled'} );
        $grid.pqGrid("goToPage", { rowIndx: rowIndx });
	}
	
	function validateComponent(){
		if($("#startDate").val() ==""){
			$("#dialog").text("Start Date can not be empty.");
			$("#dialog").dialog();
			return false;
		}else if($("#endDate").val() ==""){
			$("#dialog").text("End Date can not be empty.");
			$("#dialog").dialog();
			return false;
		}
		else if($("#purpose").val().length <= 4){
			$("#dialog").text("Purpose should be more than 4 character");
			$("#dialog").dialog();
			return false;
		}
		else{
			return true;
		}
	}
    //called when accept changes button is clicked.
    function acceptChanges(command) {
    	if(validateComponent() ==false){
			return false;
		} 
    	 
        //attempt to save editing cell.
        //debugger;
        if (grid.saveEditCell() === false) {
            return false;
        }

       /*  var isDirty = grid.isDirty();
        if (isDirty) { */
            //validate the new added rows.                
            var addList = grid.getChanges().addList;
            for( var i = 0; i < addList.length; i++) {
                var rowData = addList[i];
                
                if(typeof rowData.receipt =="undefined" || rowData.receipt == ""){
                	$("#filesDiv").append("<input type='file'name='addedFiles'/>");
                }
                else{ 
                	$("#filesDiv").append(rowData.receipt);
                }
                
                var isValid = grid.isValid({ "rowData": rowData }).valid;
                if (!isValid) {
                    return;
                }
            }
            
          //validate the new added rows.                
            var updateList = grid.getChanges().updateList;
            for (var i = 0; i < updateList.length; i++) {
                var rowData = updateList[i];
                 if(typeof rowData.receipt =="undefined" || rowData.receipt == ""){
                	$("#filesDiv").append("<input type='file'name='updatedFiles'/>");
                }
                else{ 
                	$("#filesDiv").append(rowData.receipt);
                }
                var isValid = grid.isValid({ "rowData": rowData }).valid;
                if (!isValid) {
                    return;
                }
            }
            var changes = grid.getChanges({ format: "byVal" });
            
            $("#data").val(JSON.stringify(changes));
            $("#voucherStatusId").val(command);
            
            $.ajax( { 
              	//context: $grid,
          	    url: "saveExpense", 
          	    type: 'POST', 
          	    data:new FormData($('#form')[0]),
          	    cache: false,
                contentType: false,
                processData: false,
          	 
          	    success: function(data) { 
          	    	if(data.serviceStatus=="SUCCESS"){
          	    		$('.draftExpense').click();
          	    		$(".alert").addClass("alert-success").text(data.message).show();
          	    	}
          	    	else{
          	    		$('.draftExpense').click();
          	    		$(".alert").addClass("alert-danger").text(data.message).show();
          	    		$grid.pqGrid("rollback");
          	    	}
          	    },
          	    error:function(data) { 
          	    	$(".alert").addClass("alert-danger").text(data.message).show();
          	    }
          	    
          	});
          //}
    }
    var obj = {
        wrap: false,
        hwrap: false,
       // resizable: true,
        rowBorders: true,
        numberCell: { show: false },
        track : true, //to turn on the track changes.
        trackModel: { on: true },
        flexHeight: true,
        toolbar: {
            items: [
                { type: 'separator' },
                { type: 'button', icon: 'ui-icon-disk',attr:"value='2'", label: 'Send for Approval', cls:"pull-right", listeners: [
                    { "click": function (evt, ui) {
                        acceptChanges(this.value);
                    }
                    }
                ]
                },
				{ type: 'separator',style: 'margin:0px 0px 0px 65%' },
				{ type: 'button', icon: 'ui-icon-disk', attr:"value='1'", label: 'Save as Draft',cls:"pull-right", listeners: [
                    { "click": function (evt, ui) {
                        acceptChanges(this.value);
                    }
                    }
                ]
                },
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
                /* { type: 'button', icon: 'ui-icon-cancel', label: 'Reject Changes', listeners: [
                    { "click": function (evt, ui) {
                        $grid.pqGrid("rollback");
                    }
                    }
                ]
                } */
                
            ]
        },
         scrollModel: {
            autoFit: true
        },
        editModel: {
            saveKey: $.ui.keyCode.ENTER
        },
        title: "<b>Expense Voucher</b>",

        colModel: [
			{ title: "Modified", dataType: "boolean", dataIndx: "modified", hidden: true },
            { title: "Expense ID", dataType: "integer", dataIndx: "expenseDetailId", hidden: true },
            { title: "Location Required", dataType: "boolean", dataIndx: "locationRequired",hidden: true },
            { title: "Unit Required", dataType: "boolean", dataIndx: "unitRequired", hidden: true },
            { title: "Amount Per Unit", dataType: "integer", dataIndx: "amountPerUnit", hidden: true },
            { title: "Limit Increase", dataType: "boolean", dataIndx: "limitIncrease", hidden: true },
            { title: "Date", width: "160", dataIndx: "date",
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
                    { type: 'regexp', value: '^[0-9]{2}-[A-Za-z]{3,10}-[0-9]{4}$', msg: 'Not in dd-MMM-yyyy format' }
                ]
		    }, 
		    { title: "Expense Category Id", width: 140, dataType: "string", align: "right", dataIndx: "expenseCategoryId",hidden:true},
		    { title: "Expense Category", dataIndx: "expenseCategoryName", width: 200,
                editor: {
                    type: "textbox",
                    init: autoCompleteEditor
                    //type: function (ui) { return dropdowneditor(this, ui); }
                },
                validations: [
                    { type: 'minLen', value: 1, msg: "Required" },
                    { type: function (ui) {
                        var value = ui.value;
                        var expCatName= $.grep(expenseCategoryList, function(item){
                            return item.expenseName == value;
                        }).length;
                        
                        if (expCatName != 1) {
                            ui.msg = value + " not found in list";
                            return false;
                        } 
                    }, icon: 'ui-icon-info'
                    }
                ],
                render:function(ui){
                	for (var i = 0; i < expenseCategoryList.length; i++) {
             	        if(expenseCategoryList[i].expenseCategoryId == ui.rowData.expenseCategoryId){
                        	ui.rowData.expenseCategoryName=expenseCategoryList[i].expenseName;
                        	if(expenseCategoryList[i].locationRequired == true){
                        		ui.rowData.locationRequired=true;
                        	}
                        	else{
                        		ui.rowData.locationRequired=false;
                        	}
                        	
                        	if(expenseCategoryList[i].unitRequired== true){
                        		ui.rowData.unitRequired=true;
                        		ui.rowData.amountPerUnit= expenseCategoryList[i].amount;
                        	}
                        	else{
                        		ui.rowData.unitRequired=false;
                        	}
                         	return ""+expenseCategoryList[i].expenseName;
                        }
             	    }
                }
                
            },
            { title: "Location From", width: 140, dataType: "string", align: "left", dataIndx: "fromLocation",
            	editable: function(ui){
            		if(typeof ui.rowData != "undefined" && ui.rowData != null && ui.rowData['locationRequired'] != null){
						if(ui.rowData['locationRequired'] == true){ 
							return true;
						}
						else {
							return false;
						}
            		}
            		else{
            			//$grid.pqGrid( "addClass", {rowIndx: ui.rowIndx, dataIndx: 'fromLocation', cls: 'disabled'} );	
            			return false;
            		}
                },
                render: disableFieldRenderer
            },
            { title: "Location To", width: 140, dataType: "String", align: "left", dataIndx: "toLocation",
            	editable: function(ui){
            		if(typeof ui.rowData != "undefined" && ui.rowData != null && ui.rowData['locationRequired'] != null){
						if(ui.rowData['locationRequired'] == true){  
							return true;
						}
						else{ 
							return false;
						}
            		}
            		else{
            			return false;
            		}
                },
                render: disableFieldRenderer
            	
            },
            { title: "Description", width: 220, dataType: "String", align: "left", dataIndx: "description"},
            { title: "Unit", width: 50, dataType: "integer", align: "right", dataIndx: "unit",
               editor: {
            	   init: function(ui){
                 	   ui.$cell.change(function(){
                 		  var $inp = ui.$cell.find("input");
                 		   ui.rowData['amount']=ui.rowData['amountPerUnit'] * $inp.val();
                 		   
                 		   $grid.pqGrid( "refreshCell", { rowIndx: ui.rowIndx, dataIndx: 'amount' } );
                 	   });
                   }
               },
            	editable: function(ui){
            		if(typeof ui.rowData != "undefined"  && ui.rowData != null && ui.rowData['unitRequired'] != null){
						if(ui.rowData['unitRequired'] == true){ 
							return true;
						}
						else {
							return false;
						}
            		}
            		else{
            			return false;
            		}
                },
                render: disableFieldRenderer
            },
            { title: "Amount", width: 140, dataType: "float", align: "right", dataIndx: "amount",render:amountRenderer,
            	 validations: [
                      { type: function (ui) {
                          var value = ui.value;
                          
                          if(ui.rowData['limitIncrease'] == false){
                        	 if(ui.rowData['unitRequired'] == true){
                        		 var expectedAmmount= ui.rowData['amountPerUnit']*ui.rowData['unit'];
                        		 if(value> expectedAmmount){
                        			 ui.msg="Amount can not exceed "+expectedAmmount;
                        			 return false;
                        		 }

                        	 }
                        	 else{
                        		 var expectedAmmount= ui.rowData['amountPerUnit'];
                        		 if( ui.rowData['amountPerUnit'] != null){
	                        		 if(value> expectedAmmount){
	                        			 ui.msg="Amount can not exceed "+expectedAmmount;
	                        			 return false;
	                        		 }
                        		 }
                        	 }
                          }
                      }, icon: 'ui-icon-info'
                      }
                ],
                
                editable: function(ui){
                	debugger;
            		if(typeof ui.rowData != "undefined"  && ui.rowData != null && ui.rowData['unitRequired'] != null){
						if(ui.rowData['unitRequired'] == true && (typeof ui.rowData['unit'] != "undefined" && ui.rowData['unit'] != null) ){ 
							return true;
						}
						else if(ui.rowData['unitRequired'] == false){
							return true;
						}
						else {
							return false;
						}
            		}
            		else{
            			return true;
            		}
                },
                
            },
            { title: "Receipt/Document",editable:false, dataIndx: "receipt", minWidth: 200, sortable: false, 

            	  render:function (ui) {
            		 if(ui.cellData == ""){
            			 return "";
            		 }
            		 else if(typeof ui.cellData == "undefined" && ui.rowData.fileName == null){
            		   	return "<input type='file' class='btn_file'/>";
            		 }
            		 else{
            			 var filename;
            			 //ui.rowData.fileName=ui.cellData.val();
            			 if(typeof ui.cellData != "undefined" || ui.rowData.fileName == null){
	            			 var fullPath=ui.cellData.val();
	           			     var startIndex = (fullPath.indexOf('\\') >= 0 ? fullPath.lastIndexOf('\\') : fullPath.lastIndexOf('/'));
	           			     filename = fullPath.substring(startIndex);
	           			     if (filename.indexOf('\\') === 0 || filename.indexOf('/') === 0) {
	           			        filename = filename.substring(1);
	           			     } 
            			 }
            			 else{
            				 filename =ui.rowData.fileName;
            			 }
            			 return "<div><a href="+fullPath+">"+ filename+"</a><button type='button' style='display: inline;width:20px;height:20px' class='ui-icon ui-icon-circle-close'></button></div><input type='file' class='btn_file'/>";
            		 }  
	            }  
            },
            { title: "", editable: false, minWidth: 83, dataIndx: "delButton", sortable: false, render: function (ui) {
	            	if(ui.cellData != ""){
	                	return "<button type='button' class='delete_btn' >Delete</button>";
	            	}
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
        render : function (evt, ui) {
            $summary = $("<div class='pq-grid-summary'  ></div>")
                .prependTo($(".pq-grid-bottom", this));
            calculateSummary();
        },
        cellSave : function (evt, ui) {
        	if(ui.dataIndx == "amount" || ui.dataIndx == "unit"){
	            calculateSummary();
	            obj.refresh.call(this);
        	}
        },
       /*  cellBeforeSave: function (evt, ui) {
        	var cd = ui.newVal;
            if (cd == "") {
                return false;
            }
            
        } */
    };
    obj.refresh= function () {
	   	 $("#grid_editing").find("input.btn_file").button().bind("change", function (evt){
	   		 debugger;
	   		 var $tr = $(this).closest("tr");
	            var obj = $grid.pqGrid("getRowIndx", { $tr: $tr });
	            var rowIndx = obj.rowIndx;
	            var rowData = $grid.pqGrid("getRowData", { rowIndx: rowIndx })
	            rowData.receipt=null;
	   		 var clone = $(this).clone();
	   	     rowData.receipt = clone.attr('name', 'addedFiles');
	   		 $grid.pqGrid( "updateRow", {rowIndx: rowIndx, row: { 'modified':true},checkEditable:false} );
			 $grid.pqGrid("refresh"); 
				 
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
       calculateSummary();
       var data;
       if($("input[name='isAdvance']:checked").val() == "on"){
       		data = [totalData,settlementData]; //JSON (array of objects)
       }
       else{
    	   data = [totalData];
       }
       var obj1 = { data: data, $cont: $summary }
       $(this).pqGrid("createTable", obj1);  
   }
   
    
    $(".expenseType").change(function () {
    	 if($("input[name='expenseType']:checked").val()=="EmployeeExpense"){
    		 $(".eventTab").hide();
    	 }
    	 else{
    		 $(".eventTab").show();
    	 }
    	 $("input[name='isAdvance']:checkbox").prop('checked', false);
    	 $('#advanceSelect').val("-1");
    	 $('#advanceAmount').val("");
    	 $(".advanceTab").hide();
    	 changedAdvance();
    });
    
    $("#isAdvance").change(function () {
    	
    	var flag=false;
    	if($("input[name='isAdvance']:checked").val()=="on"){
    		$(".advanceTab").show();
    	}
    	else{
    		$(".advanceTab").hide();
    	}
    	$('#advanceSelect').empty();
    	$('#advanceSelect').append($('<option>', {
    		    value: "-1",
    		    text: "--Select Advance--",
    	}));
    	
	   	 if($("input[name='expenseType']:checked").val()=="EmployeeExpense"){
	   		for(var i=0;i<advanceList.length;i++){
		   		if(advanceList[i].isEvent == false){
		   			 $('#advanceSelect').append($('<option>', {
		   				    value: advanceList[i].advanceDetailId,
		   				    text: advanceList[i].advanceNumber,
		   			}));
	   			}
   			 }
	   	 }
	   	 else if($("input[name='expenseType']:checked").val()=="EventExpense"){
	   		 if($('#eventId').val()==-1){
	   			$("#dialog").text("Please first select event for which you filling expense.");
				$("#dialog").dialog();
				$("input[name='isAdvance']:checkbox").prop('checked', false);
		    	$(".advanceTab").hide();
	   		 }
	   		 else{
	   			for(var i=0;i<advanceList.length;i++){
	   				
			   		 if(advanceList[i].isEvent == true && advanceList[i].eventId==$('#eventId').val()){
			   			 $('#advanceSelect').append($('<option>', {
			   				    value: advanceList[i].advanceDetailId,
			   				    text: advanceList[i].advanceNumber,
			   			}));
		  			 }
	   			}
	   		 }
	   	 }
   });
    
    $("#eventId").change(function () {
    	$('#advanceSelect').empty();
    	$('#advanceSelect').append($('<option>', {
    		    value: "-1",
    		    text: "--Select Advance--",
    	}));
    	for(var i=0;i<advanceList.length;i++){
	    	if(advanceList[i].isEvent == true && advanceList[i].eventId==$('#eventId').val()){
	  			 $('#advanceSelect').append($('<option>', {
	  				    value: advanceList[i].advanceDetailId,
	  				    text: advanceList[i].advanceNumber
	  			}));
			}
    	}
   });
    
    
    
    var $grid;
    
    if($('#expenseHeaderId').val() != "" && $('#expenseHeaderId').val() != null){
    		
    	$(".expenseType").change();
    	$("#eventId").change();
    	
    	$("#isAdvance").change();
    	if($('#advanceDetailId').val() != "" && $('#advanceDetailId').val() != null){
	    	$("input[name='isAdvance']:checkbox").prop('checked', true);
    		$(".advanceTab").show();
    	}
    	$('#advanceSelect').val(""+$('#advanceDetailId').val());
    	
    	changedAdvance();
    	
	    $grid = $("#grid_editing").pqGrid(obj);
    }
    else{
    	$grid = $("#grid_editing").pqGrid(obj);
    	addNewRow(); 
    }
    
    //get instance of the grid.
     grid = $grid.pqGrid( "getInstance" ).grid; 
    
});

function changedAdvance(){
	if($('#advanceSelect').val()!=-1 || $('#advanceSelect').val()!=null){
		for(var i=0;i<advanceList.length;i++){
			if( advanceList[i].advanceDetailId==$('#advanceSelect').val()){
				 $('#advanceAmountSpan').text(advanceList[i].amount);
				 $('#advanceAmount').val(advanceList[i].amount);
				 $('#advancePurposeSpan').text(advanceList[i].purpose);
				 $('#advanceDetailId').val(""+$('#advanceSelect').val())
				 break;
			}
		}
	}
	else{
		 $('#advanceAmountSpan').text("");
		 $('#advancePurposeSpan').text("");
		 $('#advanceAmount').val("");
		 
	}
	if(typeof grid != "undefined")
		grid.refresh();
}

</script>
</head>
<body>
 	<form:form  class="" id="form" action="saveExpense" method="POST" enctype="multipart/form-data" modelAttribute="ExpenseHeaderDTO">
 	    <div class=" container" id="headerToolbar" >
			  <div class="col-xs-6">			 
	 	    	 <div class="form-group row">
					 <label class="col-sm-4"><form:radiobutton class="expenseType" path="expenseType" value="EmployeeExpense"/>Employee Expense</label>
					 <label class="col-sm-4"><form:radiobutton class="expenseType" path="expenseType" value="EventExpense"/>Event Expense</label>
				 </div>
				 <br/>
			 	 <div class="form-group row">
			        <label class="col-sm-2" for="startDate">Start Date</label>
			        <div class="col-sm-3"><form:input path="startDate" class="form-control input-sm" id="startDate"/></div>
					 
			        <label class="col-sm-2" for="endDate">End Date</label>
			        <div class="col-sm-3"><form:input path="endDate" class="form-control input-sm" id="endDate" /></div>
			    </div>
			    <br/>
			    
			    <div class="form-group row">
			       <label class="col-sm-2" for="purpose">Purpose</label>
			      <div class="col-sm-3"><form:input path="purpose" class="form-control input-sm" /></div>
			      
			      <div class="eventTab" style="display: none">
				       <label class="col-sm-1" for="eventId">Event</label>
				       <div class="col-sm-3">
					      <form:select class="form-control col-sm-4" path="eventId" >
				  			<form:option value="-1" label="--- Select Event ---" />
						  	<form:options items="${eventList}" itemValue="eventId" itemLabel="eventName"/>
						  </form:select>
					  </div>
				  </div>
			    </div>
			   </div>
			   
			   <div class="col-xs-4 panel panel-primary">
			   		<h1 class="panel-heading">Advance Details</h1> 
			   		<div class="form-group row panel-heading">
			   			<input type="checkbox" name="isAdvance" id="isAdvance" >Do you want to settle against advance?
			   		</div>
			   		<div class="advanceTab" style="display: none">
			   			<div class="form-group row">
					       <label class="col-sm-4" for="advanceSelect">Select Advance:</label>
					       <div class="col-sm-5">
						      <select class="form-control col-sm-4" onchange="changedAdvance();" id="advanceSelect" >
							  </select>
							  <form:hidden id="advanceDetailId" path="advanceDetailId"></form:hidden>  
						   </div>
					   </div>
					   <br/>
					   <div class="form-group row">
					        <label class="col-sm-2">Amount</label>
					        <div class="col-sm-2">
					        	<span id="advanceAmountSpan" style="color:blue;font-weight:bold"></span>
					        	<input type="hidden" id="advanceAmount" name="advanceAmount" class="form-control input-sm" />
					        </div>
							 
					        <label class="col-sm-2">Purpose</label>
					        <div class="col-sm-5">
					        	<span id="advancePurposeSpan" style="color:blue;font-weight:bold"></span>
					        </div>
					   </div>
				  </div>
			   </div>
 	    </div>
 	    
        <div id="grid_editing" style="margin: auto;"></div>
 		<input type="hidden" id="data" name="data"/>
 		<div id="filesDiv" style="border: medium; display: none;"></div>
 		<form:hidden id="voucherStatusId" path="voucherStatusId"></form:hidden>  
 		<form:hidden path="expenseHeaderId"></form:hidden>
 		<form:hidden path="createdDate"></form:hidden>
 		<form:hidden path="createdBy"></form:hidden>
 		<form:hidden path="voucherNumber"></form:hidden>
 		<form:hidden path="processInstanceId"></form:hidden>
    </form:form>
    
    <div id="dialog" style="display: none" title="Validation failure"></div>
</body>
</html>