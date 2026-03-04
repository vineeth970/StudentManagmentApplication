		
		package com.example.demo.controller;
		
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Jwt.JwtService;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;

@RestController
@CrossOrigin("*")
public class UserController {
	
	@Autowired
	public JwtService jwtService;

	
	@Autowired
	public UserService service;

	@PostMapping("/register")
	public User saveNewUser(@RequestBody User user) {
		return  service.SaveNewUser(user);
		 
	}
  @PostMapping("/login")

	public ResponseEntity<String> authUser(@RequestBody User user) {
		 return service.authByEmailAndPass(user);
	}
	
	@GetMapping("/allusers")
	public List<User> allUsers(){
		return service.getAllUsers();
	}
	@DeleteMapping("/delete/{id}")
	public void deleteUserBy(@PathVariable Long id) {
		service.deleteUserByID(id);
	}
	
	@GetMapping("/verify")
	public ResponseEntity<?> verifyToken( @RequestHeader("Authorization") String token){
		try {
			jwtService.validateToken(token.replace("Bearer", ""));
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("Token validd..");
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Token Expired..");
		}
	
		
	}
	
	@PutMapping("/users/{id}")
	public ResponseEntity<User> updateUser(
	        @PathVariable Long id,
	        @RequestBody User user) {

	    return ResponseEntity.ok(service.updateUser(id, user));
	}

	 
}
