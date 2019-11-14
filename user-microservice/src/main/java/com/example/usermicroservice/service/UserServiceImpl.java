package com.example.usermicroservice.service;

import com.example.usermicroservice.model.User;
import com.example.usermicroservice.model.UserRole;
import com.example.usermicroservice.repository.UserRepository;
import com.example.usermicroservice.util.JwtResponse;
import com.example.usermicroservice.config.JwtUtil;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;


    @Bean("encoder")
    public PasswordEncoder encoder(){ return new BCryptPasswordEncoder();}


    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserRoleService userRoleService;

    @Override
    public Iterable<User> listUsers() {
        return userRepository.findAll();
    }

    @Override
    public JwtResponse signup(User user) {

//        UserRole userRole = userRoleService.getRole(user.getUserRole().getName());
//        user.setUserRole(userRole);
//        List<UserRole> userRoles = userRoleService.userRoles(user.getUse)

        user.setPassword(encoder().encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        if(savedUser != null) {
            UserDetails userDetails = loadUserByUsername(user.getUsername());
            return new JwtResponse( jwtUtil.generateToken(userDetails),user.getUsername());

        }
        return null;
    }

    @Override
    public String login(User user) {
        User foundUser = userRepository.findByUsername(user.getUsername());

        if (foundUser != null && encoder().matches(user.getPassword(), foundUser.getPassword())) {
            UserDetails userDetails = loadUserByUsername(foundUser.getUsername());
            return jwtUtil.generateToken(userDetails);
        }
        return null; //TODO: throw an exception
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByUserName(username);

        return new org.springframework.security.core.userdetails
                .User(user.getUsername(),
                encoder().encode(user.getPassword()), getGrantedAuthorities(user));
    }

    private List<GrantedAuthority> getGrantedAuthorities(User user){
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

//        authorities.add(new SimpleGrantedAuthority("admin"));

        return authorities;
    }

    @Override
    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }


}
