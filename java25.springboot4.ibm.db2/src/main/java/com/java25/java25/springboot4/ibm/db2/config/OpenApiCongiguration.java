package com.java25.java25.springboot4.ibm.db2.config;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiCongiguration {

	  @Value("${server.port:9090}")
	  private String prodUrl;

	  @Bean
	  public OpenAPI barclaysOpenAPI() {

		Server devServer = new Server().url("/").description("Local Development Server");
		    
		// For production, use the actual domain without repeating the port if it's standard 80/443
		Server prodServer = new Server().url(prodUrl).description("Production Server");
		  	        		  
	    Contact contact = new Contact();
	    contact.setEmail("rey107@gmail.com");
	    contact.setName("Reynald Marquez-Gragasin");
	    contact.setUrl("http://localhost:9090");

	    License mitLicense = new License().name("MIT License").url("https://construction.com/licenses/mit/");

	    Info info = new Info()
                .title("CONSTRUCTION INC. API Management")
                .version("1.0")
                .contact(contact)
                .description("This API exposes endpoints to manage and test functionality.")
                .license(mitLicense);	    
	    
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");

        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer, prodServer))
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes("Bearer Authentication", securityScheme));
    }	    	    
}
