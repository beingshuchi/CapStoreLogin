package com.cg.capstore.service;

import java.util.List;

import com.cg.capstore.beans.DiscountBean;
import com.cg.capstore.beans.PromosBean;
import com.cg.capstore.exception.DiscountDateExceedException;
import com.cg.capstore.exception.PromoCodeInvalidException;
import com.cg.capstore.exception.SearchException;

public interface IPromosAndDiscountService {
	PromosBean searchPromosByCode(String Code)throws SearchException;
	DiscountBean searchDiscountById(String Id)throws SearchException;
	DiscountBean addDiscount(DiscountBean discount) throws DiscountDateExceedException;
	DiscountBean viewDiscountById(String discountId) throws DiscountDateExceedException;
	List<DiscountBean> findAllDiscounts()throws DiscountDateExceedException;
	String addPromo(PromosBean promoss) throws PromoCodeInvalidException ;
	PromosBean viewByPromoCode(String pId) throws PromoCodeInvalidException;
	List<PromosBean> viewAllPromos() throws PromoCodeInvalidException;
	
}
