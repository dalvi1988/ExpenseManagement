package com.chaitanya.jpa;

import java.util.Calendar;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "department_head")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DepartmentHeadJPA {

	@Id
	@GeneratedValue
	@Column(name = "department_head_id")
	private Long deptHeadId;

	@OneToOne
	@JoinColumn(name = "branch_id", nullable = false)
	private BranchJPA branchJPA;

	@OneToOne
	@JoinColumn(name = "department_id", nullable = false)
	private DepartmentJPA departmentJPA;

	@OneToOne
	@JoinColumn(name = "employee_id", nullable = false)
	private EmployeeJPA employeeJPA;

	@Column(name = "created_by")
	private Long createdBy;

	@Column(name = "modified_by")
	private Long modifiedBy;

	@Column(name = "created_date")
	private Calendar createdDate;

	@Column(name = "modified_date")
	private Calendar modifiedDate;

	@Column(name = "status", nullable = false)
	private Character status;

}
