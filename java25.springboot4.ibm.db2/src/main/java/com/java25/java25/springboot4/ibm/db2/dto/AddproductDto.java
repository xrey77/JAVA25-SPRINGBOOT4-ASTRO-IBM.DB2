package com.java25.java25.springboot4.ibm.db2.dto;


import java.math.BigDecimal;


public class AddproductDto {

	private String category;
	private String descriptions;
	private Integer qty;
	private String unit;
	private BigDecimal costprice;
	private BigDecimal sellprice;
	private BigDecimal saleprice;
	private String productpicture;
	private Integer alertstocks;
	private Integer criticalstocks;
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
