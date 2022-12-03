package src.program.server;

import src.ocsf.server.*;
import src.program.structs.*;

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
	
	/**
	 * Authenticates a teller if the employee id and password match a teller
	 * @param empId the teller's employee id
	 * @param password the teller's password
	 * @return
	 */
	public boolean authenticateTellerLogin(String empID, String password);
	
	/**
	 * handles test message from the client
	 * @param message
	 */
	public void handleTestMessage(String message, ConnectionToClient client);
	
	/**
	 * finds an account holder on the server
	 * @param email the account holder's email address
	 * @return
	 */
	public AccountHolderInfo findAccountHolder(String email);
	
	/**
	 * creates an account holder on the server
	 * @param email the account holder's desired email address
	 * @param pin the desired pin number
	 * @return true if the account holder was created successfully,
	 * false if not
	 */
	public boolean createAccount(String email, String pin);
}