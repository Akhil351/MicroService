package org.Akhil.CART_ITEM_SERVICE;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableFeignClients
@ComponentScan(basePackages = {"org.Akhil.COMMON_SERVICE","org.Akhil.CART_ITEM_SERVICE"})
@EntityScan({"org.Akhil.COMMON_SERVICE.model"})
@EnableJpaRepositories({"org.Akhil.COMMON_SERVICE.repo"})
public class CartItemServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CartItemServiceApplication.class, args);
	}

}
