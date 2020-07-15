package com.rabobank.lms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.rabobank.lms"})
@EnableAutoConfiguration
@EnableJpaRepositories
public class SpringBootRaboBankLMSApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRaboBankLMSApplication.class, args);
	}

}
