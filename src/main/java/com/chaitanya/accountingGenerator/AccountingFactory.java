package com.chaitanya.accountingGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountingFactory {

	@Autowired
	ExcelAccountingGenerator excelAccountingGenerator;
	
	public IAccountingGenerator generateAccounting(String fileFormat) {
		if(fileFormat.equalsIgnoreCase("EXCEL")) {
			return excelAccountingGenerator; 
		}
		else {
			return excelAccountingGenerator;
		}
	}
}
