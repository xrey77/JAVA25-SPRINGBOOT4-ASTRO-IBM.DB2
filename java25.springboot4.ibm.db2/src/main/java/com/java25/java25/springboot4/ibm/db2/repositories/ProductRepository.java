package com.java25.java25.springboot4.ibm.db2.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.java25.java25.springboot4.ibm.db2.entities.Product;

import jakarta.persistence.QueryHint;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	@QueryHints(@QueryHint(name = "hibernate.jdbc.fetch_size", value = "500"))
	@Query("SELECT p FROM Product p WHERE LOWER(p.descriptions) LIKE LOWER(CONCAT('%', :keyword, '%')) ORDER BY p.id")
	Page<Product> searchProduct(@Param("keyword") String keyword, Pageable pageable);    
        
	@QueryHints(@QueryHint(name = "hibernate.jdbc.fetch_size", value = "500"))	
    @Query(value = "SELECT * FROM products ORDER BY id", nativeQuery = true)
    Page<Product> findProducts(Pageable pageable);
    
    boolean existsByDescriptions(String descriptions);

    
}