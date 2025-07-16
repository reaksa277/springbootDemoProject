package com.reaksa.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	// GET Request - Retrieve record form server
	// api endpoint -> /api/v1/hello
	// http://localhost:8080/api/v1/hello
//	@RequestMapping(value = "/api/v1/hello", method = RequestMethod.GET)
//	@GetMapping("/api/v1/hello")
//	public String sayHello() {
//		return "Hello World Spring Boot !";
//	}

}
