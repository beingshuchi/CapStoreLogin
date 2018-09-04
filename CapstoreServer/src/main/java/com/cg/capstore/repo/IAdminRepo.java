package com.cg.capstore.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cg.capstore.beans.AdminBean;

@Repository
public interface IAdminRepo extends JpaRepository<AdminBean, String>{
	
	@Query("SELECT admin FROM AdminBean admin WHERE admin.emailId= :email")
	AdminBean findAdmin(@Param(value="email") String email);
	@Query("SELECT admin FROM AdminBean admin WHERE admin.password= :pass")
	AdminBean findPassword(@Param(value="pass") String password);
}
