package com.rabobank.lms.payload.request;

import javax.validation.constraints.NotBlank;

public class SearchLoanRequest {

	private String userFirstname;
	
	private String userLastname;
	
	private String loanNumber;
	
	
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
	/**getUserFirstname
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
}
