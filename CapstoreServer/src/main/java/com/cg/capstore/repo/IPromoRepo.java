package com.cg.capstore.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cg.capstore.beans.PromosBean;
@Repository
public interface IPromoRepo extends JpaRepository<PromosBean, String> {
	@Query("select p from PromosBean p where p.promoCode=(:promoCode)")
	PromosBean searchPromosByCode(@Param(value = "promoCode") String promoCode);
	@Query(value="SELECT c.email FROM CustomerBean c WHERE c.email=(:email)")
	public String findCustomerEmail(@Param(value="email") String customerName);

}
