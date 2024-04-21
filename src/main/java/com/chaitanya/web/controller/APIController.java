package com.chaitanya.web.controller;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.chaitanya.base.BaseDTO;
import com.chaitanya.company.model.CompanyDTO;
import com.chaitanya.employee.model.EmployeeDTO;
import com.chaitanya.employee.service.IEmployeeService;
import com.chaitanya.expense.model.ExpenseDetailDTO;
import com.chaitanya.expense.model.ExpenseHeaderDTO;
import com.chaitanya.expense.service.IExpenseService;
import com.chaitanya.expenseCategory.model.ExpenseCategoryDTO;
import com.chaitanya.expenseCategory.service.IExpenseCategoryService;
import com.chaitanya.login.model.LoginDTO;
import com.chaitanya.login.service.ILoginService;
import com.chaitanya.utility.Convertor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(value="api/")
public class APIController {
	
	@Autowired 
	private IExpenseCategoryService expenseCategoryService;
	
	@Autowired
	private ILoginService loginService;
	
	@Autowired
	private IExpenseService expenseService;
	
	@Autowired
	private IEmployeeService employeeService;
	
	@PostMapping(value="expenseCategoryList")
	public List<ExpenseCategoryDTO> getExpenseCategoryList(@RequestBody CompanyDTO companyDTO){
		ExpenseCategoryDTO expenseCategoryDTO=new ExpenseCategoryDTO();
		expenseCategoryDTO.setCompanyDTO(companyDTO);
		List<ExpenseCategoryDTO> expenseCategoryDTOList = expenseCategoryService.getAllActiveExpenseCategoryByCompany(expenseCategoryDTO);
		return expenseCategoryDTOList;
	}
	
	@RequestMapping(value ={"login"}, method = RequestMethod.POST)
	public @ResponseBody LoginDTO login(@RequestBody LoginDTO loginDTO) {
		 loginDTO=loginService.authenticateUserForAPI(loginDTO);
		return loginDTO;
	}

	@RequestMapping(value ={"createVoucher"}, method = RequestMethod.POST)
	public @ResponseBody BaseDTO createVoucher(@RequestBody String data) throws ParseException, Exception {
		BaseDTO baseDTO= null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			JsonNode jsonNode = objectMapper.readTree(data);
			System.out.println(jsonNode.get("expenseDetailDTO"));
			
			EmployeeDTO receivedEmployeeDTO = new EmployeeDTO();
			receivedEmployeeDTO.setEmployeeId(jsonNode.get("employeeId").asLong());	
			
			EmployeeDTO employeeDTO= (EmployeeDTO) employeeService.getEmployeeById(receivedEmployeeDTO);
			
			ExpenseHeaderDTO expenseHeaderDTO = objectMapper.readValue(jsonNode.get("expenseHeaderDTO").textValue(), ExpenseHeaderDTO.class);		
			expenseHeaderDTO.setEmployeeDTO(employeeDTO);
			expenseHeaderDTO.setCreatedBy(employeeDTO.getEmployeeId());
			expenseHeaderDTO.setCreatedDate(Convertor.calendartoString(Calendar.getInstance(),Convertor.dateFormatWithTime));
			
			String addList=objectMapper.readTree(data).get("expenseDetailDTO").toString();
			List<ExpenseDetailDTO> addedExpenseDetailDTOList =
					objectMapper.readValue(addList, new TypeReference<List<ExpenseDetailDTO>>(){});
			
			expenseHeaderDTO.setAddedExpenseDetailsDTOList(addedExpenseDetailDTOList);
			baseDTO= expenseService.saveUpdateExpense(expenseHeaderDTO);
			
		}
		catch(Exception e) {
			System.out.println("Exception: "+e);
		}
		return baseDTO;
	}
}
