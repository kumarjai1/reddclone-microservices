package com.example.postmicroservice.repository;

import com.example.postmicroservice.model.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface PostRepository extends CrudRepository<Post, Long> {

    //    Iterable<Post> findPostsByUser_id(Long userId);
    @Query("FROM Post p where p.userId=?1")
    Post getPostByUserId(Long userId);

    @Query("From Post p where p.username=?1")
    Post findPostByUsername(String username);

}
