package src.program.server;
import java.io.Serializable;
import java.time.LocalDate;

public class TransactionRecord implements Serializable{
	private Account recepient;
	private LocalDate date;
	private String transactionType;
	private double amount;
	
	public TransactionRecord(Account a, String t, double x) {
		this.recepient = a;
		this.date = LocalDate.now();
		this.transactionType = t;
		this.amount = x;
	}
	
	//getters
	public String getDate() {
		return String.format("s",date);
	}
	public String getAmount() {
		return amount + "";
	}
	public String getRecepient() {
		int recepientAccNum = this.recepient.getAccountNum();
		return recepientAccNum + "";
	}
	public String getType() {
		return this.transactionType;
	}
	
	public String printTransaction() {
		switch(transactionType) {
		case "deposit":
			return String.format("Deposited: %s\nDate: %s",amount,date);
			
		case "withdraw":
			return String.format("Withdrew: %s\nDate: %s",amount,date);
			
		case "transfer":
			return String.format("Transfered: %s to Account #: %d\nDate: %s",amount,date,recepient.getAccountNum());
		
		case "paid":
			return String.format("Paid: %s\nDate: %s",amount,date);
		
		default:
			return String.format("");
		}

	}
	
	
}
