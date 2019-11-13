//package com.example.usermicroservice.controller;
//
//import com.example.usermicroservice.model.UserRole;
//import com.example.usermicroservice.service.UserRoleService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/role")
//public class UserRoleController {
//    @Autowired
//    UserRoleService userRoleService;
//
//    @PostMapping
//    public UserRole createRole(@RequestBody UserRole userRole) {
//        return userRoleService.createRole(userRole);
//    }
//
//    @GetMapping("/{rolename}")
//    public UserRole getRole(@PathVariable String rolename) {
//        return userRoleService.getRole(rolename);
//    }
//
//
//}
