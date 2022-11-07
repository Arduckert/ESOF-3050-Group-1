package program.server;
import java.util.*;

public class AccountHolder extends Person {
	private int pin;
	private int cardNum;
	private String email;
	List<Account> accountList = new ArrayList<Account>();
	LoginAttempt loginAttempt;
	
//Constructor
	public AccountHolder(int pin, int card, String email,String fName, String lName, int sin, String dob, Address address, personRole role) {
		super(fName,lName,sin,dob,address,role);
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
	
	
	
}
