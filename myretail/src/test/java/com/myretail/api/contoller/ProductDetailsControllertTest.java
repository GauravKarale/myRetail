package com.myretail.api.contoller;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.myretail.api.controller.ProductDetailsController;
import com.myretail.api.model.ProductDetails;
import com.myretail.api.model.ProductPrice;
import com.myretail.api.service.ProductDetailsServiceImpl;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes=ProductDetailsController.class)
@WebMvcTest(value=ProductDetailsController.class, secure = false)
public class ProductDetailsControllertTest {

	@Autowired
	private MockMvc mockMvc;
	
    @MockBean
    @Qualifier(value="productDetailService")
    private  ProductDetailsServiceImpl productDetailsService;
	
    private int MOVIE_ID=13860428;

	ProductDetails prodDetails=null;
	ProductPrice prodPrice= new ProductPrice();
	@Before
	public void setup() {
		prodPrice.setId(MOVIE_ID);
		prodPrice.setCurrencyCode("USD");
		prodPrice.setPrice(new BigDecimal(2000));
		prodDetails= new ProductDetails(MOVIE_ID,"The Big Lebowski (Blu-ray)",prodPrice);
	}
	
	
	@Test
	public void getProductDetailsTest() throws Exception {
		Mockito.when(productDetailsService.getProductDetails(MOVIE_ID)).thenReturn(prodDetails);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/product/"+MOVIE_ID).accept(
				MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expected = "{\"id\":13860428,\"name\":\"The Big Lebowski (Blu-ray)\",\"productPrice\":{\"price\":2000,\"currencyCode\":\"USD\"}}";
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
	}
	
	@Test(expected = MethodArgumentTypeMismatchException.class)
	public void getProductDetailsInvalidRequestTest() throws Exception,MethodArgumentTypeMismatchException {
		String var="XYZ";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/product/"+var).accept(
				MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		Assert.assertEquals(HttpStatus.BAD_REQUEST.value(),result.getResponse().getStatus());
			throw 	result.getResolvedException();
	}
	
	@Test
	public void putProductDetailsTest() throws Exception{
		ProductPrice prodPrice1= new ProductPrice();
		prodPrice1.setId(MOVIE_ID);
		prodPrice1.setCurrencyCode("Rupee");
		prodPrice1.setPrice(new BigDecimal(300));
		ProductDetails prodDetails1= new ProductDetails(MOVIE_ID,"The Big Lebowski (Blu-ray)",prodPrice1);
		
		Mockito.when(productDetailsService.putProductDetails(MOVIE_ID, prodDetails1)).thenReturn(prodDetails1);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(
				"/product/"+MOVIE_ID)
				.accept(MediaType.APPLICATION_JSON)
				.content("{\"id\":"+MOVIE_ID+",\"name\":\"The Big Lebowski (Blu-ray)\",\"productPrice\":{\"price\":300,\"currencyCode\":\"Rupee\"}}")
				.contentType(MediaType.APPLICATION_JSON_VALUE);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		Assert.assertEquals(HttpStatus.OK.value(),result.getResponse().getStatus());
	}
}
