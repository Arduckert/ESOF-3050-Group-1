package src.program.server;
import java.util.*;

import src.program.server.personRole;

public class Person {
	private String fName;
	private String lName;
	private int SIN;
	private String DOB;
	private List<Address> addresses = new ArrayList<Address>();
	private List<personRole> roles = new ArrayList<personRole>();
	
	//Constructor
	public Person(String fName, String lName, int sin, String dob) {
		this.fName = fName;
		this.lName = lName;
		this.SIN = sin;
		this.DOB = dob;
		//this.roles.add(role);
		//Either add this person to list of people in bankController or do it in GUI step
	}
	
	//Setters
	public void setFName(String x) {
		this.fName = x;
	}
	public void setDOB(String x) {
		this.DOB = x;
	}
	public void setLName(String x) {
		this.lName = x;
	}
	public void setSIN(int x) {
		this.SIN = x;
	}
	public void addAddress(Address x) {
		this.addresses.add(x);
	}
	public void setPersonRole(personRole x) {
		this.roles.add(x);
		//add if size less than 2
	}
	
	//Getters
	public String getFName() {
		return this.fName;
	}
	public String getLName() {
		return this.lName;
	}
	public String getDOB() {
		return this.DOB;
	}
	public int getSIN() {
		return this.SIN;
	}
	
	//To string 
	public String toString() {
		return String.format("First Name: %s\nLast Name: %s\nSIN: %d\nDate of Birth: %s",fName,lName,SIN,DOB);
	}
}
