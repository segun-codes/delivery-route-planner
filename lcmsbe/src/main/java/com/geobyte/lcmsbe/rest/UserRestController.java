package com.geobyte.lcmsbe.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCrypt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.geobyte.lcmsbe.entity.User;
import com.geobyte.lcmsbe.service.UserService;
import com.geobyte.lcmsbe.util.EntityNotFoundException;
import com.geobyte.lcmsbe.util.EntityNotUpdatedException;
import com.geobyte.lcmsbe.util.LoginCredential;
import com.geobyte.lcmsbe.util.StatusInWord;
import com.geobyte.lcmsbe.util.StatusMessage;


@RestController
@RequestMapping("/api/v1")
public class UserRestController {
	private final static String updateErrorMessage = "Specified user not updated, perhaps it doesn't exist";
	private final static String deleteErrorMessage = "Specified user not deleted, perhaps it never existed";
	private final static String retrievalErrorMessage = "Specified user not found";
	
	private UserService userService;
	
	
	@Autowired // auto-inject service object
	public UserRestController(UserService userService) {
		this.userService = userService;
	}
	
	private User getSingleUser(int userId) {
		User user = userService.findById(userId);
		
		if (user == null) {
			throw new EntityNotFoundException(retrievalErrorMessage);
		}
		
		return user;
	}
	
	//Retrieve single user from db
	@GetMapping("/users/{userId}")
	public User getUser(@PathVariable int userId) {
		return getSingleUser(userId);
	}
	
	// Retrieve all users from db
	@GetMapping("/users")
	public List<User> getAllUser() {
		List<User> users = userService.findAll();
		
		if (users == null) {
			return new ArrayList<User>();
		}
		
		return users;
	}
	
	// Write user to db
	@PostMapping("/users/registration")
	public User createUser(@RequestBody User user) {
		String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
		user.setPassword(hashedPassword);
		
		user.setUserId(0); // ensures JPA performs insert and not update
		User newUser = userService.save(user);
		
		return newUser;
	}
	
	@PostMapping("users/login")
	public StatusMessage logUserIn(@RequestBody LoginCredential loginCredential) {
		StatusMessage statusMessage = new StatusMessage();
		
		String email = loginCredential.getEmail();
		String plaintxtPassword = loginCredential.getPassword();
		String hashedPassword = userService.getHashedPasswordByEmail(email);
		
		if(BCrypt.checkpw(plaintxtPassword, hashedPassword)) {
			statusMessage.setMessage("login successful");
			statusMessage.setStatusInWord(StatusInWord.SUCCESS);
		} else {
			statusMessage.setMessage("login failed");
			statusMessage.setStatusInWord(StatusInWord.FAILED);
		}
		
		return statusMessage;
	}
	
	@PatchMapping("/users")
	public StatusMessage updateUser(@RequestBody User user) {
		long userId = user.getUserId();
		
		User verifieduser = userService.findById(userId);
		
		if (verifieduser == null) {
			throw new EntityNotUpdatedException(updateErrorMessage);
		}
		
		userService.update(user);
		String msge = "User with id " + userId + " updated";
		
		return new StatusMessage(msge, StatusInWord.SUCCESS);
	}
	
	// Deletes user from db
	@DeleteMapping("/users/{userId}")
	public StatusMessage deleteUser(@PathVariable int userId) {
		User user = userService.findById(userId);
		
		if (user == null) {
			throw new EntityNotFoundException(deleteErrorMessage);
		}
		
		userService.deleteById(userId);
		String msge = "User with id " + userId + " deleted";
		
		return new StatusMessage(msge, StatusInWord.SUCCESS);
	}
}
