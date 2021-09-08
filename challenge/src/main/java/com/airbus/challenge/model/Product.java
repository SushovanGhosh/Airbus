package com.airbus.challenge.model;

public class Product {

	private String productId;
	private String productCategory;
	private String productName;
	private String productDesc;
	private int units;
	
	public Product() {
		
	}
	
	public Product(String productId, String productCategory, String productName, String productDesc, int units) {
		this.productId = productId;
		this.productCategory = productCategory;
		this.productName = productName;
		this.productDesc = productDesc;
		this.units = units;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductCategory() {
		return productCategory;
	}
	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductDesc() {
		return productDesc;
	}
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
	public int getUnits() {
		return units;
	}
	public void setUnits(int units) {
		this.units = units;
	}
}
