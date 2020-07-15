package com.rabobank.lms.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rabobank.lms.models.LoanUser;
import com.rabobank.lms.models.Role;
import com.rabobank.lms.models.RoleType;
import com.rabobank.lms.models.User;
import com.rabobank.lms.payload.request.AddUserRequest;
import com.rabobank.lms.payload.request.LoginRequest;
import com.rabobank.lms.payload.request.SearchLoanRequest;
import com.rabobank.lms.payload.request.SignupRequest;
import com.rabobank.lms.payload.request.UpdateUserRequest;
import com.rabobank.lms.payload.response.JwtResponse;
import com.rabobank.lms.payload.response.MessageResponse;
import com.rabobank.lms.repository.LoanUsersRepository;
import com.rabobank.lms.repository.RoleRepository;
import com.rabobank.lms.repository.UserRepository;
import com.rabobank.lms.security.jwt.JwtUtils;
import com.rabobank.lms.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	LoanUsersRepository loanUsersRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, 
												 userDetails.getId(), 
												 userDetails.getUsername(), 
												 userDetails.getEmail(), 
												 roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(signUpRequest.getUsername(), 
							 signUpRequest.getEmail(),
							 encoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(RoleType.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(RoleType.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "mod":
					Role modRole = roleRepository.findByName(RoleType.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);

					break;
				default:
					Role userRole = roleRepository.findByName(RoleType.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
	
	@PostMapping("/addUser")
	public ResponseEntity<?> addLoanUser(@Valid @RequestBody AddUserRequest addUserRequest) {
		

		if (loanUsersRepository.existsByLoanNumber(addUserRequest.getLoanNumber())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: User  is already added this Loan Number!"));
		}
		
		LoanUser loanUser =new LoanUser(addUserRequest.getUserFirstname(), addUserRequest.getUserLastname(), addUserRequest.getLoanNumber(),
				addUserRequest.getAddressLine1(), addUserRequest.getAddressLine2(), addUserRequest.getAddressLine3(), 
				addUserRequest.getCity(), addUserRequest.getState(), addUserRequest.getCountry());

		

		loanUsersRepository.save(loanUser);

		return ResponseEntity.ok(new MessageResponse("Loan User registered successfully!"));
	}
	
	@PutMapping("/updateUser")
	public ResponseEntity<?> updateLoanUser(@Valid @RequestBody UpdateUserRequest updateUserRequest) {
		Optional<LoanUser> loanUserOptional = null;

			
			loanUserOptional= loanUsersRepository.findByLoanNumber(updateUserRequest.getLoanNumber());
			if(!loanUserOptional.isPresent()) {
				ResponseEntity.notFound().build();
			} else {
			LoanUser loanUser =loanUserOptional.get();
			loanUser.setUserFirstname(updateUserRequest.getUserFirstname());
			loanUser.setUserLastname(updateUserRequest.getUserLastname());
			loanUser.setLoanNumber(updateUserRequest.getLoanNumber());
			loanUser.setAddressLine1(updateUserRequest.getAddressLine1());
			loanUser.setAddressLine2(updateUserRequest.getAddressLine2());
			loanUser.setAddressLine3(updateUserRequest.getAddressLine3());
			loanUser.setCity(updateUserRequest.getCity());
			loanUser.setState(updateUserRequest.getState());
			loanUser.setCountry(updateUserRequest.getCountry());
			loanUsersRepository.save(loanUser);
			}

			
		

		return ResponseEntity.ok(new MessageResponse("Loan User Updated successfully!"));
	}

	
	@PostMapping("/searchUser")
	public ResponseEntity<?> searchLoanUser(@Valid @RequestBody SearchLoanRequest searchLoanRequest) {
		Optional<LoanUser> user = null;

		if ( null != searchLoanRequest.getUserLastname()&&  null != searchLoanRequest.getUserLastname() &&  null != searchLoanRequest.getUserLastname()
				&& searchLoanRequest.getUserLastname().isEmpty() && !searchLoanRequest.getUserFirstname().isEmpty() && !searchLoanRequest.getLoanNumber().isEmpty()) {
			
			
		 user=   loanUsersRepository.getByUserFirstnameAndUserLastnameAndLoanNumber(searchLoanRequest.getUserLastname(), searchLoanRequest.getLoanNumber(), searchLoanRequest.getLoanNumber());

		
		} else if( null != searchLoanRequest.getLoanNumber()) {
			user= loanUsersRepository.findByLoanNumber(searchLoanRequest.getLoanNumber());
		}
		else if( null != searchLoanRequest.getUserLastname()) {
			user=	loanUsersRepository.findByUserFirstname(searchLoanRequest.getUserLastname());
		} else if( null != searchLoanRequest.getUserFirstname()) {
			user= loanUsersRepository.findByUserFirstname(searchLoanRequest.getUserFirstname());
		}
		if(user.isPresent())	{ 	
		return new	ResponseEntity<LoanUser>(user.get(),HttpStatus.OK);
		} else {
		return new ResponseEntity<String>("No Search Result Found", HttpStatus.BAD_REQUEST);
				
		}
	}

	
	@GetMapping("/all")
	public ResponseEntity<String> allAccess() {
	
		
		return ResponseEntity.ok("");
	}
	
}
