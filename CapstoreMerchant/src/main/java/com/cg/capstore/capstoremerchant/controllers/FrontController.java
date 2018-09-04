package com.cg.capstore.capstoremerchant.controllers;


import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import com.cg.capstore.capstoremerchant.beans.MerchantBean;

@RestController
public class FrontController {
private String email;
	@RequestMapping("/")
	public String test() {
		return "Spring Rest Mer";
	}
	
	@RequestMapping("/home")
	public ModelAndView redirectToHome(String email,  String pass) {
		this.email=email;
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
	
	@RequestMapping("/change-password")
	public ModelAndView changePassword(String oldPassword, String newPassword) {
		RestTemplate restTemplate=new RestTemplate();
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("oldPassword", oldPassword);
		map.add("newPassword", newPassword);
		Boolean success=restTemplate.postForObject("http://localhost:9002/changepasswordmerchant", map,Boolean.class);
		ModelAndView mav=new ModelAndView();
		if(success) {
			mav.addObject(success);
			mav.setViewName("home");
			return mav;
		}else {
			mav.setViewName("/changepassword");
			return mav;
		}
	}
	
	@RequestMapping("/confirmsignup")
	public ModelAndView confirmSignUp(String customerName, String mobileNo, String email, String password,String merchantType) {
		RestTemplate restTemplate= new RestTemplate();
		ModelAndView mav = new ModelAndView();
		MerchantBean merchant=restTemplate.getForObject("http://localhost:9002/getmerchant?email="+email,MerchantBean.class);
			if(merchant==null) {
				MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
				map.add("customerName", customerName);
				map.add("mobileNo", mobileNo);
				map.add("email",email);
				map.add("password",password);
				map.add("merchantType", merchantType);
				Boolean success=restTemplate.getForObject("http://localhost:9002/signupmerchant?customerName="+customerName+"&mobileNo="+mobileNo+"&email="+email+"&password="+password+"&merchantType="+merchantType, Boolean.class);
			    if(success) {
			    	mav.setViewName("confirmsignup");
			    }else {
			    	mav.setViewName("signup");
			    }
			}else {
				mav.setViewName("signup");
			}
			return mav;
	}
}
