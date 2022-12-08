//--------------------------------------------------------------
//Brief description of this file:
//		This is a runnable class which amortizes all mortgage accounts and adds all bills to accounts
//
//Related Documents:
//		Specifications Document
//		Design Document
//
//File created by Aric Duckert
//File approved by Connor McNally and Mathew Camire
//
//List of Important variable names and their brief description:
//		b: a BankController variable which corresponds to the bankController running
//
//----------------------------------------------------------------
package src.program.server;

public class Timer implements Runnable{

	BankController b;
	
	public Timer(BankController x) {
		this.b = x;
	}
	
	@Override
	public void run() {
		
		for(int i=0; i < b.getAccountList().size(); i++) { //iterates through all accounts in the bank
			if(b.getAccountList().get(i).getClass() == MortgageAccount.class) { //if it is a mortgage account
				MortgageAccount mA = (MortgageAccount)b.getAccountList().get(i);
				mA.amoritize();
			}
			if(b.getAccountList().get(i).getClass() == LineOfCreditAccount.class) { //if it is a LOC account
				LineOfCreditAccount lA = (LineOfCreditAccount)b.getAccountList().get(i);
				lA.addAllBills();
			}
		}
		
	}

}
