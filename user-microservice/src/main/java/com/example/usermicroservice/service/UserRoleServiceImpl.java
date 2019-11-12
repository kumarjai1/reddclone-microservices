package com.example.usermicroservice.service;


import com.example.usermicroservice.model.UserRole;
import com.example.usermicroservice.repository.UserRoleRepository;
import com.example.usermicroservice.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRoleService{

    @Autowired
    UserRoleRepository userRoleRepository;
    @Autowired
    JwtUtil jwtUtil;

    @Bean
    public PasswordEncoder encode(){ return new BCryptPasswordEncoder();
    }

    @Override
    public UserRole createUserRole(UserRole userRole) {
        return userRoleRepository.save(userRole);
    }

    @Override
    public UserRole getRole(String name) {
        return null;
    }
}
