package org.Akhil.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan( basePackages = {"org.Akhil.PRODUCT_SERVICE","org.Akhil.COMMON_SERVICE"})
@EntityScan({"org.Akhil.COMMON_SERVICE.model"})
@EnableJpaRepositories({"org.Akhil.COMMON_SERVICE.repo"})
public class ProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}

}
