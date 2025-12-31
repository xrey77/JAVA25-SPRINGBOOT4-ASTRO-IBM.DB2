package com.java25.java25.springboot4.ibm.db2.services;


import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.java25.java25.springboot4.ibm.db2.dto.AddproductDto;
import com.java25.java25.springboot4.ibm.db2.dto.ProductDto;
import com.java25.java25.springboot4.ibm.db2.entities.Product;
import com.java25.java25.springboot4.ibm.db2.repositories.ProductRepository;

@Service
public class ProductService {

	private final ProductRepository productRepository;
	private final ModelMapper modelMapper;

	
	public ProductService(ProductRepository productRepository, ModelMapper modelMapper) {
	   this.productRepository = productRepository;
	   this.modelMapper = modelMapper; 
	}	   	   
		
	public Page<ProductDto> productSearch(String keyword, int page, int size) {
	    // Note: Spring Data JPA uses 0-based indexing for pages
	    Pageable pageable = PageRequest.of(page, size); 
	    Page<Product> products = productRepository.searchProduct(keyword, pageable);
	    return products.map(product -> modelMapper.map(product, ProductDto.class));
	}	

    public Page<ProductDto> productList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productRepository.findProducts(pageable);
        return products.map(product -> modelMapper.map(product, ProductDto.class));
    }
	
	public Product addProduct(AddproductDto productDto) {		
        Product product = modelMapper.map(productDto, Product.class);
        product.setCategory(productDto.getCategory());
        product.setDescriptions(productDto.getDescriptions());
        product.setQty(productDto.getQty());
        product.setUnit(productDto.getUnit());
        product.setCostprice(productDto.getCostprice());
        product.setSellprice(productDto.getSellprice());
        product.setSaleprice(productDto.getSaleprice());
        product.setProductpicture(productDto.getProductpicture());
        product.setAlertstocks(productDto.getAlertstocks());
        product.setCriticalstocks(productDto.getCriticalstocks());        
        Product addProductItem = productRepository.save(product);        
        return addProductItem;    			
	}
	
    public Boolean isDescriptionsExists(String descriptions) {
    	return productRepository.existsByDescriptions(descriptions);
    }
   
}
