var ajaxObj = {
    dataType: "JSON",
    async: true,
    beforeSend: function (xhr, setting) {
 	   xhr.setRequestHeader("Accept", "application/json");
       xhr.setRequestHeader("Content-Type", "application/json");
       this.pqGrid("showLoading");
    },
    complete: function () {
        this.pqGrid("hideLoading");
    },
    error: function () {
        this.pqGrid("rollback");
        this.pqGrid("hideLoading");
    }
};

function amountRenderer(ui) {
    var grid = $(this).pqGrid('getInstance').grid,
        rowData = ui.rowData,
        rowIndx = ui.rowIndx,
        dataIndx = ui.dataIndx;

    var cellData = ui.cellData;
    if (cellData != null) {
        return "&#8377;" + parseFloat(ui.cellData).toFixed(2);
    }
    else {
        return "";
    }
};



function voucherStatusRenderer(ui) {
    var grid = $(this).pqGrid('getInstance').grid,
        rowData = ui.rowData,
        rowIndx = ui.rowIndx,
        dataIndx = ui.dataIndx;

    var cellData = ui.cellData;
    if (cellData != null) {
        return ui.cellData +"</br>"+ rowData.voucherStatusDTO;
    }
    else {
        return "";
    }
};


function pqDatePicker(ui) {
    var $this = $(this);
    $this
        .css({ zIndex: 3, position: "relative" })
        .datepicker({
            yearRange: "-20:+0", //20 years prior to present.
            changeYear: true,
            changeMonth: true,
            dateFormat: "dd-MM-yy",
            //showButtonPanel: true,
            onClose: function (evt, ui) {
                $(this).focus();
            }
        });
    //default From date
    $this.filter(".pq-from").datepicker("option", "defaultDate", "Now");
    //default To date
    $this.filter(".pq-to").datepicker("option", "defaultDate", "Now");
} 


