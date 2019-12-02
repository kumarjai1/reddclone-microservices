package com.example.postmicroservice.service;

import com.example.postmicroservice.exception.EntityNotFound;
import com.example.postmicroservice.model.Post;
import com.example.postmicroservice.mq.Sender;
import com.example.postmicroservice.repository.PostRepository;
import com.netflix.discovery.converters.Auto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;


import javax.persistence.EntityNotFoundException;
import javax.xml.stream.events.Comment;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Implements the Post Service CRUD functionality
 */
@Service
public class PostServiceImpl implements PostService {

    private Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

    @Autowired
    PostRepository postRepository;
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    Sender sender;

    /**
     *
     * @return All Posts
     */
    @Override
    public Iterable<Post> listPosts() {


        return postRepository.findAll();
    }

    /**
     * Username passed on by the header to get all posts by that user
     * @param username
     * @return List of post by username
     */
    @Override
    public Iterable<Post> listPostsByUser(String username)
    {
        logger.info("Return Posts of username: "+username);
        return postRepository.findPostsByUsername(username);
    }

    /**
     *
     * @param username passed on by the header
     * @param post accepted from the body
     * @return savedPost
     */
    @Override
        public Post createPost(String username, Post post) {
            post.setUsername(username);
            Post savedPost = postRepository.save(post);
        if (savedPost != null) {
            logger.info("Post Created, By Username: "+username+" , postId:"+savedPost.getId());
        }
            return savedPost;
        }

    /**
     *
     * @param username passed on by the header
     * @param postId passed on by the path
     * @return Long postId of deleted post
     * @throws EntityNotFound
     */
    @Transactional
    @Override
    public Long deletePost(String username, Long postId) throws EntityNotFound {
        Post post = postRepository.findById(postId).orElse(null);
        System.out.println("post should return null");
        System.out.println(post);

        if(post == null) {
            logger.warn("DELETE POST: postId {} doesn't Exit.",postId);
            throw new EntityNotFound("Post doesn't exist, go ahead, create it!");
        }
        else if (post.getUsername().equals(username)) {

            System.out.println("msg send started:" + postId);

            sender.deleteCommentsOfPostUsingRabbitMq(postId);
            postRepository.deleteById(postId);
        } else {
            return 0L;
        }
        return post.getId();
    }

    /**
     *
     * @param postId comes from the RabbitMq comment service
     * @return Long postId
     * @throws EntityNotFound
     */
    @RabbitListener(queues = "comment.post")
    @Override
    public Long findPostById(Long postId) throws EntityNotFound {
        Post post = postRepository.findById(postId).orElse(null);

        if (post == null)
            throw new EntityNotFound("Post doesn't exist");
        else if (post != null) {
            System.out.println("post does exist so send back the postid" + post.getId());
            Long res = (Long) rabbitTemplate.convertSendAndReceive("post.comment", post.getId());

            return res;
        }

        return null;

    }




}

