package src.program.server;

public class MortgageAccount extends Account{

	private double mortgageValue; //value of the house
	private int mortgageLength; //years
	private double interestRate;
	private double principleAmount;
	private double monthlyIP;
	private double monthlyPP;
	
	public MortgageAccount(int accountNum, String date, AccountHolder holder, double m, int l, double p, double ir) {
		super(accountNum, date, holder);
		this.mortgageValue = m;
		this.mortgageLength = l;
		this.interestRate = ir;
		this.principleAmount = p;
	}
	
	public void amoritize() {
		double yearlyAmount = (principleAmount/mortgageLength) * (1+interestRate);
		double monthlyIR = interestRate / 12; //monthly interest rate
		monthlyIP = monthlyIR * principleAmount; //monthly interest payment
		monthlyPP = yearlyAmount / 12;
		this.setBalance(monthlyPP + monthlyIP);
		//principleAmount = principleAmount - monthlyPP;	
	}
	
	public void payMortgage(double x) {
		if(x == this.getBalance()) {
		principleAmount = principleAmount - monthlyPP;	
		this.setBalance(0);
	}
	}

}