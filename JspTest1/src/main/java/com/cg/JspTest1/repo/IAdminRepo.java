package com.cg.JspTest1.repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cg.JspTest1.bean.AdminBean;
@Repository
public interface IAdminRepo extends JpaRepository<AdminBean, String> {
	@Transactional
	@Query("SELECT admin FROM AdminBean admin WHERE admin.emailId=?1")
	AdminBean FinByEmail(String email);
}
