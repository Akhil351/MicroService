package org.Akhil.CART_SERVICE;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"org.Akhil.COMMON_SERVICE","org.Akhil.CART_SERVICE"})
@EntityScan({"org.Akhil.COMMON_SERVICE.model"})
@EnableJpaRepositories({"org.Akhil.COMMON_SERVICE.repo"})
public class CartServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CartServiceApplication.class, args);
	}

}
