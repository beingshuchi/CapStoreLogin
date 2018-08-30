package com.cg.JspTest1.service;

import com.cg.JspTest1.bean.AdminBean;
import com.cg.JspTest1.bean.CustomerBean;
import com.cg.JspTest1.bean.MerchantBean;

public interface ILoginService {
	AdminBean validateAdmin(String email,String password);
	CustomerBean validateCustomer(String email,String password);
	MerchantBean validateMerchant(String email,String password);
}
