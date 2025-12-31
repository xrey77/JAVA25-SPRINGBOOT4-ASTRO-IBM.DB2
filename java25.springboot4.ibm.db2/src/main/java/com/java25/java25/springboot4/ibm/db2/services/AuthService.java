package com.java25.java25.springboot4.ibm.db2.services;

import java.util.Set;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.java25.java25.springboot4.ibm.db2.dto.RegisterDto;
import com.java25.java25.springboot4.ibm.db2.entities.Role;
import com.java25.java25.springboot4.ibm.db2.entities.User;
import com.java25.java25.springboot4.ibm.db2.repositories.RoleRepository;
import com.java25.java25.springboot4.ibm.db2.repositories.UserRepository;

@Service
@Transactional
public class AuthService {

	private final PasswordEncoder passwordEncoder;	
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final ModelMapper modelMapper;
		
    public AuthService(
    		PasswordEncoder passwordEncoder,
    		UserRepository userRepository,
    		RoleRepository roleRepository,
    		ModelMapper modelMapper) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;         
    }	
        	
    public User registerUser(RegisterDto registerDto) {

    	String encodedPassword = passwordEncoder.encode(registerDto.getPassword());
        registerDto.setPassword(encodedPassword);
        
        User user = modelMapper.map(registerDto, User.class);
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setUserpic("pix.png");

        Role userRole = roleRepository.findById((long) 1)
        		.orElseThrow(() -> new RuntimeException("Error: Role not found."));
        user.setRoles(Set.of(userRole));        
        
        user.setFirstname(registerDto.getFirstname());
        user.setLastname(registerDto.getLastname());
        user.setEmail(registerDto.getEmail());
        user.setMobile(registerDto.getMobile());
        user.setUsername(registerDto.getUsername());
        user.setPassword(registerDto.getPassword());   
        user.setIsactivated(1);
        user.setMailtoken(0);
        user.setIsblocked(1);
        user.setUserpic("pix.png");       
        User registeredUser = userRepository.save(user);        
        return registeredUser;    	
    }        
    
    public Boolean getUserEmail(String emailadd) {
    	return userRepository.existsByEmail(emailadd);
    }
        
    public Boolean getUserInfo(String username) {
    	return userRepository.existsByUsername(username);
    	
    }        
}