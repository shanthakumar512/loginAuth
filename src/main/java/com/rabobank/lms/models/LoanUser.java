package com.rabobank.lms.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(	name = "loan_users", 
		uniqueConstraints = { 
			@UniqueConstraint(columnNames = "loanNumber")})
public class LoanUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Size(max = 20)
	private String userFirstname;
	@NotBlank
	@Size(max = 20)
	private String userLastname;
	@NotBlank
	@Size(max = 20)
	private String loanNumber;
	@Size(max = 20)
	@NotBlank private String addressLine1;
	@Size(max = 20)
	private String addressLine2;
	
	@Size(max = 20)
	private String addressLine3;
	@Size(max = 20)
	@NotBlank 
	
	private String city;
	@NotBlank
	@Size(max = 20)
	private String state;
	@Size(max = 20)
	@NotBlank 
	private String country;
	/**
	 * @return the userFirstname
	 */
	public String getUserFirstname() {
		return userFirstname;
	}
	/**
	 * @param userFirstname the userFirstname to set
	 */
	public void setUserFirstname(String userFirstname) {
		this.userFirstname = userFirstname;
	}
	/**
	 * @return the userLastname
	 */
	public String getUserLastname() {
		return userLastname;
	}
	/**
	 * @param userLastname the userLastname to set
	 */
	public void setUserLastname(String userLastname) {
		this.userLastname = userLastname;
	}
	/**
	 * @return the loanNumber
	 */
	public String getLoanNumber() {
		return loanNumber;
	}
	/**
	 * @param loanNumber the loanNumber to set
	 */
	public void setLoanNumber(String loanNumber) {
		this.loanNumber = loanNumber;
	}
	/**
	 * @return the addressLine1
	 */
	public String getAddressLine1() {
		return addressLine1;
	}
	/**
	 * @param addressLine1 the addressLine1 to set
	 */
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	/**
	 * @return the addressLine2
	 */
	public String getAddressLine2() {
		return addressLine2;
	}
	/**
	 * @param addressLine2 the addressLine2 to set
	 */
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	/**
	 * @return the addressLine3
	 */
	public String getAddressLine3() {
		return addressLine3;
	}
	/**
	 * @param addressLine3 the addressLine3 to set
	 */
	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	
	LoanUser(){
	}
	/**
	 * @param userFirstname
	 * @param userLastname
	 * @param loanNumber
	 * @param addressLine1
	 * @param addressLine2
	 * @param addressLine3
	 * @param city
	 * @param state
	 * @param country
	 */
	
	
	public LoanUser(@NotBlank @Size(max = 20) String userFirstname, @NotBlank @Size(max = 20) String userLastname,
			@NotBlank @Size(max = 20) String loanNumber, @Size(max = 20) @NotBlank String addressLine1,
			@Size(max = 20) String addressLine2, @Size(max = 20) String addressLine3,
			@Size(max = 20) @NotBlank String city, @NotBlank @Size(max = 20) String state,
			@Size(max = 20) @NotBlank String country) {
		this.userFirstname = userFirstname;
		this.userLastname = userLastname;
		this.loanNumber = loanNumber;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.addressLine3 = addressLine3;
		this.city = city;
		this.state = state;
		this.country = country;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}
	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}


}
