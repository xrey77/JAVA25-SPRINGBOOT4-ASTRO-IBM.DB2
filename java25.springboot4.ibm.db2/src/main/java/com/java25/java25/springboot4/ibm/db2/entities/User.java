package com.java25.java25.springboot4.ibm.db2.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.persistence.*;
import org.jspecify.annotations.Nullable;
import org.hibernate.annotations.ColumnDefault;


@Entity
@Table(name = "users")
public class User  implements Serializable, UserDetails {

    private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    private String firstname;
    private String lastname;

    @Column(nullable = false, unique = true)
    private String email;

    private String mobile;
    @Column(nullable = false, unique = true)
    private String username;
    private String password;
    private String userpic;
    private Integer isactivated;
    private Integer isblocked;
    private Integer mailtoken;
    
    @Column(name = "mfaactivated", nullable = false)
    @ColumnDefault("false")
    private Boolean mfaactivated = false;
    
    
    @Nullable
    @Column(name = "secret", nullable = true)    
    private String secret = null;

    @Lob
    @Nullable    
    @Column(name = "qrcodeurl", columnDefinition = "CLOB", nullable = true)
    private String qrcodeurl;
    
    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;    
            
    public User() {}

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserpic() {
		return userpic;
	}

	public void setUserpic(String userpic) {
		this.userpic = userpic;
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

	public Integer getMailtoken() {
		return mailtoken;
	}

	public void setMailtoken(Integer mailtoken) {
		this.mailtoken = mailtoken;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}
		
	public String getQrcodeurl() {
		return qrcodeurl;
	}

	public void setQrcodeurl(String qrcodeurl) {
		this.qrcodeurl = qrcodeurl;
	}

	public Boolean getMfaactivated() {
		return mfaactivated;
	}

	public void setMfaactivated(Boolean mfaactivated) {
		this.mfaactivated = mfaactivated;
	}
	
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
	    name = "user_roles",
	    joinColumns = @JoinColumn(name = "user_id"),
	    inverseJoinColumns = @JoinColumn(name = "role_id")
	)
	private Set<Role> roles;	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	    if (this.roles == null) {
	        return Collections.emptyList();
	    }
	    return this.roles.stream()
	            .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
	            .collect(Collectors.toList());
	}

	
	public void setRoles(Set<Role> roles) {
	    if (this.roles == null) {
	        this.roles = roles;
	    } else {
	        this.roles.clear();
	        if (roles != null) {
	            this.roles.addAll(roles);
	        }
	    }
	}


}
