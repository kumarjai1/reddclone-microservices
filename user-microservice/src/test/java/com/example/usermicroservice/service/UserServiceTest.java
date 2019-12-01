package com.example.usermicroservice.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.usermicroservice.config.JwtUtil;
import com.example.usermicroservice.exception.EntityAlreadyExists;
import com.example.usermicroservice.exception.EntityNotFoundException;
import com.example.usermicroservice.exception.LoginException;
import com.example.usermicroservice.model.User;
import com.example.usermicroservice.model.UserRole;
import com.example.usermicroservice.repository.UserRepository;
import com.example.usermicroservice.util.JwtResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserServiceTest {

    private User user1;
    private List<User> users;
    private UserRole userRole;
    private List<UserRole> userRoles;
    private String encodedPassword;
    private String generatedToken;
    private ObjectMapper objectMapper;

    public UserServiceTest () {
        user1 = new User();
        users = new ArrayList<>();
        userRole = new UserRole();
        userRoles = new ArrayList<>();
        encodedPassword="123456456asdasdajhgsd";
        generatedToken = "khasdkhasd.jasjkdhaskdj.jhadkjhdkasdsa";
        objectMapper = new ObjectMapper();
        MockitoAnnotations.initMocks(this);
    }

    @Before
    public void init () {
        userRole.setId(1);
        userRole.setName("ROLE_USER");
        userRoles.add(userRole);

        user1.setId(1L);
        user1.setUsername("user1");
        user1.setPassword("pw1");
        user1.setEmail("user1@email.com");
        user1.setUserRoles(userRoles);
        users.add(user1);
    }

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    UserRoleService userRoleService;

    @Mock
    PasswordEncoder encoder;

    @Mock
    JwtUtil jwtUtil;

    @Test
    public void signup_ValidUser_Sucess() throws EntityNotFoundException, EntityAlreadyExists {

        when(userRoleService.getRole(any())).thenReturn(userRole);
        when(userRepository.findUserByUsername(any())).thenReturn(null);
        when(jwtUtil.generateToken(any())).thenReturn(generatedToken);
        when(userRepository.save(any())).thenReturn(user1);
        JwtResponse jwtResponse = new JwtResponse(generatedToken, user1.getUsername());
        JwtResponse returnedJwtResponse = userService.signup(user1);

        assertThat(returnedJwtResponse).isNotNull();
        assertThat(returnedJwtResponse).isEqualToComparingFieldByField(jwtResponse);
        verify(jwtUtil, times(1)).generateToken(any());
    }

    @Test(expected = EntityAlreadyExists.class)
    public void signup_UserAlreadyExists_ThrowsException () throws EntityNotFoundException, EntityAlreadyExists {
        when(userRoleService.getRole(any())).thenReturn(userRole);
        when(userRepository.findUserByUsername(any())).thenReturn(user1);
        when(jwtUtil.generateToken(any())).thenReturn(generatedToken);
        when(userRepository.save(any())).thenReturn(user1);
        JwtResponse jwtResponse = new JwtResponse(generatedToken, user1.getUsername());
        JwtResponse returnedJwtResponse = userService.signup(user1);

        assertThat(returnedJwtResponse).isNotNull();
        assertThat(doThrow(EntityAlreadyExists.class));
    }

    @Test
    public void login_ValidUserInfo_Success() throws EntityNotFoundException {
        when(userRepository.findUserByEmail(any())).thenReturn(user1);
        when(encoder.encode(any())).thenReturn(encodedPassword);
        when(encoder.matches(any(),any())).thenReturn(true);
        when(jwtUtil.generateToken(any())).thenReturn(generatedToken);

        JwtResponse jwtResponse = new JwtResponse(generatedToken, user1.getUsername());
        System.out.println(jwtResponse.getUsername()+ "token: " + jwtResponse.getToken());

        JwtResponse returnedJwtResponse = userService.login(user1);
        System.out.println(jwtResponse);

        assertThat(jwtResponse).isNotNull();
        assertThat(returnedJwtResponse).isNotNull();
        assertThat(returnedJwtResponse).isEqualToComparingFieldByField(jwtResponse);
        verify(jwtUtil, times(1)).generateToken(any());
    }

    @Test (expected = LoginException.class)
    public void login_PasswordsIncorrectGiven_ThrowException() throws EntityNotFoundException, LoginException {
        when(userRepository.findUserByEmail(anyString())).thenReturn(user1);
        when(jwtUtil.generateToken(any())).thenReturn(generatedToken);
        when(encoder.matches(anyString(),anyString())).thenReturn(false);
        when(encoder.encode(any())).thenReturn(encodedPassword);

        JwtResponse returnedJwtResponse = userService.login(user1);

        assertThat(returnedJwtResponse).isNotNull();
        assertThat(doThrow(LoginException.class));
    }

    @Test (expected = EntityNotFoundException.class)
    public void login_UserIncorrectGiven_ThrowException() throws EntityNotFoundException, LoginException {
        when(userRepository.findUserByEmail(anyString())).thenReturn(null);
        JwtResponse returnedJwtResponse = userService.login(user1);
        assertThat(returnedJwtResponse).isNotNull();
        assertThat(doThrow(EntityNotFoundException.class));
    }

    @Test
    public void listUsers_ReturnsListOfUsers_Success() {
        when (userRepository.findAll()).thenReturn(users);

        Iterable<User> returnedUsers = userService.listUsers();

        assertThat(returnedUsers).isNotNull();
        assertThat(returnedUsers).containsSequence(users);
    }

    @Test
    public void getUserRoles_ReturnsRolesForThatUser_Success () {
        when(userRepository.findUserByUsername(any())).thenReturn(user1);

        Iterable<UserRole> returnedUserRoles = userService.getUserRoles(user1.getUsername());

        assertThat(returnedUserRoles).isNotNull();
        assertThat(returnedUserRoles).containsSequence(userRoles);
    }

    @Test
    public void addRole_UserExists_SuccessRoleAdded () throws EntityNotFoundException {
        when (userRepository.findById(any())).thenReturn(java.util.Optional.ofNullable(user1));
        when (userRoleService.getRoleById(any())).thenReturn(userRole);

        Iterable<UserRole> returnedAddedUserRoles = userService.addRole(1L, 1L);

        assertThat(returnedAddedUserRoles).isNotNull();
        assertThat(returnedAddedUserRoles).containsSequence(userRoles);
    }

    @Test (expected = EntityNotFoundException.class)
    public  void addRole_RoleNotFound_ThrowRoleNotFoundError () throws EntityNotFoundException {
        when(userRepository.findById(any())).thenReturn(java.util.Optional.ofNullable(user1));
        when(userRoleService.getRoleById(any())).thenReturn(null);

        Iterable<UserRole> returnedAddedUserRoles = userService.addRole(1L, 1L);
        assertThat(returnedAddedUserRoles).isNull();
        assertThat(doThrow(EntityNotFoundException.class));
    }

    @Test (expected = EntityNotFoundException.class)
    public  void addRole_UserNotFound_ThrowUserNotFoundError () throws EntityNotFoundException {
        when(userRepository.findById(any())).thenReturn(java.util.Optional.ofNullable(null));
        when(userRoleService.getRoleById(any())).thenReturn(userRole);

        Iterable<UserRole> returnedAddedUserRoles = userService.addRole(1L, 1L);
//        assertThat(returnedAddedUserRoles).isNull();
        assertThat(doThrow(EntityNotFoundException.class));
    }

}
