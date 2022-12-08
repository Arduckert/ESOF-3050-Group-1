package src.program.server;

import java.io.Serializable;

public class ChequingAccount extends Account  implements Serializable {

	//Constructor
	public ChequingAccount(int accountNum, AccountHolder holder) {
		super(accountNum, holder);
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
