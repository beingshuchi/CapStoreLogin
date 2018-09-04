package com.cg.capstore.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cg.capstore.beans.DiscountBean;
import com.cg.capstore.beans.PromosBean;
import com.cg.capstore.exception.DiscountDateExceedException;
import com.cg.capstore.exception.PromoCodeInvalidException;
import com.cg.capstore.exception.SearchException;
import com.cg.capstore.service.IPromosAndDiscountService;

@RestController
public class PromosAndDiscountController {
	@Autowired
	private IPromosAndDiscountService service;
	@RequestMapping(value="/searchpromocode",method=RequestMethod.GET)
	public PromosBean searchPromosByCode(String Code)throws SearchException {
		return service.searchPromosByCode(Code);	
}
	@RequestMapping(value="/searchdiscountid",method=RequestMethod.GET)
	public DiscountBean searchDiscountById(String Id)throws SearchException {
		return service.searchDiscountById(Id);	
}
	

	@RequestMapping(value = "/adddiscount", method = RequestMethod.POST)
	public DiscountBean addDiscount(DiscountBean discount) throws DiscountDateExceedException {
	
			return service.addDiscount(discount);
		
	}
	
	@RequestMapping(value = "/viewdiscountbyid", method = RequestMethod.GET)
	public DiscountBean viewDiscountById(String discountId) throws DiscountDateExceedException {
		
			return service.viewDiscountById(discountId);
		
	}
	
	@RequestMapping(value = "/viewalldiscount", method = RequestMethod.GET)
	public List<DiscountBean> findAllDiscounts() throws DiscountDateExceedException{
		
			return service.findAllDiscounts();
		 
	}
	@RequestMapping(value = "/addpromo", method = RequestMethod.POST)
	public String promoDb(PromosBean promoss) throws PromoCodeInvalidException {
		System.err.println("Back"+promoss.toString());
		//try {
			return service.addPromo(promoss);
		/*} catch (PromoCodeInvalidException e) {
			throw e;
		}*/
	}
	
	@RequestMapping(value = "/viewpromobyid", method = RequestMethod.GET)
	public PromosBean viewById(PromosBean pId) throws PromoCodeInvalidException {
		//System.out.println(pId.getPromoCode());
		//try {
			return service.viewByPromoCode(pId.getPromoCode());
		/*} catch (PromoCodeInvalidException e) {
			throw e;
		}*/
	}
	
	@RequestMapping(value = "/viewallpromos", method = RequestMethod.GET)
	public List<PromosBean> viewAllPromos() throws PromoCodeInvalidException{
		//try {
			return service.viewAllPromos();
		/*} catch (PromoCodeInvalidException e) {
			throw e;
		}*/
	}
}
