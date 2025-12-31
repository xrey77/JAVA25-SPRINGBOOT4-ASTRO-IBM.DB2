package com.java25.java25.springboot4.ibm.db2.services;

//import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.java25.java25.springboot4.ibm.db2.dto.ProfileDto;
import com.java25.java25.springboot4.ibm.db2.dto.UserlistDto;
import com.java25.java25.springboot4.ibm.db2.entities.User;
import com.java25.java25.springboot4.ibm.db2.repositories.UserRepository;
import java.util.Optional;

@Service
public class UserService {

	private final UserRepository userRepository;	
	private final ModelMapper modelMapper;
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public UserService(
			UserRepository userRepository,
			PasswordEncoder passwordEncoder,
			ModelMapper modelMapper) {
		this.userRepository = userRepository;
		this.modelMapper = modelMapper;
		this.passwordEncoder = passwordEncoder;
	}
			
	public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
	
    public List<UserlistDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserlistDto> userlistDto = users.stream()
                .map(user -> modelMapper.map(user, UserlistDto.class))
                .collect(Collectors.toList());
        return userlistDto;
    }        
    
    public User getUserName(String username) {
    	return userRepository.findByUsername(username);
    	
    }
    
    public Boolean findUserID(Long id) {
        return userRepository.findById(id).isPresent();
    }
            
    
    @Transactional
    public User changePassword(Long id, String newpassword) {
    	String encodedPassword = passwordEncoder.encode(newpassword);

    	User pwdToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    	pwdToUpdate.setPassword(encodedPassword);
    	return userRepository.save(pwdToUpdate);
    }
	
    @Transactional
    public User updateProfilepic(Long id, String newfile) {
    	User pictureToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    	pictureToUpdate.setUserpic(newfile);
    	return userRepository.save(pictureToUpdate);
    	
    }
    
    @Transactional
    public int enableMfa(Long id, String secret, String qrcodebase64) {
    	userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));        
		return userRepository.updateQrcodeurlById(id, qrcodebase64, secret);
    }
    
    @Transactional
    public int disableMfa(Long id) {
    	userRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    	return userRepository.updateQrcodeurlByIdToNull(id);
    }
    
    @Transactional
    public User updateUserProfile(Long id, ProfileDto profileDtls) {
    	
    	User profileToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    	
        modelMapper.map(profileDtls, profileToUpdate);
        User updatedUser = userRepository.save(profileToUpdate);        
        return updatedUser;    	    	    	
    }    
}