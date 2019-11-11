package com.ga.usermicroservice.service;

import java.util.List;

import com.ga.usermicroservice.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService{

	public List<User> listUsers();
	
	public List<String> signUp(User user);

	public List<String> logIn(User user);

	/*public Profile createProfile(Profile profile, String tokenHeader);

	public Profile getProfile(String token);

	public Profile updateProfile(Profile profile, String tokenHeader);

	public List<Comment> commentsByUser(String token);

	public List<Post> postsByUser(String tokenHeader);*/

}
