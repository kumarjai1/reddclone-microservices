package com.example.postmicroservice.service;

import com.example.postmicroservice.model.Post;
import com.example.postmicroservice.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.stream.events.Comment;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepository postRepository;

    @Override
    public Iterable<Post> listPosts() {
        Iterable foundPosts = postRepository.findAll();
        return foundPosts;
    }

    @Override
    public Iterable<Post> listPostsByUser(String userId) {

        //get user id, with user id, iterate through the post list,c and only get all posts that has matching userid to current user

        Long user_id = Long.parseLong(userId);

        List<Post> userPosts = new ArrayList<>();
//        ((ArrayList<Long>) userIds).add(user_id);
//
//        Iterable<Post> allPosts = postRepository.findAll();
//
//
//        Iterable<Post> userPosts = postRepository.findAllById(userIds);

        for (Post post: postRepository.findAll()) {
            if (post.getUser_id() == user_id) {
                userPosts.add(post);
            }
        }

        return userPosts;
    }

    @Override
        public Post createPost(String userId, Post post) {
            Long user_id = Long.parseLong(userId);

            post.setUser_id(user_id);
            return postRepository.save(post);
        }



    @Override
    public Post deletePost(String userId, Post post) {
            return null;
        }


}

