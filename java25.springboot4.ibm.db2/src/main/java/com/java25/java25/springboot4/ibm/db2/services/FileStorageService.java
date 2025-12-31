package com.java25.java25.springboot4.ibm.db2.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {
	
		
	@Value("${file.upload-dir}")
    private String uploadDir;
    private final Path rootLocation = Paths.get("public/users");

    public String storeFile(MultipartFile file, Long id, String oldpic) throws IOException {

    	// NORMALIZE FILE NAME
        String fileName = Objects.requireNonNull(file.getOriginalFilename());
        String extension = "";

        int lastDotIndex = fileName.lastIndexOf('.');

        // CHECK IF dot EXISTS FOR FILE EXTENSION
        if (lastDotIndex > 0) {
            extension = fileName.substring(lastDotIndex + 1);
        }
        
        String newFilename = "00" + id.toString() + "."  + extension;
        
        try {
        	System.out.print("OLD PICTURE test 1........." + oldpic);        		

//        	DELETE OLD PROFILE PICTURE
        	if (!"pix.png".equals(oldpic)) {        		
            	System.out.print("OLD PICTURE test 2........." + oldpic);        		
	        	Path fileToDelete = rootLocation.resolve(oldpic);
	        	Files.deleteIfExists(fileToDelete);
        	}
        	
            // UPLOAD FILE
            Path uploadPath = Paths.get(uploadDir+"/users").toAbsolutePath().normalize();
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // COPY FILE AND REPLACE EXISTING IMAGE
            Path filePath = uploadPath.resolve(newFilename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return newFilename;

        } catch (IOException ex) {
            throw new IOException("Could not store file " + newFilename + ". Please try again!", ex);
        }
    }
}
