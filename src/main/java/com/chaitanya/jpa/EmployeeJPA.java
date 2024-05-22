package com.chaitanya.jpa;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "employee_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class EmployeeJPA {
	@Id
	@GeneratedValue
	@Column(name = "employee_id")
	private Long employeeId;

	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "employee_code", nullable = false)
	private String employeeCode;

	@Column(name = "middle_name", nullable = true)
	private String middleName;

	@Column(name = "last_name", nullable = false)
	private String lastName;

	@Column(name = "email_id", nullable = false)
	private String emailId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reporting_mgr")
	private EmployeeJPA reportingMgr;

	@OneToMany(mappedBy = "reportingMgr", fetch = FetchType.LAZY)
	private Set<EmployeeJPA> subordinates = new HashSet<EmployeeJPA>();

	@OneToOne
	@JoinColumn(name = "department_id")
	private DepartmentJPA departmentJPA;

	@OneToOne
	@JoinColumn(name = "branch_id")
	private BranchJPA branchJPA;

	@Column(name = "gender")
	private Character gender;

	@Column(name = "created_by")
	private Long createdBy;

	@Column(name = "modified_by")
	private Long modifiedBy;

	@Column(name = "created_date")
	private Calendar createdDate;

	@Column(name = "modified_date")
	private Calendar modifiedDate;

	@Column(name = "status")
	private Character status;

}
