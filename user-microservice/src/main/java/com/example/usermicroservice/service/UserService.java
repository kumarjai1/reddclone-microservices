package com.example.usermicroservice.service;

import com.example.usermicroservice.exception.EntityAlreadyExists;
import com.example.usermicroservice.exception.EntityNotFoundException;
import com.example.usermicroservice.exception.LoginException;
import com.example.usermicroservice.model.User;
import com.example.usermicroservice.model.UserRole;
import com.example.usermicroservice.util.JwtResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

    Iterable<User> listUsers();

    JwtResponse signup(User user) throws EntityAlreadyExists, EntityNotFoundException;

    JwtResponse login(User user) throws LoginException, EntityNotFoundException;

    User getUser(String username);

    Iterable<UserRole> getUserRoles(String username);

    Iterable<UserRole> addRole (Long userId, Long roleId) throws EntityNotFoundException;

    User updateUser(User user);
}
