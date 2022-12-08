//--------------------------------------------------------------
//Brief description of this file:
//		This is a class allowing a person to have up to two roles (a teller or an account holder)
//
//Related Documents:
//		Specifications Document
//		Design Document
//
//File created by Aric Duckert
//File approved by Connor McNally and Mathew Camire
//
//List of Important variable names and their brief description:
//		person: This is the person associated to the role
//
//----------------------------------------------------------------
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