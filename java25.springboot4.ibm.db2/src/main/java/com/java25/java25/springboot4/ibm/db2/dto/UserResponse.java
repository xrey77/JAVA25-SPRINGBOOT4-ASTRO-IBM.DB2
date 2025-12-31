package com.java25.java25.springboot4.ibm.db2.dto;

public class UserResponse {

    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String mobile;
    private String username;
    private Integer isactivated;
    private Integer isblocked;
    private String userpic;
    private String qrcodeurl;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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
	public Integer getIsactivated() {
		return isactivated;
	}
	public void setIsactivated(Integer isactivated) {
		this.isactivated = isactivated;
	}
	public Integer getIsblocked() {
		return isblocked;
	}
	public void setIsblocked(Integer isblocked) {
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
	public UserResponse(Long id, String firstname, String lastname, String email, String mobile, String username,
			Integer isactivated, Integer isblocked, String userpic, String qrcodeurl) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.mobile = mobile;
		this.username = username;
		this.isactivated = isactivated;
		this.isblocked = isblocked;
		this.userpic = userpic;
		this.qrcodeurl = qrcodeurl;
	}
    

	
    
    
	
}
