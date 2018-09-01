package com.cg.capstore.capstoremerchant.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class JSPController {
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	
	
	
}
