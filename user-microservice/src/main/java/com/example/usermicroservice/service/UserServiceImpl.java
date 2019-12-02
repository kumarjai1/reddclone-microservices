package com.example.usermicroservice.service;


import com.example.usermicroservice.exception.EntityAlreadyExists;
import com.example.usermicroservice.exception.EntityNotFoundException;
import com.example.usermicroservice.exception.LoginException;
import com.example.usermicroservice.model.User;
import com.example.usermicroservice.model.UserRole;
import com.example.usermicroservice.repository.UserRepository;
import com.example.usermicroservice.util.JwtResponse;
import com.example.usermicroservice.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.ArrayList;
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
    public JwtResponse signup(User user) throws EntityAlreadyExists, EntityNotFoundException {

        if (getUser(user.getUsername()) != null || userRepository.findUserByEmail(user.getEmail()) != null) {
            throw new EntityAlreadyExists("User with this username or email already exists");
        }

        List<UserRole> userRoles = new ArrayList<>();

        //TODO: make sure I am able to create role, if database is empty and role_user doesn't exist -  it was working  11.25.2019
        UserRole userRole = userRoleService.getRole("ROLE_USER");

        if (userRole == null) {
            userRole = new UserRole();
            userRole.setName("ROLE_USER");
            userRoleService.createRole(userRole);
        }
        userRoles.add(userRole);
        user.setUserRoles(userRoles);
        user.setPassword(encoder().encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        if(savedUser != null) {
            return new JwtResponse(jwtUtil.generateToken(savedUser.getUsername()), savedUser.getUsername());
        }

        return null;
    }

    @Override
    public JwtResponse login(User user) throws LoginException, EntityNotFoundException {
        User foundUser = userRepository.findUserByEmail(user.getEmail());

        if  (foundUser == null) {
            throw new EntityNotFoundException("User with this email doesn't exist, sign up");
        } else if (foundUser != null &&
                encoder().matches(user.getPassword(), foundUser.getPassword())) {
            return new JwtResponse(jwtUtil.generateToken(foundUser.getUsername()), foundUser.getUsername());
        } else if (foundUser != null && !encoder().matches(user.getPassword(), foundUser.getPassword())) {
            System.out.println("username is coming here error - error");
            throw new LoginException("email / password incorrect"); //TODO: throw an exception
        }

        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);

        if (user == null) throw new UsernameNotFoundException("Unknown username " + username);
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
        return userRepository.findUserByUsername(username);
    }

    @Override
    public Iterable<UserRole> getUserRoles(String username) {
        User user = userRepository.findUserByUsername(username);
        return user.getUserRoles();
    }

    @Override
    public Iterable<UserRole> addRole(Long userId, Long roleId) throws EntityNotFoundException {
        User user = userRepository.findById(userId).orElse(null);
        UserRole userRole = userRoleService.getRoleById(roleId);
        if (user != null) {
            if (userRole == null) {
                throw new EntityNotFoundException("Role doesn't exists, create the role first");
            }
            else if (userRole != null) {
                user.getUserRoles().add(userRole);
                updateUser(user);
            }
        } else if (user == null) {
            throw new EntityNotFoundException("User doesn't exist");
        }

        return user.getUserRoles();
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }


}
