package com.java25.java25.springboot4.ibm.db2.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


@Entity
@Table(name = "products")
public class Product {
	
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category;
    
    @Column(nullable = false, unique = true)    
    private String descriptions;
    private Integer qty;
    private String unit;
    private BigDecimal costprice;
    private BigDecimal sellprice;
    private BigDecimal saleprice;
    private String productpicture;
    private Integer alertstocks;
    private Integer criticalstocks;
    
    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;    
    
    
    public Product() {}
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDescriptions() {
		return descriptions;
	}
	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}
	public Integer getQty() {
		return qty;
	}
	public void setQty(Integer qty) {
		this.qty = qty;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public BigDecimal getCostprice() {
		return costprice;
	}
	public void setCostprice(BigDecimal costprice) {
		this.costprice = costprice;
	}
	public BigDecimal getSellprice() {
		return sellprice;
	}
	public void setSellprice(BigDecimal sellprice) {
		this.sellprice = sellprice;
	}
	public BigDecimal getSaleprice() {
		return saleprice;
	}
	public void setSaleprice(BigDecimal saleprice) {
		this.saleprice = saleprice;
	}
	public String getProductpicture() {
		return productpicture;
	}
	public void setProductpicture(String productpicture) {
		this.productpicture = productpicture;
	}
	public Integer getAlertstocks() {
		return alertstocks;
	}
	public void setAlertstocks(Integer alertstocks) {
		this.alertstocks = alertstocks;
	}
	public Integer getCriticalstocks() {
		return criticalstocks;
	}
	public void setCriticalstocks(Integer criticalstocks) {
		this.criticalstocks = criticalstocks;
	}
	
}
