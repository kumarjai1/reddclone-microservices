package com.example.usermicroservice.service;


import com.example.usermicroservice.exception.EntityNotFoundException;
import com.example.usermicroservice.model.UserRole;
import com.example.usermicroservice.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.ArrayList;
import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    UserRoleRepository userRoleRepository;

    private static Logger logger = LoggerFactory.getLogger(UserRoleServiceImpl.class.getName());

    @Override
    public UserRole createRole(UserRole newRole) {
        logger.info("New role created with the name: " + newRole.getName());
        return (UserRole) userRoleRepository.save(newRole);
    }

    @Override
    public UserRole getRole(String roleName) {
        logger.info("Getting role by rolename: " + roleName);
        return userRoleRepository.findByName(roleName);
    }

    @Override
    public UserRole getRoleById(Long roleId) throws EntityNotFoundException {

        if (userRoleRepository.findById(Math.toIntExact(roleId)).orElse(null) == null) {
            logger.error("Getting a role by id that doesn't exist so throw not found error");
            throw new EntityNotFoundException("Such a role doesn't exist, please create it first");
        }
        logger.info("Returning role that exists with id: " + roleId);
        return userRoleRepository.findById(Math.toIntExact(roleId)).orElse(null);
    }

    @Override
    public Iterable<UserRole> listRoles() {
        logger.info("Returning the list of users");
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
