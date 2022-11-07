package program.server;

public class Account {
	private double balance;
	private int accountNum;
	private String dateOpened;
	private AccountHolder accountHolder;
	//List<TransactionRecord> accountHistory = new ArrayList<TransactionRecord>();

//Constructor
	public Account(int accountNum, String date, AccountHolder holder) {
		this.balance = 0.00;
		this.accountNum = accountNum;
		this.dateOpened = date;
		this.accountHolder = holder;
		//add to list in banking controller or do it in createAccount method
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
		return this.dateOpened;
	}
	public AccountHolder getAccountHolder() {
		return this.accountHolder;
	}
	
}
