package com.example.apigateway.config;

import com.example.apigateway.bean.UserBean;
import com.example.apigateway.repository.UserRepository;
import com.netflix.zuul.context.RequestContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationFilterTest {

    @InjectMocks
    AuthenticationFilter authenticationFilter;

    @Mock
    UserRepository userRepository;

    @Mock
    private SecurityContextHolder  securityContextHolder;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication auth;
//
//    @Mock
//    RequestContext requestContext;

    @Before
    public void init () {
        securityContextHolder.setContext(securityContext);
    }

    @Test
    public void filterType_ReturnsPreFilterString_Success () {
        String pre = authenticationFilter.filterType();

        assertThat(pre).isNotNull();
        assertThat(pre).isEqualTo("pre");
    }

    @Test
    public void filterOrder_ReturnsOne_Success () {
        int order = authenticationFilter.filterOrder();

        assertThat(order).isNotNull();
        assertThat(order).isEqualTo(1);
    }

    @Test
    public void shouldFilter_returnsTrueIfUserExists_Success () {
        when(securityContext.getAuthentication()).thenReturn(auth);
        when(auth.getName()).thenReturn("user1");
        boolean shouldFilter = authenticationFilter.shouldFilter();
        assertThat(shouldFilter).isTrue();
    }

    @Test
    public void shouldFilter_ReturnsFalse_StopsFilter () {
        when(securityContext.getAuthentication()).thenReturn(auth);
        when(auth.getName()).thenReturn("anonymousUser");

        boolean shouldFilter = authenticationFilter.shouldFilter();
        assertThat(shouldFilter).isFalse();
    }

    @Test
    public void runFilter_addToHeader_Success () {
        UserBean user = new UserBean(1L, "user1@email", "user1", "pw1");
        when(securityContext.getAuthentication()).thenReturn(auth);
        when(auth.getName()).thenReturn("user1");
        when(userRepository.getUserByUsername(any())).thenReturn(user);

        Object ctx = authenticationFilter.run();
        RequestContext returnedRequestContext = (RequestContext) ctx;
        System.out.println(returnedRequestContext);
        assertThat("user1").isEqualTo(returnedRequestContext.getZuulRequestHeaders().get("username"));
    }

    @Test
    public void runFilter_UserNotFound_ReturnNull () {
//        UserBean user = new UserBean(1L, "user1@email", "user1", "pw1");
        when(securityContext.getAuthentication()).thenReturn(auth);
        when(auth.getName()).thenReturn("user1");
        when(userRepository.getUserByUsername(any())).thenReturn(null);

        Object ctx = authenticationFilter.run();
        RequestContext returnedRequestContext = (RequestContext) ctx;
        assertThat(returnedRequestContext).isNull();
    }


}
