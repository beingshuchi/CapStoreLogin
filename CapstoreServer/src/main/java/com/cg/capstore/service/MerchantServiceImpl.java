package com.cg.capstore.service;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.cg.capstore.beans.MerchantBean;
import com.cg.capstore.exception.UserNotFoundException;
import com.cg.capstore.repo.IMerchantRepo;
@Service(value="service")
public class MerchantServiceImpl implements IMerchantService {
	
	
	@Autowired
	private IMerchantRepo merchanRepo;
	
	
	
	private String encryption(String password) {
		StringBuilder sb=new StringBuilder(password);
		sb.reverse().append(password);
		return sb.toString();
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


	@Override
	@Transactional
	public Boolean changePasswordMerchant(String oldPassword, String newPassword) throws UserNotFoundException {
		oldPassword=encryption(oldPassword);
		MerchantBean merchant= merchanRepo.findPassword(oldPassword);
		if(oldPassword.equals(merchant.getPassword())) {
			newPassword=encryption(newPassword);
			merchant.setPassword(newPassword);
			merchanRepo.save(merchant);
			return true;
		}
		return false;
	}

	

	@Override
	public MerchantBean getMechant(String email) throws UserNotFoundException {
		// TODO Auto-generated method stub
		return merchanRepo.findMerchant(email);
	}

	@Override
	@Transactional
	public Boolean confirmSignUpMerchant(String customerName, String mobileNo, String email, String password,
			String merchantType) throws UserNotFoundException {
		password=encryption(password);
		MerchantBean merchant = new MerchantBean();
		merchant.setMerchantName(customerName);
		merchant.setEmailId(email);
		merchant.setPassword(password);
		merchant.setPhoneNo(mobileNo);
		merchant.setType(merchantType);
		merchanRepo.save(merchant);
		return true;
	}
	
	

		

	
}
