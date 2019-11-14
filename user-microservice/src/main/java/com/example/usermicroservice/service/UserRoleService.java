package com.example.usermicroservice.service;
import com.example.usermicroservice.model.UserRole;

import java.util.List;


public interface UserRoleService {

    public UserRole createRole(UserRole newRole);

    public UserRole getRole(String roleName);

    public Iterable<UserRole> userRoles(Long userId);
}
