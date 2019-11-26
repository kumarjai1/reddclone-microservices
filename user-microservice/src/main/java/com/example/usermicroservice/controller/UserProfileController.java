package com.example.usermicroservice.controller;


import com.example.usermicroservice.model.UserProfile;
import com.example.usermicroservice.service.UserProfileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
@Api(tags="User Profile Handler")
public class UserProfileController {

    @Autowired
    UserProfileService userProfileService;

    @ApiOperation(value="Creates an User Profile", produces="application/json")
    @PostMapping
    public UserProfile createUserProfile(@RequestBody UserProfile userProfile, @RequestHeader("username") String username) {
        return userProfileService.createProfile(userProfile, username);
    }

//    @PutMapping("/{username}")
//    public UserProfile updateProfile(@RequestBody UserProfile profile, @RequestHeader("username") String username) {
//        return userProfileService.updateUserProfile(username, profile);
//    }

    @ApiOperation(value="Gets an User Profile", produces="application/json")
    @GetMapping
    public UserProfile getUserProfile(@RequestHeader ("username") String username){
        return userProfileService.getUserProfile(username);
    }

}
