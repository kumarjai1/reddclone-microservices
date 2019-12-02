package com.example.usermicroservice.controller;

import com.example.usermicroservice.exception.UserExceptionHandler;
import com.example.usermicroservice.model.User;
import com.example.usermicroservice.model.UserRole;
import com.example.usermicroservice.service.UserRoleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserRoleControllerTest {

    private MockMvc mockMvc;
    private User user;
    private UserRole userRole;
    private List<UserRole> roles;
    private ObjectMapper objectMapper;

    public UserRoleControllerTest() {
        user = new User();
        userRole = new UserRole();
        roles = new ArrayList<>();
        objectMapper = new ObjectMapper();
        MockitoAnnotations.initMocks(this);
    }

    @InjectMocks
    UserRoleController userRoleController;

    @Mock
    UserRoleService userRoleService;

    @Before
    public void init () {
        mockMvc = MockMvcBuilders.standaloneSetup(userRoleController)
                .setControllerAdvice(UserExceptionHandler.class)
                .build();
        userRole.setId(1);
        userRole.setName("ROLE_USER");
        roles.add(userRole);

        user.setId(1L);
        user.setUsername("user1");
        user.setPassword("pw1");
        user.setEmail("user1@email.com");
    }

    @Test
    public void listRoles_AuthenticatedUser_SuccessRolesList () throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/roles");

        when (userRoleService.listRoles()).thenReturn(roles);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(roles)));
    }

    @Test
    public void getRole_RoleWithNameExists_Success () throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/"+userRole.getName());
//                .contentType(MediaType.APPLICATION_JSON);
        when(userRoleService.getRole(any())).thenReturn(userRole);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(userRole)));
    }

    @Test
    public void getRoleById_RoleWithIdExists_Success () throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/roles/"+userRole.getId());

        when(userRoleService.getRoleById(any())).thenReturn(userRole);
        System.out.println(userRole.getId());

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());
//                .andExpect(content().json(objectMapper.writeValueAsString(userRole)));
    }

    @Test
    public void creatRole_AuthenticatedUsed_Success () throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/role")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRole));

        when(userRoleService.createRole(any())).thenReturn(userRole);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(userRole)));
    }

//    @Test
//    public void getUserProfile_ValidUser_SuccessProfileReturned () throws Exception {
//
//        RequestBuilder requestBuilder = MockMvcRequestBuilders
//                .get("/profile")
//                .accept(MediaType.APPLICATION_JSON)
//                .header("username", "user1");
//
//        when(userProfileService.getUserProfile(any())).thenReturn(userProfile);
//
//        mockMvc.perform(requestBuilder)
//                .andExpect(status().isOk())
//                .andExpect(content().json(objectMapper.writeValueAsString(userProfile)));
//    }
}