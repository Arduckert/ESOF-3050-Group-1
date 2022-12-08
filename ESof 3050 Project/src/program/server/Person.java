//--------------------------------------------------------------
//Brief description of this file:
//		The person class stores all important data on a person in the banking system.
//
//Related Documents:
//		Specifications Document
//		Design Document
//
//File created by Aric Duckert
//File approved by Connor McNally and Mathew Camire
//
//List of Important variable names and their brief description:
//		fName & lName: the first and last name of the person
//		SIN: the social insurance number of a person (used as a unique key)
//		DOB: the date of birth of the person
//		addresses: a list of all addresses belonging to that person 
//		personRole: the roles of the person (a person can be a teller, account holder, neither, or both)
//----------------------------------------------------------------
package src.program.server;
import java.io.Serializable;
import java.util.*;

import src.program.server.personRole;

public class Person implements Serializable{
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
	public void addPersonRole(personRole x) {
		if(roles.size()<2) {
		this.roles.add(x);
		}
	}
	public void removeRole(personRole x) {
		this.roles.remove(x);
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
	public List<personRole> getRoles() {
		return this.roles;
	}
	public List<Address> getAdresses(){
		return this.addresses;
	}
	
	//search for an address via postalCode
	public Address searchAddress(String pCode) {
		for(int i=0; i < addresses.size(); i++) {
			if(addresses.get(i).getPostalCode().equals(pCode)) {
				return addresses.get(i);
			}
		}
		return null;
	}
	
	//To string 
	public String toString() {
		return String.format("First Name: %s\nLast Name: %s\nSIN: %d\nDate of Birth: %s",fName,lName,SIN,DOB);
	}
}
