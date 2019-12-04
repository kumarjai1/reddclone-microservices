package com.example.usermicroservice.repository;

import com.example.usermicroservice.model.User;
import com.example.usermicroservice.model.UserProfile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends CrudRepository<UserProfile, Long> {

    @Query("from UserProfile up inner join User u on u.username = ?1 and up.id = u.userProfile.id")
    UserProfile findUserProfileByUsername(String username);



}
