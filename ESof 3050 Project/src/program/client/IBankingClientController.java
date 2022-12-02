package src.program.client;

/**
 * @author Connor
 * This is the IBankingClientController interface. This is used by the BankingClient class as an
 * indirect way to communicate with the gui of the client. Only the functions that the BankingClient
 * class needs to call should be included in this interface as this is being used as a sort of
 * abstraction design pattern.
 * 
 * This interface was made to make testing the OCSF much easier and to remove a direct
 * dependency between the BankingClient class and the BankingClientController class.
 */
public interface IBankingClientController
{
	////////////////////
	// BASIC MESSAGES //
	////////////////////
	
	/**
	 * Handles a basic message sent by the server
	 * @param message
	 */
	public void handleBasicMessage(String message);
	
	/**
	 * sends a basic message to the server
	 * @param message
	 */
	public void sendBasicMessage(String message);
	
	/////////////////////////////
	// LOGIN AS ACCOUNT HOLDER //
	/////////////////////////////
	
	/**
	 * send a login request to the server as an account holder
	 * @param accountNumber the account holder's card number
	 * @param pin the account holder's pin
	 */
	public void sendAccountHolderLoginRequest(String cardNumber, String pin);
	
	/**
	 * handles an account holder login result received from the server
	 * @param isSuccessful true for login was successful, false if
	 * not successful
	 */
	public void handleAccountHolderLoginResult(boolean isSuccessful);
	
	/////////////////////
	// LOGIN AS TELLER //
	/////////////////////
	
	/**
	 * send a login request to the teller as a teller
	 * @param empID employee ID of the teller
	 * @param password the teller's password
	 */
	public void sendTellerLoginRequest(String empID, String password);
	
	/**
	 * handles a teller login result received from the server
	 * @param isSuccessful a boolean that represents if the login
	 * was successful
	 */
	public void handleTellerLoginResult(boolean isSuccessful);
}
