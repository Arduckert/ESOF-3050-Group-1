package src.program.server;

public class CustomerRecord extends Record {

	private AccountHolder accountHolder;
	public CustomerRecord(Teller teller, String a, AccountHolder ah) {
		super(teller, a);
		this.accountHolder = ah;
	}
	
	@Override
	public String toString() {
		return String.format("%s Account Holder: %s %s. Card Number: %d",super.toString(), accountHolder.getPerson().getFName(), accountHolder.getPerson().getLName(), accountHolder.getCardNum());
	}

}
