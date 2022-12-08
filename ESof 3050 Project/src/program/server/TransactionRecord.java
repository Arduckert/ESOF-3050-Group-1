//--------------------------------------------------------------
//Brief description of this file:
//		This is the transaction record. A transaction record is created every time a transaction occurs
//		
//Related Documents:
//		Specifications Document
//		Design Document
//
//File created by Aric Duckert
//File approved by Connor McNally and Mathew Camire
//
//List of Important variable names and their brief description:
//		sender & receiver: stores an account type corresponding to the sender and receiver of the account
//		date: the day the transaction occurs
//		transactionType: is either withdraw, deposit, or transfer
//		amount: a double variable for the amount of money
//----------------------------------------------------------------
package src.program.server;
import java.io.Serializable;
import java.time.LocalDate;

public class TransactionRecord implements Serializable{
	private Account sender;
	private Account recepient;
	private LocalDate date;
	private String transactionType;
	private double amount;
	
	public TransactionRecord(Account sen, Account rec, String t, double x) {
		this.sender = sen;
		this.recepient = rec;
		this.date = LocalDate.now();
		this.transactionType = t;
		this.amount = x;
	}
	
	//getters
	public String getDate() {
		return String.format("%s",date);
	}
	public String getAmount() {
		return amount + "";
	}
	public String getRecepient() {
		if(recepient == null) {
			return null;
		}
		int recepientAccNum = this.recepient.getAccountNum();
		return recepientAccNum + "";
	}
	public String getSender() {
		if(sender == null) {
			return null;
		}
		int sendingAccNum = this.sender.getAccountNum();
		return sendingAccNum + "";
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
