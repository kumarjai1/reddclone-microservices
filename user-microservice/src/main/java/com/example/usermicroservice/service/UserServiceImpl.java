package com.example.usermicroservice.service;

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
    public JwtResponse signup(User user) {

        List<UserRole> userRoles = new ArrayList<>();
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
//            UserDetails userDetails = loadUserByUsername(user.getUsername());
            return new JwtResponse(jwtUtil.generateToken(savedUser.getUsername()), savedUser.getUsername());
        }
        return null;
    }

    @Override
    public JwtResponse login(User user) throws LoginException, EntityNotFoundException {
        //TODO: test user respository login method with the custom query
        User foundUser = userRepository.findUserByEmail(user.getEmail());
        System.out.println(foundUser.getUsername() + " " + foundUser.getEmail());
        if (foundUser != null &&
                encoder().matches(user.getPassword(), foundUser.getPassword())) {
            return new JwtResponse(jwtUtil.generateToken(foundUser.getUsername()), foundUser.getUsername());
        } else if (foundUser != null && !encoder().matches(user.getPassword(), foundUser.getPassword())) {
            System.out.println("username is coming here error - error");
            throw new LoginException("Username / password incorrect"); //TODO: throw an exception
        }
        System.out.println("username it coming here error");
        throw new EntityNotFoundException("User not found");
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
    public Iterable<UserRole> getUserRoles(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        return user.getUserRoles();
    }

    @Override
    public Iterable<UserRole> addRole(Long userId, Long roleId) {
        //TODO: check if userid belongs to the user
        User user = userRepository.findById(userId).orElse(null);
        UserRole userRole = userRoleService.getRoleById(roleId);
        if (user != null) {
            if (userRole != null) {
                user.getUserRoles().add(userRole);
                userRepository.save(user); //TODO: change to method, updateUser
            }
//            else {
//                throw new EntityNotFoundException("Role does not exist");
//            }
        }
        return user.getUserRoles();
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }


}
