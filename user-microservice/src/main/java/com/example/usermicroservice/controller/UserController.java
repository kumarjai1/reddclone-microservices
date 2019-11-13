//package com.example.usermicroservice.controller;
//
//import com.example.usermicroservice.model.User;
//import com.example.usermicroservice.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
////import org.springframework.security.access.prepost.PreAuthorize;
////import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestAttribute;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestHeader;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class UserController {
//
//    @Autowired
//    UserService userService;
//
//    //  @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @GetMapping("/list")
//    public Iterable<User> listUsers(@RequestHeader("username") String username) {
//        System.out.println(username);
//        return userService.listUsers();
//    }
//
//    @PostMapping("/signup")
//    public ResponseEntity<?> createUser(@RequestBody User user) {
//        return ResponseEntity.ok(userService.signup(user));
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody User user) {
//        return ResponseEntity.ok(userService.login(user));
//    }
//
////  @PostMapping("/getuserfromtoken")
////  public User getUserFromToken() {
//////    String username = SecurityContextHolder.getContext().getAuthentication().getName();
//////    System.out.println(username);
////    return userService.getUserByUsername(username);
////  }
//}