package com.chaitanya.jpa;

import java.util.Calendar;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "company_details")
public class CompanyJPA {

	@Id
	@GeneratedValue
	@Column(name = "company_id")
	private Long companyId;

	@Column(name = "company_code", unique = true, nullable = false)
	private String companyCode;

	@Column(name = "company_name", unique = true, nullable = false)
	private String companyName;

	@OneToMany(mappedBy = "companyJPA")
	private Set<BranchJPA> branchJPA;

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
