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
        System.out.println("1. username: " + username + " usernameUser:" + user.getUsername());
        System.out.println("1. userProfile: " + user.getUserProfile().getId());

        if (user.getUsername() != null) {
            System.out.println("2. username: " + username);
            System.out.println("2. userProfile: " + user.getUserProfile().getId());

            if (user.getUserProfile() != null) {
                System.out.println("3. username: " + username);
                userProfile.setId(user.getUserProfile().getId());
            }
            user.setUserProfile(userProfile);
            userProfile.setUser(user);
            userProfileRepository.save(userProfile);
            return userService.updateUser(user).getUserProfile();
        }
//         else {
//            return null;
//        }

//        if (user.getUserProfile() == null) {
//            user.setUserProfile(userProfile);
//            userProfileRepository.save(userProfile);
//        }
//        return userService.getUser(username).getUserProfile();

        return userRepository.findUserByUsername(username).getUserProfile();

//        User user = userRepository.findUserByUsername(username);
//        if (user.getUserProfile() == null) {
//            user.setUserProfile(userProfile);
//            userProfileRepository.save(userProfile);
//        }
//        return userRepository.findUserByUsername(username).getUserProfile();
    }



    @Override
    public UserProfile getUserProfile(String username) {
        if (userService.getUser(username).getUserProfile() == null) {
            createProfile(new UserProfile(), username);
        }
        return userProfileRepository.findUserProfileByUsername(username);
    }

    @Override
    public UserProfile updateUserProfile(String username, UserProfile updateProfile) {
        User user = userService.getUser(username);
        UserProfile profile = userProfileRepository.findUserProfileByUsername(username);
        if (updateProfile.getAdditionalEmail() !=null) profile.setAdditionalEmail(updateProfile.getAdditionalEmail());
        if (updateProfile.getAddress() !=null) profile.setAdditionalEmail(updateProfile.getAddress());
        if (updateProfile.getMobile() != null) profile.setMobile(updateProfile.getMobile());

        userProfileRepository.save(profile);
        user.setUserProfile(profile);
        return user.getUserProfile();
    }


}
