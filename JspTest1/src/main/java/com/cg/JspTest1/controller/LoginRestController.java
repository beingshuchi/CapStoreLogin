package com.cg.JspTest1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.JspTest1.bean.AdminBean;
import com.cg.JspTest1.bean.CustomerBean;
import com.cg.JspTest1.bean.MerchantBean;
import com.cg.JspTest1.service.ILoginService;

@RestController
public class LoginRestController {
	@Autowired
	private ILoginService loginService;
	@RequestMapping("/")
	public String sayHello(){
		
		return "Spring Boot is cool...";
	}
	@RequestMapping(value="/adminLogin")
	public AdminBean validateAdmin( String email, String password) {		
		return loginService.validateAdmin(email, password);
	}
	
	@RequestMapping(value="/loginCustomer")
	public CustomerBean validateCustomer(String email, String password){
		return loginService.validateCustomer(email, password);
	}
	@RequestMapping(value="/loginMerchant")
	public MerchantBean validateMerhcant( String email, String password){
		return loginService.validateMerchant(email, password);
	}
}
