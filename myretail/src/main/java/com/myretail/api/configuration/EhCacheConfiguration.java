package com.myretail.api.configuration;

import org.apache.log4j.Logger;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
/**
* Target design challenge option 1
* Author: Gaurav Karale
* version 1.0.0
* Date :- 05/24/2017
* Info :-EhCache configuration class 
* */
@EnableCaching
@Configuration
public class EhCacheConfiguration {

	private final Logger log = Logger.getLogger(EhCacheConfiguration.class.getName());
	@Bean
    public CacheManager cacheManager() {
		log.info("in cacheManager");
        return new EhCacheCacheManager(cacheMangerFactory().getObject());
    }

    @Bean
    public EhCacheManagerFactoryBean cacheMangerFactory() {
    	log.info("in cacheMangerFactory");
        EhCacheManagerFactoryBean bean = new EhCacheManagerFactoryBean();
        bean.setConfigLocation(new ClassPathResource("ehcache.xml"));
        bean.setShared(true);
        return bean;
    }
}
