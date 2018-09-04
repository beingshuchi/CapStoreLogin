package com.cg.capstore.service;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.capstore.beans.CouponsBean;
import com.cg.capstore.beans.CustomerBean;
import com.cg.capstore.beans.OrderBean;
import com.cg.capstore.beans.ProductBean;
import com.cg.capstore.beans.WishlistBean;
import com.cg.capstore.exception.CartException;
import com.cg.capstore.exception.OrderDetailsNotFoundException;
import com.cg.capstore.exception.TransactionException;
import com.cg.capstore.exception.UserNotFoundException;
import com.cg.capstore.repo.ICouponRepo;
import com.cg.capstore.repo.ICustomerRepo;
import com.cg.capstore.repo.IOrderRepo;
import com.cg.capstore.repo.IProductRepo;
import com.cg.capstore.repo.IWishlistRepo;

@Service
public class CustomerServiceImpl implements ICustomerService{
	
	@Autowired
	private ICustomerRepo customerRepo;

	@Autowired
	private IProductRepo productRepo;
	@Autowired
	private IOrderRepo orderRepo;
	@Autowired
	private IWishlistRepo wishlistRepo;
	@Autowired
	private ICouponRepo couponRepo;
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

	private String encryption(String password) {
		StringBuilder sb=new StringBuilder(password);
		sb.reverse().append(password);
		return sb.toString();
	}
	@Override
	@Transactional
	public Boolean changePasswordCustomer(String oldPassword, String newPassword) throws UserNotFoundException {
		// TODO Auto-generated method stub
		oldPassword=encryption(oldPassword);
		CustomerBean customer= customerRepo.findPassword(oldPassword);
		if(oldPassword.equals(customer.getPassword())) {
			newPassword=encryption(newPassword);
			customer.setPassword(newPassword);
			customerRepo.save(customer);
			return true;
		}
		return false;
	}

	@Override
	public CustomerBean getCustomer(String email) throws UserNotFoundException {
		// TODO Auto-generated method stub
		return customerRepo.findCustomer(email);
	}
	@Override
	@Transactional
	public CustomerBean editProfileCustomer(String email,String customerName, String address, String mobileNo)
			throws UserNotFoundException {
		// TODO Auto-generated method stub
		CustomerBean customer=customerRepo.findCustomer(email);
		if(customer!=null) {
			customer.setCustomerName(customerName);
			customer.setAddress(address);
			customer.setMobileNo(mobileNo);
			customerRepo.save(customer);
			return customer;
		}
		return null;
	}

	@Override
	@Transactional
	public Boolean confirmSignUpCustomer(String customerName, String mobileNo, String email, String password, String address)
			throws UserNotFoundException {
		// TODO Auto-generated method stub
		password=encryption(password);
		CustomerBean customer = new CustomerBean();
		customer.setCustomerName(customerName);
		customer.setAddress(address);
		customer.setEmail(email);
		customer.setPassword(password);
		customer.setMobileNo(mobileNo);
		customerRepo.save(customer);
		return true;
	}

	@Override
	@Transactional
	public OrderBean placingOrderOfProduct(OrderBean orderBean,String couponCode) throws OrderDetailsNotFoundException {
		if(orderBean.getOrderId()== null) {
			throw new OrderDetailsNotFoundException("Product is not available");
		}
		
		else {
		if(orderBean.getMinBillingAmount()<orderBean.getTotalPrice()) {
		double price=applyCoupons(couponCode, orderBean.getTotalPrice());
		orderBean.setTotalPrice(price);
		}
		
		return orderRepo.save(orderBean);
	}}
	private Double applyCoupons(String couponCode,Double price)
	{
		CouponsBean coupon1=couponRepo.getCouponDetails(couponCode);
		LocalDate startDate=coupon1.getStartDate().toLocalDate();
		LocalDate endDate=coupon1.getEndDate().toLocalDate();
		Double totalPrice=0.0;
		if(coupon1.getCouponCode().equals(couponCode)&&startDate.isBefore(LocalDate.now())&&endDate.isAfter(LocalDate.now()))
		{
			totalPrice=price-((price*coupon1.getCouponAmount())/100);
		}
			return totalPrice;
	}
	@Transactional
	@Override
	public List<ProductBean> addProductToCart(String emailId, String productId) throws CartException {

		CustomerBean customer = customerRepo.findCustomer(emailId);
		ProductBean product = productRepo.getProduct(productId);

		
		if(customer==null) {
			throw new CartException("customer doesnt exists");
		}else if(product==null){
			throw new CartException("product doesnt exists");
		}else {
			
		
			customer.getCart().add(product);
			customerRepo.save(customer);
		

		return customer.getCart();
		}
	}
	
	@Transactional
	@Override
	public List<ProductBean> removeProductFromProduct(String email, String productId) throws CartException {

		CustomerBean customer = customerRepo.findCustomer(email);
		ProductBean product = productRepo.getProduct(productId);

		if(customer==null) {
			throw new CartException("customer doesnt exists");
		}else if(product==null){
			throw new CartException("product doesnt exists");
		}else
		{
			if (customer.getCart().contains(product)) 
			{
			customer.getCart().remove(product);
			}
			else {
				throw new CartException("product is not in cart");
			}
			customerRepo.save(customer);
			return customer.getCart();
		}
	}

	@Override
	public List<ProductBean> displayCart(String emailId) throws CartException {

		CustomerBean customer = customerRepo.findCustomer(emailId);
		if(customer==null) {
			throw new CartException("customer doesnt exists");
		}
		else {
			return customer.getCart();
		}
		
	}
	@Override
	public OrderBean getTransactionalDetails(String orderId) throws TransactionException {

		return orderRepo.getOne(orderId);
	}

	@Override
	public String generateInvoice(String customerId)
	
	{
		
		String res = "";
				
		CustomerBean bean = customerRepo.getOne(customerId);
		List<ProductBean> cartProd = bean.getCart();
		
		System.err.println(cartProd);
		
		for (ProductBean productBean : cartProd) {
			res = "<ul><li>Product Name: "+productBean.getProductName() + "</li>"
					+ "<br/><li> Product Price: "+productBean.getPrice()+"</li></ul>";
		}
		
		return "Hello "+customerId+"<br/> Your Product(s) are<br/>"+res;
		
	}
	@Override
	public CustomerBean shippingDetailsMsg(String email) {
		
		CustomerBean customerBean=customerRepo.findCustomer(email);
		
		return customerBean;
	}
	
	@Override
	@Transactional
	public WishlistBean deleteProductsFromWishlist(String email,String productId) {
		ProductBean product = productRepo.getOne(productId);
		CustomerBean customer = customerRepo.getOne(email);
		WishlistBean wishlist=wishlistRepo.getOne(customer.getWishlist().getWishlistId());
		List<ProductBean> prod = wishlist.getProduct();
		prod.remove(product);
		wishlist.setProduct(prod);
		wishlistRepo.save(wishlist);
		return wishlist;
		
	}

	@Override
	public WishlistBean displayProductsFromWishlist(String email) {
		System.err.println("email:"+email);
		CustomerBean customer = customerRepo.getOne(email);
		String wishlistId = customer.getWishlist().getWishlistId();
		return wishlistRepo.getOne(wishlistId);
	}
	@Override
	public String inviteFriend(String customerName) {
		// TODO Auto-generated method stub
		return customerRepo.findCustomerEmail(customerName);
	}
}
