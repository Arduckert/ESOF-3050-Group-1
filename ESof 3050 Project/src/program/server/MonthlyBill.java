package src.program.server;

import java.io.Serializable;

public class MonthlyBill  implements Serializable{

	private double amount;
	private String receivingParty;
	private LineOfCreditAccount account;
	
	public MonthlyBill(double x, String r, LineOfCreditAccount a) {
		this.amount = x;
		this.receivingParty = r;
		this.account = a;
	}
	
	public double getAmount() {
		return this.amount;
	}
	public String getReceiver() {
		return this.receivingParty;
	}
}
