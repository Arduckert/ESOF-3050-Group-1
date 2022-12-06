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
	public AccountHolderInfo createAccountHolder(String email, String pin, String sin, String tellerEmpID);
	
	/**
	 * deletes an account holder on the server
	 * @param accountNumber the account holder's card number
	 * @param pin the account holder's pin
	 * @return true if the deletion was successful, false if not successful
	 */
	public boolean deleteAccountHolder(String accountNumber, String tellerEmpID);
	
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
	
	/**
	 * adds an address to a person on the server
	 * @param streetName
	 * @param streetNumber
	 * @param postalCode
	 * @param province
	 * @param country
	 * @param sid person's social insurance number
	 * @return true if the address was added, false if not
	 */
	public boolean addAddress(String streetName, String streetNumber, String postalCode, String province, String country, String sid);
	
	/**
	 * removes an address from a person on the server
	 * @param sin
	 * @param postalCode
	 * @return true if the address was removed, false if not
	 */
	public boolean removeAddress(String sin, String postalCode);
	
	/**
	 * adds an account holder role to a person
	 * @param sin social insurance number of person
	 * @param email the account holder email address
	 * @return true if the role was added, false if not
	 */
	public boolean addAccountHolderToPerson(String sin, String email);
	
	/**
	 * Creates an account and adds it to an account holder
	 * @param accountType
	 * @param cardNumber
	 * @return true if the account was created successfully, false if not
	 */
	public boolean createAccount(AccountType accountType, String cardNumber);
	
	/**
	 * Deletes an account and removes it from an account holder
	 * @param accountType
	 * @param cardNumber
	 * @return true if the account was deleted successfully, false if not
	 */
	public boolean deleteAccount(AccountType accountType, String cardNumber);
	
	/**
	 * Returns information about a specific account from a specific account holder
	 * @param accountType type of account (chequing, savings, etc.)
	 * @param cardNumber the account holder's card number
	 * @return
	 */
	public AccountInfo getAccount(AccountType accountType, String cardNumber);
	
	/**
	 * Completes a transfer of funds from one party to another.
	 * @param accountType the account type (chequing, savings, etc.)
	 * @param transferType the transfer type (deposit, withdraw, transfer)
	 * @param cardNumber the sender's card number
	 * @param recipientEmail the recipient's email address
	 * @param amount the amount of funds to transfer
	 * @return the sender's new balance after the transfer is complete
	 */
	public String transfer(AccountType accountType, TransferType transferType, String cardNumber, String recipientEmail, String amount);
}