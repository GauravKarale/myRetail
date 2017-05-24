package com.myretail.api.model;

import java.io.Serializable;
import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Target design challenge option 1
 * Author: Gaurav Karale
 * version 1.0.0
 * Date :- 05/24/2017
 * Info :-ProductPrice POJO mapped with mongodb collection 'productprice'
 * */
@Document(collection="productprice")
public class ProductPrice implements Serializable{

	
	private static final long serialVersionUID = -8331537878236316444L;
	
	@Id
	private int id;
	private BigDecimal price;
	private String currencyCode;
	
	
	public ProductPrice(){
	}


	@JsonIgnore
	@JsonProperty(value = "id")
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}
	
	public BigDecimal getPrice() {
		return price;
	}


	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}


	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	@Override
	public String toString() {
		return "ProductPrice {"
			+ "price=" + price + ","
			+ "currencyCode =" + currencyCode + "}";
	}


	
}
