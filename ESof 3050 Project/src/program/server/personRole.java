package src.program.server;

import java.io.Serializable;

public class personRole  implements Serializable{
	Person person;
	//Constructor
	public personRole(Person p) {
		this.person = p;
	}
	
	//Getter
	public Person getPerson() {
		return this.person;
	}
}