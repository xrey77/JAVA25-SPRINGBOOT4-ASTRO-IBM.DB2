package com.java25.java25.springboot4.ibm.db2.dto;

public class UserlistDto {
	private int id;
	private String firstname;
	private String lastname;
	private String email;
	private String mobile;
	private String username;
	private String roles;
	private int isactivated;
	private int isblocked;
	private String userpic;
	private String qrcodeurl;
	
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
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
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
	public String getUserpic() {
		return userpic;
	}
	public void setUserpic(String userpic) {
		this.userpic = userpic;
	}
	public String getQrcodeurl() {
		return qrcodeurl;
	}
	public void setQrcodeurl(String qrcodeurl) {
		this.qrcodeurl = qrcodeurl;
	}			
}