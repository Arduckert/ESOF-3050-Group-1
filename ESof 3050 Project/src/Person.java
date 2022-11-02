import java.util.*;

public class Person {
	String fName;
	String lName;
	int SIN;
	String DOB;
	List<Address> addresses;
	List<personRole> roles;
	
	//Constructor
	public Person(String fName, String lName, int sin, String dob, Address address, personRole role) {
		this.fName = fName;
		this.lName = lName;
		this.SIN = sin;
		this.DOB = dob;
		this.addresses.add(address);
		this.roles.add(role);
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
}
