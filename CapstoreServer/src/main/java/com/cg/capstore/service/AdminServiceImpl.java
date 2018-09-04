package com.cg.capstore.service;

import java.sql.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.capstore.beans.AdminBean;
import com.cg.capstore.beans.CouponsBean;
import com.cg.capstore.beans.CustomerBean;
import com.cg.capstore.beans.MerchantBean;
import com.cg.capstore.beans.OrderBean;
import com.cg.capstore.beans.PaymentDetailsBean;
import com.cg.capstore.beans.ProductBean;
import com.cg.capstore.beans.WishlistBean;
import com.cg.capstore.exception.GeneratingCouponsException;
import com.cg.capstore.exception.OrderDetailsNotFoundException;
import com.cg.capstore.exception.SearchException;
import com.cg.capstore.exception.UserNotFoundException;
import com.cg.capstore.repo.IAdminRepo;
import com.cg.capstore.repo.ICouponRepo;
import com.cg.capstore.repo.ICustomerRepo;
import com.cg.capstore.repo.IDiscountRepo;
import com.cg.capstore.repo.IFeedbackProductRepo;
import com.cg.capstore.repo.IImageRepo;
import com.cg.capstore.repo.IMerchantRepo;
import com.cg.capstore.repo.IOrderRepo;
import com.cg.capstore.repo.IPaymentRepo;
import com.cg.capstore.repo.IProductRepo;
import com.cg.capstore.repo.IPromoRepo;
import com.cg.capstore.repo.IWishlistRepo;

@Service
public class AdminServiceImpl implements IAdminService {
	@Autowired
	private ICustomerRepo customerRepo;
	@Autowired
	private IAdminRepo adminRepo;
	@Autowired
	private IMerchantRepo merchanRepo;
	@Autowired
	private IProductRepo productRepo;
	@Autowired
	private IOrderRepo orderRepo;
	@Autowired
	private ICouponRepo couponRepo;
	@Autowired
	private IPromoRepo promoRepo;
	@Autowired
	private IPaymentRepo paymentRepo;
	@Autowired
	private IWishlistRepo wishlistRepo;
	
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
	private String encryption(String password) {
		StringBuilder sb=new StringBuilder(password);
		sb.reverse().append(password);
		return sb.toString();
	}
	@Override
	@Transactional
	public Boolean changePasswordAdmin(String oldPassword, String newPassword) throws UserNotFoundException {
		oldPassword=encryption(oldPassword);
		AdminBean admin= adminRepo.findPassword(oldPassword);
		if(oldPassword.equals(admin.getPassword())) {
			newPassword=encryption(newPassword);
			admin.setPassword(newPassword);
			adminRepo.save(admin);
			return true;
		}
		return false;
	}
	@Override
	public List<CustomerBean> getAllCustomerDetails() {
		// TODO Auto-generated method stub
		return customerRepo.findAll();
	}

	@Override
	public List<MerchantBean> getAllMerchants() {
		// TODO Auto-generated method stub
		return merchanRepo.findAll();
	}
	@Override
	public CustomerBean searchCustomerByName(String name) throws SearchException{
		
		return customerRepo.searchCustomerByName(name);
	}
//Merchant search by merchant name
	@Override
	public MerchantBean searchMerchantByName(String name)throws SearchException {
		// TODO Auto-generated method stub
		return merchanRepo.searchMerchantByName(name);
	}
	@Transactional
	@Override
	public String createCoupon(String emailId, CouponsBean coupon) throws GeneratingCouponsException {
		CouponsBean coupon1=new CouponsBean();
		AdminBean admin1=adminRepo.findAdmin(emailId);
		if(admin1.getEmailId().equals(emailId)) {
		coupon1.setCouponCode(coupon.getCouponCode());
		coupon1.setCouponAmount(coupon.getCouponAmount());
		coupon1.setStartDate(coupon.getStartDate());
		coupon1.setEndDate(coupon.getEndDate());
		couponRepo.save(coupon1);
		//String output=sendEmail(emailId, coupon1);
		return (coupon1.getCouponCode()+" is generated and sent to all customers successfully");
		} 
			else {
				throw new GeneratingCouponsException("coupon cannot be generated");
			}
		
	}
	//*************Checking, Updating Status of delivery*******************
			@Override
			public OrderBean updateStatus(OrderBean status) throws OrderDetailsNotFoundException{

				OrderBean o = orderRepo.getOne(status.getOrderId());
				o.setOrderStatus(status.getOrderStatus());
				return orderRepo.save(o);
			}

		
			// ********************Sending New Promos & list of new
			// items*********************

