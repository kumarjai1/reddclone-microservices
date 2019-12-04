package com.ga.commentmicroservice.controller;

import com.ga.commentmicroservice.model.Comment;
import com.ga.commentmicroservice.service.CommentServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class CommentControllerTest {
    @InjectMocks
    private CommentController commentController;

    @Mock
    private CommentServiceImpl commentService;

    private MockMvc mockMvc;

    @Before
    public void init(){
        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
    }

    private List<Comment> listCommentsDummy;

    private Comment dummyComment;


    @Before
    public void initDummies(){
        listCommentsDummy = new ArrayList<>();

        Comment comment1 = new Comment();
        comment1.setText("This is comment 1 Text");

        Comment comment2 = new Comment();
        comment2.setText("This is comment 2 Text");


        listCommentsDummy.add(comment1);
        listCommentsDummy.add(comment2);

        dummyComment = new Comment();
        dummyComment.setText("This is dummy Text");
        dummyComment.setUsername("bear");
        dummyComment.setId(1L);
        dummyComment.setPostId(1L);

    }


    @Test
    public void listComments_List_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/list")
                .contentType(MediaType.APPLICATION_JSON);

        when(commentService.listComments()).thenReturn(listCommentsDummy);

        ResultActions result = mockMvc
                .perform(requestBuilder)
                .andExpect(status().isOk());

    }

    @Test
    public void createComment_Post_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/1")
                .header("username","robin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(commentJson(dummyComment.getText()));
        when(commentService.createComment(anyString(),anyLong(),any())).thenReturn(dummyComment);

        ResultActions result = mockMvc
                .perform(requestBuilder)
                .andExpect(status().isOk());

    }

    @Test
    public void findCommentsByUser_List_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/user")
                .header("username","rouge")
                .contentType(MediaType.APPLICATION_JSON);
        when(commentService.getCommentsByUser(any())).thenReturn(listCommentsDummy);

        ResultActions result = mockMvc
                .perform(requestBuilder)
                .andExpect(status().isOk());
    }


    @Test
    public void getCommentsByPostId_List_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/"+dummyComment.getPostId())
                .header("username","robin")
                .contentType(MediaType.APPLICATION_JSON);
        when(commentService.getCommentsByPostId(any())).thenReturn(listCommentsDummy);
        ResultActions result = mockMvc
                .perform(requestBuilder)
                .andExpect(status().isOk());
    }


    @Test
    public void deleteCommentByUser_Long_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/1")
                .header("username","robina")
                .contentType(MediaType.APPLICATION_JSON);
        when(commentService.deleteCommentByUser(anyString(),anyLong())).thenReturn(1L);
        ResultActions result = mockMvc
                .perform(requestBuilder)
                .andExpect(status().isOk());


    }


    @Test
    public void deleteCommentsByPostId_Long_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/post/1")
                .contentType(MediaType.APPLICATION_JSON);
        when(commentService.deleteCommentsByPostId(anyLong())).thenReturn(1L);
        ResultActions result = mockMvc
                .perform(requestBuilder)
                .andExpect(status().isOk());


    }

    private String commentJson(String text){
        return "{" +
                "\"text\":\"" + text+ "\" " +
                "}";
    }
}