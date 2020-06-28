package com.chaitanya.web.controller;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chaitanya.branch.model.BranchDTO;
import com.chaitanya.branch.service.IBranchService;
import com.chaitanya.department.model.DepartmentDTO;
import com.chaitanya.department.service.IDepartmentService;
import com.chaitanya.employee.model.EmployeeDTO;
import com.chaitanya.employee.service.IEmployeeService;
import com.chaitanya.login.model.LoginUserDetails;
import com.chaitanya.mis.model.ExpenseMISDTO;
import com.chaitanya.mis.service.IMISService;
import com.chaitanya.utility.Validation;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class CompanyMISController {
	
	@Autowired
	IMISService misService;
	
	@Autowired
	IBranchService branchService;
	
	@Autowired
	IDepartmentService departmentService;
	
	@Autowired
	IEmployeeService employeeService;
	
	private Logger logger= LoggerFactory.getLogger(CompanyMISController.class);

	@RequestMapping(value="/expenseMIS",method=RequestMethod.GET)
	public @ResponseBody ModelAndView getAllExpenseByCompany() {
		
		ModelAndView model=new ModelAndView();
		ObjectMapper mapper = new ObjectMapper();
		try{
			LoginUserDetails user = (LoginUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<ExpenseMISDTO> expenseHeaderDTOList=null;
			ExpenseMISDTO expenseHeaderDTO=new ExpenseMISDTO();
			expenseHeaderDTO.setEmployeeDTO(user.getLoginDTO().getEmployeeDTO());
			List<BranchDTO> branchDTOList=null;
			List<DepartmentDTO> departmentDTOList=null;
			List<EmployeeDTO> employeeDTOList=null;
			if(Validation.validateForNullObject(user.getLoginDTO().getEmployeeDTO())){
				employeeDTOList=new ArrayList<EmployeeDTO>();
				 expenseHeaderDTOList = misService.getAllExpensesByCompany(expenseHeaderDTO);
				 branchDTOList=branchService.findAllBranchUnderCompany(user.getLoginDTO().getEmployeeDTO().getBranchDTO());
				 DepartmentDTO departmentDTO= new DepartmentDTO();
					departmentDTO.setBranchDTO(user.getLoginDTO().getEmployeeDTO().getBranchDTO());
				 departmentDTOList=departmentService.findAllDepartmentUnderCompany(departmentDTO);
				 employeeDTOList=employeeService.findEmployeeOnCompany(user.getLoginDTO().getEmployeeDTO());
			}
			
			model.addObject("expenseHeaderList",mapper.writeValueAsString(expenseHeaderDTOList));
			model.addObject("employeeList", mapper.writeValueAsString(employeeDTOList));
			model.addObject("branchList", mapper.writeValueAsString(branchDTOList));
			model.addObject("departmentList", mapper.writeValueAsString(departmentDTOList));
			model.setViewName("mis/companyExpenseMIS");
		}
		catch(Exception e){
			logger.error("CompanyMIS: getAllExpenseByCompany",e);
			model.setViewName("others/505");
		}
		return model;
	}

	@RequestMapping(value = "/exportMIS", method = RequestMethod.POST)
    public @ResponseBody
    String excel(String excel, String extension, HttpServletRequest request) throws IOException {
        String filename="";
        if (extension.equals("csv") || extension.equals("xml")) {
            filename = "pqGrid." + extension;
            HttpSession ses = request.getSession(true);
            ses.setAttribute("excel", excel);
        } 
        return filename;
    }

    @RequestMapping(value = "/exportMIS", method = RequestMethod.GET)
    public void excel(String filename, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (filename.equals("pqGrid.csv") || filename.equals("pqGrid.xml")) {
            HttpSession ses = request.getSession(true);
            String excel = (String) ses.getAttribute("excel");
                        
            byte[] bytes = excel.getBytes(Charset.forName("UTF-8"));
                    
            response.setContentType("text/plain");
            
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + filename);
            response.setContentLength(bytes.length);
            ServletOutputStream out = response.getOutputStream();
            out.write(bytes);
            
            out.flush();
            out.close();
        }
    }
}
