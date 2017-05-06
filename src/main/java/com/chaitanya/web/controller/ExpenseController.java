package com.chaitanya.web.controller;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.chaitanya.base.BaseDTO;
import com.chaitanya.expense.model.ExpenseDetailDTO;
import com.chaitanya.expense.model.ExpenseHeaderDTO;
import com.chaitanya.expense.service.IExpenseService;
import com.chaitanya.expenseCategory.model.ExpenseCategoryDTO;
import com.chaitanya.expenseCategory.service.IExpenseCategoryService;
import com.chaitanya.login.model.LoginUserDetails;
import com.chaitanya.utility.Validation;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@Transactional
public class ExpenseController {
	
	@Autowired
	@Qualifier("expenseService")
	private IExpenseService expenseService;
	
	@Autowired 
	@Qualifier("expenseCategoryService")
	private IExpenseCategoryService expenseCategoryService;
	
	@RequestMapping(value="/expense",method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView createExpense(@RequestParam(value="expenseHeaderId",required=false) Long expenseHeaderId) throws Exception{
		ModelAndView model=new ModelAndView();
		ObjectMapper mapper= new ObjectMapper();
		ExpenseHeaderDTO expenseHeaderDTO =new ExpenseHeaderDTO();
		List<ExpenseCategoryDTO> expenseCategoryDTOList= null;
		if(Validation.validateForZero(expenseHeaderId)){
			expenseHeaderDTO.setExpenseHeaderId(expenseHeaderId);
			BaseDTO baseDTO= expenseService.getExpense(expenseHeaderDTO);
			
			if(Validation.validateForSuccessStatus(baseDTO)){
				expenseHeaderDTO = (ExpenseHeaderDTO) baseDTO;
			}else{
				throw new Exception("");
			}
		}
		expenseCategoryDTOList = expenseCategoryService.findAll();
		model.addObject("expenseDetailList", mapper.writeValueAsString(expenseHeaderDTO.getAddedExpenseDetailsDTOList()));
		model.addObject("expenseCategoryList", mapper.writeValueAsString(expenseCategoryDTOList));
		model.addObject("ExpenseHeaderDTO", expenseHeaderDTO);
		model.setViewName("expense/expenseJSP");
		return model;
	}
	
	@RequestMapping(value="/toBeApproveExpense",method=RequestMethod.GET)
	public @ResponseBody ModelAndView getExppenseAprrovalPage() throws JsonGenerationException, JsonMappingException, IOException{
		ModelAndView model=new ModelAndView();
		model.setViewName("expense/toBeApproveExpenseJSP");
		return model;
	}
	
	@RequestMapping(value="/toBeApproveExpenseList",method=RequestMethod.POST)
	public @ResponseBody String getExpenseHeaderListForApproval() throws JsonGenerationException, JsonMappingException, IOException{
		ObjectMapper mapper= new ObjectMapper();
		
		LoginUserDetails user = (LoginUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<ExpenseHeaderDTO> expenseHeaderDTOList=null;
		ExpenseHeaderDTO expenseHeaderDTO=new ExpenseHeaderDTO();
		expenseHeaderDTO.setEmployeeDTO(user.getLoginDTO().getEmployeeDTO());
		expenseHeaderDTOList=expenseService.getExpenseToBeApprove(expenseHeaderDTO);

		return "{\"data\":"+mapper.writeValueAsString(expenseHeaderDTOList)+"}";
	}
	
	@RequestMapping(value="/expenseDetail",method=RequestMethod.POST)
	public @ResponseBody String getExpenseDetailListByHeaderId(@RequestBody ExpenseHeaderDTO receivedExpenseHeaderDTO) throws JsonGenerationException, JsonMappingException, IOException{
		ObjectMapper mapper= new ObjectMapper();
		
		List<ExpenseDetailDTO> expenseDetailsDTOList=null;

		expenseDetailsDTOList=expenseService.getExpenseDetailsByHeaderId(receivedExpenseHeaderDTO);

		return "{\"data\":"+mapper.writeValueAsString(expenseDetailsDTOList)+"}";
	}
	
	@RequestMapping(value="/approveRejectExpense",method=RequestMethod.POST)
	public @ResponseBody String approveRejectExpenses(@RequestBody List<ExpenseHeaderDTO> expenseHeaderDTOList) throws JsonGenerationException, JsonMappingException, IOException{
		ObjectMapper mapper= new ObjectMapper();

		for(ExpenseHeaderDTO expenseHeaderDTO:expenseHeaderDTOList){
			BaseDTO baseDTO=expenseService.approveRejectExpenses(expenseHeaderDTO);
		}

		return "{\"data\":"+mapper.writeValueAsString(expenseHeaderDTOList)+"}";
	}
	
	@RequestMapping(value="/saveExpense",method=RequestMethod.POST)
	public @ResponseBody ExpenseHeaderDTO saveExpense(@Valid @ModelAttribute("ExpenseHeaderDTO") ExpenseHeaderDTO receivedExpenseHeaderDTO,BindingResult result, @RequestParam("addedFiles") List<MultipartFile> addedFiles,@RequestParam("updatedFiles") List<MultipartFile> updatedFiles, String data) throws JsonGenerationException, JsonMappingException, IOException{
		ObjectMapper mapper= new ObjectMapper();
		ExpenseHeaderDTO toBeSendExpenseHeaderDTO = null;
		//ModelAndView model=new ModelAndView();
		/*if(result.hasErrors()){
			model.addObject("ExpenseHeaderDTO", expenseHeaderDTO);
			model.setViewName("expense/expenseJSP");
			return model;
		}*/
		try{
			LoginUserDetails user = (LoginUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			receivedExpenseHeaderDTO.setEmployeeDTO(user.getLoginDTO().getEmployeeDTO());
			
			// added List
			String addList=mapper.readTree(data).get("addList").toString();
			List<ExpenseDetailDTO> addedExpenseDetailDTOList =
				    mapper.readValue(addList, new TypeReference<List<ExpenseDetailDTO>>(){});
			for(int i=0; i< addedExpenseDetailDTOList.size(); i++){
				ExpenseDetailDTO expenseDetailDTO=addedExpenseDetailDTOList.get(i);
				MultipartFile receipt=addedFiles.get(i);
				expenseDetailDTO.setReceipt(receipt);
			}
			
			//updated list
			String updateList=mapper.readTree(data).get("updateList").toString();
			List<ExpenseDetailDTO> updatedExpenseDetailDTOList =
				    mapper.readValue(updateList, new TypeReference<List<ExpenseDetailDTO>>(){});
			for(int i=0; i< updatedExpenseDetailDTOList.size(); i++){
				ExpenseDetailDTO expenseDetailDTO=updatedExpenseDetailDTOList.get(i);
				MultipartFile receipt=addedFiles.get(i);
				expenseDetailDTO.setReceipt(receipt);
			}
			
			String deletedList=mapper.readTree(data).get("deleteList").toString();
			List<ExpenseDetailDTO> deletedExpenseDetailDTOList =
				    mapper.readValue(deletedList, new TypeReference<List<ExpenseDetailDTO>>(){});
			
			receivedExpenseHeaderDTO.setAddedExpenseDetailsDTOList(addedExpenseDetailDTOList);
			receivedExpenseHeaderDTO.setUpdatedExpenseDetailsDTOList(updatedExpenseDetailDTOList);
			receivedExpenseHeaderDTO.setDeletedExpenseDetailsDTOList(deletedExpenseDetailDTOList);
			
			BaseDTO baseDTO= expenseService.saveUpdateExpense(receivedExpenseHeaderDTO);
			if(Validation.validateForSuccessStatus(baseDTO)){
				toBeSendExpenseHeaderDTO=(ExpenseHeaderDTO)baseDTO;
				if(receivedExpenseHeaderDTO.getVoucherStatusId() == 2){
					toBeSendExpenseHeaderDTO.setMessage(new StringBuilder("Your voucher number: "+toBeSendExpenseHeaderDTO.getVoucherNumber()+" has beed send for approval."));
				}
				else{
					toBeSendExpenseHeaderDTO.setMessage(new StringBuilder("Your voucher has been saved in draft."));
				}
			}
			else{
				toBeSendExpenseHeaderDTO=receivedExpenseHeaderDTO;
			}
		}
		catch(Exception e){
			//model.setViewName("others/505");
		}
		//return model;
		return toBeSendExpenseHeaderDTO;
	}
	
	@RequestMapping(value="/viewDraftExpense",method=RequestMethod.GET)
	public ModelAndView viewDraftExpense() throws JsonGenerationException, JsonMappingException, IOException{
		ModelAndView model=new ModelAndView();
		ObjectMapper mapper = new ObjectMapper();
		LoginUserDetails user = (LoginUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<ExpenseHeaderDTO> expenseHeaderDTOList=null;
		ExpenseHeaderDTO expenseHeaderDTO=new ExpenseHeaderDTO();
		expenseHeaderDTO.setEmployeeDTO(user.getLoginDTO().getEmployeeDTO());
		
		if(Validation.validateForNullObject(user.getLoginDTO().getEmployeeDTO())){
			 expenseHeaderDTOList = expenseService.getDraftExpenseList(expenseHeaderDTO);
		}
		
		model.addObject("expenseHeaderList",mapper.writeValueAsString(expenseHeaderDTOList));
		model.setViewName("expense/viewExpensesJSP");
		return model;
	}
	
}
