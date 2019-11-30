package com.example.postmicroservice.controller;

import com.example.postmicroservice.model.Post;
import com.example.postmicroservice.service.PostServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class PostControllerTest {

    @InjectMocks
    private PostController postController;

    @Mock
    private PostServiceImpl postService;

    private MockMvc mockMvc;

    @Before
    public void init(){
        mockMvc = MockMvcBuilders.standaloneSetup(postController).build();
    }

    private List<Post> listPostsDummy;

    private Post dummyPost;


    @Before
    public void initDummies(){
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
    public void listAll_List_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/list")
                .contentType(MediaType.APPLICATION_JSON);

        when(postService.listPosts()).thenReturn(listPostsDummy);

        ResultActions result = mockMvc
                .perform(requestBuilder)
                .andExpect(status().isOk());
    }

    @Test
    public void createPost_Post_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/")
                .header("username","batman")
                .contentType(MediaType.APPLICATION_JSON)
                .content(postJson(dummyPost.getTitle(), dummyPost.getDescription()));

        when(postService.createPost(anyString(), any())).thenReturn(dummyPost);

        ResultActions result = mockMvc
                .perform(requestBuilder)
                .andExpect(status().isOk());

    }

    @Test
    public void listPostByUser_List_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/")
                .header("username","mohawk")
                .contentType(MediaType.APPLICATION_JSON);

        when(postService.listPostsByUser(any())).thenReturn(listPostsDummy);

        ResultActions result = mockMvc
                .perform(requestBuilder)
                .andExpect(status().isOk());
    }

    @Test
    public void deletePost_Long_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/1")
                .header("username","mohawk")
                .contentType(MediaType.APPLICATION_JSON);

        when(postService.deletePost(anyString(), anyLong())).thenReturn(1L);

        ResultActions result = mockMvc
                .perform(requestBuilder)
                .andExpect(status().isOk());


    }

    private String postJson(String title, String description){
        return "{" +
                "\"title\":\"" + title + "\", " +
                "\"description\":\"" + description + "\" " +
                "}";
    }
}