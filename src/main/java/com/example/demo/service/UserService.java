package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.ExceptionHandling.UsernameAlreadyExistsException;
import com.example.demo.Repository.UserRepo;
import com.example.demo.model.User;

@Service
public class UserService {

	@Autowired
	private UserRepo repo;
	
	  @Autowired
	    private BCryptPasswordEncoder passwordEncoder;

	    public User saveUser(User user) throws UsernameAlreadyExistsException {
	        // Check if username exists
	        if (repo.existsByUsername(user.getUsername())) {
	            throw new UsernameAlreadyExistsException("Username already exists!");
	        }
	        
	        user.setPassword(passwordEncoder.encode(user.getPassword()));
	        return repo.save(user);

	    }
	    
}
