//--------------------------------------------------------------
//Brief description of this file:
//		This is a specific account called mortgage, a mortgage account is opened when the bank gives a loan on a house
//
//Related Documents:
//		Specifications Document
//		Design Document
//
//File created by Aric Duckert
//File approved by Connor McNally and Mathew Camire
//
//List of Important variable names and their brief description:
//		mortgageLength: the time in months that it should be paid off
//		interestRate: the interest rate of the mortgage
//		principleAmount: this is the value of the loan
//		
//----------------------------------------------------------------
package src.program.server;

import java.io.Serializable;

public class MortgageAccount extends Account implements Serializable{

	private int mortgageLength; //years
	private double interestRate;
	private double principleAmount;
	private double monthlyIP;
	private double monthlyPP;
	
	public MortgageAccount(int accountNum, AccountHolder holder, int l, double p, double ir) {
		super(accountNum, holder);
		this.mortgageLength = l;
		this.interestRate = ir;
		this.principleAmount = p;
	}
	
	public void amoritize() {
		double yearlyAmount = (principleAmount/mortgageLength) * (1+interestRate);
		double monthlyIR = interestRate / 12; //monthly interest rate
		monthlyIP = monthlyIR * principleAmount; //monthly interest payment
		monthlyPP = yearlyAmount / 12;
		this.setBalance(this.getBalance() + (monthlyPP + monthlyIP));
		//principleAmount = principleAmount - monthlyPP;	
	}
	
	public void payMortgage(double x) {
		if(x == this.getBalance()) {
		principleAmount = principleAmount - monthlyPP;	
		this.setBalance(0);
	}
	}

}
