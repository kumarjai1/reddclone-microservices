package com.example.postmicroservice.repository;

import com.example.postmicroservice.model.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {

}
