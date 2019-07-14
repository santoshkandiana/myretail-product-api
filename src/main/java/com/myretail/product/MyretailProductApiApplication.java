package com.myretail.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.dao.PersistenceExceptionTranslationAutoConfiguration;

@SpringBootApplication(exclude = PersistenceExceptionTranslationAutoConfiguration.class)
public class MyretailProductApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyretailProductApiApplication.class, args);
	}

}
