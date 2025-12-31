package com.java25.java25.springboot4.ibm.db2.controllers.users;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.java25.java25.springboot4.ibm.db2.services.UserService;

@RestController
@RequestMapping("/api")
public class GetUserId {

	private final UserService userService;
		
	public GetUserId(UserService userService) {
		this.userService = userService;		
	}
		
	public record GetuserResponse(
		    Long id, 
		    String firstname, 
		    String lastname,
		    String email,
		    String mobile,
		    String username,
		    Integer isactivated,
		    Integer isblocked,
		    Integer mailtoken,
		    String userpic,
		    boolean mfaactivated,
		    String qrcodeurl		    
		) {}	
	

	@GetMapping(path="/getuserid/{id}")
	public ResponseEntity<Object> getUserById(@PathVariable Long id) {
	    return userService.getUserById(id)
	            .<ResponseEntity<Object>>map(user -> {
	            	
	            	String qrcode = null;
	            	if (user.getMfaactivated()) {
	            		qrcode = user.getQrcodeurl();
	            	}
	            	
	                GetuserResponse response = new GetuserResponse(
	                        user.getId(), user.getFirstname(), user.getLastname(),
	                        user.getEmail(), user.getMobile(), user.getUsername(),
	                        user.getIsactivated(), user.getIsblocked(), user.getMailtoken(),
	                        user.getUserpic(), user.getMfaactivated(), qrcode
	                );
	                return ResponseEntity.ok(response);
	            })
	            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
	                    .body(Map.of("message", "User ID not found.")));
	}
	
}