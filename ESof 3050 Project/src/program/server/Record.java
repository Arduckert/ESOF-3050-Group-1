//--------------------------------------------------------------
//Brief description of this file:
//		The record class is an abstract class that stores information about either a customer or account creation/deletion
//
//Related Documents:
//		Specifications Document
//		Design Document
//
//File created by Aric Duckert
//File approved by Connor McNally and Mathew Camire
//
//List of Important variable names and their brief description:
//		recordDate: This is the date the record was created
//		teller: the teller that performed the action
//		action: either "created" or "removed" depending on teller action
//----------------------------------------------------------------
package src.program.server;
import java.io.Serializable;
import java.time.LocalDate;

public abstract class Record implements Serializable{
	private LocalDate recordDate;
	private Teller teller;
	private String action;
	
	public Record(Teller teller, String a) {
		this.recordDate = LocalDate.now();
		this.teller = teller;
		this.action = a;
	}
	public Teller getTeller() {
		return this.teller;
	}
	public String getTellerString() {
		String fName = this.getTeller().getPerson().getFName();
		String lName = this.getTeller().getPerson().getLName();
		return String.format("%s %s",fName,lName);
	}
	
	public String getRecordDate() {
		return String.format("%s",recordDate);
	}
	public String getAction() {
		return this.action;
	}
	
	@Override
	public String toString() {
		switch(action) {
		case "created":
			return String.format("%s: Created",recordDate);
		case "removed":
			return String.format("%s: Removed",recordDate);
		default:
			return String.format("");
		}
	}

}