package com.java25.java25.springboot4.ibm.db2.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.java25.java25.springboot4.ibm.db2.entities.User;
import java.util.stream.Collectors;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;


@Service
public class JwtService {
	
	@Value("${jwt.secret}")
	private String secretKey;
	
	public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .header().add("typ", "JWT").and()
                .issuer("SUPERCAR INC.")
                .subject(username)
                .claim("role", authorities)
                .issuedAt(new Date())										  // 1hr = 3600000 milliseconds
                .expiration(new Date(System.currentTimeMillis() + 28800000))  // 8hr * 3600000 = 28800000 
                .signWith(getSigningKey())
                .compact();
    }

    private SecretKey getSigningKey() {
        // Ensure the secret is at least 256 bits (32 characters)
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
	
    
	private Claims extractAllClaims(String token) {
		try {
		return Jwts
				.parser()            // Now returns JwtParserBuilder
			    .verifyWith(getSigningKey2())               // Replaces setSigningKey()
			    .build()                             // Builds the immutable JwtParser
			    .parseSignedClaims(token)            // Replaces parseClaimsJws()
			    .getPayload();  				
		} catch(Exception e) {
			return null;
		}
	}
    
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    	try {
	        final Claims claims = extractAllClaims(token);
	        System.out.println("CLAIMS : " + claims);
	        return claimsResolver.apply(claims);
    	} catch(Exception ex) {
    		return null;
    	}
    }
    
	
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    
    
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
	    
    
    public Boolean validateToken(String token, UserDetails userDetails) {
    	try {
	        final String username = extractUsername(token);
	        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    	} catch(Exception ex) {
    		return null;
    	}
    }
    	
	public String generateToken2(User user) {
		
	    String scope = user.getAuthorities().stream()
	    .map(GrantedAuthority::getAuthority)
	    .collect(Collectors.joining(" "));		
		
	    return Jwts.builder()
	            .issuer("SUPERCAR INC.")
	            .subject(user.getEmail())
	            .claim("scope", scope)
	            .issuedAt(new Date())
	            .expiration(new Date(System.currentTimeMillis() + 900_000)) // 15 mins
	            .signWith(getSigningKey2())
	            .compact();
	}
        
    private SecretKey getSigningKey2() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}