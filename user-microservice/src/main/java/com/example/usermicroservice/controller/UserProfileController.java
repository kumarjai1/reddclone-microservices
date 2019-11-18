package com.example.usermicroservice.controller;


import com.example.usermicroservice.model.UserProfile;
import com.example.usermicroservice.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class UserProfileController {

    @Autowired
    UserProfileService userProfileService;

    @PostMapping
    public UserProfile createUserProfile(@RequestBody UserProfile userProfile, @RequestHeader("username") String username) {
        return userProfileService.createProfile(userProfile, username);
    }

    @PutMapping("/{username}")
    public UserProfile updateProfile(@RequestBody UserProfile profile, @RequestHeader("username") String username) {
        return userProfileService.updateUserProfile(username, profile);
    }

    @GetMapping
    public UserProfile getUserProfile(@RequestHeader ("username") String username){
        return userProfileService.getUserProfile(username);
    }

}
