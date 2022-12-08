//--------------------------------------------------------------
//Brief description of this file:
//		this is a specific record corresponding to a customer creation or deletion
//
//Related Documents:
//		Specifications Document
//		Design Document
//
//File created by Aric Duckert
//File approved by Connor McNally and Mathew Camire
//
//List of Important variable names and their brief description:
//		accountHolder: the account holder that was created or deleted
//----------------------------------------------------------------
package src.program.server;

import java.io.Serializable;

public class CustomerRecord extends Record  implements Serializable{

	private AccountHolder accountHolder;
	public CustomerRecord(Teller teller, String a, AccountHolder ah) {
		super(teller, a);
		this.accountHolder = ah;
	}
	
	public AccountHolder getAccountHolder() {
		return this.accountHolder;
	}
	@Override
	public String toString() {
		return String.format("%s Account Holder: %s %s. Card Number: %d",super.toString(), accountHolder.getPerson().getFName(), accountHolder.getPerson().getLName(), accountHolder.getCardNum());
	}

}
