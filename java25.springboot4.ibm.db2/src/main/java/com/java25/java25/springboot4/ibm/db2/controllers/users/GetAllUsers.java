package com.java25.java25.springboot4.ibm.db2.controllers.users;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java25.java25.springboot4.ibm.db2.dto.UserlistDto;
import com.java25.java25.springboot4.ibm.db2.services.UserService;

@RestController
@RequestMapping("/api")
public class GetAllUsers {

	private final UserService userService;
	
	public GetAllUsers(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping(path="/getallusers")
	public List<UserlistDto> getUsers() {
		return userService.getAllUsers();
	}
			
}