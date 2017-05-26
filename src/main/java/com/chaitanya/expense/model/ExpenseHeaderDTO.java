package com.chaitanya.expense.model;

import java.util.List;

import com.chaitanya.base.BaseDTO;
import com.chaitanya.employee.model.EmployeeDTO;
import com.chaitanya.event.model.EventDTO;
import com.chaitanya.utility.model.VoucherStatusDTO;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

public class ExpenseHeaderDTO extends BaseDTO{

	private static final long serialVersionUID = 1L;
	
	private Long expenseHeaderId;
	
	private String voucherNumber;
	
	
	private Double totalAmount;
	
	private Integer voucherStatusId;
	@JsonIgnore
	private VoucherStatusDTO voucherStatusDTO;

	private Integer eventId;
	@JsonIgnore
	private EventDTO eventDTO;
	
	private String startDate;
	
	private String endDate;
	
	private String title;
	
	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	private String purpose;
	
	@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="fullName")
	@JsonIdentityReference(alwaysAsId=true)
	private EmployeeDTO employeeDTO;
	
	@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="fullName")
	@JsonIdentityReference(alwaysAsId=true)
	private EmployeeDTO pendingAtEmployeeDTO;
	
	@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="fullName")
	@JsonIdentityReference(alwaysAsId=true)
	private EmployeeDTO approvedByEmployeeDTO;
	
	private List<ExpenseDetailDTO> addedExpenseDetailsDTOList;
	
	private List<ExpenseDetailDTO> updatedExpenseDetailsDTOList;

	private List<ExpenseDetailDTO> deletedExpenseDetailsDTOList;

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public EmployeeDTO getApprovedByEmployeeDTO() {
		return approvedByEmployeeDTO;
	}

	public void setApprovedByEmployeeDTO(EmployeeDTO approvedByEmployeeDTO) {
		this.approvedByEmployeeDTO = approvedByEmployeeDTO;
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
	
}
