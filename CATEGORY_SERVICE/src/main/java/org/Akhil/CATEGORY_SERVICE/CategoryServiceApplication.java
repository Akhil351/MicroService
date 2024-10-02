package org.Akhil.CATEGORY_SERVICE;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages={"org.Akhil.CATEGORY_SERVICE","org.Akhil.COMMON_SERVICE"})
@EnableJpaRepositories({"org.Akhil.COMMON_SERVICE.repo"})
@EntityScan("org.Akhil.COMMON_SERVICE.model")
public class CategoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CategoryServiceApplication.class, args);
	}

}
