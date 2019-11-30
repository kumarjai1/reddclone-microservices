package com.ga.commentmicroservice.service;

import com.ga.commentmicroservice.model.Comment;
import com.ga.commentmicroservice.mq.Sender;
import com.ga.commentmicroservice.repository.CommentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CommentServiceTest {

    @Spy
    private CommentRepository commentRepository;

    @Mock
    private Sender sender;

    @InjectMocks
    private CommentServiceImpl commentService;
    private List<Comment> listCommentsDummy;

    private Comment dummyComment;


    @Before
    public void init(){
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
    public void createComment_Comment_Success() {
        when(sender.checkIfPostExists(anyLong())).thenReturn(1L);
        when(commentRepository.save(any())).thenReturn(dummyComment);
        Comment actual = commentService.createComment("username",1l,dummyComment);
        assertEquals(dummyComment.getText(),actual.getText());


    }

    @Test
    public void listComments_List_Success() {
        when(commentRepository.findAll()).thenReturn(listCommentsDummy);
        List<Comment> actual = (List<Comment>) commentService.listComments();
        assertTrue(actual.size()>0);
    }

    @Test
    public void getCommentsByUser_List_Success() {
        when(commentRepository.findCommentsByUsername(anyString())).thenReturn(listCommentsDummy);
        List<Comment> actual = (List<Comment>) commentService.getCommentsByUser("username");
        assertTrue(actual.size()>0);
    }


//    @Override
//    public Iterable<Comment> getCommentsByPostId(Long postId) {
//        return commentRepository.findCommentsByPostId(postId);
//    }
    @Test
    public void getCommentsByPostId_List_Success() {
        when(commentRepository.findCommentsByPostId(any())).thenReturn(listCommentsDummy);
        List<Comment> actual = (List<Comment>) commentService.getCommentsByPostId(1L);
        assertTrue(actual.size()>0);
    }

    @Test
    public void deleteCommentsByPostId_Long_Success() {
        when(commentRepository.findCommentsByPostId(anyLong())).thenReturn(listCommentsDummy);
        doNothing().when(commentRepository).deleteAll(anyIterable());
        Long actual = commentService.deleteCommentsByPostId(1L);

        assertEquals(java.util.Optional.of(1l), java.util.Optional.of(actual));
    }

    @Test
    public void deleteCommentByUser_Long_Success() {
        when(commentRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(dummyComment));
        doNothing().when(commentRepository).deleteById(anyLong());
        Long actual = commentService.deleteCommentByUser("bear",1L);

        assertEquals(java.util.Optional.of(1L), java.util.Optional.of(actual));


    }
}