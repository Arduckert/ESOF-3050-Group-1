package src.program.server;

public class MonthlyBill {

	double amount;
	String receivingParty;
	LineOfCreditAccount account;
	
	public MonthlyBill(double x, String r, LineOfCreditAccount a) {
		this.amount = x;
		this.receivingParty = r;
		this.account = a;
	}
	
	public double getAmount() {
		return this.amount;
	}
}
