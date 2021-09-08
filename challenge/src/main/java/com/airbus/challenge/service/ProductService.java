package com.airbus.challenge.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.airbus.challenge.entity.ProductEntity;
import com.airbus.challenge.exception.ProductServiceException;
import com.airbus.challenge.model.Product;

public interface ProductService {

	public List<Product> getAllProducts();
	public List<Product> getProductsByCategory(String category) throws ProductServiceException;
	public Product saveProduct(Product product);
	public Product updateProduct(Product product) throws ProductServiceException;
}
