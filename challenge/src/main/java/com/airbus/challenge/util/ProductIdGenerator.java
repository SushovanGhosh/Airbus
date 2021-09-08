package com.airbus.challenge.util;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class ProductIdGenerator implements IdentifierGenerator {

	private static int counter = 1;
	
	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		int id = counter++;
		String value = id < 10 ? "P0" : "P";
		return value + id;
	}

}
