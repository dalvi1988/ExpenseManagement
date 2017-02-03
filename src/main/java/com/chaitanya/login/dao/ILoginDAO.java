package com.chaitanya.login.dao;

import com.chaitanya.jpa.LoginJPA;

public interface ILoginDAO {

	LoginJPA findByUserName(String username);

}