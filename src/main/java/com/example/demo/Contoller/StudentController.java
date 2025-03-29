package com.example.demo.Contoller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.student;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class StudentController {

	List<student> students = new ArrayList<>(Arrays.asList(
			 new student(1, "Sai", "Nellore"),
             new student(2, "John", "Chennai"),
             new student(3, "Ravi", "Hyderabad")
             
            ));
	
	
	@GetMapping("/csrf-token")
	public CsrfToken getCsrfToken(HttpServletRequest req) {
		return (CsrfToken) req.getAttribute("_csrf");
	}
	
	
	@GetMapping("students")
	public List<student> getStudents(){
		return students;
	}
	
	@PostMapping("students")
	public void addStudent(@RequestBody student s) {
		students.add(s);
	}
}
