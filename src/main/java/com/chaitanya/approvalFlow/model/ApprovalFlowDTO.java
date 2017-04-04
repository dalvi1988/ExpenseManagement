package com.chaitanya.approvalFlow.model;

import com.chaitanya.base.BaseDTO;
import com.chaitanya.branch.model.BranchDTO;
import com.chaitanya.department.model.DepartmentDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;


public class ApprovalFlowDTO extends BaseDTO {
	
	private static final long serialVersionUID = 1L;

	private Long flowId;
	
	private String flowType;
	
	private Boolean isBranchFlow;
	
	private Long departmentId;
	@JsonIgnore
	private DepartmentDTO departmentDTO;
	
	private Long branchId;
	@JsonIgnore
	private BranchDTO branchDTO;
	
	private Integer noOfLevel;
	
	private Long level1,level2,level3;
	
	public Long getFlowId() {
		return flowId;
	}

	public void setFlowId(Long flowId) {
		this.flowId = flowId;
	}

	public DepartmentDTO getDepartmentDTO() {
		return departmentDTO;
	}

	public void setDepartmentDTO(DepartmentDTO departmentDTO) {
		this.departmentDTO = departmentDTO;
		this.departmentId=this.departmentDTO.getDepartmentId();
	}

	public BranchDTO getBranchDTO() {
		return branchDTO;
	}

	public void setBranchDTO(BranchDTO branchhDTO) {
		this.branchDTO = branchhDTO;
		this.branchId =branchhDTO.getBranchId();
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
		DepartmentDTO deptDTO= new DepartmentDTO();
		deptDTO.setDepartmentId(departmentId);
		this.departmentDTO=deptDTO;
	}

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
		BranchDTO branchDTO= new BranchDTO();
		branchDTO.setBranchId(branchId);
		this.branchDTO=branchDTO;
	}

	public Integer getNoOfLevel() {
		return noOfLevel;
	}

	public void setNoOfLevel(Integer noOfLevel) {
		this.noOfLevel = noOfLevel;
	}

	public Long getLevel1() {
		return level1;
	}

	public void setLevel1(Long level1) {
		this.level1 = level1;
	}

	public Long getLevel2() {
		return level2;
	}

	public void setLevel2(Long level2) {
		this.level2 = level2;
	}

	public Long getLevel3() {
		return level3;
	}

	public void setLevel3(Long level3) {
		this.level3 = level3;
	}

	public Boolean getIsBranchFlow() {
		return isBranchFlow;
	}

	public void setIsBranchFlow(Boolean isBranchhFlow) {
		this.isBranchFlow = isBranchhFlow;
	}

	public String getFlowType() {
		return flowType;
	}

	public void setFlowType(String flowType) {
		this.flowType = flowType;
	}

}
