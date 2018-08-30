package com.cg.JspTest1.repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.cg.JspTest1.bean.MerchantBean;
@Repository
public interface IMerchantRepo extends JpaRepository<MerchantBean, String> {
	@Transactional
	@Query("SELECT merchant FROM MerchantBean merchant WHERE merchant.emailId=?1")
	MerchantBean FinByEmail(String email);
}
