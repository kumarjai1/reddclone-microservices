package com.example.usermicroservice.service;

import com.example.usermicroservice.model.UserProfile;

public interface UserProfileService {

    UserProfile createProfile (UserProfile userProfile, String username);
    UserProfile getUserProfile(String username);
    UserProfile updateUserProfile(String username, UserProfile updateProfile);
}
