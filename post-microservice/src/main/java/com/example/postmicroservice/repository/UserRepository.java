package com.example.postmicroservice.repository;

import com.example.postmicroservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User getUserById(Long id){
        String sql = "SELECT * FROM users WHERE id = ?";

        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) ->
                new User(
                        rs.getString("username")
                ));
    }

}
