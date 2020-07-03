package com.chaitanya.customException;

public class ApprovalFlowException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ApprovalFlowException(){  
		  super("No Approval Flow exists.\n Please contact administrator.");  
	}  
}
