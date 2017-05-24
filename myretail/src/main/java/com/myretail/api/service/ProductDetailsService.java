package com.myretail.api.service;

import java.io.IOException;

import org.springframework.web.client.HttpClientErrorException;

import com.mongodb.MongoException;
import com.myretail.api.model.ProductDetails;
/**
 * Target design challenge option 1
 * Author: Gaurav Karale
 * version 1.0.0
 * Date :- 05/24/2017
 * Info :- Product detail service interface
 * */
public interface ProductDetailsService {

	public ProductDetails getProductDetails(int id) throws MongoException,MongoException, HttpClientErrorException,IOException;
	public ProductDetails putProductDetails(int id,ProductDetails newProduct) throws Exception;
}
