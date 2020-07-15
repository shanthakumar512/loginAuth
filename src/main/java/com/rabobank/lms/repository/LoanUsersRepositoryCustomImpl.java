package com.rabobank.lms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rabobank.lms.models.LoanUser;

@Repository
public interface LoanUsersRepositoryCustomImpl extends LoanUsersRepositoryCustom {
	
	@Query("select l from LoanUser l where"
			+ " l.userFirstname LIKE  LOWER(CONCAT('%',:userFirstName, '%')) OR "
			+ "l.userLastname LIKE  LOWER(CONCAT('%',:userLastName, '%')) OR "
			+ "l.loanNumber LIKE  LOWER(CONCAT('%',:userLoanNumber, '%'))")
	Optional<LoanUser> getByUserFirstnameAndUserLastnameAndLoanNumber( String userFirstName,  String userLastName , String userLoanNumber);
	
}
