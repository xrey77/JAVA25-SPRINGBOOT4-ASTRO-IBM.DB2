package com.java25.java25.springboot4.ibm.db2.controllers.products;

import java.util.HashMap;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.java25.java25.springboot4.ibm.db2.dto.ProductDto;
import com.java25.java25.springboot4.ibm.db2.services.ProductService;

@RestController
@RequestMapping("/take")
public class ProductSearch {

	private final ProductService productService;
	
	public ProductSearch(ProductService productService) {
	   this.productService = productService;	
	}
			
	@GetMapping(path="/products/search/{page}/{key}")
	public ResponseEntity<Map<String, Object>> productSearch(@PathVariable int page, @PathVariable String key) {
	    int springPage = page > 0 ? page - 1 : 0; 
	    int perPage = 5;
	    
	    String keyword = key.toLowerCase();

	    Page<ProductDto> productPage = productService.productSearch(keyword, springPage, perPage);

	    if (productPage.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                             .body(Map.of("message", "No record(s) found."));
	    }

	    Map<String, Object> response = new HashMap<>();
	    response.put("page", page);
	    response.put("totpage", productPage.getTotalPages()); // Automatically calculated
	    response.put("totalrecords", productPage.getTotalElements()); // From countQuery
	    response.put("products", productPage.getContent());
	    
	    return ResponseEntity.ok(response); 
	}
}