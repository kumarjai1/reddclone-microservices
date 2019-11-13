package com.example.usermicroservice;

import com.example.usermicroservice.model.User;
//import com.example.usermicroservice.model.UserRole;
//import com.example.usermicroservice.service.UserRoleService;
import com.example.usermicroservice.service.UserService;
import com.example.usermicroservice.util.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@SpringBootApplication
@RestController
@EnableEurekaClient
public class UserMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserMicroserviceApplication.class, args);
	}

	@Autowired
	UserService userService;

//	@Autowired
//	UserRoleService userRoleService;

	@GetMapping("/hello")
	public String hello () {
		return "Hello suckers";
	}

	@PostMapping("/signup")
	public ResponseEntity signup(@Valid @RequestBody User user) {
		return ResponseEntity.ok(userService.signup(user));
	}

	@PostMapping("/login")
	public ResponseEntity login(@RequestBody User user) {
		return ResponseEntity.ok(new JwtResponse(userService.login(user), user.getUsername()));
	}
//	@PostMapping("/role")
//	public UserRole createRole(@RequestBody UserRole userRole) { return userRoleService.createRole(userRole);}

}
