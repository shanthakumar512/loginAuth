package com.rabobank.lms.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.rabobank.lms.models.LoanUser;

@Repository
public interface LoanUsersRepositoryCustom {

	Optional<LoanUser> getByUserFirstnameAndUserLastnameAndLoanNumber( String userFirstName, String userLastName , String userLoanNumber);
	
}
