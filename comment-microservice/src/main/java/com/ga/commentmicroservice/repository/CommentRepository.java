package com.ga.commentmicroservice.repository;

import com.ga.commentmicroservice.model.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Long> {
}
