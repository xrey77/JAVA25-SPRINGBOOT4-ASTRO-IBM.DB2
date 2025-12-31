package com.java25.java25.springboot4.ibm.db2.controllers.userAccess;

import java.util.Map;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.java25.java25.springboot4.ibm.db2.dto.MfaActivateDto;
import com.java25.java25.springboot4.ibm.db2.services.TwoFactorAuthService;
import com.java25.java25.springboot4.ibm.db2.services.UserService;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ActivateMfa {
	
	private final UserService userService;
	private final TwoFactorAuthService twoFactorAuthService;
	
	@Autowired
	public ActivateMfa(
			TwoFactorAuthService twoFactorAuthService,
			UserService userService) {
		this.userService = userService;
		this.twoFactorAuthService = twoFactorAuthService;
	}
		
    @PatchMapping(path="/mfa/activate/{id}")	
	public ResponseEntity<Map<String, String>> mfaActivate(@RequestBody MfaActivateDto mfaDto, @PathVariable Long id) {    	
    	
    	return userService.getUserById(id)
    			.map(user -> {		        	
    			    
    		    	if (mfaDto.isTwoFactorEnabled()) {
	    	    		String secret = twoFactorAuthService.generateSecretKey();
	    	            String dataUri = twoFactorAuthService.getQRCodeUrl(secret, user.getEmail(), "CONSTRUCTION INC.");
	    	    		
	    	    		String base64String = "";
	    				try {
	    					base64String = generateQRBase64(dataUri);
	    				} catch (Exception e) {
	    					e.printStackTrace();
	    				}
	    				userService.enableMfa(id, secret, base64String);
        			    return ResponseEntity.ok(Map.of(
        			            "message", "Multi-Factor Authenticator has been enabled.",
        			            "qrcodeurl", base64String
        			        ));		        	
    		    		
    		    	}
    		    	else {
    		    		
        				userService.disableMfa(id);
        			    return ResponseEntity.ok(Map.of(
        			            "message", "Multi-Factor Authenticator has been disabled."
        			        ));		        	
    		    		
    		    	}
    			})
    			.orElseGet(() -> {
    				
    			    return new ResponseEntity<>(
    			            Map.of("message", "User iD not found."), 
    			            HttpStatus.NOT_FOUND
    			        );
    			});		
    	
	}
	
    public String generateQRBase64(String otpAuthUri) throws Exception {
        if (otpAuthUri == null || otpAuthUri.isBlank()) {
            throw new IllegalArgumentException("URI cannot be empty");
        }

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(otpAuthUri, BarcodeFormat.QR_CODE, 200, 200);

        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ImageIO.write(bufferedImage, "PNG", outputStream);
            return "data:image/png;base64," + Base64.getEncoder().encodeToString(outputStream.toByteArray());
        }        
    }           
}