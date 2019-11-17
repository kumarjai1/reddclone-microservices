package com.example.postmicroservice.service;

import com.example.postmicroservice.model.Post;
import com.example.postmicroservice.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;


import javax.xml.stream.events.Comment;
import java.util.ArrayList;
import java.util.Arrays;
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
    public Iterable<Post> listPostsByUser(String username) {

        Long user_id = Long.parseLong(username);

//        List<Post> userPosts = new ArrayList<>();
//        for (Post post: postRepository.findAll()) {
//            if (post.getUsername() == username) {
//                userPosts.add(post);
//            }
//        }
        return (Iterable<Post>) postRepository.findPostByUsername(username);
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
        public Post createPost(String username, Post post) {
            Long user_id = Long.parseLong(username);

            post.setUsername(username);
            return postRepository.save(post);
        }


    @Transactional
    @Override
    public Long deletePost(String username, Long postId) {
        Post post = postRepository.findById(postId).orElse(null);
        Long user_id = Long.parseLong(username);
        if (post.getUsername() == username ) {
            deleteCommentsOfPost(postId);
            postRepository.delete(post);

        }
        return post.getId();
    }

//    @Override
//    public Post getPostById(Long postId) {
//        RestTemplate restTemplate = new RestTemplate();
//        Post savedPost = postRepository.findById(postId).orElse(null);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//        HttpEntity<String> entity = new HttpEntity<String>(headers);
//        Comment[] comments =  restTemplate.exchange("http://comments-api:2123/"+savedPost.getId(), HttpMethod.GET, entity, Comment[].class).getBody();
//        savedPost.(Arrays.asList(comments));
//        return savedPost;
//    }
    private Long deleteCommentsOfPost(Long postId){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        Long res =  restTemplate.exchange("http://comments-api:2123/post/"+postId, HttpMethod.DELETE, entity, Long.class).getBody();
        return res;
    }

}

