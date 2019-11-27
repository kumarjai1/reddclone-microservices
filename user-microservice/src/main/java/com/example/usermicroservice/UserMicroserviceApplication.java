package com.example.usermicroservice;

import com.example.usermicroservice.exception.EntityAlreadyExists;
import com.example.usermicroservice.exception.EntityNotFoundException;
import com.example.usermicroservice.exception.LoginException;
import com.example.usermicroservice.model.User;
//import com.example.usermicroservice.model.UserRole;
//import com.example.usermicroservice.service.UserRoleService;
import com.example.usermicroservice.model.UserRole;
import com.example.usermicroservice.service.UserService;
import com.example.usermicroservice.util.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@SpringBootApplication
@EnableEurekaClient
public class UserMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserMicroserviceApplication.class, args);
	}
}
