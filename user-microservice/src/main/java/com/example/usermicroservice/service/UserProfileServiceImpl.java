package com.example.usermicroservice.service;

import com.example.usermicroservice.model.User;
import com.example.usermicroservice.model.UserProfile;
import com.example.usermicroservice.model.UserRole;
import com.example.usermicroservice.repository.UserProfileRepository;
import com.example.usermicroservice.util.AuthenticationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    UserProfileRepository userProfileRepository;

    @Autowired
    AuthenticationUtil authenticationUtil;

    @Autowired
    UserService userService;

    @Override
    public UserProfile createProfile(UserProfile userProfile, String username) {
//        Authentication authentication = authenticationUtil.getAuthentication();
//        System.out.println("1. auth: "+ authentication.getName() + " | username passsed: " + username);

        User user = userService.getUser(username);
        System.out.println("1. username: " + username);
        if (user != null) {
            System.out.println("2. username: " + username);
            if (user.getUserProfile() != null) {
                System.out.println("3. username: " + username);
                userProfile.setId(user.getUserProfile().getId());
            }
            user.setUserProfile(userProfile);
            userProfile.setUser(user);
            userProfileRepository.save(userProfile);
            return userService.updateUser(user).getUserProfile();

        }

        return userProfileRepository.save(userProfile);
    }
//
//    public Iterable<UserRole> addRole(Long userId, Long roleId) {
//        //TODO: check if userid belongs to the user
//        User user = userRepository.findById(userId).orElse(null);
//        UserRole userRole = userRoleService.getRoleById(roleId);
//        if (user != null) {
//            if (userRole != null) {
//                user.getUserRoles().add(userRole);
//                userRepository.save(user); //TODO: change to method, updateUser
//            } else {
//                throw new EntityNotFoundException();
//            }
//        }
//        return user.getUserRoles();
//    }



    @Override
    public UserProfile getUserProfile(String username) {
        return null;
    }
}
