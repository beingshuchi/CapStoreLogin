package com.cg.JspTest1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.JspTest1.bean.AdminBean;
import com.cg.JspTest1.bean.CustomerBean;
import com.cg.JspTest1.bean.MerchantBean;
import com.cg.JspTest1.repo.IAdminRepo;
import com.cg.JspTest1.repo.ICustomerRepo;
import com.cg.JspTest1.repo.IMerchantRepo;

@Service
public class LoginService implements ILoginService {
	@Autowired
	private IAdminRepo adminRepo;
	@Autowired
	private ICustomerRepo customerRepo;
	@Autowired
	private IMerchantRepo merchantRepo;
	@Override
	public AdminBean validateAdmin(String email, String password) {
		// TODO Auto-generated method stub
		//System.out.println(password);
		password="!"+password+"!";//encryption
		/*System.out.println(password);
		AdminBean admin=adminRepo.getOne(email);
		CustomerBean customer=customerRepo.getOne(email);
		MerchantBean merchant=merchantRepo.getOne(email);
		System.out.println(admin);*/
		AdminBean admin=adminRepo.FinByEmail(email);
		if(admin!= null) {
			if(admin.getPassword().equals(password)) {
				return admin;
			}else return null;
		}else 
			{
			return null;
			}
			}
	@Override
	public CustomerBean validateCustomer(String email, String password) {
		// TODO Auto-generated method stub
		password="!"+password+"!";//encryption
		CustomerBean customer=customerRepo.FinByEmail(email);
		if(customer!= null) {
			if(customer.getPassword().equals(password)) {
				return customer;
			}else return null;
		}else 
			{
			return null;
			}
	}
	@Override
	public MerchantBean validateMerchant(String email, String password) {
		password="!"+password+"!";//encryption
		MerchantBean merchant=merchantRepo.FinByEmail(email);
		if(merchant!= null) {
			if(merchant.getPassword().equals(password)) {
				return merchant;
			}else return null;
		}else 
			{
			return null;
			}
	}
}
