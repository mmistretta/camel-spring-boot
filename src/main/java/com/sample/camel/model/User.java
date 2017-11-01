package com.sample.camel.model;

public class User {
	
	private String firstName;
	private String lastName;
	private String favoriteNumber;
	
	public User(String firstName, String lastName, String favoriteNumber) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.favoriteNumber = favoriteNumber;
	}
	public User() {
		// TODO Auto-generated constructor stub
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFavoriteNumber() {
		return favoriteNumber;
	}
	public void setFavoriteNumber(String favoriteNumber) {
		this.favoriteNumber = favoriteNumber;
	}
	@Override
	public String toString() {
		return "User [firstName=" + firstName + ", lastName=" + lastName + ", favoriteNumber=" + favoriteNumber + "]";
	}

}
