package com.example.demo.Contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.ott.GenerateOneTimeTokenFilter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.service.JwtService;
import com.example.demo.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtService jwtService;
	
	
	@Autowired
	private AuthenticationManager  authenticationManager;
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
	
//	@PostMapping("register")
//	public User UserRegister(@RequestBody User user) {
//		
//		user.setPassword(encoder.encode(user.getPassword()));
//		System.out.println(user.getPassword());
//		
//		return userService.saveUser(user);
//	}
	
	@PostMapping("login")
	public String UserLogin(@RequestBody User user) {
		
		Authentication authentication = authenticationManager
		.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		
		if(authentication.isAuthenticated()){
			//return "success..";
			
			return jwtService.generateToken(user.getUsername());
		}else {
			return "Login Failed..";
		}
	}


}