			@Override
			public String sendPromo(String name) throws MessagingException {

				return promoRepo.findCustomerEmail(name);
			}
			//***********************refund*****************
			@Override
			public String refund(String orderId) {
				OrderBean order = orderRepo.getOne(orderId);
				 order.setOrderStatus("returned");
				 orderRepo.save(order );
				double a =  (order.getMinBillingAmount())*(order.getQuantity());
				PaymentDetailsBean p = paymentRepo.refund(orderId);
				double d=p.getCapStoreRevenueAmount();
				double c = d-a;
				p.setCapStoreRevenueAmount(c);
				paymentRepo.save(p);
				return "refunded";
			}
			
			/*
			 * Ganesh team*/
			@Override
			public List<PaymentDetailsBean> transactionAnalysis(Date start, Date end){
				
				return productRepo.transactionAnalysis(start, end);
			}
			
			@Transactional
			@Override
			public Double updateRevenue(String payment) {
				
				PaymentDetailsBean payment1= paymentRepo.getOne(payment);
				payment1.setTransactionId(payment);
				
				double revenue=payment1.getCapStoreRevenueAmount();
				double amt = payment1.getOrder().getMinBillingAmount();
				revenue=revenue+amt;
						
				payment1.setCapStoreRevenueAmount(revenue);
				paymentRepo.save(payment1);
				
				return revenue;
				
			}


			@Override
			@Transactional
			public MerchantBean addMerchant(MerchantBean merchant) throws UserNotFoundException{
				return	merchanRepo.save(merchant);
				 	
			}
			@Override
			@Transactional
			public void deleteMerchant(String id) throws UserNotFoundException{
				
				
				merchanRepo.deleteById(id);
				
			}

			@Override
			@Transactional
			public MerchantBean addThirdPartyMerchant(MerchantBean merchant) throws UserNotFoundException{
				return	merchanRepo.save(merchant);
			}
			@Override
			public String sendPromoToMerchant(String name) throws UserNotFoundException{
				// TODO Auto-generated method stub
				return merchanRepo.findMerchantEmail(name);
			}

			@Override
			public String sendPromoToCustomer(String name)throws UserNotFoundException {
				// TODO Auto-generated method stub
				return customerRepo.findCustomerEmail(name);
			}
			@Override
			public String regiteredNewUser(String name) throws UserNotFoundException{
				// TODO Auto-generated method stub
				return customerRepo.findCustomerEmail(name);
			}

			@Override
			public CustomerBean sentInvitationToCustomer(String email) throws UserNotFoundException{
				// TODO Auto-generated method stub
				return customerRepo.findCustomer(email);
			}

			@Override
			public MerchantBean sendInvitationToMerchant(String email)throws UserNotFoundException {
				// TODO Auto-generated method stub
				return merchanRepo.findMerchant(email);
			}

			@Override
			public String sendSchemeToMerchant(String name)throws UserNotFoundException {
				// TODO Auto-generated method stub
				return merchanRepo.findMerchantEmail(name);
			}

			@Override
			public String sendSchemeToCustomer(String name)throws UserNotFoundException {
				// TODO Auto-generated method stub
				return customerRepo.findCustomerEmail(name);
			}
			@Override
			@Transactional
			public ProductBean addProduct(String productId, String email) {
				ProductBean product = productRepo.getOne(productId);
				CustomerBean customer =customerRepo.getOne(email);
				WishlistBean wishlist = customer.getWishlist();
				List<ProductBean> prod = wishlist.getProduct();
				wishlist.setProduct(prod);
				prod.add(product);
				wishlist.setProduct(prod);
				wishlistRepo.save(wishlist);
				return product;
			}

}
