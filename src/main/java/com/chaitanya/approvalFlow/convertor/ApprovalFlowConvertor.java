package com.chaitanya.approvalFlow.convertor;

import java.text.ParseException;

import com.chaitanya.approvalFlow.model.ApprovalFlowDTO;
import com.chaitanya.branch.model.BranchDTO;
import com.chaitanya.department.model.DepartmentDTO;
import com.chaitanya.jpa.ApprovalFlowJPA;
import com.chaitanya.jpa.BranchJPA;
import com.chaitanya.jpa.DepartmentJPA;
import com.chaitanya.utility.Convertor;
import com.chaitanya.utility.Validation;

public class ApprovalFlowConvertor {
	
	public static ApprovalFlowDTO setApprovalFlowJPAToDTO(ApprovalFlowJPA approvalFlowJPA){
		ApprovalFlowDTO approvalFlowDTO=null;
		if(Validation.validateForNullObject(approvalFlowJPA)){
			approvalFlowDTO=new ApprovalFlowDTO(); 
			
			approvalFlowDTO.setFlowId(approvalFlowJPA.getFlowId());
			
			approvalFlowDTO.setIsBranchFlow(Convertor.convetStatusToBool(approvalFlowJPA.getIsBranchFlow()));
			
			BranchDTO branchDTO= new BranchDTO();
			branchDTO.setBranchId(approvalFlowJPA.getBranchJPA().getBranchId());
			approvalFlowDTO.setBranchDTO(branchDTO);
			
			if(Validation.validateForNullObject(approvalFlowJPA.getDepartmentJPA())){
				DepartmentDTO departmentDTO= new DepartmentDTO();
				departmentDTO.setDepartmentId(approvalFlowJPA.getDepartmentJPA().getDepartmentId());
				approvalFlowDTO.setDepartmentDTO(departmentDTO);
			}
			
			approvalFlowDTO.setNoOfLevel(approvalFlowJPA.getNoOfLevel());
			
			approvalFlowDTO.setLevel1(approvalFlowJPA.getLevel1());
			
			if(Validation.validateForZero(approvalFlowJPA.getLevel2())){
				approvalFlowDTO.setLevel2(approvalFlowJPA.getLevel2());
			}
			
			if(Validation.validateForZero(approvalFlowJPA.getLevel3())){
				approvalFlowDTO.setLevel3(approvalFlowJPA.getLevel3());
			}
			
			if(Validation.validateForNullObject(approvalFlowJPA.getCreatedBy())){
				approvalFlowDTO.setCreatedBy(approvalFlowJPA.getCreatedBy());
			}
			if(Validation.validateForNullObject(approvalFlowJPA.getModifiedBy())){
				approvalFlowDTO.setModifiedBy(approvalFlowJPA.getModifiedBy());
			}
			if(Validation.validateForNullObject(approvalFlowJPA.getCreatedDate())){
				approvalFlowDTO.setCreatedDate(Convertor.calendartoString(approvalFlowJPA.getCreatedDate()));
			}
			if(Validation.validateForNullObject(approvalFlowJPA.getModifiedDate())){
				approvalFlowDTO.setModifiedDate(Convertor.calendartoString(approvalFlowJPA.getModifiedDate()));
			}
			approvalFlowDTO.setStatus(Convertor.convetStatusToBool(approvalFlowJPA.getStatus()));
			
			
		}
		return approvalFlowDTO;
	}
	
	
	public static ApprovalFlowJPA setApprovalFlowDTOToJPA(ApprovalFlowDTO approvalFlowDTO) throws ParseException
	{
		ApprovalFlowJPA approvalFlowJPA=null;
		if(Validation.validateForNullObject(approvalFlowDTO)){
			approvalFlowJPA=new ApprovalFlowJPA();
			
			if(Validation.validateForNullObject(approvalFlowDTO.getFlowId())){
				approvalFlowJPA.setFlowId(approvalFlowDTO.getFlowId());
			}
			
			approvalFlowJPA.setIsBranchFlow(Convertor.convertStatusToChar(approvalFlowDTO.getIsBranchFlow()));
			
			BranchJPA branchJPA=new BranchJPA();
			branchJPA.setBranchId(approvalFlowDTO.getBranchDTO().getBranchId());
			approvalFlowJPA.setBranchJPA(branchJPA);

			if(Validation.validateForNullObject(approvalFlowDTO.getDepartmentDTO())){
				DepartmentJPA departmentJPA= new DepartmentJPA();
				departmentJPA.setDepartmentId(approvalFlowDTO.getDepartmentDTO().getDepartmentId());
				approvalFlowJPA.setDepartmentJPA(departmentJPA);
			}
			
			approvalFlowJPA.setNoOfLevel(approvalFlowDTO.getNoOfLevel());
			
			approvalFlowJPA.setLevel1(approvalFlowDTO.getLevel1());
			
			if(Validation.validateForZero(approvalFlowDTO.getLevel2())){
				approvalFlowJPA.setLevel2(approvalFlowDTO.getLevel2());
			}
			
			if(Validation.validateForZero(approvalFlowDTO.getLevel3())){
				approvalFlowJPA.setLevel3(approvalFlowDTO.getLevel3());
			}
			
			if(Validation.validateForZero(approvalFlowDTO.getModifiedBy())){
				approvalFlowJPA.setModifiedBy(approvalFlowDTO.getModifiedBy());
			}
			if(Validation.validateForZero(approvalFlowDTO.getCreatedBy())){
				approvalFlowJPA.setCreatedBy(approvalFlowDTO.getCreatedBy());
			}
			if(Validation.validateForNullObject(approvalFlowDTO.getCreatedDate())){
				approvalFlowJPA.setCreatedDate(Convertor.stringToCalendar(approvalFlowDTO.getCreatedDate()));
			}
			if(Validation.validateForNullObject(approvalFlowDTO.getModifiedDate())){
				approvalFlowJPA.setModifiedDate(Convertor.stringToCalendar(approvalFlowDTO.getModifiedDate()));
			}
			
			approvalFlowJPA.setStatus(Convertor.convertStatusToChar(approvalFlowDTO.getStatus()));
		}
		return approvalFlowJPA;
	}
}
