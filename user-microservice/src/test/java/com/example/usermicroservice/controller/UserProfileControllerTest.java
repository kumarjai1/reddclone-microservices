package com.example.usermicroservice.controller;

import com.example.usermicroservice.exception.UserExceptionHandler;
import com.example.usermicroservice.model.User;
import com.example.usermicroservice.model.UserProfile;
import com.example.usermicroservice.service.UserProfileService;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@WebMvcTest(UserProfileController.class)
public class UserProfileControllerTest {

    private MockMvc mockMvc;
    private User user;
    private UserProfile userProfile;
    private ObjectMapper objectMapper;

    @InjectMocks
    UserProfileController userProfileController;

    @Mock
    UserProfileService userProfileService;

    public UserProfileControllerTest () {
        user = new User();
        userProfile = new UserProfile();
        objectMapper = new ObjectMapper();
        MockitoAnnotations.initMocks(this);
    }

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(userProfileController)
                .setControllerAdvice(UserExceptionHandler.class)
                .build();
        userProfile.setId(1L);
        userProfile.setMobile("123.456.7890");
        userProfile.setAdditionalEmail("aemail@gmail.com");
        userProfile.setAddress("123 St, City, State, 12345");
        userProfile.setUser(user);

        user.setId(1L);
        user.setUsername("user1");
        user.setPassword("pw1");
        user.setEmail("user1@email.com");
        user.setUserProfile(userProfile);
    }

    @Test
    public void getUserProfile_ValidUser_SuccessProfileReturned () throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/profile")
                .accept(MediaType.APPLICATION_JSON)
                .header("username", "user1");

        when(userProfileService.getUserProfile(any())).thenReturn(userProfile);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(userProfile)));
    }

    @Test
    public void createOrUpdateUserProfile_ValidUserNamePassed_Success () throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userProfile))
                .header("username", "user1");

        when(userProfileService.createProfile(any(), any())).thenReturn(userProfile);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(userProfile)));
    }
}
