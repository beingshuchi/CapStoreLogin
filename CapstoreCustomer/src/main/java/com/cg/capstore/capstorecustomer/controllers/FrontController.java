package com.cg.capstore.capstorecustomer.controllers;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.cg.capstore.capstorecustomer.beans.AdminBean;
import com.cg.capstore.capstorecustomer.beans.CustomerBean;

@RestController
public class FrontController {

	@RequestMapping("/")
	public String test() {
		return "Spring Rest";
	}
	
	@RequestMapping("/home")
	public ModelAndView redirectToHome(String email,  String pass) {
		RestTemplate restTemplate= new RestTemplate();
		CustomerBean customer=restTemplate.getForObject("http://localhost:9002/validatecustomer?email="+email+"&password="+pass,CustomerBean.class);
		ModelAndView mav=new ModelAndView();
		if(customer!=null){
			mav.addObject("customer",customer);
			mav.setViewName("home");
			return mav;
		}else {
			mav.addObject("message", "Invalid username and password");
			mav.setViewName("login");
			return mav;
		}
		
	}
	
	@RequestMapping("/admin")
	public ModelAndView redirectToAdmin(String email,  String pass) {
		RestTemplate restTemplate= new RestTemplate();
		AdminBean admin=restTemplate.getForObject("http://localhost:9002/validateadmin?email="+email+"&password="+pass,AdminBean.class);
		ModelAndView mav=new ModelAndView();
		if(admin!=null){
			mav.addObject("admin",admin);
			mav.setViewName("admin");
			return mav;
		}else {
			mav.addObject("message", "Invalid username and password");
			mav.setViewName("adminlogin");
			return mav;
		}
		
	}
	
	
	/*@RequestMapping("/editprofile")
	public String editCustomerProfile(String email,String customerName,
	String mobileNo,String address,ModelMap map) {
	RestTemplate restTemplate=new RestTemplate();
	return "editprofile"; 
	}*/
}
