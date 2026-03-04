package com.example.demo.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.Jwt.JwtService;
import com.example.demo.Repo.UserRepo;
import com.example.demo.entity.User;

@Service
public class UserService {
	
	
	@Autowired
	public UserRepo repo ;
	
	@Autowired
	public JwtService jwtService;
	

	public User SaveNewUser(User user) {
		return repo.save(user);
	}

	public ResponseEntity<String> authByEmailAndPass(User user) {
		
		User user2= repo.findByEmailAndPassword(
				user.getEmail(), 
				user.getPassword());
		if(user2==null) {
	      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid details...");
			
			}
	String token=	jwtService.generateToken(user2.getEmail());
	
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(token);
		}
	
	public List<User> getAllUsers() {
		return repo.findAll();
	}
	
	public void deleteUserByID(Long id) {
		 repo.deleteById(id);
	}
	
	public User updateUser(Long id,User updatedUser) {
        User existingUser=  repo.findById(id)
      		    .orElseThrow(()-> new RuntimeException("User Not Found"));
        
       
        existingUser.setEmail(updatedUser.getEmail());
        return repo.save(existingUser);
  }
  
	
}
