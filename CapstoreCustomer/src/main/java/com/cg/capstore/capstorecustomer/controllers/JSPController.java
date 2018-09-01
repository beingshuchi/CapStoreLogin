package com.cg.capstore.capstorecustomer.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class JSPController {
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	
	@RequestMapping("/adminlogin")
	public String adminLogin() {
		return "adminlogin";
	}
	@RequestMapping("/customerprofile")
	public String customerProfile() {
		return "customerprofile";
	}
	@RequestMapping("/editprofile")
	public String editCustomerProfile() {
	return "editprofile"; 
	}

}
