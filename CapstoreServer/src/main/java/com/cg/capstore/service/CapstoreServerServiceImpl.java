package com.cg.capstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.capstore.beans.AdminBean;
import com.cg.capstore.beans.CustomerBean;
import com.cg.capstore.beans.MerchantBean;
import com.cg.capstore.exception.UserNotFoundException;
import com.cg.capstore.repo.IAdminRepo;
import com.cg.capstore.repo.ICustomerRepo;
import com.cg.capstore.repo.IMerchantRepo;
@Service(value="service")
public class CapstoreServerServiceImpl implements ICapstoreServerService {
	@Autowired
	private ICustomerRepo customerRepo;
	@Autowired
	private IAdminRepo adminRepo;
	@Autowired
	private IMerchantRepo merchanRepo;
	
	@Override
	public CustomerBean validateCustomer(String email, String password) throws UserNotFoundException  {
		// TODO Auto-generated method stub
		password=encryption(password);
		CustomerBean customer=customerRepo.findCustomer(email);
		if(customer!=null && customer.getPassword().equals(password)) {
			return customer;
		}/*else {
			throw new UserNotFoundException("Invalid email or password");	
		}*/
		return null;
		
	}
	
	private static String encryption(String password) {
		StringBuilder sb=new StringBuilder(password);
		sb.reverse().append(password);
		return sb.toString();
	}

	@Override
	public AdminBean validateAdmin(String email, String password) throws UserNotFoundException {
		password=encryption(password);
		AdminBean admin=adminRepo.findAdmin(email);
		if(admin!=null && admin.getPassword().equals(password)) {
			return admin;
		}
		/*else {
			throw new UserNotFoundException("Invalid email or password");	
		}*/
		return null;
		
	}

	@Override
	public MerchantBean validateMerchant(String email, String password) throws UserNotFoundException {
		password=encryption(password);
		MerchantBean merchant=merchanRepo.findMerchant(email);
		if(merchant!=null && merchant.getPassword().equals(password)) {
			return merchant;
		}
		/*else {
			throw new UserNotFoundException("Invalid email or password");	
		}*/
		return null;
	}
}
