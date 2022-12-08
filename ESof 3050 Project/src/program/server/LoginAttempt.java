//--------------------------------------------------------------
//Brief description of this file:
//		this class stores all information on an account holder attempting to login
//		it allows for the login ability to become locked after a certain number of failed attempts
//
//Related Documents:
//		Specifications Document
//		Design Document
//
//File created by Aric Duckert
//File approved by Connor McNally and Mathew Camire
//
//List of Important variable names and their brief description:
//		failCont: the amount of failed attempts the user has had
//		success: is set to 1 if the login was a success 0 if it was a fail
//		accountHolder: the account holder that is attempting the login
//----------------------------------------------------------------
package src.program.server;

import java.io.Serializable;

public class LoginAttempt  implements Serializable{
	int failCount;
	int success;
	AccountHolder accountHolder;
	
	//Constructor
	public LoginAttempt(AccountHolder x) {
		this.failCount = 0;
		this.success = 0;
		this.accountHolder = x;
	}
	//Setters
	public void setSuccess(int x) {
		this.success = x;
	}
	//getters
	public int getFailCount() {
		return this.failCount;
	}
	public int isSuccess() {
		return this.success;
	}
	
	public void increaseFailCount() {
		this.failCount++;
	}
	public void resetFailCount() {
		this.failCount = 0;
	}
}
