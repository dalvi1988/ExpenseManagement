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


function viewApprovalFlowRenderer(ui) {
    var grid = $(this).pqGrid('getInstance').grid,
        rowData = ui.rowData,
        rowIndx = ui.rowIndx,
        dataIndx = ui.dataIndx;

    return "<a href='javascript:showApprovalFlow("+ui.cellData+","+rowData.expenseHeaderId+");'>View Approval Flow</a>";
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
    else if(rowData.voucherStatusDTO != null){
    	if(rowData.voucherStatusDTO =="Completely Approved"){
    		return rowData.voucherStatusDTO +"</br>"+ "Pending At Payment Desk";
    	}
    	return rowData.voucherStatusDTO;
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

function activeInactiveMenu(obj) {
    var activeTab = $(obj).attr("class");
    if(typeof activeTab != "undefined" ){
   	 $("ul.sidebar-menu li").removeClass("active"); //Remove any "active" class
        $(obj).parent().addClass("active"); //Add "active" class to selected tab
    	$('.overlay').show();
    	loadPage(activeTab);
    }
      /* $(".tab_content").hide(); //Hide all tab content
    var activeTab = $(this).find("a").attr("href"); //Find the rel attribute value to identify the active tab + content
    $(activeTab).fadeIn();  //Fade in the active content
    //return false;  */
}; 

function loadPage(pageName){
	$('.content').load(pageName,function( response, status, xhr ) {
    	$('.overlay').hide();
	});
}

