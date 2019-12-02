package com.example.usermicroservice.service;

import com.example.usermicroservice.model.User;
import com.example.usermicroservice.model.UserProfile;
import com.example.usermicroservice.repository.UserProfileRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserProfileServiceTest {

    private User user;
    private UserProfile userProfile;
    private ObjectMapper objectMapper;

    public UserProfileServiceTest () {
        user = new User();
        userProfile = new UserProfile();
        objectMapper = new ObjectMapper();
        MockitoAnnotations.initMocks(this);
    }

    @Before
    public void init () {
        userProfile.setId(1L);
        userProfile.setUser(user);
        userProfile.setMobile("123.456.7890");
        userProfile.setAdditionalEmail("aemail@gmail.com");
        userProfile.setAddress("123 St, City, State, 12345");

        user.setId(1L);
        user.setUsername("user1");
        user.setPassword("pw1");
        user.setEmail("user1@email.com");
        user.setUserProfile(userProfile);
    }

    @InjectMocks
    UserProfileServiceImpl userProfileService;

    @Mock
    UserService userService;

    @Mock
    UserProfileRepository userProfileRepository;

    @Test
    public void  createUserProfile_NoProfileBeforeReturnNewProfile_Success () {

        when(userService.getUser(any())).thenReturn(user);
//        when(userProfileRepository.findUserProfileByUsername(anyString())).thenReturn(null); //previous user profile doens't exist
        when(userProfileRepository.save(any())).thenReturn(userProfile);
        when(userService.updateUser(any())).thenReturn(user);
        UserProfile newUserProfile = userProfileService.createProfile(userProfile, user.getUsername());
        assertThat(userProfile.getId()).isNotNull();
        assertThat(newUserProfile).isEqualToComparingFieldByField(userProfile);
    }

    @Test
    public void updateUserProfile_UpdateExistingProfile_Success () {

        when(userService.getUser(anyString())).thenReturn(user);
//        when(userProfileRepository.findUserProfileByUsername(anyString())).thenReturn(userProfile);
        when(userProfileRepository.save(userProfile)).thenReturn(userProfile);
        when(userService.updateUser(any())).thenReturn(user);

        UserProfile updatedProfile = userProfileService.createProfile(userProfile, user.getUsername());

        assertThat(updatedProfile).isNotNull();
        assertThat(updatedProfile).isEqualToComparingFieldByField(userProfile);
    }

    @Test
    public void getUserProfile_ValidUser_SuccessReturnUserProfile () {
        when(userService.getUser(anyString())).thenReturn(user);
        when(userProfileRepository.findUserProfileByUsername(anyString())).thenReturn(userProfile);
//        when(userProfileRepository.save(any())).thenReturn(userProfile);
//        when(userService.updateUser(any())).thenReturn(user);

        UserProfile returnedUserProfile = userProfileService.getUserProfile(user.getUsername());

        assertThat(returnedUserProfile).isNotNull();
        assertThat(returnedUserProfile).isEqualToComparingFieldByField(userProfile);
    }

    @Test
    public void getUserProfile_ProfileNotFound_CreateEmptyProfile () {
        when(userService.getUser(anyString())).thenReturn(user);
        when(userProfileRepository.findUserProfileByUsername(anyString())).thenReturn(null);
        when(userProfileRepository.save(any())).thenReturn(userProfile);
        when(userService.updateUser(any())).thenReturn(user);

        UserProfile returnedUserProfile = userProfileService.getUserProfile(user.getUsername());

        assertThat(returnedUserProfile).isNotNull();
        assertThat(returnedUserProfile).isEqualToComparingFieldByField(userProfile);

    }
}