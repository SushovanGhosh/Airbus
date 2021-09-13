package com.airbus.challenge.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.airbus.challenge.dao.ProductDAO;
import com.airbus.challenge.entity.ProductEntity;
import com.airbus.challenge.exception.ProductServiceDBOperationException;
import com.airbus.challenge.exception.ProductServiceErrorCodesEnum;
import com.airbus.challenge.exception.ProductServiceException;
import com.airbus.challenge.model.Product;

@Service
public class ProductServiceImpl implements ProductService {

	private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

	@Autowired
	private ProductDAO productDAO;

	@Override
	public List<Product> getAllProducts() {
		logger.debug("START :: ProductServiceImpl :: getAllProducts");

		List<ProductEntity> productEntityList = null;
		productEntityList = productDAO.findAll();
		List<Product> productList = productEntityList.stream()
				.map(productEntity -> createProductFromProductEntity(productEntity))
				.collect(Collectors.toList());
		logger.debug("END :: ProductServiceImpl :: getAllProducts");
		return productList;
	}

	@Override
	public List<Product> getProductsByCategory(String category) throws ProductServiceException {
		logger.debug("START :: ProductServiceImpl :: getProductsByCategory :: {}", category);

		List<ProductEntity> productEntityList = null;
		productEntityList = productDAO.findProductByCategory(category);
		if (productEntityList.isEmpty())
			throw new ProductServiceDBOperationException(ProductServiceErrorCodesEnum.INVALID_CATEGORY.toString());
		List<Product> productList = productEntityList.stream()
				.map(productEntity -> createProductFromProductEntity(productEntity))
				.collect(Collectors.toList());
		logger.debug("END :: ProductServiceImpl :: getProductsByCategory");
		return productList;
	}

	@Override
	public Product saveProduct(Product product) {
		logger.debug("START :: ProductServiceImpl :: saveProduct :: {}", product.getProductName());
		ProductEntity productEntity = null;
		if (product != null)
			productEntity = productDAO.save(createProductEntityFromProduct(product));
		product = createProductFromProductEntity(productEntity);
		logger.debug("END :: ProductServiceImpl :: saveProduct :: {}", product.getProductName());
		return product;
	}

	@Override
	public Product updateProduct(Product product) throws ProductServiceException {
		logger.debug("START :: ProductServiceImpl :: updateProduct :: {}", product.getProductId());
		
		ProductEntity productEntity = productDAO.findById(product.getProductId()).orElse(null);
		if (productEntity == null)
			throw new ProductServiceDBOperationException(ProductServiceErrorCodesEnum.INVALID_PRODUCT.toString());
		productEntity.setProductName(product.getProductName());
		productEntity.setProductCategory(product.getProductCategory());
		productEntity.setProductDesc(product.getProductDesc());
		productEntity.setUnits(product.getUnits());
		productEntity = productDAO.save(productEntity);
		product = createProductFromProductEntity(productEntity);
		logger.debug("END :: ProductServiceImpl :: updateProduct :: {}", product.getProductId());
		return product;
	}

	public Product createProductFromProductEntity(ProductEntity productEntity) {

		Product product = new Product();
		product.setProductId(productEntity.getProductId());
		product.setProductName(productEntity.getProductName());
		product.setProductCategory(productEntity.getProductCategory());
		product.setProductDesc(productEntity.getProductDesc());
		product.setUnits(productEntity.getUnits());
		return product;

	}

	public ProductEntity createProductEntityFromProduct(Product product) {

		ProductEntity productEntity = new ProductEntity();
		productEntity.setProductId(product.getProductId());
		productEntity.setProductName(product.getProductName());
		productEntity.setProductCategory(product.getProductCategory());
		productEntity.setProductDesc(product.getProductDesc());
		productEntity.setUnits(product.getUnits());
		return productEntity;

	}
}
