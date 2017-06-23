package com.chaitanya.expense.model;

import java.util.List;

import com.chaitanya.advance.model.AdvanceDTO;
import com.chaitanya.base.BaseDTO;
import com.chaitanya.employee.model.EmployeeDTO;
import com.chaitanya.event.model.EventDTO;
import com.chaitanya.utility.Validation;
import com.chaitanya.utility.model.VoucherStatusDTO;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

public class ExpenseHeaderDTO extends BaseDTO{

	private static final long serialVersionUID = 1L;
	
	private Long expenseHeaderId;
	
	private String expenseType; 
	
	private String voucherNumber;
	
	private Double totalAmount;
	
	private Integer voucherStatusId;
	@JsonIgnore
	private VoucherStatusDTO voucherStatusDTO;

	private Integer eventId;
	
	@JsonProperty(access = Access.READ_ONLY)
	@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="eventName")
	@JsonIdentityReference(alwaysAsId=true)
	private EventDTO eventDTO;
	
	private String startDate;
	
	private String endDate;
	
	private Long advanceDetailId;
	
	@JsonProperty(access = Access.READ_ONLY)
	@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="advanceNumber")
	@JsonIdentityReference(alwaysAsId=true)
	private AdvanceDTO advanceDTO;
	
	private String rejectionComment;
	
	private String purpose;
	
	@JsonProperty(access = Access.READ_ONLY)
	@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="fullName")
	@JsonIdentityReference(alwaysAsId=true)
	private EmployeeDTO employeeDTO;
	
	@JsonProperty(access = Access.READ_ONLY)
	@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="fullName")
	@JsonIdentityReference(alwaysAsId=true)
	private EmployeeDTO pendingAtEmployeeDTO;
	
	@JsonProperty(access = Access.READ_ONLY)
	@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="fullName")
	@JsonIdentityReference(alwaysAsId=true)
	private EmployeeDTO processedByEmployeeDTO;
	
	private List<ExpenseDetailDTO> addedExpenseDetailsDTOList;
	
	private List<ExpenseDetailDTO> updatedExpenseDetailsDTOList;

	private List<ExpenseDetailDTO> deletedExpenseDetailsDTOList;
	
	private Double advanceAmount;

	public Long getExpenseHeaderId() {
		return expenseHeaderId;
	}

	public void setExpenseHeaderId(Long expenseHeaderId) {
		this.expenseHeaderId = expenseHeaderId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public List<ExpenseDetailDTO> getAddedExpenseDetailsDTOList() {
		return addedExpenseDetailsDTOList;
	}

	public void setAddedExpenseDetailsDTOList(List<ExpenseDetailDTO> addedExpenseDetailsDTOList) {
		this.addedExpenseDetailsDTOList = addedExpenseDetailsDTOList;
	}

	public List<ExpenseDetailDTO> getUpdatedExpenseDetailsDTOList() {
		return updatedExpenseDetailsDTOList;
	}

	public void setUpdatedExpenseDetailsDTOList(List<ExpenseDetailDTO> updatedExpenseDetailsDTOList) {
		this.updatedExpenseDetailsDTOList = updatedExpenseDetailsDTOList;
	}

	public List<ExpenseDetailDTO> getDeletedExpenseDetailsDTOList() {
		return deletedExpenseDetailsDTOList;
	}

	public void setDeletedExpenseDetailsDTOList(List<ExpenseDetailDTO> deletedExpenseDetailsDTOList) {
		this.deletedExpenseDetailsDTOList = deletedExpenseDetailsDTOList;
	}

	public EmployeeDTO getEmployeeDTO() {
		return employeeDTO;
	}

	public void setEmployeeDTO(EmployeeDTO employeeDTO) {
		this.employeeDTO = employeeDTO;
	}

	public Integer getVoucherStatusId() {
		return voucherStatusId;
	}

	public void setVoucherStatusId(Integer voucherStatusId) {
		this.voucherStatusId = voucherStatusId;
		VoucherStatusDTO voucherStatusDTO= new VoucherStatusDTO();
		voucherStatusDTO.setVoucherStatusId(voucherStatusId);
		this.setVoucherStatusDTO(voucherStatusDTO);
	}

	public VoucherStatusDTO getVoucherStatusDTO() {
		return voucherStatusDTO;
	}

	@JsonIgnore
	public void setVoucherStatusDTO(VoucherStatusDTO voucherStatusDTO) {
		this.voucherStatusDTO = voucherStatusDTO;
		this.voucherStatusId=voucherStatusDTO.getVoucherStatusId();
	}

	public String getVoucherNumber() {
		return voucherNumber;
	}

	public void setVoucherNumber(String voucherNumber) {
		this.voucherNumber = voucherNumber;
	}

	public EmployeeDTO getPendingAtEmployeeDTO() {
		return pendingAtEmployeeDTO;
	}

	public void setPendingAtEmployeeDTO(EmployeeDTO pendingAtEmployeeDTO) {
		this.pendingAtEmployeeDTO = pendingAtEmployeeDTO;
	}

	public String getExpenseType() {
		return expenseType;
	}

	public void setExpenseType(String expenseType) {
		this.expenseType = expenseType;
	}

	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
		EventDTO eventDTO= new EventDTO();
		eventDTO.setEventId(eventId);
		this.setEventDTO(eventDTO);
	}

	public EventDTO getEventDTO() {
		return eventDTO;
	}

	public void setEventDTO(EventDTO eventDTO) {
		this.eventDTO = eventDTO;
		this.eventId=eventDTO.getEventId();
	}
	
	public Long getAdvanceDetailId() {
		return advanceDetailId;
	}

	public void setAdvanceDetailId(Long advanceDetailId) {
		this.advanceDetailId = advanceDetailId;
		AdvanceDTO advanceDTO=new AdvanceDTO();
		advanceDTO.setAdvanceDetailId(advanceDetailId);
		this.setAdvanceDTO(advanceDTO);
	}

	public AdvanceDTO getAdvanceDTO() {
		return advanceDTO;
	}

	public void setAdvanceDTO(AdvanceDTO advanceDTO) {
		this.advanceDTO = advanceDTO;
		this.advanceDetailId=advanceDTO.getAdvanceDetailId();
		this.advanceAmount = advanceDTO.getAmount();
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getRejectionComment() {
		return rejectionComment;
	}

	public void setRejectionComment(String rejectionComment) {
		this.rejectionComment = rejectionComment;
	}

	public EmployeeDTO getProcessedByEmployeeDTO() {
		return processedByEmployeeDTO;
	}

	public void setProcessedByEmployeeDTO(EmployeeDTO processedByEmployeeDTO) {
		this.processedByEmployeeDTO = processedByEmployeeDTO;
	}

	public Double getAdvanceAmount() {
		return advanceAmount;
	}

	public void setAdvanceAmount(Double advanceAmount) {
		this.advanceAmount = advanceAmount;
	}

}
