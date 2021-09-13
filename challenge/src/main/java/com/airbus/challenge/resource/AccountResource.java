package com.airbus.challenge.resource;

import java.net.URI;
import java.net.UnknownServiceException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.airbus.challenge.dao.UserDAO;
import com.airbus.challenge.exception.UserServiceErrorCodesEnum;
import com.airbus.challenge.exception.UserServiceException;
import com.airbus.challenge.model.LoginRequest;
import com.airbus.challenge.model.SignUpRequest;
import com.airbus.challenge.model.User;
import com.airbus.challenge.response.ApiResponse;
import com.airbus.challenge.response.JwtAuthenticationResponse;
import com.airbus.challenge.security.JwtTokenProvider;
import com.airbus.challenge.service.UserService;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AccountResource {

	private static final Logger logger = LoggerFactory.getLogger(AccountResource.class);

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserService userService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JwtTokenProvider jwtTokenProvider;
	
	@Value("${app.jwtExpirationInMs}")
	private int jwtExpirationInMs;

	@ApiOperation(value="Sign in a user", notes = "Login to the application with appropriate params and retrieve token as a reponse")
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		logger.debug("START :: AccountResource :: authenticateUser");
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		User user = userService.getUserByUsernameorEmail(loginRequest.getUsernameOrEmail());

		String jwt = jwtTokenProvider.generateToken(authentication);
		logger.debug("END :: AccountResource :: authenticateUser");
		return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, user.getUsername(), user.getId(), jwtExpirationInMs));
	}

	@ApiOperation(value="Register a user", notes = "Register to the application with appropriate params and retrieve token as a reponse")
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {

		logger.debug("START :: AccountResource :: registerUser");
		// Creating user's account
		User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(), signUpRequest.getPassword());

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		User result = null;

		try {
			result = userService.saveUser(user);
		} catch (UserServiceException e) {
			return new ResponseEntity(new ApiResponse(false, e.getMessage(), "email"), HttpStatus.BAD_REQUEST);
		}
//		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/{username}")
//				.buildAndExpand(result.getUsername()).toUri();
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signUpRequest.getUsername(), signUpRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtTokenProvider.generateToken(authentication);
		
		logger.debug("END :: AccountResource :: registerUser");
//		return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully", null));
		return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, signUpRequest.getUsername(), user.getId(),jwtExpirationInMs));
	}
}
