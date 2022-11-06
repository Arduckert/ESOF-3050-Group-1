package program.server;

public class Teller {
	private int empNum;
	private String password;
	
	//Constructor
	public Teller(int x, String y) {
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
