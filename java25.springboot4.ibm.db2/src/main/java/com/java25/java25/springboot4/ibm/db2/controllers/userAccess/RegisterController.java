package com.java25.java25.springboot4.ibm.db2.controllers.userAccess;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.java25.java25.springboot4.ibm.db2.dto.RegisterDto;
import com.java25.java25.springboot4.ibm.db2.services.AuthService;

@RestController
@RequestMapping("/auth")
public class RegisterController {

	private final AuthService authService;
		
	public RegisterController(AuthService authService) {
		this.authService = authService;
	}
			
    @PostMapping(path="/signup")	
	public ResponseEntity<Map<String, String>> signUp(@RequestBody RegisterDto user) {
    	
    	Boolean userEmail = authService.getUserEmail(user.getEmail());
    	if (userEmail) {
            HashMap<String, String> map = new HashMap<>();
            map.put("message", "Email Address is already taken.");
            return new ResponseEntity<>(map, HttpStatus.CONFLICT);             		
    	}
    	
    	Boolean userName = authService.getUserInfo(user.getUsername());
    	if (userName) {
            HashMap<String, String> map = new HashMap<>();
            map.put("message", "Username is already taken.");        	
            return new ResponseEntity<>(map, HttpStatus.CONFLICT);             		    		
    	}
    	
    	authService.registerUser(user);
        HashMap<String, String> map = new HashMap<>();
        map.put("message", "You have registered successfully, please login now.");
        return new ResponseEntity<>(map, HttpStatus.CREATED);         

	}
}