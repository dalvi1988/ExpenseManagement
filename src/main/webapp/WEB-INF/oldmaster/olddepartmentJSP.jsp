<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<head>
    <title>Department Master</title>
    <link rel="stylesheet" href=<spring:url value="/jqwidgets/styles/jqx.base.css"/> type="text/css" />
    <script type="text/javascript" src=<spring:url value="/scripts/jquery-1.11.1.min.js"/> ></script>
    <script type="text/javascript" src=<spring:url value="/jqwidgets/jqxcore.js"/> ></script>
    <script type="text/javascript" src=<spring:url value="/jqwidgets/jqxdata.js"/>></script> 
    <script type="text/javascript" src=<spring:url value="/jqwidgets/jqxbuttons.js"/>></script>
    <script type="text/javascript" src=<spring:url value="/jqwidgets/jqxtooltip.js"/>></script>
    <script type="text/javascript" src=<spring:url value="/jqwidgets/jqxscrollbar.js"/>></script>
    <script type="text/javascript" src=<spring:url value="/jqwidgets/jqxmenu.js"/>></script>
    <script type="text/javascript" src=<spring:url value="/jqwidgets/jqxgrid.js"/>></script>
    <script type="text/javascript" src=<spring:url value="/jqwidgets/jqxgrid.edit.js"/>></script>  
    <script type="text/javascript" src=<spring:url value="/jqwidgets/jqxgrid.selection.js"/>></script> 
    <script type="text/javascript" src=<spring:url value="/jqwidgets/jqxlistbox.js"/>></script>
    <script type="text/javascript" src=<spring:url value="/jqwidgets/jqxdropdownlist.js"/>></script>
    <script type="text/javascript" src=<spring:url value="/jqwidgets/jqxcheckbox.js"/>></script>
    <script type="text/javascript" src=<spring:url value="/jqwidgets/jqxgrid.filter.js"/>></script>
    <script type="text/javascript" src=<spring:url value="/jqwidgets/jqxgrid.sort.js"/>></script>
    <script type="text/javascript" src=<spring:url value="/jqwidgets/jqxgrid.pager.js"/>></script>
    <script type="text/javascript" src=<spring:url value="/jqwidgets/globalization/globalize.js"/>></script>
    <script type="text/javascript" src=<spring:url value="/scripts/demos.js"/>></script>
    <script type="text/javascript">
    var that = this;
    var flag =false;
        $(document).ready(function () {
            // prepare the data
            var data = '${departmentList}';
            alert("data"+data)
            var source =
            {
                localdata: data,
                datatype: "json",
                updaterow: function (rowid, rowData, commit) {
                	if(flag==true){
	                	var jsonToBeSend=new Object();
	                	//"{\"departmentId\":"+rowData.departmentId+",\"departmentName\":\""+rowData.departmentName+"\",\"createdBy\":1,\"modifiedBy\":1,\"status\":"+rowData.status+"}";
	                	if (rowData.departmentId != "" || rowData.departmentId != null){
	                        jsonToBeSend["departmentId"] = rowData.departmentId;
	                       }
	                       jsonToBeSend["departmentName"] = rowData.departmentName;
	                       jsonToBeSend["departmentCode"] = rowData.departmentCode;
	                       jsonToBeSend["createdDate"] = rowData.createdDate;
	                       jsonToBeSend["modifiedDate"] = rowData.modifiedDate;
	                       jsonToBeSend["createdBy"] = rowData.createdBy;
	                       jsonToBeSend["modifiedBy"] = rowData.modifiedBy;
	                       jsonToBeSend["status"] = rowData.status;
	                       
			                	$.ajax({ 
			                	    url: "/BusinessReimbursment/addDepartment", 
			                	    type: 'POST', 
			                	    dataType: 'json', 
			                	    data: JSON.stringify(jsonToBeSend),
			                	    async: false,
			                	    beforeSend: function(xhr) {                 
			                            xhr.setRequestHeader("Accept", "application/json");
			                            xhr.setRequestHeader("Content-Type", "application/json");
			                        },
			                	    success: function(data) { 
			                	    	if(data.serviceStatus=="SUCCESS"){
				                	    	rowData.departmentId=data.departmentId;
				                	        commit(true);
			                	    	}
			                	    	else{
			                	    		alert("else")
			                	    		Edit(that.editrow, "onclick")
			                	    	}
			                	    },
			                	    error:function(data) { 
			                	       alert("Error!!! Please contact system administrator.");
			                	       $("#jqxgrid").jqxGrid('beginrowedit', rowid);
			                	       commit(false);
			                	    }
			                	});
                	}
                	else
                	{
                		commit(false);
                	}
                },
                dataFields: [
                             { name: 'departmentId', type: 'int' },
                             { name: 'departmentName', type: 'string' },
                             { name: 'departmentCode', type: 'string' },
                             { name: 'createdDate', type: 'string' },,
                             { name: 'modifiedDate', type: 'string' },
                             { name: 'status', type: 'bool' }
                         ]
            };
            this.editrow = -1;
            var dataAdapter = new $.jqx.dataAdapter(source, {
                loadComplete: function () {
                    // data is loaded.
                }
            });
            
            var out = false;
            // initialize jqxGrid
            $("#jqxgrid").jqxGrid(
            {
            	width: '70%',
                height: '70%',
                source: dataAdapter,
                editable: true,
                showfilterrow: true,
                filterable: true,
                rowsheight: 34,
                selectionmode: 'none',
                editmode: 'selectedrow',
                altRows: true,
                showToolbar: true,
                sortable: true,
                pageable: true,
                renderToolbar: function(toolBar)
                {
                    var toTheme = function (className) {
                        if (theme == "") return className;
                        return className + " " + className + "-" + theme;
                    }
                    // appends buttons to the status bar.
                    var container = $("<div style='overflow: hidden; position: relative; height: 100%; width: 100%;'></div>");
                    var buttonTemplate = "<div style='float: left; padding: 3px; margin: 2px;'><div style='margin: 4px; width: 16px; height: 16px;'></div></div>";
                    var addButton = $(buttonTemplate);
                    container.append(addButton);
                    toolBar.append(container);
                    addButton.jqxButton({cursor: "pointer", enableDefault: false,  height: 25, width: 25 });
                    addButton.find('div:first').addClass(toTheme('jqx-icon-plus'));
                    addButton.jqxTooltip({ position: 'bottom', content: "Add"});
                  
                    addButton.click(function (event) {
                        if (!addButton.jqxButton('disabled')) {
                        	that.editrow = 0;
                            // add new empty row.
                            $("#jqxgrid").jqxGrid('addRow', null, {}, 'first');
                            // select the first row and clear the selection.
                            $("#jqxgrid").jqxGrid('clearSelection');
                            $("#jqxgrid").jqxGrid('selectRow', 0);
                            // edit the new row.
                            $("#jqxgrid").jqxGrid('beginRowEdit', 0);
                            //updateButtons('add');
                            
                        }
                    });
                },
                pagerButtonsCount: 8,
                columns: [
                  { text: 'Department Id', columntype: 'textbox', editable:false, datafield: 'departmentId', width: "20%" },
                  { text: 'Department Name', datafield: 'departmentName', columntype: 'textbox', width: "20%" },
                  { text: 'Department Code', datafield: 'departmentCode', columntype: 'textbox', width: "20%" },
                  { text: 'Status', datafield: 'status', filtertype: 'checkedlist', filteritems: ['Active', 'Inactive'], columntype: 'custom',width: "20%",
                	  cellsrenderer: function (row, column, value) {
                		  if(value==true){
                			  return "Active";
                		  }
                		  else{
                			  return "Inactive";
                		  }
                	  },
                	  createEditor: function (row, cellvalue, editor, cellText, width, height) {
                          // construct the editor. 
                          var element = $('<div tabIndex=0 style="position: absolute; top: 50%; left: 50%; margin-top: -7px; margin-left: -10px;"></div>');
                          editor.append(element);
                          element.jqxCheckBox({
                              animationShowDelay: 0,
                              animationHideDelay: 0,
                              width: 16,
                              height: 16
                          });
                      },
                      initEditor: function (row, cellvalue, editor, celltext, width, height) {
                          // set the editor's current value. The callback is called each time the editor is displayed.					   
                          var checkBoxHTMLElement = editor.find('div:first');
                          checkBoxHTMLElement.jqxCheckBox({
                              checked: cellvalue.toString() == "true"
                          });
                      },
                      getEditorValue: function (row, cellvalue, editor) {
                          // return the editor's value.
                          var checkBoxHTMLElement = editor.find('div:first');
                          return checkBoxHTMLElement.val();
                      }
                  },
                  {
                	  text: 'Edit',filterable:false, align: "center", editable: false, sortable: false, datafield: '', cellsrenderer: function (row, column, value) {
                      var eventName = "onclick";
                      if ((row === that.editrow) && out === false){
                          //return "<div style='text-align: center; width: 100%; top: 7px; position: relative;'><a " + eventName + "='Update(" + row + ", event)' style='color: inherit;' href='javascript:;'>Update</a><span style=''>/</span>" + "<a " + eventName + "='Cancel(" + row + ", event)' style='color: inherit;' href='javascript:;'>Cancel</a></div>";
                    	  return "<button " + eventName + "='Update(" + row + ", event)' style='color: inherit; margin-left: 40%; left: -15px; top: 7px; position: relative;' data-row='" + row + "'>Update</button><button " + eventName + "='Cancel(" + row + ", event)' style='margin-left: 5px; left: -15px; top: 7px; position: relative;' data-row='" + row + "'>Cancel</button>";
                      }
                      //return "<a " + eventName + "='Edit(" + row + ", event)' style='color: inherit; margin-left: 50%; left: -15px; top: 7px; position: relative;' href='javascript:;'>Edit</a>";
                      return "<button "+ eventName + "='Edit(" + row + ", event)' style='color: inherit; margin-left: 50%; left: -15px; top: 7px; position: relative;' data-row='" + row + "' class='editButtons'>Edit</button>";
	                  }
                  }
                ]
            });
            
            $("#jqxgrid").click(function (event) {
                that.editrow = -1;
                event.stopPropagation();
            });

            $(document).click(function () {
                out = true;
                if (that.editrow !== undefined) {
                    $("#jqxgrid").jqxGrid('beginrowedit', that.editrow);
                    $("#jqxgrid").jqxGrid('endrowedit', that.editrow, true);
                }
                that.editrow = -1;
                out = false;
            });
            
        });
    </script>
    <script type="text/javascript">
				    function disableFiltering(disable) {
				        var columns = $('#jqxgrid').jqxGrid('columns').records;
				        for (var i = 0; i < columns.length - 1; i++) {
				            $('#jqxgrid').jqxGrid('setcolumnproperty', columns[i].datafield, 'filterable', !disable);
				        }
				    }
                    function Edit(row, event) {
                    	alert("edit"+row)
                        that.editrow = row;
                        $("#jqxgrid").jqxGrid('beginrowedit', row);
                        if (event) {
                            if (event.preventDefault) {
                                event.preventDefault();
                            }
                        }
                        return false;
                    }
                    function Update(row, event) {
                    	flag=true;
                        that.editrow = -1;
                        $("#jqxgrid").jqxGrid('endrowedit', row);
                        if (event) {
                            if (event.preventDefault) {
                                event.preventDefault();
                            }
                        }
                        return false;
                    }
                    function Cancel(row, event) {
                    	flag=false;
                        that.editrow = -1;
                        $("#jqxgrid").jqxGrid('endrowedit', row, true);
                        var value = $("#jqxgrid").jqxGrid('getcellvalue', row, 'departmentId');
                        if (value == null) {
                            var id = $('#jqxgrid').jqxGrid('getrowid', row);
                            $("#jqxgrid").jqxGrid('deleterow', id);

                        }
                        if (event) {
                            if (event.preventDefault) {
                                event.preventDefault();
                            }
                        }
                        return false;
                    }
                </script>
</head>
<body class='default'>
        <div id="jqxgrid"></div>
       
</body>
</html>