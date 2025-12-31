package com.java25.java25.springboot4.ibm.db2.controllers.userAccess;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java25.java25.springboot4.ibm.db2.dto.LoginDto;
import com.java25.java25.springboot4.ibm.db2.entities.User;
import com.java25.java25.springboot4.ibm.db2.services.JwtService;
import com.java25.java25.springboot4.ibm.db2.services.UserService;

@RestController
@RequestMapping("/auth")
public class LoginController {

	private final UserService userService;	
	private final JwtService jwtService;	
	private final PasswordEncoder passwordEncoder;
	
	public LoginController(
			PasswordEncoder passwordEncoder,
			JwtService jwtService,
			UserService userService) {
		this.userService = userService;
		this.jwtService = jwtService;
		this.passwordEncoder = passwordEncoder;
	}
	
	public record LoginResponse(
		    String message, 
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
		    String qrcodeurl,
		    String token
		) {}	
	
	
	@PostMapping(path="/signin")
	public ResponseEntity<?> signIn(@RequestBody LoginDto loginDto) {
	    return switch (userService.getUserName(loginDto.getUsername())) {
	        // Matches valid user and checks password
	        case User user when passwordEncoder.matches(loginDto.getPassword(), user.getPassword()) -> {
	            String token = jwtService.generateToken2(user);
	            
            	String qrcode = null;
            	if (user.getMfaactivated()) {
            		qrcode = user.getQrcodeurl();
            	}
	            
	            LoginResponse response = new LoginResponse(
	                "You have logged-in successfully.",
	                user.getId(),
	                user.getFirstname(),
	                user.getLastname(),
	                user.getEmail(),
	                user.getMobile(),
	                user.getUsername(),
	                user.getIsactivated(),
	                user.getIsblocked(),
	                user.getMailtoken(),
	                user.getUserpic(),
	                user.getMfaactivated(),
	                qrcode,
	                token
	            );
	            yield ResponseEntity.ok(response);
	        }
	        case User xuser -> ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                                    .body(Map.of("message", "Invalid password."));
	        case null -> ResponseEntity.status(HttpStatus.NOT_FOUND)
	                                   .body(Map.of("message", "Username not found."));
	    };
	}

}


