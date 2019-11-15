package com.example.usermicroservice.service;
import com.example.usermicroservice.model.UserRole;

import java.util.List;


public interface UserRoleService {

    UserRole createRole(UserRole newRole);

    UserRole getRole(String roleName);
    UserRole getRoleById (Long roleId);

    Iterable<UserRole> listRoles();

}
