package com.java25.java25.springboot4.ibm.db2.dto;

import java.util.Optional;

public class UserDto {
	private int id;
	private String firstname;
	private String lastname;
	private String email;
	private String mobile;
	private String username;
	private String password;
	private Optional<String> roles;
	private int isactivated;
	private int isblocked;
	private int mailtoken;
	private Optional<String> userpic;
	private Optional<String> qrcodeurl;
	private Optional<String> secret;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getFirstname() {
		return firstname;
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public String getLastname() {
		return lastname;
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Optional<String> getRoles() {
		return roles;
	}
	
	public void setRoles(Optional<String> roles) {
		this.roles = roles;
	}
	
	public int getIsactivated() {
		return isactivated;
	}
	
	public void setIsactivated(int isactivated) {
		this.isactivated = isactivated;
	}
	
	public int getIsblocked() {
		return isblocked;
	}
	
	public void setIsblocked(int isblocked) {
		this.isblocked = isblocked;
	}
	
	public int getMailtoken() {
		return mailtoken;
	
	}
	public void setMailtoken(int mailtoken) {
		this.mailtoken = mailtoken;
	}
	
	public Optional<String> getUserpic() {
		return userpic;
	}
	
	public void setUserpic(Optional<String> userpic) {
		this.userpic = userpic;
	}
	
	public Optional<String> getQrcodeurl() {
		return qrcodeurl;
	}
	
	public void setQrcodeurl(Optional<String> qrcodeurl) {
		this.qrcodeurl = qrcodeurl;
	}
	
	public Optional<String> getSecret() {
		return secret;
	}
	
	public void setSecret(Optional<String> secret) {
		this.secret = secret;
	}	
}