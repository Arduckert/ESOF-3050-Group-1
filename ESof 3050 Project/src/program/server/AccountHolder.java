//--------------------------------------------------------------
//Brief description of this file:
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
import java.util.*;

public class AccountHolder extends personRole  implements Serializable{
	private int pin;
	private int cardNum;
	private String email;
	private ArrayList<Account> accountList = new ArrayList<Account>();
	LoginAttempt loginAttempt;
	
//Constructor
	public AccountHolder(int pin, int card, String email, Person person) {
		super(person); //0 for role = 0;
		this.pin = pin;
		this.cardNum = card;
		this.email = email;
	}
//Setters
	public void setPin(int x) {
		this.pin = x;
	}
	public void setCardNum(int x) {
		this.cardNum = x;
	}
	public void setEmail(String x) {
		this.email = x;
	}
//Getters
	public int getPin() {
		return this.pin;
	}
	public int getCardNum() {
		return this.cardNum;
	}
	public String getEmail() {
		return this.email;
	}
	
	public ArrayList<Account> getAccounts(){
		return this.accountList;
	}
	public void addAccount(Account a) {
		this.accountList.add(a);
	}
	
	
	
}
