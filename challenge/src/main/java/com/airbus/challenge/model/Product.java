package com.airbus.challenge.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Details about the product")
public class Product {

	@ApiModelProperty(notes = "The unique id of a product")
	private String productId;
	@ApiModelProperty(notes = "The category of a product")
	private String productCategory;
	@ApiModelProperty(notes = "The name of a product")
	private String productName;
	@ApiModelProperty(notes = "The description of a product")
	private String productDesc;
	@ApiModelProperty(notes = "The number of units of a product")
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
