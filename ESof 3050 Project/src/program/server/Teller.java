package program.server;

public class Teller extends Person {
	private int empNum;
	private String password;
	
	//Constructor
	public Teller(int x, String y,String fName, String lName, int sin, String dob, Address address, personRole role) {
		super(fName,lName, sin, dob, address, role);
		this.empNum = x;
		this.password = y;
		
	}
	//Getters
	public int getEmpNum() {
		return this.empNum;
	}
	public String getPassword() {
		return this.password;
	}
}
