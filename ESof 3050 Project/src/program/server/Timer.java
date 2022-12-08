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
