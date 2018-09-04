package com.cg.capstore.service;


import com.cg.capstore.beans.MerchantBean;
import com.cg.capstore.exception.UserNotFoundException;

public interface IMerchantService {
	
	Boolean confirmSignUpMerchant(String customerName, String mobileNo, String email, String password, String merchantType)throws UserNotFoundException;
	
	
	MerchantBean validateMerchant(String email,String password) throws UserNotFoundException;
	
	Boolean changePasswordMerchant(String oldPassword,String newPassword) throws UserNotFoundException;
	
	
	 MerchantBean getMechant(String email) throws UserNotFoundException;
	

			
		 
}
