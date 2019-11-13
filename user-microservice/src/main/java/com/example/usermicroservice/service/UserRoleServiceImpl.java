//package com.example.usermicroservice.service;
//
//
//import com.example.usermicroservice.model.UserRole;
//import com.example.usermicroservice.repository.UserRoleRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UserRoleServiceImpl implements UserRoleService {
//
//    @Autowired
//    UserRoleRepository userRoleRepository;
//    @Override
//    public UserRole createRole(UserRole newRole) {
//        return (UserRole) userRoleRepository.save(newRole);
//    }
//
//    @Override
//    public UserRole getRole(String roleName) {
//        return userRoleRepository.findByName(roleName);
//    }
//}