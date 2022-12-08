package src.program.server;

import java.io.Serializable;

public class SavingsAccount extends Account implements Serializable {

	private double interestRate;
	
	public SavingsAccount(int accountNum, AccountHolder holder, double i) {
		super(accountNum, holder);
		this.interestRate = i;
	}
	
	public void setInterestRate(double i) {
		this.interestRate = i;
	}
	public double getInterestRate() {
		return this.interestRate;
	}
	
	public void deposit(double x) {
		double current = this.getBalance();
		this.setBalance(x+current);
	}
	
	public void withdaw(double x) {
		if((this.getBalance() - x) >= 0) {
			this.setBalance(this.getBalance() - x);
		}
	}
	
	public void accrueInterest() {
		this.setBalance(this.getBalance()*(1+interestRate));
	}


}
