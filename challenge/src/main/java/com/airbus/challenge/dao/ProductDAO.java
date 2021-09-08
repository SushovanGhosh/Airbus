package com.airbus.challenge.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.airbus.challenge.entity.ProductEntity;

@Repository
public interface ProductDAO extends JpaRepository<ProductEntity, String> {

	@Query("select p from ProductEntity p where p.productCategory = :category")
	public List<ProductEntity> findProductByCategory(@Param("category") String category);
}
