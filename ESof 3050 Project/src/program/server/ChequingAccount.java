package src.program.server;

public class ChequingAccount extends Account {

	//Constructor
	public ChequingAccount(int accountNum, String date, AccountHolder holder) {
		super(accountNum, date, holder);
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

}
