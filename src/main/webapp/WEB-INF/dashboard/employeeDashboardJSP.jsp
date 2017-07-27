<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<head>

<title>Expense Management System</title>
<style type="text/css">

canvas {
  border: 1px dotted red;
}

.chart-container {
  position: relative;
  margin: auto;
  height: 80vh;
  width: 80vw;
}

</style>
</head>
<body>
       <h2> Expense Voucher</h2>
       <div class="row">
        <div class="col-lg-3 col-xs-6">
          <!-- small box -->
          <div class="small-box bg-aqua">
            <div class="inner">
              <h4>Draft Expense</h4>

              <h1>150</h1>
            </div>
            <div class="icon">
              <i class="ion ion-android-drafts"></i>
            </div>
            <a href="#" class="small-box-footer draftExpense">More info <i class="fa fa-arrow-circle-right"></i></a>
          </div>
        </div>
        <!-- ./col -->
        <div class="col-lg-3 col-xs-6">
          <!-- small box -->
          <div class="small-box bg-green">
            <div class="inner">
              <h4>Paid Expense</h4>

              <h1>120</h1>
            </div>
            <div class="icon">
              <i class="ion ion-checkmark-round"></i>
            </div>
            <a href="#" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a>
          </div>
        </div>
        <!-- ./col -->
        <div class="col-lg-3 col-xs-6">
          <!-- small box -->
          <div class="small-box bg-yellow">
            <div class="inner">
              <h4>Pending Expense</h4>

              <h1>120</h1>
            </div>
            <div class="icon">
              <i class="ion ion-person"></i>
            </div>
            <a href="#" class="small-box-footer pendingExpense">More info <i class="fa fa-arrow-circle-right"></i></a>
          </div>
        </div>
        <!-- ./col -->
        <div class="col-lg-3 col-xs-6">
          <!-- small box -->
          <div class="small-box bg-red">
            <div class="inner">
              <h4>Rejected Expense</h4>

              <h1>120</h1>
            </div>
            <div class="icon">
              <i class="ion ion-android-cancel"></i>
            </div>
            <a href="#" class="small-box-footer rejectedExpense">More info <i class="fa fa-arrow-circle-right"></i></a>
          </div>
        </div>
        <!-- ./col -->
      </div>
      
       <h2> Advance Voucher</h2>
       <div class="row">
        <div class="col-lg-3 col-xs-6">
          <!-- small box -->
          <div class="small-box bg-aqua">
            <div class="inner">
              <h4>Draft Advance</h4>

              <h1>150</h1>
            </div>
            <div class="icon">
              <i class="ion ion-android-drafts"></i>
            </div>
            <a href="#" class="small-box-footer draftExpense">More info <i class="fa fa-arrow-circle-right"></i></a>
          </div>
        </div>
        <!-- ./col -->
        <div class="col-lg-3 col-xs-6">
          <!-- small box -->
          <div class="small-box bg-green">
            <div class="inner">
              <h4>Paid Advance</h4>

              <h1>120</h1>
            </div>
            <div class="icon">
              <i class="ion ion-checkmark-round"></i>
            </div>
            <a href="#" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a>
          </div>
        </div>
        <!-- ./col -->
        <div class="col-lg-3 col-xs-6">
          <!-- small box -->
          <div class="small-box bg-yellow">
            <div class="inner">
              <h4>Pending Advance</h4>

              <h1>120</h1>
            </div>
            <div class="icon">
              <i class="ion ion-person"></i>
            </div>
            <a href="#" class="small-box-footer pendingExpense">More info <i class="fa fa-arrow-circle-right"></i></a>
          </div>
        </div>
        <!-- ./col -->
        <div class="col-lg-3 col-xs-6">
          <!-- small box -->
          <div class="small-box bg-red">
            <div class="inner">
              <h4>Rejected Advance</h4>

              <h1>120</h1>
            </div>
            <div class="icon">
              <i class="ion ion-android-cancel"></i>
            </div>
            <a href="#" class="small-box-footer rejectedExpense">More info <i class="fa fa-arrow-circle-right"></i></a>
          </div>
        </div>
        <!-- ./col -->
      </div>
      
       <!-- /.col (LEFT) -->
        <div class="col-md-6">
          <!-- LINE CHART -->
          <div class="box box-info">
            <div class="box-header with-border">
              <h3 class="box-title">Line Chart</h3>

              <div class="box-tools pull-right">
                <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                </button>
                <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
              </div>
            </div>
            <div class="box-body">
              <div class="chart">
                <canvas id="lineChart" style="height:250px"></canvas>
              </div>
            </div>
            <!-- /.box-body -->
          </div>
          <!-- /.box -->

          <!-- BAR CHART -->
          <div class="box box-success">
            <div class="box-header with-border">
              <h3 class="box-title">Bar Chart</h3>

              <div class="box-tools pull-right">
                <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                </button>
                <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
              </div>
            </div>
            <div class="box-body">
              <div class="chart">
                <canvas id="barChart" style="height:230px"></canvas>
              </div>
            </div>
            <!-- /.box-body -->
          </div>
          <!-- /.box -->

        </div>
      
       
</body>

<script>

$(document).ready(function(){
	var dashboardDTOList= ${dashboardDTOList}
	var label = Object.keys(dashboardDTOList).map(function(k) { return dashboardDTOList[k].label });
	var amount = Object.keys(dashboardDTOList).map(function(k) { return dashboardDTOList[k].amount });
	
	var data = {
			  labels: label,
			  datasets: [{
			    label: "Total Approved Amount Per Month",
			    backgroundColor: "rgba(255,99,132,0.2)",
			    borderColor: "rgba(255,99,132,1)",
			    borderWidth: 2,
			    hoverBackgroundColor: "rgba(255,99,132,0.4)",
			    hoverBorderColor: "rgba(255,99,132,1)",
			    data: amount,
			  }]
			};

			var options = {
			  maintainAspectRatio: false,
			  scales: {
			    yAxes: [{
			      stacked: true,
			      gridLines: {
			        display: true,
			        color: "rgba(255,99,132,0.2)"
			      }
			    }],
			    xAxes: [{
			      gridLines: {
			        display: false
			      }
			    }]
			  }
			};

			Chart.Bar('barChart', {
			  options: options,
			  data: data
			});

	
  });

</script>
</html>