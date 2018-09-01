package com.cg.capstore.capstoremerchant.controllers;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.cg.capstore.capstoremerchant.beans.MerchantBean;

@RestController
public class FrontController {

	@RequestMapping("/")
	public String test() {
		return "Spring Rest Mer";
	}
	
	@RequestMapping("/home")
	public ModelAndView redirectToHome(String email,  String pass) {
		RestTemplate restTemplate= new RestTemplate();
		MerchantBean merchant=restTemplate.getForObject("http://localhost:9002/validatemerchant?email="+email+"&password="+pass,MerchantBean.class);
		ModelAndView mav=new ModelAndView();
		if(merchant!=null){
			mav.addObject("merchant",merchant);
			mav.setViewName("home");
			return mav;
		}else {
			mav.addObject("message", "Invalid username and password");
			mav.setViewName("login");
			return mav;
		}
	}
	
	
	
}
