package com.example.postmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class PostMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostMicroserviceApplication.class, args);
	}

	@GetMapping("/hello")
	public String hello () {
		return "Hello world";
	}


}
