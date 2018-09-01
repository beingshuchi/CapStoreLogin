package com.cg.capstore.service;

import com.cg.capstore.beans.AdminBean;
import com.cg.capstore.beans.CustomerBean;
import com.cg.capstore.beans.MerchantBean;
import com.cg.capstore.exception.UserNotFoundException;

public interface ICapstoreServerService {
	CustomerBean validateCustomer(String email,String password) throws UserNotFoundException;
	AdminBean validateAdmin(String email,String password) throws UserNotFoundException;
	MerchantBean validateMerchant(String email,String password) throws UserNotFoundException;
}
