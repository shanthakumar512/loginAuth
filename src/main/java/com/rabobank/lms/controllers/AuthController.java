package com.rabobank.lms.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rabobank.lms.payload.request.LoginRequest;
import com.rabobank.lms.payload.response.JwtResponse;
import com.rabobank.lms.security.jwt.JwtUtils;
import com.rabobank.lms.security.services.UserDetailsImpl;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtUtils jwtUtils; 
	
	@ApiOperation(value = "Authenticate the login request and sign in", response = JwtResponse.class, tags = "authenticateUser")
	@ApiResponses(value = { 
	            @ApiResponse(code = 200, message = "Success|OK"),
	            @ApiResponse(code = 401, message = "not authorized!"), 
	            @ApiResponse(code = 403, message = "forbidden!!!"),
	            @ApiResponse(code = 404, message = "not found!!!") })
	
	@PostMapping("/signin")
	public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		
		logger.info("Entered authenticate user Method for user :{}", loginRequest.getUsername());

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		logger.debug("Access token generated{}", jwt);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority :: getAuthority)
				.collect(Collectors.toList());
		return ResponseEntity.ok(
				new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
	}
}
