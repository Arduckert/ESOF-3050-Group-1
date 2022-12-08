//--------------------------------------------------------------
//Brief description of this file:
//		This stores information corresponding to an account of type chequing
//
//Related Documents:
//		Specifications Document
//		Design Document
//
//File created by Aric Duckert
//File approved by Connor McNally and Mathew Camire
//
//List of Important variable names and their brief description:
//
//----------------------------------------------------------------
package src.program.server;

import java.io.Serializable;

public class ChequingAccount extends Account  implements Serializable {

	//Constructor
	public ChequingAccount(int accountNum, AccountHolder holder) {
		super(accountNum, holder);
	}
	
	public void deposit(double x) {
		double current = this.getBalance();
		this.setBalance(x+current);
	}
	
	public void withdaw(double x) {
		if((this.getBalance() - x) >= 0) {
			this.setBalance(this.getBalance() - x);
		}
	}

}
