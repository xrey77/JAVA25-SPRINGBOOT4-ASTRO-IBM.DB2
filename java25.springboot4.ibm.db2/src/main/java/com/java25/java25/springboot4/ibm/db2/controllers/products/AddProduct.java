package com.java25.java25.springboot4.ibm.db2.controllers.products;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.java25.java25.springboot4.ibm.db2.dto.AddproductDto;
import com.java25.java25.springboot4.ibm.db2.services.ProductService;

@RestController
@RequestMapping("/take")
public class AddProduct {

	
	private final ProductService productService;
	
	public AddProduct(ProductService productService) {
		this.productService = productService;
	}
	
	@PostMapping("/addproduct")
	public ResponseEntity<Map<String, String>> addProduct(@RequestBody AddproductDto product) {

    	Boolean productDescription = productService.isDescriptionsExists(product.getDescriptions());
    	if (productDescription) {
    		return ResponseEntity.status(HttpStatus.CONFLICT)
            	    .body(Map.of("message", "Product Descriptions is already exists."));    		
    	}

		productService.addProduct(product);
		return ResponseEntity.status(HttpStatus.CREATED)
        	    .body(Map.of("message", "New Product is successfully added."));
	}
	
}
