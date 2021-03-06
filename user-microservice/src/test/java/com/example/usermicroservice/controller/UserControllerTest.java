package com.example.usermicroservice.controller;




import com.example.usermicroservice.config.JwtUtil;
import com.example.usermicroservice.exception.UserExceptionHandler;
import com.example.usermicroservice.model.User;
import com.example.usermicroservice.model.UserRole;
import com.example.usermicroservice.service.UserService;
import com.example.usermicroservice.util.JwtResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@WebMvcTest(UserController.class)
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

    public UserControllerTest() {
        user1 = new User();
        userRole = new UserRole();
        userRoles = new ArrayList<>();
        encodedPassword="123456456asdasdajhgsd";
        generatedToken = "khasdkhasd.jasjkdhaskdj.jhadkjhdkasdsa";
        objectMapper = new ObjectMapper();
        MockitoAnnotations.initMocks(this);
    }

    @Before
    public void init() {
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
    public void signup_BadEmailInput_Failure() throws Exception {
        user1.setEmail("");
        System.out.println("printing username after empty: " + user1.getUsername());
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user1));

        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void signup_BlankPasswords_Failure() throws Exception {
        user1.setPassword("");
        System.out.println("printing username after empty: " + user1.getUsername());
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user1));

        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void login_ValidUser_ReturnsJsonAndUsername() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user1));

        JwtResponse jwtResponse = new JwtResponse(generatedToken, user1.getUsername());
        when(userService.login(any())).thenReturn(jwtResponse);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(jwtResponse)));
    }

    @Test
    @WithMockUser(username = "user1")
    public void listUsers_AuthorizedUser_ReturnsListOfUsers() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/list");

        List<User> users = new ArrayList<>();
        users.add(user1);
        when (userService.listUsers()).thenReturn(users);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(users)));

    }

    @Test
    public void listUserRoles_ValidUserPassed_ReturnsAllUserRoles() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/" + user1.getUsername()+"/roles");

        when(userService.getUserRoles(user1.getUsername())).thenReturn(userRoles);
        System.out.println(userRoles);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(userRoles)));

    }

    @Test
    public void addRoleToUser_AuthorizedUser_ReturnsUserRolesWithNewRole () throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/"+user1.getId()+"/"+userRole.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user1));

        when(userService.addRole(any(), any())).thenReturn(userRoles);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(userRoles)));
    }
}