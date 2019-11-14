package com.example.postmicroservice.service;

import com.example.postmicroservice.model.Post;
import com.example.postmicroservice.model.User;
import com.example.postmicroservice.repository.PostRepository;
import com.example.postmicroservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.stream.events.Comment;
import java.util.List;


@Service
    public class PostServiceImpl implements PostService {

        @Autowired
        PostRepository postRepository;

        @Autowired
        UserRepository userRepository;

        @Override
        public Iterable<Post> listPosts() {
            Iterable foundPosts = postRepository.findAll();
            return foundPosts;
        }

        @Override
        public Post createPost(String userId, Post post) {
            Long user_id = Long.parseLong(userId,10);

            post.setUser_id(user_id);
            return postRepository.save(post);
        }



    @Override
        public Post deletePost(String userId, Post post) {
            return null;
        }


        @Override
            public List<Comment> allComments(Long postId) {
                return null;
            }

}

