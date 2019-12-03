package com.example.apigateway.service;

import com.example.apigateway.bean.UserBean;
import com.example.apigateway.repository.UserRepository;
import static org.assertj.core.api.Assertions.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class CustomUserServiceTest {

    @InjectMocks
    CustomUserService customUserService;

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    private UserBean userBean;
    private String email;
    private String username;
    private String password;
    private Long id;


    @Before
    public void init () {
        email = "user1@email.com";
        username = "user1";
        password = "pw1";
        id = 1L;

        userBean = new UserBean(id, email, username, password);
    }

    @Test
    public void loadUserByUsername_UserExists_SuccessLoadUser() {
        when(userRepository.getUserByUsername(any())).thenReturn(userBean);
        when(passwordEncoder.encode(any())).thenReturn(userBean.getPassword());

        UserDetails foundUser = customUserService.loadUserByUsername("user1");

        assertNotNull(foundUser.getUsername());
        assertEquals(foundUser.getUsername(), userBean.getUsername());
    }

    @Test (expected = UsernameNotFoundException.class)
    public void LoadUserByUsername_UserNotFound_ThrowException () throws UsernameNotFoundException {
        when(userRepository.getUserByUsername(any())).thenReturn(null);
        UserDetails foundUser = customUserService.loadUserByUsername(userBean.getUsername());

        assertThat(foundUser).isNull();
        assertThat(doThrow(UsernameNotFoundException.class));

    }
}
