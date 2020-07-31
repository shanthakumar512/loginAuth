 package com.rabobank.lms;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import com.rabobank.lms.payload.request.LoginRequest;
import com.rabobank.lms.payload.response.JwtResponse;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SpringBootRaboBankLMSApplicationTests {

	@Test
	public void contextLoads() {
	}
	
	@Autowired
    private WebApplicationContext context;

	
	 	@Autowired
	    private TestRestTemplate template;
	 
	    // ... other methods
	 	
	    @Test
	    public void givenAuthRequestOnPrivateService_shouldSucceedWith200() throws Exception {
	    	LoginRequest loginRequest = new LoginRequest();
	    	loginRequest.setUsername("user1");
	    	loginRequest.setPassword("abc@123");
	        ResponseEntity<?> result = template.
	        		postForEntity("http://localhost:8765/api/auth/signin", loginRequest, JwtResponse.class);
//	          .getForEntity("http://localhost:8765/api/auth/", String.class);
	        assertEquals(HttpStatus.OK, result.getStatusCode());
	    }
	    
//	    @Test
	    public  void testPreAuthorisedControl() {
	    	Map<String, String> uriVariables = new HashMap<>();
			uriVariables.put("loanNumber", "ABC1234SH");
			uriVariables.put("loanUserEmail", "abc@gmail.c");
	    	ResponseEntity<?> result = template.
	        		getForEntity( "http://localhost:8765/api/loanInfo/getLoanInfoByEmail/{loanUserEmail}", null,uriVariables);
	    	
	    	assertEquals(HttpStatus.OK, result.getStatusCode());
	    }
	    
	    
}
