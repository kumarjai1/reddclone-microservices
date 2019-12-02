package com.example.usermicroservice.service;


import com.example.usermicroservice.exception.EntityNotFoundException;
import com.example.usermicroservice.model.UserRole;
import com.example.usermicroservice.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    UserRoleRepository userRoleRepository;
    @Override
    public UserRole createRole(UserRole newRole) {
        return (UserRole) userRoleRepository.save(newRole);
    }

    @Override
    public UserRole getRole(String roleName) {
        return userRoleRepository.findByName(roleName);
    }

    @Override
    public UserRole getRoleById(Long roleId) throws EntityNotFoundException {
        if (userRoleRepository.findById(Math.toIntExact(roleId)).orElse(null) == null) throw new EntityNotFoundException("Such a role doesn't exist, please create it first");
        return userRoleRepository.findById(Math.toIntExact(roleId)).orElse(null);
    }

    @Override
    public Iterable<UserRole> listRoles() {
        return userRoleRepository.findAll();
    }

    //TODO: get users associated with a specific role
//    @Override
//    public Iterable<UserRole> userRoles(Long userId) {
//        ArrayList<Long> userIds = new ArrayList<>();
//        userIds.add(userId);
//        return userRoleRepository.findUserRoleByUsers(userIds);
//    }

}
