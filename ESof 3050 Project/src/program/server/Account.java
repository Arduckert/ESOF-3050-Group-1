package src.program.server;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Account  implements Serializable {
	private double balance;
	private int accountNum;
	private LocalDate dateOpened;
	private AccountHolder accountHolder;
	ArrayList<TransactionRecord> accountHistory = new ArrayList<TransactionRecord>();

//Constructor
	public Account(int accountNum, AccountHolder holder) {
		this.balance = 0.00;
		this.accountNum = accountNum;
		this.dateOpened = LocalDate.now();
		this.accountHolder = holder;
		//add to list in banking controller or do it in createAccount method
	}
	
	public void addTransaction(TransactionRecord t) {
		this.accountHistory.add(t);
	}
//Setters
	public void setBalance(double x) {
		this.balance = x;
	}
//Getters
	public double getBalance() {
		return this.balance;
	}
	public int getAccountNum() {
		return this.accountNum;
	}
	public String getDateOpened() {
		return String.format(getDateOpened());
	}
	public AccountHolder getAccountHolder() {
		return this.accountHolder;
	}
	public ArrayList<TransactionRecord> getRecords(){
		return this.accountHistory;
	}
}
