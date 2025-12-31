package com.java25.java25.springboot4.ibm.db2.controllers.users;

import java.io.IOException;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.java25.java25.springboot4.ibm.db2.services.FileStorageService;
import com.java25.java25.springboot4.ibm.db2.services.UserService;

@RestController
@RequestMapping("/api")
public class UploadPicture {

	private final UserService userService;	
    private final FileStorageService fileStorageService;
	
	public UploadPicture(
			FileStorageService fileStorageService,
			UserService userService) {
		this.userService = userService;
		this.fileStorageService = fileStorageService;
	}
	
	@PatchMapping(path="/uploadpicture/{id}")
	public ResponseEntity<Map<String, String>>  uploadProfilePicture(
			@RequestParam("userpic") MultipartFile file,
			@PathVariable Long id) throws java.io.IOException {
		
		return userService.getUserById(id)
		        .map(user -> {
		            // Success Logic: User found
		            String oldPic = user.getUserpic();
		            String fileName = null;
					try {
						fileName = fileStorageService.storeFile(file, id, oldPic);
					} catch (IOException e) {
						e.printStackTrace();
					}
		            
		            userService.updateProfilepic(id, fileName);
		            
		            return ResponseEntity.ok(Map.of(
		                "message", "You have changed your profile picture successfully.",
		                "userpic", fileName 
		            ));
		        })
		        .orElseGet(() -> {
		            return new ResponseEntity<>(
		                Map.of("message", "User ID not found."), 
		                HttpStatus.NOT_FOUND
		            );
		        });		
	}		
}