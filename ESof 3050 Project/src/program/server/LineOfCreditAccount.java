//--------------------------------------------------------------
//Brief description of this file:
//		The line of credit account class stores all information for accounts of the type line of credit
//		
//Related Documents:
//		Specifications Document
//		Design Document
//
//File created by Aric Duckert
//File approved by Connor McNally and Mathew Camire
//
//List of Important variable names and their brief description:
//		interestRate: the interest rate the account holder is charged every time increment
//		creditLimit: the maximum amount of unpaid debt able to be had at one time
//		bills: a list of bills that are added every increment
//----------------------------------------------------------------
package src.program.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LineOfCreditAccount extends Account  implements Serializable{

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
