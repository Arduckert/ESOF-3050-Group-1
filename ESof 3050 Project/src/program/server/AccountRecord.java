//--------------------------------------------------------------
//Brief description of this file:
//		this class stores all important information on a record of the type account
//
//Related Documents:
//		Specifications Document
//		Design Document
//
//File created by Aric Duckert
//File approved by Connor McNally and Mathew Camire
//
//List of Important variable names and their brief description:
//	account: the account that was either created or removed
//
//----------------------------------------------------------------
package src.program.server;

import java.io.Serializable;

public class AccountRecord extends Record  implements Serializable{

	Account account;
	public AccountRecord(Teller teller, String x, Account a) {
		super(teller, x);
		this.account = a;
	}
	
	public Account getAccount() {
		return this.account;
	}
	@Override
	public String toString() {
		return String.format("%s Account: %s",super.toString(), account.getAccountNum());
	}

}
