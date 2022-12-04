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
	 * @return true if the login was successful, false if not successful
	 */
	public boolean authenticateTellerLogin(String empID, String password);
	
	/**
	 * handles test message from the client
	 * @param message
	 */
	public String handleTestMessage(String message);
	
	/**
	 * finds an account holder on the server
	 * @param email the account holder's email address
	 * @return a data structure containing all the information about the
	 * account holder
	 */
	public AccountHolderInfo findAccountHolder(String email);
	
	/**
	 * creates an account holder on the server
	 * @param email the account holder's desired email address
	 * @param pin the desired pin number
	 * @return true if the account holder was created successfully,
	 * false if not
	 */
	public AccountHolderInfo createAccountHolder(String email, String tellerEmpID);
	
	/**
	 * deletes an account holder on the server
	 * @param accountNumber the account holder's card number
	 * @param pin the account holder's pin
	 * @return true if the deletion was successful, false if not successful
	 */
	public boolean deleteAccountHolder(String accountNumber, String pin, String tellerEmpID);
	
	/**
	 * creates a person on the server
	 * @param firstName
	 * @param lastName
	 * @param sin social insurance number
	 * @param dateOfBirth
	 * @return true if the person was created, false if not
	 */
	public boolean createPerson(String firstName, String lastName, String sin, String dateOfBirth);
	
	/**
	 * deletes a person from the server
	 * @param sin
	 * @return true if the person was deleted, false if not
	 */
	public boolean deletePerson(String sin);
}