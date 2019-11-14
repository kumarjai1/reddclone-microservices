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
        return postRepository.findAll();
    }

    @Override
    public Iterable<Post> listPostsByUser(String userId) {

        Long user_id = Long.parseLong(userId);

        List<Post> userPosts = new ArrayList<>();
        for (Post post: postRepository.findAll()) {
            if (post.getUser_id() == user_id) {
                userPosts.add(post);
            }
        }
        return userPosts;
//        return postRepository.findPostsByUser_id(user_id);
//        ((ArrayList<Long>) userIds).add(user_id);
//
//        Iterable<Post> allPosts = postRepository.findAll();
//
//
//        Iterable<Post> userPosts = postRepository.findAllById(userIds);
//

    }

    @Override
        public Post createPost(String userId, Post post) {
            Long user_id = Long.parseLong(userId);

            post.setUser_id(user_id);
            return postRepository.save(post);
        }



    @Override
    public Long deletePost(String userId, Long postId) {
        Post post = postRepository.findById(postId).orElse(null);
        Long user_id = Long.parseLong(userId);
        if (post.getUser_id() == user_id ) {
            postRepository.delete(post);
        }
        return post.getId();
    }


}

