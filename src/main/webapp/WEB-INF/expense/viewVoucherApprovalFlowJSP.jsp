<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<head>

<script type="text/javascript" src=<spring:url value="/drawline/jquery.html-svg-connect.js"/> ></script>
<title>View Approval Flow</title>

<script type="text/javascript">
		
$(document).ready(function() {  
	$("#svgContainer").HTMLSVGconnect({
		  paths: [
		    { start: "#level1Fun", end: "#level2Fun",orientation: "vertical"},
		    { start: "#level2Fun", end: "#level3Fun",orientation: "vertical"},
		    { start: "#level3Fun", end: "#level1Fin",orientation: "horizontal"},
		    { start: "#level1Fin", end: "#level2Fin",orientation: "vertical"},
		    { start: "#level2Fin", end: "#level3Fin",orientation: "vertical"}
		  ]
		}); 
	 /* $("#svgContainer").HTMLSVGconnect({
		  paths: [
		    { start: "#el-a", end: "#el-b"}
		  ]
	});  */
});
</script>
</head>
	<body >
	 <!-- <div id="svgContainer2"></div>
	
	<div id="el-a" class="fromContainer" style="position: absolute; top: 150px; left: 100px;">
       <p>From</p>
       <p>id = from1</p>
    </div>
    <div id="el-b" class="fromContainer" style="position: absolute; top: 150px; left: 600px;">
        <p>From</p>
        <p>id = from2</p>
    </div>  -->
	<jstl:set var="y" value="75"/>
	
	<div class="row" >
	 	<div id="svgContainer"></div>
	<jstl:set var="approvalFlow" value="${approvalFlowList[0]}"/>
		<h1 style="position: absolute; top: 5px; left: 10px;" class="center">Functional Approval</h1>
			 	
		<jstl:if test="${not empty approvalFlow.level1}">
	        <div id="level1Fun" style="position: absolute; top: ${y}px; left: 10px;" class="fromContainer col-md-3 col-sm-6 col-xs-12">
	        <div><i><span class="info-box-text">Functional 1<sup>st</sup> level approval</span></i></div>
	          <div class="info-box">
	          	<jstl:choose>
				   <jstl:when test="${approvalFlow.level1EmployeeDTO.gender.toString()=='M'}">
				        <span class="info-box-icon bg-green"><img src="icon/Male Boss.ico" alt="Smiley face" height="42" width="42"></span>
				    </jstl:when>    
				    <jstl:otherwise>
				        <span class="info-box-icon bg-green"><img src="icon/Female Boss.ico" alt="Smiley face" height="42" width="42"></span>
				    </jstl:otherwise>
				</jstl:choose>
				
	            <div class="info-box-content">
	              <span class="info-box-text-wrap">${approvalFlow.level1EmployeeDTO.fullName}</span>
	            </div>
	            <!-- /.info-box-content -->
	          </div>
	        <!-- /.col -->
	      	</div>
		</jstl:if>
		
		<!-- Level 2 -->
		<jstl:if test="${not empty approvalFlow.level2}">
		    	
	        <div id="level2Fun" style="position: absolute; top: ${y}px; left: 330px;" class="fromContainer col-md-3 col-sm-6 col-xs-12">
	        <div><i><span class="info-box-text">Functional 2<sup>nd</sup> level approval</span></i></div>
	          <div class="info-box">
	           <jstl:choose>
				   <jstl:when test="${approvalFlow.level2EmployeeDTO.gender.toString()=='M'}">

				        <span class="info-box-icon bg-gray"><img src="icon/Male Boss.ico" alt="Smiley face" height="42" width="42"></span>
				    </jstl:when>    
				    <jstl:otherwise>
				        <span class="info-box-icon bg-gray"><img src="icon/Female Boss.ico" alt="Smiley face" height="42" width="42"></span>

				        <span class="info-box-icon bg-orange"><img src="icon/Male Boss.ico" alt="Smiley face" height="42" width="42"></span>
				    </jstl:when>    
				    <jstl:otherwise>
				        <span class="info-box-icon bg-orange"><img src="icon/Female Boss.ico" alt="Smiley face" height="42" width="42"></span>
