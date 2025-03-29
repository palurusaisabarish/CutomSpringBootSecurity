package com.example.demo.Contoller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class HomeContoller {
	
	@GetMapping("/helllooo")
	public String greet(HttpServletRequest req) {
		System.out.println("Method called");
		return "Hello Welcome To NextEduaction Learning PlatForm" + req.getSession().getId();
	}
	
	@GetMapping("/about")
	public String about(HttpServletRequest req) {
		return "This is B2B Platform" + req.getSession().getId();
	}

}
