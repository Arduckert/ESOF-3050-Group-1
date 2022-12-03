package src.program.server;

public class Teller extends personRole{
	private int empNum;
	private String password;
	
	//Constructor
	public Teller(int x, String y, Person person) {
		super(1, person);
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
