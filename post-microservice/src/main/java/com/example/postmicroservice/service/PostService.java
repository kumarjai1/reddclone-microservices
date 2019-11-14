package com.example.postmicroservice.service;

import com.example.postmicroservice.model.Post;

import javax.xml.stream.events.Comment;
import java.util.List;

public interface PostService {
    public Iterable<Post> listPosts();

    public Post createPost(String userId, Post post);

    public Post deletePost(String userId, Post post);

    public List<Comment> allComments(Long postId);
}

