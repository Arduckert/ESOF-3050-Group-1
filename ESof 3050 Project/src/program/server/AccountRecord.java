package src.program.server;

public class AccountRecord extends Record{

	Account account;
	public AccountRecord(Teller teller, String x, Account a) {
		super(teller, x);
		this.account = a;
	}
	
	@Override
	public String toString() {
		return String.format("%s Account: %s",super.toString(), account.getAccountNum());
	}

}
