package com.cg.capstore.controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cg.capstore.bean.AdminBean;
import com.cg.capstore.bean.CustomerBean;
@RestController
public class FrontController {
	
	@RequestMapping("/ahome")
	public String logIn(@RequestParam String email,@RequestParam String psw,ModelMap map) {
		
	RestTemplate restTemplate=new RestTemplate();
	AdminBean admin = restTemplate.getForObject("http://localhost:9191/adminLogin?email="+email+"&password="+psw, AdminBean.class);
	map.put("admin", admin);
	return "adminhome";

	}
	
	@RequestMapping("/log")
	public String login(@RequestParam String email,@RequestParam String psw,ModelMap map) {
		System.out.println(email+":"+psw);
		RestTemplate restTemplate=new RestTemplate();
		CustomerBean cust=restTemplate.getForObject("http://localhost:9191/loginCustomer?email="+email+"&password="+psw, CustomerBean.class);
		map.put("customer",cust);
		return "home";
	}
}
