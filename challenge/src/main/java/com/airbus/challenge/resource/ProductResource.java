package com.airbus.challenge.resource;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.airbus.challenge.entity.ProductEntity;
import com.airbus.challenge.exception.ProductServiceException;
import com.airbus.challenge.model.Error;
import com.airbus.challenge.model.Product;
import com.airbus.challenge.service.ProductService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin
@RequestMapping("/v1")
public class ProductResource {

	private static final Logger logger = LoggerFactory.getLogger(ProductResource.class);

	@Autowired
	private ProductService productService;

	@ApiOperation(value="Get All Products", notes = "Retrieve all the products")
	@ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")})
	@GetMapping(path = "/products", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Product>> getAllProducts() {

		logger.debug("START :: ProductResource :: getAllProducts");
		List<Product> productList = productService.getAllProducts();
		logger.debug("END :: ProductResource :: getAllProducts");
		return new ResponseEntity<List<Product>>(productList, HttpStatus.OK);
	}

	@ApiOperation(value="Get Products By Category", notes = "Retrieve any products based on a specific category")
	@ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")})
	@GetMapping(path = "/products/{category}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> getProductsByCategory(@PathVariable("category") String category) {

		logger.debug("START :: ProductResource :: getProductsByCategory :: {}", category);
		List<Product> productList = null;
		try {
			productList = productService.getProductsByCategory(category);
		}catch (ProductServiceException e) {
			logger.error("Invalid Category type :: {}", category);
			Error error = new Error(215, e.getMessage());  
			return new ResponseEntity<Error>(error, HttpStatus.BAD_REQUEST);
		}
		logger.debug("END :: ProductResource :: getProductsByCategory :: {}", category);
		return new ResponseEntity<List<Product>>(productList, HttpStatus.OK);
	}

	@ApiOperation(value="Save a Product", notes = "Persist any product by providing appropriate params")
	@ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")})
	@PostMapping(path = "/products", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Product> saveProduct(@RequestBody Product product) {

		logger.debug("START :: ProductResource :: saveProduct");
		product = productService.saveProduct(product);
		logger.debug("END :: ProductResource :: saveProduct");
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}
	
	@ApiOperation(value="Update a Product", notes = "Update any product by providing appropriate params")
	@ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")})
	@PutMapping(path = "/products", consumes = {MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> updateProduct(@RequestBody Product product) {
		
		logger.debug("START :: ProductResource :: updateProduct");
		try {
			productService.updateProduct(product);
		}catch (ProductServiceException e) {
			logger.error("Invalid product with product id :: {}", product.getProductId());
			Error error = new Error(216, "Selected product is invalid");
			return new ResponseEntity<Error>(error, HttpStatus.BAD_REQUEST);
		}
		logger.debug("END :: ProductResource :: updateProduct");
		return new ResponseEntity<Product>(product, HttpStatus.OK);
		
	}
}
