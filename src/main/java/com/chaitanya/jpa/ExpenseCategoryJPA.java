package com.chaitanya.jpa;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="expense_category")
public class ExpenseCategoryJPA {
	
	@Id @GeneratedValue
	@Column(name="expense_category_id")
	private Long expCategoryId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "company_id")
	private CompanyJPA companyJPA;

	@Column(name="expense_name",unique=true,nullable=false)
	private String expenseName;
		
	@Column(name="gl_code")
	private String glCode;
	
	@Column(name="location_required",nullable=false)
	private Character locationRequired;
	
	@Column(name="unit_required",nullable=false)
    private Character unitRequired;
	
	@Column(name="amount")
	private Double amount;
	
	@Column(name="limit_increase",nullable=false)
    private Character limitIncrease;
	
	@Column(name="created_by")
	private Long createdBy;
	
	@Column(name="modified_by")
    private Long modifiedBy;
	
	@Column(name="created_date")
	private Calendar createdDate;
		
	@Column(name="modified_date")
	private Calendar modifiedDate;
	
	@Column(name="status")
	private Character status;

	public Long getExpCategoryId() {
		return expCategoryId;
	}

	public void setExpCategoryId(Long expCategoryId) {
		this.expCategoryId = expCategoryId;
	}

	public String getExpenseName() {
		return expenseName;
	}

	public void setExpenseName(String expenseName) {
		this.expenseName = expenseName;
	}

	public Character getLocationRequired() {
		return locationRequired;
	}

	public void setLocationRequired(Character locationRequired) {
		this.locationRequired = locationRequired;
	}

	public Character getUnitRequired() {
		return unitRequired;
	}

	public void setUnitRequired(Character unitRequired) {
		this.unitRequired = unitRequired;
	}

	public String getGlCode() {
		return glCode;
	}

	public void setGlCode(String glCode) {
		this.glCode = glCode;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Calendar getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Calendar createdDate) {
		this.createdDate = createdDate;
	}

	public Calendar getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Calendar modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Character getStatus() {
		return status;
	}

	public void setStatus(Character status) {
		this.status = status;
	}

	public CompanyJPA getCompanyJPA() {
		return companyJPA;
	}

	public void setCompanyJPA(CompanyJPA companyJPA) {
		this.companyJPA = companyJPA;
	}

	public Character getLimitIncrease() {
		return limitIncrease;
	}

	public void setLimitIncrease(Character limitIncrease) {
		this.limitIncrease = limitIncrease;
	}
}
