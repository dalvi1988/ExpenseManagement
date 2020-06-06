package com.chaitanya.accountingGenerator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.chaitanya.jpa.EmployeeJPA;
import com.chaitanya.jpa.ExpenseDetailJPA;
import com.chaitanya.jpa.ExpenseHeaderJPA;
import com.chaitanya.utility.Convertor;

@Service
@Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE,proxyMode=ScopedProxyMode.TARGET_CLASS)
public class ExcelAccountingGenerator implements IAccountingGenerator {

	private Workbook workbook;
	
	private Sheet sheet;
	
	private CellStyle headerCellStyle;
	
	private Map<Integer,String> headerMap;
	
	@Override
	public byte[] generate(List<ExpenseHeaderJPA> expenseHeaderJPAList) throws IOException {
		init();
		createHeader();
		createRecords(expenseHeaderJPAList);
		return createFile();
	}


	private void init() {
	   workbook= new XSSFWorkbook();
	   sheet=  workbook.createSheet("Accounting Entry");
	   createHeaderStyles();
	}
	private void createHeaderStyles() {
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setColor(IndexedColors.BLACK.getIndex());

		headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);
	}
	
	private void createHeader() {
		headerMap =new LinkedHashMap<>();
		headerMap.put(0, "Expense Type");
		headerMap.put(1, "Voucher Number");
		headerMap.put(2, "Employee Name");
		headerMap.put(3, "Branch Name");
		headerMap.put(4, "Department Name");
		headerMap.put(5, "Purpose");
		headerMap.put(6, "Expense Category");
		headerMap.put(7, "GL Code");
		headerMap.put(8, "Date");
		headerMap.put(9, "Amount");
		headerMap.put(10, "Credit/Debit");
		
		Row headerRow = sheet.createRow(0);

		headerMap.forEach((k, v) -> {
		  Cell cell = headerRow.createCell(k);
		  cell.setCellValue(v);
		  cell.setCellStyle(headerCellStyle);
		});
	}
	
	private void createRecords(List<ExpenseHeaderJPA> expenseHeaderJPAList) {
		int headerIndex=1;
		for(int i=0; i< expenseHeaderJPAList.size(); i++) {
			ExpenseHeaderJPA expenseHeaderJPA =expenseHeaderJPAList.get(i);
			
			Row expenseHeaderRow = sheet.createRow(headerIndex);
			//Expense Type
			Cell expenseTypeCell= expenseHeaderRow.createCell(0);
			expenseTypeCell.setCellValue(expenseHeaderJPA.getExpenseType());
			
			//Voucher Number
			Cell voucherNumberCell= expenseHeaderRow.createCell(1);
			voucherNumberCell.setCellValue(expenseHeaderJPA.getVoucherNumber());
			
			//Voucher Number
			EmployeeJPA employeeJPA= expenseHeaderJPA.getEmployeeJPA();
			Cell employeeNameCell= expenseHeaderRow.createCell(2);
			employeeNameCell.setCellValue(employeeJPA.getFirstName()+" "+employeeJPA.getLastName());
			
			//Branch Name
			Cell branchCell= expenseHeaderRow.createCell(3);
			branchCell.setCellValue(employeeJPA.getBranchJPA().getBranchName());
			
			//Department Name
			Cell departmentCell= expenseHeaderRow.createCell(4);
			departmentCell.setCellValue(employeeJPA.getDepartmentJPA().getDeptName());
			
			//Voucher Number
			Cell purposeCell= expenseHeaderRow.createCell(5);
			purposeCell.setCellValue(expenseHeaderJPA.getPurpose());
			
			Double sumAmount=0.0;
			int detailIndex=1;
			for(int j=0; j< expenseHeaderJPA.getExpenseDetailJPA().size(); j++) {
				ExpenseDetailJPA expenseDetailJPA= expenseHeaderJPA.getExpenseDetailJPA().get(j);
				Row expenseDetailRow = sheet.createRow(headerIndex+detailIndex);
				
				// Expense Category Name
				Cell expenseCategoryCell= expenseDetailRow.createCell(6);
				expenseCategoryCell.setCellValue(expenseDetailJPA.getExpenseCategoryJPA().getExpenseName());
				
				// Expense Category GL COde
				Cell expenseCategoryGLCodeCell= expenseDetailRow.createCell(7);
				expenseCategoryGLCodeCell.setCellValue(expenseDetailJPA.getExpenseCategoryJPA().getGlCode());
				
				// Expense date COde
				Cell dateCell= expenseDetailRow.createCell(8);
				dateCell.setCellValue(Convertor.calendartoString(expenseDetailJPA.getDate(),Convertor.dateFormat));
				
				// Expense detail amount
				Cell amountCell= expenseDetailRow.createCell(9);
				amountCell.setCellValue(expenseDetailJPA.getAmount());
				
				//Debit cell
				Cell debitCell= expenseDetailRow.createCell(10);
				debitCell.setCellValue("Dr");
				
				sumAmount+=expenseDetailJPA.getAmount();
				detailIndex++;
			}
			//Expense Header amount
			Cell expenseHeadeAmountCell= expenseHeaderRow.createCell(9);
			expenseHeadeAmountCell.setCellValue(sumAmount);
			
			//Credit Cell
			Cell creditCell= expenseHeaderRow.createCell(10);
			creditCell.setCellValue("Cr");
			
			headerIndex+=(detailIndex+1);
		}
		
	}
	private byte[] createFile() throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		workbook.write(bos);
		return bos.toByteArray();
	}
}
