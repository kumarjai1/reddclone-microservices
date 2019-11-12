package com.ga.usermicroservice.repository;

import com.ga.usermicroservice.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    @Query("from User u where u.username = ?1")
    User login(String userName);

    @Query("from User u where u.username = ?1")
    User getUserByUserName(String userName);
}
