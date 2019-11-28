package com.example.usermicroservice.controller;

import com.example.usermicroservice.config.JwtUtil;
import com.example.usermicroservice.exception.UserExceptionHandler;
import com.example.usermicroservice.model.User;
import com.example.usermicroservice.model.UserRole;
import com.example.usermicroservice.service.UserService;
import com.example.usermicroservice.util.JwtResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = {UserController.class})
//@ExtendWith(SpringExtension.class)
////@SpringBootTest
//@WebMvcTest(UserController.class)
public class UserControllerTest {

    private MockMvc mockMvc;
    private User user1;
    private UserRole userRole;
    private List<UserRole> userRoles;
    private String encodedPassword;
    private String generatedToken;
    private ObjectMapper objectMapper;

    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    @Mock
    private JwtUtil jwtUtil;

//    @BeforeEach
//    public void init () {
//
//    }

    public UserControllerTest() {
        System.out.println("test");
        user1 = new User();
        userRole = new UserRole();
        userRoles = new ArrayList<>();
        encodedPassword="123456456asdasdajhgsd";
        generatedToken = "khasdkhasd.jasjkdhaskdj.jhadkjhdkasdsa";
        objectMapper = new ObjectMapper();
        MockitoAnnotations.initMocks(this);
    }

    @BeforeEach
    public void init() {
        System.out.println("test");
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(UserExceptionHandler.class)
                .build();
        userRole.setId(1);
        userRole.setName("ROLE_USER");
        userRoles.add(userRole);

        user1.setId(1L);
        user1.setUsername("user1");
        user1.setPassword("pw1");
        user1.setEmail("user1@email.com");
        user1.setUserRoles(userRoles);
    }

    @Test
    public void signup_ValidNewUser_ReturnsJwtAndUsernameSuccessfully() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user1));

        JwtResponse jwtResponse = new JwtResponse(generatedToken, user1.getUsername());
        when(userService.signup(any())).thenReturn(jwtResponse);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(jwtResponse)));
    }

    @Test
    void justAnExample() {
        System.out.println("test");
    }


}
