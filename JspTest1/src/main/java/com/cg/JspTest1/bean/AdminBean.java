package com.cg.JspTest1.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name="admin")
public class AdminBean {
	
	@Id
	@Column(name="admin_id")
	private String emailId;
	@Column(name="password")
	private String Password;

	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		this.Password = password;
	}
	public AdminBean(String emailId, String password) {
		super();
		this.emailId = emailId;
		this.Password = password;
	}
	public AdminBean() {
		super();
	}
	
	
}
