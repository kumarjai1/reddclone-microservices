package com.example.apigateway.config;

import com.example.apigateway.bean.UserBean;
import com.example.apigateway.repository.UserRepository;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFilter extends ZuulFilter {

  @Autowired
  UserRepository userRepository;

  @Override
  public String filterType() {
    return "pre";
  }

  @Override
  public int filterOrder() {
    return 1;
  }

  @Override
  public boolean shouldFilter() {
//    String username = SecurityContextHolder.getContext().getAuthentication().getName();
//    if(username != "anonUser") return true;
//    else return false;
//    return false;

    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    System.out.println(username);
    if (username !="anonymousUser") {
      return true;
    }
    return false;
  }

  @Override
  public Object run() {
    RequestContext ctx = RequestContext.getCurrentContext();
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    UserBean user = null;

    if (userRepository.getUserByUsername(username) !=null) {
      user = userRepository.getUserByUsername(username);
      String userId = String.valueOf(user.getId());
      ctx.addZuulRequestHeader("userId", userId);
      ctx.addZuulRequestHeader("username", username);
      System.out.println("found user username: " + username);
    }
    return null;
  }
}