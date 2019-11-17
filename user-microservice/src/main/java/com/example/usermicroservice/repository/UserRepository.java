package com.example.usermicroservice.repository;

import com.example.usermicroservice.model.User;
//import com.example.usermicroservice.model.UserRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {


//    User login(String userName);

    @Query("from User u where u.username = ?1")
    User getUserByUserName(String userName);

    User findByUsername(String username);

//    User getUserByEmail(String email);
    User findUserByEmail(String email);
    User findUserByUsername(String username);

}
