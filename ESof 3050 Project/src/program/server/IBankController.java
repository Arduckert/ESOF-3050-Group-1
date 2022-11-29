package src.program.server;

/**
 * @author Connor
 * This is the IBankController interface. This is used by the BankingServer class as an
 * indirect way to communicate with the back-end of the banking server. Only the functions
 * that the BankingServer class needs to call should be included in this interface as this
 * is being used as a sort of abstraction design pattern.
 * 
 * This interface was made to make testing the OCSF much easier and to remove a direct
 * dependency between the BankingServer class and the BankController class.
 */
public interface IBankController
{
	/**
	 * Authenticates an account holder if the card number and pin match an account
	 * @param cardNumber the card number of the account holder
	 * @param pin the pin number of the account holder
	 * @return a boolean value that determines if the authentication was successful
	 */
	public boolean authenticateAccountHolderLogin(String cardNumber, String pin);
}