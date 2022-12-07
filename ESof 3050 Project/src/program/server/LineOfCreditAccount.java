package src.program.server;

import java.util.ArrayList;
import java.util.List;

public class LineOfCreditAccount extends Account {

	private double interestRate;
	private double creditLimit; 
	List<MonthlyBill> bills = new ArrayList<MonthlyBill>();
	
	public LineOfCreditAccount(int accountNum, AccountHolder holder, double i, double c) {
		super(accountNum, holder);
		this.interestRate = i;
		this.creditLimit = c;
	}
	
	public void addPurchase(double x) {
		if((this.getBalance()+x) <= creditLimit){
			this.setBalance(this.getBalance()+(x*(1+interestRate)));
		}
		//do nothing
	}
	
	public void addAllBills() { //do this every month or time increment
		double balance = this.getBalance();
		for(int i=0; i<bills.size(); i++) {
			balance = balance + bills.get(i).getAmount();
		}
		
		this.setBalance(balance);
	}
	
	public void pay(double x) {
		if((this.getBalance() - x) >= 0) {
			this.setBalance((this.getBalance() - x));
		}
	}
	
	public MonthlyBill searchBill(String receiver) {
		for(int i=0; i<bills.size(); i++) {
			if(bills.get(i).getReceiver().equals(receiver)) {
				return bills.get(i);
			}
		}
		return null;
	}
	
	public void addBill(MonthlyBill bill) {
		this.bills.add(bill);
	}
	
	public void removeBill(MonthlyBill bill) {
		this.bills.remove(bill);
	}
	

}
