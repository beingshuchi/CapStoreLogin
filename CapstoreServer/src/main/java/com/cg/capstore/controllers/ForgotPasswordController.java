package com.cg.capstore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.cg.capstore.exception.UserNotFoundException;
import com.cg.capstore.service.IForgotPasswordService;

@RestController
public class ForgotPasswordController {
	@Autowired
	private IForgotPasswordService service;
	@RequestMapping(value = "/forgotpassword", method = RequestMethod.GET)
	public String getPassword(String email) throws UserNotFoundException {
		
		return service.forgotPassword(email);
		
	}
	
}
