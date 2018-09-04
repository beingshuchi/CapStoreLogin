package com.cg.capstore.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.capstore.beans.DiscountBean;
import com.cg.capstore.beans.PromosBean;
import com.cg.capstore.exception.DiscountDateExceedException;
import com.cg.capstore.exception.PromoCodeInvalidException;
import com.cg.capstore.exception.SearchException;
import com.cg.capstore.repo.IDiscountRepo;
import com.cg.capstore.repo.IPromoRepo;

@Service
public class PromosAndDiscountServiceImpl implements IPromosAndDiscountService {
	
	@Autowired
	private IDiscountRepo discountRepo;
	@Autowired
	private IPromoRepo promoRepo;

	
	@Override
	public PromosBean searchPromosByCode(String Code)throws SearchException {
		// TODO Auto-generated method stub
		return promoRepo.searchPromosByCode(Code);
	}

	@Override
	public DiscountBean searchDiscountById(String Id)throws SearchException {
		// TODO Auto-generated method stub
		return discountRepo.searchDiscountById(Id);
	}
	
	@Override
	@Transactional
	public DiscountBean addDiscount(DiscountBean discount) throws DiscountDateExceedException {
		/*if(discount.getDiscountId()!= null) {
			throw new DiscountDateExceedException("Discount is already available");
		}else {*/
		return discountRepo.save(discount);
		//}
	}

	@Override
	public DiscountBean viewDiscountById(String discountId) throws DiscountDateExceedException {
		/*if(discountId==null) {
			throw new DiscountDateExceedException("Discount Id is not available");
		}*/
		
		return discountRepo.getOne(discountId);
	}

	@Override
	public List<DiscountBean> findAllDiscounts() throws DiscountDateExceedException {
		/*if(DiscountBean.class== null) {
			throw new DiscountDateExceedException("Discount is not available");
		}else {*/
		return discountRepo.findAll();
	//}
	}
	@Override
	@Transactional
	public String addPromo(PromosBean promos) throws PromoCodeInvalidException {
		
		/*if(promoss.getPromoCode()==null) {
			throw new PromoCodeInvalidException("PromoCode is Not Valid");
		}*/
		//else {
		promoRepo.save(promos);
		return promos.getPromoCode()+" is added ";
	//}
	}

	@Override
	public PromosBean viewByPromoCode(String pId) throws PromoCodeInvalidException{
		/*if(pId==null) {
			throw new PromoCodeInvalidException("Promo code is not a valid one");
		}else*/
		return promoRepo.getOne(pId);
	}

	@Override
	public List<PromosBean> viewAllPromos() throws PromoCodeInvalidException {
		/*if(PromosBean.class==null) {
			throw new PromoCodeInvalidException("No Promos Available");
		}
		else*/
		return promoRepo.findAll();
	}
}
