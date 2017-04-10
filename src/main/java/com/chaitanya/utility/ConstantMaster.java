package com.chaitanya.utility;

import java.util.Map;

import org.springframework.context.annotation.Scope;

import com.chaitanya.utility.model.VoucherStatusDTO;

@Scope(value = "singleton")
public class ConstantMaster {

	Map<Integer,VoucherStatusDTO> voucherStatusMap;

	public Map<Integer, VoucherStatusDTO> getVoucherStatusMap() {
		return voucherStatusMap;
	}

	public void setVoucherStatusMap(Map<Integer, VoucherStatusDTO> voucherStatusMap) {
		this.voucherStatusMap = voucherStatusMap;
	}
}
