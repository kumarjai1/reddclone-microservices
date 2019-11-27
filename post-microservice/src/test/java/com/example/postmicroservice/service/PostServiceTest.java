package com.example.postmicroservice.service;

import com.example.postmicroservice.exception.EntityNotFound;
import com.example.postmicroservice.model.Post;
import com.example.postmicroservice.mq.Sender;
import com.example.postmicroservice.repository.PostRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PostServiceTest {

    @Spy
    private PostRepository postRepository;

    @Mock
    private Sender sender;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private PostServiceImpl postService;

    private List<Post> listPostsDummy;

    private Post dummyPost;


    @Before
    public void init(){
        listPostsDummy = new ArrayList<>();

        Post post1 = new Post();
        post1.setTitle("This is Post 1 Title");
        post1.setDescription("This is Post 1 Description");
        Post post2 = new Post();
        post2.setTitle("This is Post 2 Title");
        post2.setDescription("This is Post 2 Description");

        listPostsDummy.add(post1);
        listPostsDummy.add(post2);

        dummyPost = new Post();
        dummyPost.setTitle("This is dummy Title");
        dummyPost.setDescription("This is dummy Description");
        dummyPost.setUsername("jai");
        dummyPost.setId(1L);


    }

    @Test
    public void listPosts_List_Success() {
        when(postRepository.findAll()).thenReturn(listPostsDummy);

        List<Post> actual = (List<Post>) postService.listPosts();

        assertTrue(actual.size()>0);
    }

    @Test
    public void listPostsByUser_List_Success() {
        when(postRepository.findPostsByUsername(anyString())).thenReturn(listPostsDummy);
        List<Post> actual = (List<Post>) postService.listPostsByUser("username");
        assertTrue(actual.size()>0);
    }

    @Test
    public void createPost_Post_Success() {
        when(postRepository.save(any())).thenReturn(dummyPost);
        Post actual = postService.createPost("username", dummyPost);
        assertEquals(actual.getTitle(),dummyPost.getTitle());
    }

    @Test
    public void deletePost_Long_Success() throws EntityNotFound {
        when(postRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(dummyPost));
        when(sender.deleteCommentsOfPostUsingRabbitMq(anyLong())).thenReturn(1L);
        doNothing().when(postRepository).deleteById(anyLong());

        Long actual = postService.deletePost("jai", 1L);
        assertEquals(java.util.Optional.ofNullable(actual),java.util.Optional.ofNullable(1L));
    }

    @Test(expected = EntityNotFound.class)
    public void deletePost_Exception_Error() throws EntityNotFound {
        when(postRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(null));
        when(sender.deleteCommentsOfPostUsingRabbitMq(anyLong())).thenReturn(1L);
        doNothing().when(postRepository).deleteById(anyLong());

        Long actual = postService.deletePost("jai", 1L);
        assertEquals(java.util.Optional.ofNullable(actual),java.util.Optional.ofNullable(1L));
    }

    @Test
    public void findPostById_Long_Success() throws EntityNotFound {
        when(postRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(dummyPost));
        when(rabbitTemplate.convertSendAndReceive(anyString(),anyLong())).thenReturn(1L);
        Long actual = postService.findPostById(1L);
        assertEquals(java.util.Optional.ofNullable(actual),java.util.Optional.ofNullable(1L));

    }

    @Test(expected=EntityNotFound.class)
    public void findPostById_Exeption_Error() throws EntityNotFound {
        when(postRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(null));
        when(rabbitTemplate.convertSendAndReceive(anyString(),anyLong())).thenReturn(1L);
        Long actual = postService.findPostById(1L);
        assertEquals(java.util.Optional.ofNullable(actual),java.util.Optional.ofNullable(1L));

    }
}