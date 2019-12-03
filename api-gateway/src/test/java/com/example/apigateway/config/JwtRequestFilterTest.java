package com.example.apigateway.config;

import com.example.apigateway.service.CustomUserService;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JwtRequestFilterTest {

    @InjectMocks
    JwtRequestFilter jwtRequestFilter;

    @Mock
    CustomUserService userService;

    @Mock
    JwtUtil jwtUtil;

    @Mock
    UserDetails userDetails;

    @Mock
    FilterChain filterChain;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    private String headerToken;
    private String username;

    @Before
    public void init () {
        headerToken = "Bearer 123abc";
        username = "user1";
    }

    @Test
    public void doFilterInternal_Success() throws ServletException, IOException {
        when(request.getHeader(anyString())).thenReturn(headerToken);
        when(jwtUtil.getUsernameFromToken(any())).thenReturn(username);
        when(userService.loadUserByUsername(anyString())).thenReturn(userDetails);
        when(jwtUtil.validateToken(any(), any())).thenReturn(true);

        jwtRequestFilter.doFilterInternal(request, response, filterChain);
    }

    @Test
    public void doFilterInternal_UnableToGetJwtToken_ThrowError() throws ServletException, IOException {
        when(request.getHeader(anyString())).thenReturn(headerToken);
        when(jwtUtil.getUsernameFromToken(any())).thenThrow(IllegalArgumentException.class);

        jwtRequestFilter.doFilterInternal(request, response, filterChain);
    }

    @Test
    public void doFilterInternal_ExpiredToken_ThrowExpirationError() throws ServletException, IOException {
        when(request.getHeader(anyString())).thenReturn(headerToken);
        when(jwtUtil.getUsernameFromToken(any())).thenThrow(ExpiredJwtException.class);

        jwtRequestFilter.doFilterInternal(request, response, filterChain);
    }
}
