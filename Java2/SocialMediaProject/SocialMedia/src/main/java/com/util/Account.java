package com.util;

import java.util.Date;

public class Account {
	private int ProfileID;
	private String FirstName;
	private String LastName;
	private String Password;
	private String MailAddress;
	private Date DateCreated;
	private Date DateDeleted;
	private int  ZipCode;
	
	public Account(int profileID, String firstName, String lastName, String password, String mailAddress, Date dateCreated,
			Date dateDeleted, int zipCode) {
		super();
		ProfileID = profileID;
		FirstName = firstName;
		LastName = lastName;
		Password = password;
		MailAddress = mailAddress;
		DateCreated = dateCreated;
		DateDeleted = dateDeleted;
		ZipCode = zipCode;
	}

	public Account(String firstName, String lastName, String password, String mailAddress, Date dateCreated, Date dateDeleted,
			int zipCode) {
		super();
		FirstName = firstName;
		LastName = lastName;
		Password = password;
		MailAddress = mailAddress;
		DateCreated = dateCreated;
		DateDeleted = dateDeleted;
		ZipCode = zipCode;
	}

	public int getProfileID() {
		return ProfileID;
	}

	public void setProfileID(int profileID) {
		ProfileID = profileID;
	}

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getMailAddress() {
		return MailAddress;
	}

	public void setMailAddress(String mailAddress) {
		MailAddress = mailAddress;
	}

	public Date getDateCreated() {
		return DateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		DateCreated = dateCreated;
	}

	public Date getDateDeleted() {
		return DateDeleted;
	}

	public void setDateDeleted(Date dateDeleted) {
		DateDeleted = dateDeleted;
	}

	public int getZipCode() {
		return ZipCode;
	}

	public void setZipCode(int zipCode) {
		ZipCode = zipCode;
	}

	@Override
	public String toString() {
		return "Account [ProfileID=" + ProfileID + ", FirstName=" + FirstName + ", LastName=" + LastName + ", Password="
				+ Password + ", MailAddress=" + MailAddress + ", DateCreated=" + DateCreated + ", DateDeleted="
				+ DateDeleted + ", ZipCode=" + ZipCode + "]";
	}


	
	
	
	
	
	
	
}
