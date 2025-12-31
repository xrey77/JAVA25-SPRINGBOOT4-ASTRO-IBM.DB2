package com.java25.java25.springboot4.ibm.db2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import com.java25.java25.springboot4.ibm.db2.entities.User;
import com.java25.java25.springboot4.ibm.db2.repositories.UserRepository;

@Component
public class JwtUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			    throw new UsernameNotFoundException("User not found with username");			 
		}
	    return user;			 		 
	}	
}