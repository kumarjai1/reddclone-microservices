package com.ga.commentmicroservice.repository;

import com.ga.commentmicroservice.model.Comment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {

    @Query("FROM Comment c where c.postId=?1")
    public Iterable<Comment> findCommentsByPostId(Long postId);

    Iterable<Comment> findCommentsByUsername(String username);




//    @Transactional
//    public Iterable<Comment> deleteCommentsByPostId(Long postId);

}