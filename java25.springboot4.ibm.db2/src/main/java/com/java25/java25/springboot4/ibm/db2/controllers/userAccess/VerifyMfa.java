package com.java25.java25.springboot4.ibm.db2.controllers.userAccess;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.java25.java25.springboot4.ibm.db2.dto.MfaToken;
import com.java25.java25.springboot4.ibm.db2.services.UserService;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class VerifyMfa {

	private final UserService userService;
	
	public VerifyMfa(UserService userService) {
		this.userService = userService;
	}
		
    @PatchMapping(path="/mfa/verifytotp/{id}")	
    public ResponseEntity<Map<String, String>> mfaVerifyTotp(@RequestBody MfaToken mfaToken, @PathVariable Long id) {    	
	    return userService.getUserById(id)
	            .map(user -> {
	            	
    			    if (user.getSecret() == null) {				    	
					    return new ResponseEntity<>(
					        Map.of("message", "Multi-Factor Authenticator is not yet enabled."), 
					          HttpStatus.CONFLICT
					     );    			    	
				    }    			    
	            	
	    	        String otpcode = mfaToken.getOtp();
	    	        Integer otp = Integer.parseInt(otpcode);
	    	        String secret = user.getSecret();
	    	        GoogleAuthenticator gAuth = new GoogleAuthenticator();
	    	        boolean isCodeValid = gAuth.authorize(secret, otp);
	    	        if (isCodeValid) {    	        	
						return new ResponseEntity<>(
						        Map.of("message", "OTP code has been veriried successfully.","username", user.getUsername()), 
						            HttpStatus.OK);    			    	
	    	        	
	    	        } else {
	
	    	        	return new ResponseEntity<>(
	    			            Map.of("message", "Invalid OTP code, please try again."), 
	    			            HttpStatus.CONFLICT
	    			        );    	        	
	    	        }
	                
	            })
	            .orElseGet(() -> new ResponseEntity<>(
	                    Map.of("message", "User ID not found."), 
	                    HttpStatus.NOT_FOUND
	            ));

	}		
}