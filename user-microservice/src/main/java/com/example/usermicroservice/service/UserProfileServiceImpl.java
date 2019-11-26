package com.example.usermicroservice.service;

import com.example.usermicroservice.model.User;
import com.example.usermicroservice.model.UserProfile;
import com.example.usermicroservice.model.UserRole;
import com.example.usermicroservice.repository.UserProfileRepository;
import com.example.usermicroservice.repository.UserRepository;
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

    @Autowired
    UserRepository userRepository;

    @Override
    public UserProfile createProfile(UserProfile userProfile, String username) {
//        Authentication authentication = authenticationUtil.getAuthentication();
//        System.out.println("1. auth: "+ authentication.getName() + " | username passsed: " + username);
//
        User user = userService.getUser(username);
        UserProfile foundProfile = user.getUserProfile();
        System.out.println("1. username: " + username + " usernameUser:" + user.getUsername());
        

        if (user.getUsername() != null) {
            System.out.println("2. usernamePassed: " + username);

            if (foundProfile != null) {
                System.out.println("3. username: " + username);
                userProfile.setId(foundProfile.getId());
            }
            foundProfile = userProfileRepository.save(userProfile);
            user.setUserProfile(foundProfile);
            foundProfile.setUser(user);
            userService.updateUser(user);
            return userProfileRepository.save(foundProfile);
        }

        return userProfileRepository.save(userProfile);

    }



    @Override
    public UserProfile getUserProfile(String username) {
        User user = userService.getUser(username);
        System.out.println(user.getUsername() + " passed username:" + username);

        if (user == null) {
            return null;
        }

        UserProfile savedProfile = userProfileRepository.findUserProfileByUsername(username);
        if (savedProfile == null) {
            System.out.println(2 + user.getUsername() + " passed username:" + username);
            savedProfile = new UserProfile();
            userProfileRepository.save(savedProfile);
            savedProfile.setUser(user);
            user.setUserProfile(savedProfile);
            userService.updateUser(user);
            return userProfileRepository.save(savedProfile);
        }
        return savedProfile;

    }

}
