package com.myretail.api.service;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoException;
import com.myretail.api.model.ProductDetails;
import com.myretail.api.model.ProductPrice;
import com.myretail.api.repository.ProductPriceRepository;

/**
 * Target design challenge option 1
 * Author: Gaurav Karale
 * version 1.0.0
 * Date :- 05/24/2017
 * Info :- Product detail service implementation 
 * */
@Service(value="productDetailService")
@Scope(proxyMode=ScopedProxyMode.TARGET_CLASS)
public class ProductDetailsServiceImpl implements ProductDetailsService {
	private final Logger log = Logger.getLogger(ProductDetailsServiceImpl.class.getName());
	
	@Autowired
	private ProductPriceRepository productPriceRepository;
	@Autowired
	private ProductDetailsServiceImpl _productDetailsServiceImpl;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private Environment env;
	
	
	@Override
	public ProductDetails getProductDetails(int id) throws MongoException,IOException{
		log.info("in  getProductDetails ");
		log.debug("id: "+id);
		String productName=getProductName(id);
		log.debug("productName: "+productName);
		ProductPrice prodPrice=_productDetailsServiceImpl.getProductPrice(id);
		if(prodPrice==null){
			log.error("price detail null mongo exception thrown");
			throw new MongoException("price details for product with id="+id+" not found in mongo db for collection productprice");
		}
		ProductDetails prodDetails= new ProductDetails(id,productName,prodPrice);
		log.debug("prodDetails: "+prodDetails);
		return prodDetails;
	}

	@Override
	public ProductDetails putProductDetails(int id, ProductDetails newProduct) throws Exception{
		log.info("in putProductDetails");
		log.debug(" newProduct : "+newProduct);
		if(id!=newProduct.getId()){
			throw new Exception(" Product price cannot be updated, request body json should have matching id with path variable.");
		}
		ProductPrice newProductPrice=newProduct.getProductPrice();
		if(newProduct.getProductPrice().getCurrencyCode()==null||newProduct.getProductPrice().getPrice()==null){
				throw new Exception(" Please check product price and currency code details, it should not be empty ");
		}
		newProductPrice.setId(id);
		String productName=getProductName(id);
		newProduct.setName(productName);
		newProductPrice=_productDetailsServiceImpl.updateProductPrice(id,newProduct);
		
		newProduct.setProductPrice(newProductPrice);
		return newProduct;
	}
	
	@Cacheable(value = "productPriceCache", key = "#id")
	public ProductPrice getProductPrice(int id) throws MongoException{
		log.info("in getProductPrice");
		log.debug("id : "+id);
		ProductPrice prodPrice=productPriceRepository.findById(id);
		log.debug("prodPrice : "+prodPrice);
		return prodPrice;
	}
	
	
	@CachePut(value = "productPriceCache", key = "#id")
	public ProductPrice updateProductPrice(int id,ProductDetails newProduct) throws MongoException{
		log.info("in updateProductPrice");
		ProductPrice newProductPrice=newProduct.getProductPrice();
		newProductPrice.setId(id);
		if(productPriceRepository.findById(newProductPrice.getId())!=null){
			newProductPrice=productPriceRepository.save(newProduct.getProductPrice());
		}else{
			log.error("price detail null mongo exception thrown");
			throw new MongoException("price details for product with id="+id+" not found in mongo db for collection 'productprice'");
		}
		log.debug("newProductPrice : "+newProductPrice);
		return newProductPrice;
	}
	
	private String getProductName(int id) throws IOException{
		log.info("in getProductName");
		String url=	env.getProperty("target.restUrl1")+id+env.getProperty("target.restUrl2");
		ResponseEntity<String> response= restTemplate.getForEntity(url, String.class);
		ObjectMapper mapper = new ObjectMapper();
		String productName="";
		try {
			JsonNode root=null;
			String jsonString=response.getBody();
			if(jsonString!=null||!"".equals(jsonString)){
				root = mapper.readTree(jsonString);
				if(root.findValue("product")!=null){
					root=root.findValue("product");
					if(root.findValue("item")!=null){
						root=root.findValue("item");
						if(root.findValue("product_description")!=null){
							root=root.findValue("product_description");
							if(root.findValue("title")!=null){
								productName=root.findValue("title").asText();
							}
						}
					}
				}
			}
			log.debug("productName : "+productName);
		} 
		catch (IOException e) {
			log.error("Parsing failed IOException "+e.getMessage());
			throw new IOException(e.getMessage());
		}
		return productName;
	}

}
