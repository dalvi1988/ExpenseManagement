<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<head>

<title>View Approval Flow</title>

<script type="text/javascript">
		

</script>
</head>
	<body>
		<fieldset>
	      
	      
		

			
		
		<jstl:forEach var="approvalFlow" items="${approvalFlowList}">
		 	<div class="row">
			<jstl:if test="${not empty approvalFlow.level1}">
			    	
		        <div class="col-md-3 col-sm-6 col-xs-12">
		          <div class="info-box">
		          	<jstl:choose>
					   <jstl:when test="${approvalFlow.level1EmployeeDTO.gender.toString()=='M'}">
					        <span class="info-box-icon bg-aqua"><img src="icon/Male Boss.ico" alt="Smiley face" height="42" width="42"></span>
					    </jstl:when>    
					    <jstl:otherwise>
					        <span class="info-box-icon bg-aqua"><img src="icon/Female Boss.ico" alt="Smiley face" height="42" width="42"></span>
					    </jstl:otherwise>
					</jstl:choose>
					
		            <div class="info-box-content">
		              <span class="info-box-text">${approvalFlow.level1EmployeeDTO.fullName}</span>
		              <span class="info-box-number">90<small>%</small></span>
		            </div>
		            <!-- /.info-box-content -->
		          </div>
		        <!-- /.col -->
		      	</div>
			</jstl:if>
			
			<!-- Level 2 -->
			<jstl:if test="${not empty approvalFlow.level2}">
			    	
		        <div class="col-md-3 col-sm-6 col-xs-12">
		          <div class="info-box">
		           <jstl:choose>
					   <jstl:when test="${approvalFlow.level2EmployeeDTO.gender.toString()=='M'}">
					        <span class="info-box-icon bg-aqua"><img src="icon/Male Boss.ico" alt="Smiley face" height="42" width="42"></span>
					    </jstl:when>    
					    <jstl:otherwise>
					        <span class="info-box-icon bg-aqua"><img src="icon/Female Boss.ico" alt="Smiley face" height="42" width="42"></span>
					    </jstl:otherwise>
					</jstl:choose>
		
		            <div class="info-box-content">
		              <span class="info-box-text">${approvalFlow.level2EmployeeDTO.fullName}</span>
		              <span class="info-box-number">91<small>%</small></span>
		            </div>
		            <!-- /.info-box-content -->
			          </div>
		        <!-- /.col -->
		      	</div>
			</jstl:if>	      	
			
			<!-- Level 3 -->
			<jstl:if test="${not empty approvalFlow.level3}">
			    	
		        <div class="col-md-3 col-sm-6 col-xs-12">
		          <div class="info-box">
		            <jstl:choose>
					   <jstl:when test="${approvalFlow.level3EmployeeDTO.gender.toString()=='M'}">
					        <span class="info-box-icon bg-aqua"><img src="icon/Male Boss.ico" alt="Smiley face" height="42" width="42"></span>
					    </jstl:when>    
					    <jstl:otherwise>
					        <span class="info-box-icon bg-aqua"><img src="icon/Female Boss.ico" alt="Smiley face" height="42" width="42"></span>
					    </jstl:otherwise>
					</jstl:choose>
		            <div class="info-box-content">
		              <span class="info-box-text">${approvalFlow.level3EmployeeDTO.fullName}</span>
		              <span class="info-box-number">93<small>%</small></span>
		            </div>
		            <!-- /.info-box-content -->
		          </div>
		          <!-- /.info-box -->
		        </div>
			</jstl:if>
			</div>
	    </jstl:forEach>
	    </fieldset>
	</body>
</html>