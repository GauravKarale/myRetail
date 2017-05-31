package com.myretail.api.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.client.RestTemplate;

/**
 * Target design challenge option 1
 * Author: Gaurav Karale
 * version 1.0.0
 * Date :- 05/24/2017
 * Info :-Myretail api spring boot starter class
 * */
@SpringBootApplication
@EnableMongoRepositories("com.myretail.api.repository")
@ComponentScan("com.myretail.api.exception")
@ComponentScan("com.myretail.api.controller")
@ComponentScan("com.myretail.api.service")
@ComponentScan("com.myretail.api.model")
@ComponentScan("com.myretail.api.configuration")
public class MyRetailApp {

	public static void main(String[] args) {
		SpringApplication.run(MyRetailApp.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
	
}
