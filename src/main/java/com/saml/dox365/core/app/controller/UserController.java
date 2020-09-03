package com.saml.dox365.core.app.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.saml.dox365.core.app.domain.Users;
import com.saml.dox365.core.app.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	public static final String SUCCESS = "success";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";

    
	@Autowired
	UserService userService;
	
	@PostMapping("/register")
    public void user(@RequestParam String userName, @RequestParam String password, @RequestBody HashMap<String, ArrayList<String>> roles) {
		//GrantedAuthority authority = new SimpleGrantedAuthority(roles);
		Users user = new Users();
		user.setUsername(userName);
		user.setPassword(new BCryptPasswordEncoder().encode(password));
		user.setRoles(roles);
        userService.insertUser(user);
    }
	
	
	
	//@Secured({ROLE_ADMIN})
	@GetMapping("/check")
    public ResponseEntity<UserDetails> user(@RequestParam String userName) {
        return ResponseEntity.ok(userService.loadUserByUsername(userName));
    }

}
