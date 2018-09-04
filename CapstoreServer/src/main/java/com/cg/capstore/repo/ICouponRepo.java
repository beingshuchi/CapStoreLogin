package com.cg.capstore.repo;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cg.capstore.beans.CouponsBean;
@Repository
public interface ICouponRepo extends JpaRepository<CouponsBean, String> {

	@Query(value="select c from CouponsBean c where c.couponCode=(:couponCode) ")
	CouponsBean getCouponDetails(@Param("couponCode")String couponCode);
}
