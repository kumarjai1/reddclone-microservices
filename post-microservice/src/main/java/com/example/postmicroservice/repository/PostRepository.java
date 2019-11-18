package com.example.postmicroservice.repository;

import com.example.postmicroservice.model.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface PostRepository extends CrudRepository<Post, Long> {



//    Post findPostByUsername(String username);
    Iterable<Post> findPostsByUsername(String username);


}
