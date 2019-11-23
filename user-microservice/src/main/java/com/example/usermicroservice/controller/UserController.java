package com.example.usermicroservice.controller;

import com.example.usermicroservice.model.User;
import com.example.usermicroservice.model.UserRole;
import com.example.usermicroservice.service.UserService;
import com.example.usermicroservice.util.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
    UserService userService;


    //  @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @GetMapping("/list")
//    public Iterable<User> listUsers(@RequestHeader("username") String username) {
//        System.out.println(username);
//        return userService.listUsers();
//    }

    @PostMapping("/signup")
    public ResponseEntity signup(@Valid @RequestBody User user) {
        return ResponseEntity.ok(userService.signup(user));
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody User user) {
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