package src.program.server;

public class personRole {
	int role;
	Person person;
	//Constructor
	public personRole(int x, Person p) {
		this.role = x;
		this.person = p;
	}
	
	//Getter
	public int getRole() {
		return this.role;
	}
	public Person getPerson() {
		return this.person;
	}
}