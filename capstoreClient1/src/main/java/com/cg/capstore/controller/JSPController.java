package com.cg.capstore.controller;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class JSPController {
	
	@RequestMapping(value="/admin")
	public String loginPage() {
		return "admin";
	}
	@RequestMapping(value="/adminhome")
	public String adminHome() {
		return "adminhome";
	}
	
	@RequestMapping(value="/login")
	public String Login() {
		return "login";
	}
	@RequestMapping("/home")
	public String capStoreHome() {
		return "home";
	}
	
	@RequestMapping("/merchanthome")
	public String merchHome() {
		return "merchanthome";
	}
}
