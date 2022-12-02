package src.program.server;
import java.time.LocalDate;

public class TransactionRecord {
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
