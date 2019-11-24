package com.example.usermicroservice;

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
@RestController
public class UserMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserMicroserviceApplication.class, args);
	}

	@Autowired
	UserService userService;


	//  @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @GetMapping("/list")
//    public Iterable<User> listUsers(@RequestHeader("username") String username) {
//        System.out.println(username);
//        return userService.listUsers();
//    }

	@GetMapping("/hello")
	public String hello () {
		return "Hello suckers";
	}

	@PostMapping("/signup")
	public ResponseEntity signup(@Valid @RequestBody User user) {
		return ResponseEntity.ok(userService.signup(user));
	}

	@PostMapping("/login")
	public ResponseEntity login(@Valid @RequestBody User user) throws LoginException, EntityNotFoundException {
		return ResponseEntity.ok(userService.login(user));
	}

	@GetMapping("/{userId}/roles")
	public Iterable<UserRole> listUserRoles(@PathVariable Long userId) {
		return userService.getUserRoles(userId);
	}

	@PostMapping("{userId}/{roleId}")
	public Iterable<UserRole> addRole (@PathVariable Long userId, @PathVariable Long roleId) {
		return userService.addRole(userId, roleId);
	}
}
