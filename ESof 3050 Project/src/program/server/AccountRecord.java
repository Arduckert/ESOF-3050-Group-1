package src.program.server;

import java.io.Serializable;

public class AccountRecord extends Record  implements Serializable{

	Account account;
	public AccountRecord(Teller teller, String x, Account a) {
		super(teller, x);
		this.account = a;
	}
	
	public Account getAccount() {
		return this.account;
	}
	@Override
	public String toString() {
		return String.format("%s Account: %s",super.toString(), account.getAccountNum());
	}

}
