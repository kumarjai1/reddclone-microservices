package com.example.apigateway.config;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.linesOf;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JwtUtilTest {

    @InjectMocks
    JwtUtil jwtUtil;

    @Mock
    UserDetails userDetails;

    private String jwtToken;
    private String username;
    private String secret;

    @Before
    public void init () {
        ReflectionTestUtils.setField(jwtUtil,"secret","twolittleorphans");
        username = "user1";
        when(userDetails.getUsername()).thenReturn(username);
        jwtToken = jwtUtil.generateToken(userDetails);

    }

    @Test
    public void getUsernameFromToken_ReturnsUsername_Success() {
        String tokenUsername = jwtUtil.getUsernameFromToken(jwtToken);
//        String token = jwtUtil.generateToken(userDetails);
        assertThat(tokenUsername).isNotNull();
        assertThat(tokenUsername).isEqualTo(username);

    }


    @Test
    public void validateToken_BooleanValid_Success() {
        Boolean isValid = jwtUtil.validateToken(jwtToken, userDetails);
        assertThat(isValid).isNotNull();
        assertThat(isValid).isTrue();
    }

    @Test
    public void getExpirationDateFromToken() {
        jwtUtil.getExpirationDateFromToken(jwtToken);
        Boolean isExpired = jwtUtil.isTokenExpired(jwtToken);

        assertThat(isExpired).isNotNull();

    }
}
