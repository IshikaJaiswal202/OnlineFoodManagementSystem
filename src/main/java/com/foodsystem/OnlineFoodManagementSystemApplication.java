package com.foodsystem;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@OpenAPIDefinition(info = @Info(title = "Food Delivery API", version = "v1"))

@SpringBootApplication


public class OnlineFoodManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineFoodManagementSystemApplication.class, args);
	}

}
