package com.example.usermicroservice.repository;

import com.example.usermicroservice.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, Long> {

    @Query("from User u where u.username = ?1")
    User login(String userName);

    @Query("from User u where u.username = ?1")
    User getUserByUserName(String userName);

    User findByUsername(String username);
}
