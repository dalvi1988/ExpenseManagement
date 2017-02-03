<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<meta content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport"/>
    <meta name="msapplication-tap-highlight" content="no" />
<head>
    <title id='Description'>JavaScript DataGrid Editing - Mobile Example
    </title>
    <link rel="stylesheet" href=<spring:url value="/jqwidgets/styles/jqx.base.css"/> type="text/css" />
    <link rel="stylesheet" href=<spring:url value="/jqwidgets/styles/jqx.windowsphone.css"/> type="text/css" />
    <link rel="stylesheet" href=<spring:url value="/jqwidgets/styles/jqx.blackberry.css"/> type="text/css" />
    <link rel="stylesheet" href=<spring:url value="/jqwidgets/styles/jqx.mobile.css"/> type="text/css" />
    <link rel="stylesheet" href=<spring:url value="/jqwidgets/styles/jqx.android.css"/> type="text/css" />
    <script type="text/javascript" src=<spring:url value="/scripts/jquery-1.11.1.min.js"/> ></script>
    <script type="text/javascript" src=<spring:url value="/jqwidgets/jqxcore.js"/> ></script>
    <script type="text/javascript" src=<spring:url value="/jqwidgets/jqxdata.js"/> ></script>
    <script type="text/javascript" src=<spring:url value="/jqwidgets/jqxbuttons.js"/> ></script>
    <script type="text/javascript" src=<spring:url value="/jqwidgets/jqxscrollbar.js"/> ></script>
    <script type="text/javascript" src=<spring:url value="/jqwidgets/jqxlistbox.js"/> ></script>
    <script type="text/javascript" src=<spring:url value="/jqwidgets/jqxdropdownlist.js"/> ></script>
    <script type="text/javascript" src=<spring:url value="/jqwidgets/jqxmenu.js"/> ></script>
    <script type="text/javascript" src=<spring:url value="/jqwidgets/jqxgrid.js"/> ></script>
    <script type="text/javascript" src=<spring:url value="/jqwidgets/jqxcalendar.js"/> ></script>
    <script type="text/javascript" src=<spring:url value="/jqwidgets/jqxdatetimeinput.js"/> ></script>
    <script type="text/javascript" src=<spring:url value="/jqwidgets/jqxcheckbox.js"/> ></script>
    <script type="text/javascript" src=<spring:url value="/jqwidgets/globalization/globalize.js"/> ></script>
    <script type="text/javascript" src=<spring:url value="/jqwidgets/jqxgrid.pager.js"/> ></script>
    <script type="text/javascript" src=<spring:url value="/jqwidgets/jqxgrid.selection.js"/> ></script>
    <script type="text/javascript" src=<spring:url value="/jqwidgets/jqxgrid.edit.js"/> ></script>
    <script type="text/javascript" src=<spring:url value="/jqwidgets/jqxgrid.sort.js"/> ></script>
    <script type="text/javascript" src=<spring:url value="/jqwidgets/jqxgrid.filter.js"/> ></script>
    <script type="text/javascript" src=<spring:url value="/jqwidgets/jqxpanel.js"/> ></script>
    <script type="text/javascript" src=<spring:url value="/demos/jqxgrid/generatedata.js"/> ></script>
    <script type="text/javascript" src=<spring:url value="/mobiledemos/simulator.js"/> ></script>
    <script type="text/javascript">
        var that = this;
        $(document).ready(function () {
            // prepares the simulator. 
            var theme = prepareSimulator("grid");
            var data = generatedata(50);
            this.editrow = -1;
         
            var source =
            {
                localdata: data,
                datatype: "array",
                updaterow: function (rowid, rowdata, commit) {
                	alert("updaterow")
                    // synchronize with the server - send update command
                    // call commit with parameter true if the synchronization with the server is successful 
                    // and with parameter false if the synchronization failder.
                    commit(true);
                },
                datafields:
                [
                    { name: 'name', type: 'string' },
                    { name: 'firstname', type: 'string' },
                    { name: 'lastname', type: 'string' },
                    { name: 'productname', type: 'string' },
                    { name: 'available', type: 'bool' },
                    { name: 'quantity', type: 'number' },
                    { name: 'price', type: 'number' },
                    { name: 'date', type: 'date' }
                ]
            };
            var dataAdapter = new $.jqx.dataAdapter(source);
            // initialize jqxGrid
            $("#grid").jqxGrid(
            {
                width: '100%',
                height: '100%',
                source: dataAdapter,
                editable: true,
                columnsheight: 40,
                columnsmenuwidth: 24,
                rowsheight: 34,
                theme: theme,
                sortable: true,
                selectionmode: 'none',
                editmode: 'selectedrow',
                columns: [
                  { text: 'Name', columntype: 'textbox', datafield: 'name', width: '30%' },
                  { text: 'Product', width: '40%', columntype: 'textbox', datafield: 'productname' },
                  {
                      text: 'Edit', align: "center", editable: false, sortable: false, datafield: 'available', cellsrenderer: function (row, column, value) {
                          var eventName = "onclick";
                          
                          if (row === that.editrow) {
                              return "<div style='text-align: center; width: 100%; top: 7px; position: relative;'><a " + eventName + "='Update(" + row + ", event)' style='color: inherit;' href='javascript:;'>Update</a><span style=''>/</span>" + "<a " + eventName + "='Cancel(" + row + ", event)' style='color: inherit;' href='javascript:;'>Cancel</a></div>";
                          }
                          return "<a " + eventName + "='Edit(" + row + ", event)' style='color: inherit; margin-left: 50%; left: -15px; top: 7px; position: relative;' href='javascript:;'>Edit</a>";
                      }
                  }
                ]
            });
            initSimulator("grid");
        });
    </script>
    <style type="text/css">
    a:link{
        font-size: 16px !important;
     }
    </style>
</head>
<body class='default'>
    <div id="demoContainer" class="device-mobile-tablet">
        <div id="container" class="device-mobile-tablet-container">
            <div id='grid'>
                <script type="text/javascript">
                    function Edit(row, event) {
                        that.editrow = row;
                        $("#grid").jqxGrid('beginrowedit', row);
                        if (event) {
                            if (event.preventDefault) {
                                event.preventDefault();
                            }
                        }
                        return false;
                    }
                    function Update(row, event) {
                        that.editrow = -1;
                        $("#grid").jqxGrid('endrowedit', row);
                        if (event) {
                            if (event.preventDefault) {
                                event.preventDefault();
                            }
                        }
                        return false;
                    }
                    function Cancel(row, event) {
                        that.editrow = -1;
                        $("#grid").jqxGrid('endrowedit', row,true);
                        if (event) {
                            if (event.preventDefault) {
                                event.preventDefault();
                            }
                        }
                        return false;
                    }
                </script>
            </div>
        </div>
    </div>
</body>
</html>