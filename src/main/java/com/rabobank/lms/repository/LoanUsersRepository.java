package com.rabobank.lms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rabobank.lms.models.LoanUser;

@Repository
public interface LoanUsersRepository extends JpaRepository<LoanUser, Long>, LoanUsersRepositoryCustom {
	
	Optional<LoanUser> findByUserFirstname(String userFirstName);
	Optional<LoanUser> findByUserLastname(String userLastName);
	Optional<LoanUser> findByLoanNumber(String userLoanNumber);
	Boolean existsByLoanNumber(String username);
}
