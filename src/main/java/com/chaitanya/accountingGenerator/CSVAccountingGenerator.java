package com.chaitanya.accountingGenerator;

import java.util.List;

import com.chaitanya.jpa.ExpenseHeaderJPA;

public class CSVAccountingGenerator implements IAccountingGenerator {

	@Override
	public byte[] generate(List<ExpenseHeaderJPA> expenseHeaderJPAList) {
		// TODO Auto-generated method stub
		return "CSV".getBytes();
	}

}
