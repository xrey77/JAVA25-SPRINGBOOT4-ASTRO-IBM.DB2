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
public class ProductList {

    private final ProductService productService;

    public ProductList(ProductService productService) {
        this.productService = productService;
    }
    
    @GetMapping("/products/list/{page}")
    public ResponseEntity<Map<String, Object>> productList(@PathVariable Integer page) {
        int pageSize = 5;
        Page<ProductDto> productPage = productService.productList(page - 1, pageSize);

        if (productPage.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "No record(s) found."));
        }

	    Map<String, Object> response = new HashMap<>();
	    response.put("page", page);
	    response.put("totpage", productPage.getTotalPages());
	    response.put("totalrecords", productPage.getTotalElements());
	    response.put("products", productPage.getContent());
	    
	    return ResponseEntity.ok(response); 		        
    }    
    
}
