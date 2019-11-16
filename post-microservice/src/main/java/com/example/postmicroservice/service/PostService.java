package com.example.postmicroservice.service;

import com.example.postmicroservice.model.Post;

import javax.xml.stream.events.Comment;
import java.util.List;

public interface PostService {
    public Iterable<Post> listPosts();

    Iterable<Post> listPostsByUser(String userId);

    public Post createPost(String userId, Post post);

    Long deletePost(String userId, Long postId);


//    public Post getPostById(Long postId);
}

