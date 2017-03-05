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
    }
};