//--------------------------------------------------------------
//Brief description of this file:
//		The Teller class which has all details for a bank employee stored.
//
//Related Documents:
//		Specifications Document
//		Design Document
//
//File created by Aric Duckert
//File approved by Connor McNally and Mathew Camire
//
//List of Important variable names and their brief description:
//		empNum: the employee number of the teller (used as a unique key)
//		password: this is the employees password used for loging in
//		records: a list of all records that the teller took part in (either creating or deleting an account or am account holder)
//
//----------------------------------------------------------------
package src.program.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Teller extends personRole implements Serializable{
	private int empNum;
	private String password;
	private List<Record> records = new ArrayList<Record>();
	
	//Constructor
	public Teller(int x, String y, Person person) {
		super(person);
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
