package com.example.apigateway.service;

import com.example.apigateway.bean.UserBean;
import com.example.apigateway.config.AuthenticationFilter;
import com.example.apigateway.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserService implements UserDetailsService {
  private Logger logger = LoggerFactory.getLogger(CustomUserService.class);

  @Autowired
  @Qualifier("encoder")
  PasswordEncoder bCryptPasswordEncoder;

  @Autowired
  UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    logger.info("loadUser");
    UserBean user = userRepository.getUserByUsername(username);
    logger.info("found user username: " + user.getUsername());
    if(user == null) {
      logger.info("not found user username: " + username);
      throw new UsernameNotFoundException("Unknown username " + username);
    }


    return new org.springframework.security.core.userdetails.User(user.getUsername(), bCryptPasswordEncoder.encode(user.getPassword()),
        true, true, true, true, new ArrayList<>());
  }

  private List<GrantedAuthority> getGrantedAuthorities(UserBean user){
    List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
//    TODO : add this back in for user roles
//    authorities.add(new SimpleGrSystem.out.println("error coming here handleexceptionentitynotfound");antedAuthority(user.getUserRole().getName()));

    return authorities;
  }
}
