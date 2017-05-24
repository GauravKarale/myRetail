package com.myretail.api.model;

public class ProductDetails {
	
	
	private int id;
	private String name;
	private ProductPrice productPrice;
	
	public ProductDetails(){
	}

	public ProductDetails(int id,String name,ProductPrice prodPrice){
		this.id=id;
		this.name=name;
		this.productPrice=prodPrice;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ProductPrice getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(ProductPrice productPrice) {
		this.productPrice = productPrice;
	}

	@Override
	public String toString() {
		return "ProductDetails {"
				+ "id=" + id + ","
				+ "name=" + name +","
				+ productPrice + 
				"}";
	}
}
