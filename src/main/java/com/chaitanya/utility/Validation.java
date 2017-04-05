package com.chaitanya.utility;

import java.util.Collection;

import com.chaitanya.base.BaseDTO;
import com.chaitanya.base.BaseDTO.ServiceStatus;

public class Validation {

	public static Boolean validateForNullObject(Object object){
		Boolean result=false;
		if(object!=null){
			result=true;
		}
		return result;
	}
	
	public static Boolean validateForEmptyString(String string){
		Boolean result=false;
		if(validateForNullObject(string)){
			if(!string.isEmpty()){
				result=true;
			}
		}
		return result;
	}
	public static Boolean validateCollectionForNullSize(Collection<?> collection ){
		Boolean result=false;
		if(collection!=null){
			result=true;
		}
		return result;
	}
	public static boolean validateForZero(Long value){
		Boolean result=false;
		if(value!=null){
			if (value != 0 ) {
				result= true;
			}
		}
		return result;
	}
	public static boolean validateForZero(Integer value){
		Boolean result=false;
		if(value!=null){
			if (value != 0 ) {
				result= true;
			}
		}
		return result;
	}
	
	public static boolean validateForZero(Double value){
		Boolean result=false;
		if(value!=null){
			if (value != 0 ) {
				result= true;
			}
		}
		return result;
	}
	public static boolean validateForSuccessStatus(BaseDTO baseDTO){
		Boolean result=false;
		if(validateForNullObject(baseDTO)){
			if(baseDTO.getServiceStatus().equals(ServiceStatus.SUCCESS))
			{
				result=true;
			}
		}
		return result;
	}
	
	
	
	public static boolean validateForSystemFailureStatus(BaseDTO baseDTO){
		Boolean result=false;
		if(validateForNullObject(baseDTO)){
			if(baseDTO.getServiceStatus().equals(ServiceStatus.SYSTEM_FAILURE))
			{
				result=true;
			}
		}
		return result;
	}
	
	public static boolean validateForBusinessFailureStatus(BaseDTO baseDTO){
		Boolean result=false;
		if(validateForNullObject(baseDTO)){
			if(baseDTO.getServiceStatus().equals(ServiceStatus.BUSINESS_VALIDATION_FAILURE))
			{
				result=true;
			}
		}
		return result;
	}
}
