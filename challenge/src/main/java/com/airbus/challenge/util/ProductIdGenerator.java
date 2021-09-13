package com.airbus.challenge.util;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.PageRequest;

import com.airbus.challenge.dao.ProductDAO;
import com.airbus.challenge.entity.ProductEntity;

public class ProductIdGenerator implements IdentifierGenerator {

	private static int counter = 0;
	private ProductDAO productDAO;
	
	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		if(counter == 0) {
			productDAO = ApplicationContextUtil.getBean(ProductDAO.class);
			List<ProductEntity> productEntity = productDAO.findLatestProduct(PageRequest.of(0, 1));
			if(!productEntity.isEmpty()) {
				String productId = productEntity.get(0).getProductId();
				counter = Integer.parseInt(productId.substring(1));
			}
		}
		int id = ++counter;
		String value = id < 10 ? "P0" : "P";
		return value + id;
	}

}
