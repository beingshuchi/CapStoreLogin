package com.cg.capstore.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.cg.capstore.beans.AdminBean;
import com.cg.capstore.beans.CustomerBean;
import com.cg.capstore.beans.MerchantBean;
import com.cg.capstore.exception.UserNotFoundException;
import com.cg.capstore.repo.IAdminRepo;
import com.cg.capstore.repo.ICustomerRepo;
import com.cg.capstore.repo.IMerchantRepo;

public class ForgotPasswordServiceImpl implements IForgotPasswordService{
	
	@Autowired
	private ICustomerRepo customerRepo;
	@Autowired
	private IAdminRepo adminRepo;
	@Autowired
	private IMerchantRepo merchanRepo;
	@Override
	public String forgotPassword(String email) throws UserNotFoundException {

		AdminBean admin = adminRepo.findAdmin(email);

		MerchantBean merchant = merchanRepo.findMerchant(email);
		CustomerBean cust = customerRepo.findCustomer(email);

		if (admin != null) {
			String a1 = admin.getPassword();
			int l1 = a1.length();
			String pass = (a1.substring(l1 / 2, l1));
			return pass;
		} else if (merchant != null) {
			String a1 = merchant.getPassword();
			int l1 = a1.length();
			// System.out.println(l1);
			String pass = (a1.substring(l1 / 2, l1));
			// System.out.println(pass);
			return pass;
		} else if (cust != null) {
			// return cust.getPassword();
			String a1 = cust.getPassword();
			int l1 = a1.length();
			String pass = (a1.substring(l1 / 2, l1));
			return pass;
		} else {
			throw new UserNotFoundException( "email does not exist");
		}

	}
}
