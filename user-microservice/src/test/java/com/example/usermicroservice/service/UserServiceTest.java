package com.example.usermicroservice.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.usermicroservice.config.JwtUtil;
import com.example.usermicroservice.exception.EntityAlreadyExists;
import com.example.usermicroservice.exception.EntityNotFoundException;
import com.example.usermicroservice.model.User;
import com.example.usermicroservice.model.UserRole;
import com.example.usermicroservice.repository.UserRepository;
import com.example.usermicroservice.util.JwtResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    private User user1;
    private UserRole userRole;
    private List<UserRole> userRoles;
    private String encodedPassword;
    private String generatedToken;
    private ObjectMapper objectMapper;

    public UserServiceTest () {
        user1 = new User();
        userRole = new UserRole();
        userRoles = new ArrayList<>();
        encodedPassword="123456456asdasdajhgsd";
        generatedToken = "khasdkhasd.jasjkdhaskdj.jhadkjhdkasdsa";
        objectMapper = new ObjectMapper();
        MockitoAnnotations.initMocks(this);
    }

    @BeforeEach
    public void init () {
        userRole.setId(1);
        userRole.setName("ROLE_USER");
        userRoles.add(userRole);

        user1.setId(1L);
        user1.setUsername("user1");
        user1.setPassword("pw1");
        user1.setEmail("user1@email.com");
        user1.setUserRoles(userRoles);
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
        when(encoder.encode(anyString())).thenReturn(encodedPassword);
        when(userRepository.save(any())).thenReturn(user1);
        when(jwtUtil.generateToken(any())).thenReturn(generatedToken);
        when(userRepository.findUserByUsername(anyString())).thenReturn(user1);

        JwtResponse jwtResponse = new JwtResponse(generatedToken, user1.getUsername());
        JwtResponse returnedJwtResponse = userService.signup(user1);

        assertThat(returnedJwtResponse).isNotNull();
        assertThat(returnedJwtResponse).isEqualToComparingFieldByField(jwtResponse);

    }
}
