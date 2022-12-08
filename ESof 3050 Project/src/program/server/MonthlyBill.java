//--------------------------------------------------------------
//Brief description of this file:
//		the monthly bill class stores information on a bill that adds to the balance of an LOC account every time increment
//
//Related Documents:
//		Specifications Document
//		Design Document
//
//File created by Aric Duckert
//File approved by Connor McNally and Mathew Camire
//
//List of Important variable names and their brief description:
//		amount: the monthly amount of the bill (ie: $50 a month for a phone bill)
//		receivingParty: The company that sends you the bill (ie "TbayTel")
//		account: this is the LOC account that pays the bill
//----------------------------------------------------------------
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
