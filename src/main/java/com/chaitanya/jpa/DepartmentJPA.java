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
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "department_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class DepartmentJPA {

	@Id
	@GeneratedValue
	@Column(name = "dept_id")
	private Long departmentId;

	@Column(name = "dept_name", unique = true, nullable = false)
	private String deptName;

	@Column(name = "dept_code", unique = true, nullable = false)
	private String deptCode;

	@OneToOne
	@JoinColumn(name = "branch_id")
	private BranchJPA branchJPA;

	@Column(name = "created_by")
	private Long createdBy;

	@Column(name = "modified_by")
	private Long modifiedBy;

	@Column(name = "created_date")
	private Calendar createdDate;

	@Column(name = "modified_date")
	private Calendar modifiedDate;

	@Column(name = "status", nullable = false)
	Character status;

}
