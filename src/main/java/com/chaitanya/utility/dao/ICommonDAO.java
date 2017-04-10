package com.chaitanya.utility.dao;

import java.util.List;

import com.chaitanya.jpa.VoucherStatusJPA;

public interface ICommonDAO {

	public List<VoucherStatusJPA> getVoucherStatus();
}
