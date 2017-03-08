package com.chaitanya.utility;

import java.util.List;

import com.chaitanya.employee.model.EmployeeDTO;

public class Utility {

	public static void addLevelsToEmployeeDTO(List<EmployeeDTO> employeeDTOList){
		EmployeeDTO emp1=new EmployeeDTO();
		emp1.setEmployeeId(-10L);
		emp1.setFirstName("Reporting Manager");
		emp1.setLastName("");
		employeeDTOList.add(0, emp1);
		
		EmployeeDTO emp2=new EmployeeDTO();
		emp2.setEmployeeId(-11L);
		emp2.setFirstName("Reporting Manager's Manager");
		emp2.setLastName("");
		employeeDTOList.add(1, emp2);
		
		EmployeeDTO emp3=new EmployeeDTO();
		emp3.setEmployeeId(-1L);
		emp3.setFirstName("Department Head");
		emp3.setLastName("");
		employeeDTOList.add(2, emp3);
	}
}
