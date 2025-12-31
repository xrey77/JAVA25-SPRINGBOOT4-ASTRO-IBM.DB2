package com.java25.java25.springboot4.ibm.db2.controllers.users;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.java25.java25.springboot4.ibm.db2.dto.ProfileDto;
import com.java25.java25.springboot4.ibm.db2.services.UserService;

@RestController
@RequestMapping("/api")
public class UpdateProfile {

	private final UserService userService;
	
	public UpdateProfile(UserService userService) {
		this.userService = userService;
	}
	
	@PatchMapping(path="/updateprofile/{id}")
	public ResponseEntity<Map<String, String>>  profileUpdate(@RequestBody ProfileDto profileDto, @PathVariable Long id) {
		
		Boolean idno = userService.findUserID(id);
		if (idno) {
				
			userService.updateUserProfile(id, profileDto);
			HashMap<String, String> map = new HashMap<>();
	        map.put("message", "You have updated your profile successfully.");
	        return new ResponseEntity<>(map, HttpStatus.OK);
			
		} else {
			
			HashMap<String, String> map = new HashMap<>();
	        map.put("message", "User ID not found.");          	
	        return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);         			
			
		}		
	}	
}