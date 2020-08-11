 package com.rabobank.lms;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import com.rabobank.lms.models.User;
import com.rabobank.lms.payload.request.LoginRequest;
import com.rabobank.lms.payload.response.JwtResponse;
import com.rabobank.lms.repository.UserRepository;

import static org.junit.Assert.assertEquals;

@SpringBootTest(classes= {H2JpaConfig.class,SpringBootRaboBankLMSApplication.class},webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
 class SpringBootRaboBankLMSApplicationTests {

	 	@Autowired
	    private TestRestTemplate template;
	 
	 	@LocalServerPort
		private int port;

		private String getRootUrl() {
			return "http://localhost:" + port;
		}
		
		@Autowired
		UserRepository userRepository;
		
		private static String token="";
	    @Test
	     void givenAuthRequest_shouldSucceedWith200() throws Exception {
	    	User user= new User();
	    	user.setId(1l);
	    	user.setUsername("user2");
	    	user.setPassword("$2a$10$M0MZfhaBKR.G7HNeAhOJvOeyjIIsCQD3hIIywwyyIhSbMy3nzGVri");
	    	user.setEmail("user2@gmail.com");
	    	user.setRoles(null);
	    	
	    	userRepository.save(user);
	    	
	    	LoginRequest loginRequest = new LoginRequest();
	    	loginRequest.setUsername("user2");
	    	loginRequest.setPassword("abc@123");
	        ResponseEntity<JwtResponse> result = template.
	        		postForEntity(getRootUrl()+"/api/auth/signin", loginRequest, JwtResponse.class);
	        assertEquals(HttpStatus.OK, result.getStatusCode());
	        token=result.getBody().getAccessToken();
	    }
	    
	    @Test
	     void givenSignin_shouldSucceedWith200() throws Exception {
	    	User user= new User();
	    	user.setId(1l);
	    	user.setUsername("user1");
	    	user.setPassword("$2a$10$M0MZfhaBKR.G7HNeAhOJvOeyjIIsCQD3hIIywwyyIhSbMy3nzGVri");
	    	user.setEmail("user1@gmail.com");
	    	user.setRoles(null);
	    	userRepository.save(user);
	    	
	    	LoginRequest loginRequest = new LoginRequest();
	    	loginRequest.setUsername("user1");
	    	loginRequest.setPassword("abc@123");
	        ResponseEntity<JwtResponse> result = template.
	        		postForEntity(getRootUrl()+"/api/auth/signin", loginRequest, JwtResponse.class);
	        assertEquals(HttpStatus.OK, result.getStatusCode());
	    }
	    
	    @Test
	     void givenSignin_shouldThrowUnauthorizedError() throws Exception {
	    	LoginRequest loginRequest = new LoginRequest();
	    	loginRequest.setUsername("user8");
	    	loginRequest.setPassword("abc@123");
	        ResponseEntity<JwtResponse> result = template.
	        		postForEntity(getRootUrl()+"/api/auth/signin", loginRequest, JwtResponse.class);
	        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
	    }
	    
	    @Test
	     void givenSignin_shouldThrow() throws UsernameNotFoundException {
	    	LoginRequest loginRequest = new LoginRequest();
	    	loginRequest.setUsername("user1");
	    	loginRequest.setPassword("abc@123");
	    	ResponseEntity<JwtResponse> response = template.
	        		postForEntity(getRootUrl()+"/api/auth/signin", loginRequest, JwtResponse.class);
	    	 token=response.getBody().getAccessToken();
	    	 
	        HttpHeaders headers = new HttpHeaders();
	        headers.set("Authorization", "Bearer "+token);
	        headers.setContentType(MediaType.APPLICATION_JSON);

	        HttpEntity<String> entity = new HttpEntity<String>(loginRequest.toString() , headers);

	        
	        ResponseEntity<JwtResponse> result = template.postForEntity(getRootUrl()+"/api/auth/signin", entity, JwtResponse.class);
	        assertEquals(HttpStatus.BAD_REQUEST,result.getStatusCode());
	    }
	    
}
