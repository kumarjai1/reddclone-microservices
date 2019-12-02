package com.example.usermicroservice.service;

import com.example.usermicroservice.model.User;
import com.example.usermicroservice.model.UserProfile;
import com.example.usermicroservice.model.UserRole;
import com.example.usermicroservice.repository.UserProfileRepository;
import com.example.usermicroservice.repository.UserRepository;
//import com.example.usermicroservice.util.AuthenticationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    UserProfileRepository userProfileRepository;

//    @Autowired
//    AuthenticationUtil authenticationUtil;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    private static Logger logger = LoggerFactory.getLogger(UserProfileServiceImpl.class.getName());

    @Override
    public UserProfile createProfile(UserProfile userProfile, String username) {
//        Authentication authentication = authenticationUtil.getAuthentication();
//        System.out.println("1. auth: "+ authentication.getName() + " | username passsed: " + username);
//
        User user = userService.getUser(username);
        UserProfile foundProfile = user.getUserProfile();
        logger.info("Creating profile for user");

        if (user.getUsername() != null) {
            logger.info("Creating profile for the user found  with with username: " + user.getUsername());
//            System.out.println("2. usernamePassed: " + username);

            if (foundProfile != null) {
                logger.info("User already has profile so just keep the same id for the profile: " + foundProfile.getId());
                userProfile.setId(foundProfile.getId());
            }
            logger.info("User doesn't have profile so creating and saving the new user profile.");
            foundProfile = userProfileRepository.save(userProfile);
            user.setUserProfile(foundProfile);
            foundProfile.setUser(user);
            userService.updateUser(user);
            logger.info("User profile creeated and saved");
            return userProfileRepository.save(foundProfile);
        }

        return userProfileRepository.save(userProfile);

    }



    @Override
    public UserProfile getUserProfile(String username) {
        User user = userService.getUser(username);
        logger.info("Getting userprofile for specific user");
        System.out.println(user.getUsername() + " passed username:" + username);

        if (user == null) {
            logger.error("User doesn't exist, so no profile is shown");
            return null;
        }

        UserProfile savedProfile = userProfileRepository.findUserProfileByUsername(username);
        if (savedProfile == null) {
            logger.error("User profile is null or doesn't exist so create new profile");
            savedProfile = new UserProfile();
            userProfileRepository.save(savedProfile);
            savedProfile.setUser(user);
            user.setUserProfile(savedProfile);
            userService.updateUser(user);
            logger.error("Creating a blank user profile for the user with username: " + username);
            return userProfileRepository.save(savedProfile);
        }
        return savedProfile;

    }

}
