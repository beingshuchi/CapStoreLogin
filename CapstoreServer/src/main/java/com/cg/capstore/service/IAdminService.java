package com.cg.capstore.service;

import java.sql.Date;
import java.util.List;

import javax.mail.MessagingException;

import com.cg.capstore.beans.AdminBean;
import com.cg.capstore.beans.CouponsBean;
import com.cg.capstore.beans.CustomerBean;
import com.cg.capstore.beans.MerchantBean;
import com.cg.capstore.beans.OrderBean;
import com.cg.capstore.beans.PaymentDetailsBean;
import com.cg.capstore.beans.ProductBean;
import com.cg.capstore.exception.GeneratingCouponsException;
import com.cg.capstore.exception.OrderDetailsNotFoundException;
import com.cg.capstore.exception.SearchException;
import com.cg.capstore.exception.UserNotFoundException;

public interface IAdminService {
	AdminBean validateAdmin(String email,String password) throws UserNotFoundException;
	Boolean changePasswordAdmin(String oldPassword,String newPassword) throws UserNotFoundException;
	List<CustomerBean> getAllCustomerDetails();
	List<MerchantBean> getAllMerchants();
	CustomerBean searchCustomerByName(String name)throws SearchException;
	MerchantBean searchMerchantByName(String name)throws SearchException;
	String createCoupon(String emailId, CouponsBean coupon) throws GeneratingCouponsException;
	String sendPromo(String name) throws MessagingException;
	String refund(String orderId) throws OrderDetailsNotFoundException;
	 OrderBean updateStatus(OrderBean status) throws OrderDetailsNotFoundException;
	 List<PaymentDetailsBean> transactionAnalysis(Date start, Date end);
	 MerchantBean addMerchant(MerchantBean merchant) throws UserNotFoundException;
	 void deleteMerchant(String Id) throws UserNotFoundException;
	 MerchantBean addThirdPartyMerchant(MerchantBean merchant) throws UserNotFoundException;
	 Double updateRevenue(String payment)throws UserNotFoundException;
	 String regiteredNewUser(String name) throws UserNotFoundException;
	MerchantBean sendInvitationToMerchant(String email) throws UserNotFoundException;
		CustomerBean sentInvitationToCustomer(String email) throws UserNotFoundException;
		 String sendSchemeToMerchant(String name) throws UserNotFoundException;
	 String sendSchemeToCustomer(String name) throws UserNotFoundException;
		String sendPromoToMerchant(String name) throws UserNotFoundException;
		 String sendPromoToCustomer(String name) throws UserNotFoundException;
		 ProductBean addProduct(String productId, String email);
		
}
