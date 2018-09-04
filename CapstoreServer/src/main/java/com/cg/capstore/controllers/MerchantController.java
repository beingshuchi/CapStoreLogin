package com.cg.capstore.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cg.capstore.beans.MerchantBean;
import com.cg.capstore.beans.ProductBean;
import com.cg.capstore.exception.CategoryNotFoundException;
import com.cg.capstore.exception.ProductNotFoundException;
import com.cg.capstore.exception.UserNotFoundException;
import com.cg.capstore.service.IMerchantService;

@RestController
public class MerchantController {
	@Autowired
	private IMerchantService service;
	
	@RequestMapping("/signupmerchant")
	public Boolean confirmSignUpMerchant(String customerName, String mobileNo, String email, String password, String merchantType) throws UserNotFoundException {
		return service.confirmSignUpMerchant(customerName, mobileNo, email, password, merchantType);
	}
	@RequestMapping("/validatemerchant")
	public MerchantBean validateMerchant(String email, String password) throws UserNotFoundException {
		return service.validateMerchant(email, password);
	}
	@RequestMapping(value="/changepasswordmerchant",method=RequestMethod.POST)
	public Boolean changePasswordMerchant(String oldPassword, String newPassword) throws UserNotFoundException {
		return service.changePasswordMerchant(oldPassword, newPassword);
	}
	@RequestMapping(value="/getmerchant")
	public MerchantBean getMechant(String email) throws UserNotFoundException {
		return service.getMechant(email);
	}
	/*
	 * Merchant managing inventory
	 * */
	
	
	
	

}
