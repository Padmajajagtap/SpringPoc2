package com.main.neosoft.controller;

import java.util.ArrayList;
import java.util.List;

import com.main.neosoft.Service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.main.neosoft.entity.UserContactDetails;
import com.main.neosoft.entity.UserDetails;
import com.main.neosoft.repository.UserContactDetailsRepository;
import com.main.neosoft.repository.UserCredentialsRepository;
import com.main.neosoft.repository.UserDetailsRepository;

@RestController
public class HomeController {
	@Autowired
	UserServiceImpl userService;
	
	@Autowired
	UserDetailsRepository userDetailsRepo;
	
	@Autowired
	UserContactDetailsRepository userContactRepo;
	
	@Autowired
	UserCredentialsRepository userCredRepo;

	@PostMapping("/singup")
	public String registerForm(@RequestBody UserDetails userDetails)
	{
		userService.saveData(userDetails);
		return "Data save Successfully";
	}
		

	
	 @GetMapping("/search")
	 public ResponseEntity<List<UserDetails>> searchUsers(@RequestParam("query") String query){
		 List<UserDetails> userDetails = new ArrayList<UserDetails>();
		 userDetailsRepo.searchUserDetails(query).forEach(userDetails::add);
		 
		 if(userDetails.isEmpty()) {
			 return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		 }
		 return new ResponseEntity<>(userDetails, HttpStatus.OK);
	 }

	@GetMapping("/search/allUsers")
	public ResponseEntity<List<UserDetails>> getAllUsers () {
		List<UserDetails> userDetails = new ArrayList<UserDetails>();
		
		userDetailsRepo.findAll().forEach(userDetails::add);
		
		if(userDetails.isEmpty()) {
			 return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		 return new ResponseEntity<>(userDetails, HttpStatus.OK);
	}

	
	@PutMapping("/user/{userId}")
	public ResponseEntity<UserDetails> updateUser (@PathVariable("userId") Long id, @RequestBody UserDetails ud) {
		UserDetails userDetails = userDetailsRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User Not Found with id = " +id));
		
		userDetails.setFirstName(ud.getFirstName());
		userDetails.setLastName(ud.getLastName());
		userDetails.setEmail(ud.getEmail());
		userDetails.setDob(ud.getDob());
		userDetails.setUserContactDetails(ud.getUserContactDetails());
		userDetails.setUserCredentials(ud.getUserCredentials());
		
		return new ResponseEntity<>(userDetailsRepo.save(userDetails), HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<List<UserDetails>> deleteUserById (@PathVariable("userId") Long id) {
		
		if(!userDetailsRepo.existsById(id)) {
			throw new ResourceNotFoundException("User Not Found with id = " +id);
		}
		
		userDetailsRepo.deleteById(id);
		return new ResponseEntity<>(userDetailsRepo.findAll(), HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/All")
	public ResponseEntity<String> deleteAllUsers() {
		userDetailsRepo.deleteAll();
		return new ResponseEntity<>("All Users Deleted Successfully", HttpStatus.NO_CONTENT);
	}

	@GetMapping("/search/byCity/{cityName}")
	public ResponseEntity<List<UserContactDetails>> searchUserByCity (@PathVariable("cityName") String cityName) {
		List<UserContactDetails> userContactDetails = new ArrayList<UserContactDetails>();
		userContactRepo.findByCityName(cityName).forEach(userContactDetails::add);
		
		if(userContactDetails.isEmpty()) {
			 return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		 return new ResponseEntity<>(userContactDetails, HttpStatus.OK);
	}

}
