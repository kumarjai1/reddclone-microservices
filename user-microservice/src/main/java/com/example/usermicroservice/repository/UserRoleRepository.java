package com.example.usermicroservice.repository;

import com.example.usermicroservice.model.UserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface UserRoleRepository extends CrudRepository<UserRole, Integer> {

    public UserRole findByName(String name);
//
//    Iterable<UserRole> findUserRoleByUsers(ArrayList<Long> userIds);
//    Iterable<UserRole> findUserRoleByUsers(String userId);


}