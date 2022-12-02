package src.program.server;

import java.util.ArrayList;
import java.util.List;

public class LineOfCreditAccount extends Account {

	private double interestRate;
	private double creditLimit; 
	List<MonthlyBill> bills = new ArrayList<MonthlyBill>();
	
	public LineOfCreditAccount(int accountNum, String date, AccountHolder holder, double i, double c) {
		super(accountNum, date, holder);
		this.interestRate = i;
		this.creditLimit = c;
	}
	
	public void addPurchase(double x) {
		this.setBalance(this.getBalance()+(x*(1+interestRate)));
	}
	
	public void pay(double x) {
		if((this.getBalance() - x) >= 0) {
			this.setBalance((this.getBalance() - x));
		}
	}
	

}
