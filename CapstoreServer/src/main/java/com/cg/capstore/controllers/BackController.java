package com.cg.capstore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.capstore.beans.AdminBean;
import com.cg.capstore.beans.CustomerBean;
import com.cg.capstore.beans.MerchantBean;
import com.cg.capstore.exception.UserNotFoundException;
import com.cg.capstore.service.ICapstoreServerService;

@RestController
public class BackController {
	@Autowired
	private ICapstoreServerService service;
	@RequestMapping("/")
	public String test() {
		return "Spring backend";
	}
	
	@RequestMapping("/validatecustomer")
	public CustomerBean validateCustomer(String email, String password) throws UserNotFoundException {
		return service.validateCustomer(email, password);
	}
	
	@RequestMapping("/validateadmin")
	public AdminBean validateAdmin(String email, String password) throws UserNotFoundException {
		return service.validateAdmin(email, password);
	}
	@RequestMapping("/validatemerchant")
	public MerchantBean validateMerchant(String email, String password) throws UserNotFoundException {
		return service.validateMerchant(email, password);
	}
}
