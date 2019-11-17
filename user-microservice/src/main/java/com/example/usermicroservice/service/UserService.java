package com.example.usermicroservice.service;

import com.example.usermicroservice.model.User;
import com.example.usermicroservice.model.UserRole;
import com.example.usermicroservice.util.JwtResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    Iterable<User> listUsers();

    JwtResponse signup(User user);

    String login(User user);

    User getUser(String username);

    Iterable<UserRole> getUserRoles(Long userId);

    Iterable<UserRole> addRole (Long userId, Long roleId);

    User updateUser(User user);
}
