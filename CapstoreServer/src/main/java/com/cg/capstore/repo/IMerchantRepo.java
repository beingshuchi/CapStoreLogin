package com.cg.capstore.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.cg.capstore.beans.MerchantBean;
@Repository
public interface IMerchantRepo extends JpaRepository<MerchantBean, String> {
	
	@Query("SELECT merchant FROM MerchantBean merchant WHERE merchant.emailId= :email")
	MerchantBean findMerchant(@Param(value="email") String email);
	@Query("SELECT merchant FROM MerchantBean merchant WHERE merchant.password= :pass")
	MerchantBean findPassword(@Param(value="pass")String password);
	@Query("select p from MerchantBean p where merchantName=(:merchantName)")
	public MerchantBean searchMerchantByName(@Param(value = "merchantName") String name);
	@Query(value = "SELECT m.emailId FROM MerchantBean m WHERE m.merchantName=(:name)")
	public String findMerchantEmail(@Param(value = "name") String name);
}