>>>>>>> branch 'master' of https://github.com/dalvi1988/ExpenseManagement.git
				    </jstl:otherwise>
				</jstl:choose>
	
	            <div class="info-box-content">
	              <span class="info-box-text">${approvalFlow.level2EmployeeDTO.fullName}</span>
	            </div>
	            <!-- /.info-box-content -->
		          </div>
	        <!-- /.col -->
	      	</div>
		</jstl:if>	      	
		
		<!-- Level 3 -->
		<jstl:if test="${not empty approvalFlow.level3}">
		    	
	        <div id="level3Fun" style="position: absolute; top: ${y}px; left: 630px;" class="fromContainer col-md-3 col-sm-6 col-xs-12">
	        	<div><i><span class="info-box-text">Functional 3<sup>rd</sup> level approval</span></i></div>
	          <div class="info-box">
	            <jstl:choose>
				   <jstl:when test="${approvalFlow.level3EmployeeDTO.gender.toString()=='M'}">
				        <span class="info-box-icon bg-gray"><img src="icon/Male Boss.ico" alt="Smiley face" height="42" width="42"></span>
				    </jstl:when>    
				    <jstl:otherwise>
				        <span class="info-box-icon bg-gray"><img src="icon/Female Boss.ico" alt="Smiley face" height="42" width="42"></span>
				    </jstl:otherwise>
				</jstl:choose>
	            <div class="info-box-content">
	              <span class="info-box-text">${approvalFlow.level3EmployeeDTO.fullName}</span>
	            </div>
	            <!-- /.info-box-content -->
	          </div>
	          <!-- /.info-box -->
	        </div>
		</jstl:if>

		<jstl:if test="${empty approvalFlow.level3}">
		    	
	        <div id="level3Fun" style="position: absolute; top: ${y}px; left: 630px;" class="fromContainer col-md-3 col-sm-6 col-xs-12">
	        	<div><i><span class="info-box-text">Functional 3<sup>rd</sup> level approval</span></i></div>
	          <div class="info-box">
				<span class="info-box-icon glyphicon glyphicon-ban-circle"></span>
	            <div class="info-box-content">
	              <span class="info-box-text">Not Applicable</span>
	              <span class="info-box-text-wrap">This level will be skip...</span>
	            </div>
	            <!-- /.info-box-content -->
	          </div>
	          <!-- /.info-box -->
	        </div>
		</jstl:if>		    	
	        
		<jstl:set var="y" value="250" />
	
	<!-- Finance Flow -->
	<jstl:set var="financeFlow" value="${approvalFlowList[1]}"/>
        <h1 style="position: absolute; top: 175px; left: 10px;" class="center">Finance Approval</h1>
			 	
		<!-- Level 3 -->
		<jstl:if test="${not empty financeFlow.level3}">
		    	
	        <div id="level3Fin" style="position: absolute; top: ${y}px; left: 10px;" class="fromContainer col-md-3 col-sm-6 col-xs-12">
	        <div><i><span class="info-box-text">Finance 3<sup>rd</sup> level approval</span></i></div>
	          <div class="info-box">
	            <jstl:choose>
				   <jstl:when test="${financeFlow.level3EmployeeDTO.gender.toString()=='M'}">
				        <span class="info-box-icon bg-gray"><img src="icon/Male Boss.ico" alt="Smiley face" height="42" width="42"></span>
				    </jstl:when>    
				    <jstl:otherwise>
				        <span class="info-box-icon bg-gray"><img src="icon/Female Boss.ico" alt="Smiley face" height="42" width="42"></span>
				    </jstl:otherwise>
				</jstl:choose>
	            <div class="info-box-content">
	              <span class="info-box-text">${financeFlow.level3EmployeeDTO.fullName}</span>
	              
	            </div>
	            <!-- /.info-box-content -->
	          </div>
	          <!-- /.info-box -->
	        </div>
		</jstl:if>
		
		<!-- Level 2 -->
		<jstl:if test="${not empty financeFlow.level2}">
		    	
	        <div id="level2Fin" style="position: absolute; top: ${y}px; left: 330px;" class="fromContainer col-md-3 col-sm-6 col-xs-12">
	        <div><i><span class="info-box-text">Finance 2<sup>nd</sup> level approval</span></i></div>
	          <div class="info-box">
	           <jstl:choose>
				   <jstl:when test="${financeFlow.level2EmployeeDTO.gender.toString()=='M'}">

				        <span class="info-box-icon bg-gray"><img src="icon/Male Boss.ico" alt="Smiley face" height="42" width="42"></span>
				    </jstl:when>    
				    <jstl:otherwise>
				        <span class="info-box-icon bg-gray"><img src="icon/Female Boss.ico" alt="Smiley face" height="42" width="42"></span>
				    </jstl:when>    
				    <jstl:otherwise>
				        <span class="info-box-icon bg-orange"><img src="icon/Female Boss.ico" alt="Smiley face" height="42" width="42"></span>
>>>>>>> branch 'master' of https://github.com/dalvi1988/ExpenseManagement.git
				    </jstl:otherwise>
				</jstl:choose>
	
	            <div class="info-box-content">
	              <span class="info-box-text">${financeFlow.level2EmployeeDTO.fullName}</span>
	            </div>
	            <!-- /.info-box-content -->
		          </div>
	        <!-- /.col -->
	      	</div>
		</jstl:if>	      	
		
		<jstl:if test="${not empty financeFlow.level1}">
	        <div id="level1Fin" style="position: absolute; top: ${y}px; left: 630px;" class="fromContainer col-md-3 col-sm-6 col-xs-12">
	        <div><i><span class="info-box-text">Finance 1<sup>st</sup> level approval</span></i></div>
	          <div class="info-box">
	          	<jstl:choose>
				   <jstl:when test="${financeFlow.level1EmployeeDTO.gender.toString()=='M'}">

				        <span class="info-box-icon bg-gray"><img src="icon/Male Boss.ico" alt="Smiley face" height="42" width="42"></span>
				    </jstl:when>    
				    <jstl:otherwise>
				        <span class="info-box-icon bg-gray"><img src="icon/Female Boss.ico" alt="Smiley face" height="42" width="42"></span>

				        <span class="info-box-icon bg-green"><img src="icon/Male Boss.ico" alt="Smiley face" height="42" width="42"></span>
				    </jstl:when>    
				    <jstl:otherwise>
				        <span class="info-box-icon bg-green"><img src="icon/Female Boss.ico" alt="Smiley face" height="42" width="42"></span>
>>>>>>> branch 'master' of https://github.com/dalvi1988/ExpenseManagement.git
				    </jstl:otherwise>
				</jstl:choose>
				
	            <div class="info-box-content">
	              <span class="info-box-text">${financeFlow.level1EmployeeDTO.fullName}</span>
	            </div>
	            <!-- /.info-box-content -->
	          </div>
	        <!-- /.col -->
	      	</div>
		</jstl:if>
		
			
    </div><!-- End Row Div -->
    
	</body>
</html>
