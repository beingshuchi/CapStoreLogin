package com.cg.capstore.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name="admin")
public class AdminBean {
	
	@Override
	public String toString() {
		return "AdminBean [emailId=" + emailId + ", Password=" + password + "]";
	}
	@Id
	@Column(name="admin_id")
	private String emailId;
	@Column(name="password")
	private String password;

	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public AdminBean(String emailId, String password) {
		super();
		this.emailId = emailId;
		this.password = password;
	}
	public AdminBean() {
		super();
	}
	
	
}
