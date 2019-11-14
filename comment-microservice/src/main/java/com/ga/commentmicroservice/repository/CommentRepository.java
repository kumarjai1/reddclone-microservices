package com.ga.commentmicroservice.repository;

import com.ga.commentmicroservice.model.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {

    public Iterable<Comment> findCommentByPostId(Long postId);
    public Iterable<Comment> findCommentByUserId(Long userId);

//    @Transactional
//    public Iterable<Comment> deleteCommentsByPostId(Long postId);

}