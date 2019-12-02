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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class.getName());

    @Override
    public Iterable<User> listUsers() {
        return userRepository.findAll();
    }

    @Override
    public JwtResponse signup(User user) throws EntityAlreadyExists, EntityNotFoundException {

        if (getUser(user.getUsername()) != null || userRepository.findUserByEmail(user.getEmail()) != null) {
            logger.error("User already exists with the email or username: " + user.getEmail() + " | " + user.getUsername());
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
            logger.info("User signed up with an email: " + savedUser.getEmail());
            return new JwtResponse(jwtUtil.generateToken(savedUser.getUsername()), savedUser.getUsername());
        }

        return null;
    }

    @Override
    public JwtResponse login(User user) throws LoginException, EntityNotFoundException {
        User foundUser = userRepository.findUserByEmail(user.getEmail());

        if  (foundUser == null) {
            logger.error("User not found with email: " + user.getEmail());
            throw new EntityNotFoundException("User with this email doesn't exist, sign up");
        } else if (foundUser != null &&
                encoder().matches(user.getPassword(), foundUser.getPassword())) {
            logger.info("User logged in with email: " + foundUser.getEmail());
            return new JwtResponse(jwtUtil.generateToken(foundUser.getUsername()), foundUser.getUsername());
        } else if (foundUser != null && !encoder().matches(user.getPassword(), foundUser.getPassword())) {
//            System.out.println("username is coming here error - error");
            logger.error("User tried to login with incorrect password");
            throw new LoginException("email / password incorrect"); //TODO: throw an exception
        }

        return null;
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.findUserByUsername(username);
//
//        if (user == null) throw new UsernameNotFoundException("Unknown username " + username);
//        return new org.springframework.security.core.userdetails
//                .User(user.getUsername(),
//                encoder().encode(user.getPassword()), getGrantedAuthorities(user));
//    }
//
//    private List<GrantedAuthority> getGrantedAuthorities(User user){
//        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
//
////        authorities.add(new SimpleGrantedAuthority("admin"));
//
//        return authorities;
//    }

    @Override
    public User getUser(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public Iterable<UserRole> getUserRoles(String username) {
        logger.info("User is logged in and gets list of user roles");
        User user = userRepository.findUserByUsername(username);
        return user.getUserRoles();
    }

    @Override
    public Iterable<UserRole> addRole(Long userId, Long roleId) throws EntityNotFoundException {
        logger.info("User is logged in and wants to add role");
        User user = userRepository.findById(userId).orElse(null);
        UserRole userRole = userRoleService.getRoleById(roleId);
        if (user != null) {
            if (userRole == null) {
                logger.error("User is logged in adds a role that doesn't exist.");
                throw new EntityNotFoundException("Role doesn't exists, create the role first");
            }
            else if (userRole != null) {
                logger.info("Adding the role '" + userRole.getName() + "' " + "to user '" + user.getUsername() + "'.");
                user.getUserRoles().add(userRole);
                updateUser(user);
            }
        } else if (user == null) {
            logger.error("Trying to add a role to a user that doesn't exist");
            throw new EntityNotFoundException("User doesn't exist");
        }

        return user.getUserRoles();
    }

    @Override
    public User updateUser(User user) {
        logger.info("Updating user info with username: " + user.getUsername());
        return userRepository.save(user);
    }


}
