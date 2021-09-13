//package com.airbus.challenge;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.skyscreamer.jsonassert.JSONAssert;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.RequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import com.airbus.challenge.model.Product;
//import com.airbus.challenge.resource.ProductResource;
//import com.airbus.challenge.service.ProductService;
//
//@WebMvcTest(value = ProductResource.class)
//@WithMockUser
//public class TestProductResource {
//
//	@Autowired
//	private MockMvc mockMvc;
//
//	@MockBean
//	private ProductService productService;
//
//	List<Product> productList = new ArrayList<Product>();
//	String productJson = ("{\r\n" + "        \"productId\": \"P01\",\r\n"
//			+ "        \"productCategory\": \"Commercial\",\r\n" + "        \"productName\": \"A320\",\r\n"
//			+ "        \"productDesc\": \"Passenger Aircraft Family\",\r\n" + "        \"units\": 2\r\n" + "    }");
//
//	@Test
//	public void testGetAllProducts() throws Exception{
//		productList.add(new Product("P01", "Commercial", "A320", "Passenger Aircraft Family,", 2));
//		Mockito.when(productService.getAllProducts()).thenReturn(productList);
//		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/airbus/v1/products/")
//				.accept(MediaType.APPLICATION_JSON_VALUE);
//		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//		JSONAssert.assertEquals(productJson, result.getResponse().getContentAsString(), false);
//	}
//}
