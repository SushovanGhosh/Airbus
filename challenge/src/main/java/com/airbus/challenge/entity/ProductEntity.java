package com.airbus.challenge.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="PRODUCT_DETAILS")
public class ProductEntity {

	@Id
	@Column(name = "product_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_seq")
	@GenericGenerator(name = "product_id_seq", strategy = "com.airbus.challenge.util.ProductIdGenerator")
	private String productId;
	@Column(name = "product_category", nullable = false)
	private String productCategory;
	@Column(name = "product_name", nullable = false)
	private String productName;
	@Column(name = "product_description")
	private String productDesc;
	@Column(name = "units")
	private int units;
	
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
