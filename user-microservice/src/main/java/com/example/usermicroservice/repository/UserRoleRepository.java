package com.example.usermicroservice.repository;

import com.example.usermicroservice.model.UserRole;
import org.springframework.data.repository.CrudRepository;

public interface UserRoleRepository extends CrudRepository {
    UserRole findByName(String name);
}
