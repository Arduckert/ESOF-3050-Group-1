package src.program.client;

import java.util.ArrayList;

import src.program.structs.*;

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
	
	//////////////////////////////////
	// FIND ACCOUNT HOLDER BY EMAIL //
	//////////////////////////////////
	
	/**
	 * Sends a request to the server to find an account holder by email address
	 * @param email the email address of the account holder you want to find
	 */
	public void sendFindAccountHolderRequest(InputType inputType, String parameter);
	
	/**
	 * Handles the response to a find account holder by email request by the server
	 * @param isSuccessful true if the account holder was found, false if the account holder was not found
	 * @param ahi information about the account holder (this is null if it is not found)
	 */
	public void handleFindAccountHolderResult(AccountHolderInfo ahi);
	
	///////////////////////////
	// CREATE ACCOUNT HOLDER //
	///////////////////////////
	
	/**
	 * Sends a request to the server to create a new account holder given an email
	 * address and pin number
	 * @param email the desired email address
	 * @param pin the desired pin number
	 */
	public void createNewAccountHolder(String email, String pin, String sin);
	
	/**
	 * Handles the result of the creation of an account holder
	 * @param isSuccessful true if an account holder was made, false if not
	 */
	public void handleCreateNewAccountHolderResult(AccountHolderInfo ahi);
	
	///////////////////////////
	// DELETE ACCOUNT HOLDER //
	///////////////////////////
	
	/**
	 * Sends a request to the server to delete an account holder
	 * @param accountNumber the account holder's account number
	 * @param pin the account holder's pin
	 */
	public void deleteAccountHolder(String accountNumber);
	
	/**
	 * Handles the result of an account holder being deleted
	 * @param isSuccessful true if the account holder was deleted, false
	 * if not
	 */
	public void handleAccountHolderDeletion(boolean isSuccessful);
	
	///////////////////
	// CREATE PERSON //
	///////////////////
	
	/**
	 * Sends a request to the server to create a new account holder given an email
	 * address and pin number
	 * @param email the desired email address
	 * @param pin the desired pin number
	 */
	public void createNewPerson(String firstName, String lastName, String sin, String dateOfBirth);
	
	/**
	 * Handles the result of the creation of an account holder
	 * @param isSuccessful true if an account holder was made, false if not
	 */
	public void handleCreatePersonResult(boolean isSuccessful);
	
	///////////////////
	// DELETE PERSON //
	///////////////////
	
	/**
	 * Sends a request to the server to delete an account holder
	 * @param accountNumber the account holder's account number
	 * @param pin the account holder's pin
	 */
	public void deletePerson(String sin);
	
	/**
	 * Handles the result of an account holder being deleted
	 * @param isSuccessful true if the account holder was deleted, false
	 * if not
	 */
	public void handlePersonDeletion(boolean isSuccessful);
	
	/////////////////
	// ADD ADDRESS //
	/////////////////
	
	/**
	 * Adds an address to a person on the server
	 * @param streetName
	 * @param streetNumber
	 * @param postalCode
	 * @param province
	 * @param country
	 * @param sid person's social insurance number
	 */
	public void addAddress(String streetName, String streetNumber, String postalCode, String province, String country, String sid);
	
	/**
	 * handles the result of adding an address to a person
	 * @param isSuccessful
	 */
	public void handleAddAddressResult(boolean isSuccessful);
	
	////////////////////
	// REMOVE ADDRESS //
	////////////////////
	
	/**
	 * removes an address from a person
	 * @param sin person's social insurance number
	 * @param postalCode
	 */
	public void removeAddress(String sin, String postalCode);
	
	/**
	 * handles the result of removing an address from a person
	 * @param isSuccessful
	 */
	public void handleRemoveAddressResult(boolean isSuccessful);
	
	///////////////////////////////////////
	// ADD ACCOUNT HOLDER ROLE TO PERSON //
	///////////////////////////////////////
	
	/**
	 * adds an account holder association to a person
	 * @param sin social insurance number
	 * @param email account holder email address
	 */
	public void addAccountHolderToPerson(String sin, String email);
	
	/**
	 * handles the result of adding an account holder to a person
	 * @param isSuccessful
	 */
	public void handleAccountHolderToPersonResult(boolean isSuccessful);
	
	////////////////////
	// CREATE ACCOUNT //
	////////////////////
	
	/**
	 * tells the server to create a new account and add it to an
	 * existing account holder. Only tellers can do this action.
	 * @param accountType account type
	 * @param cardNumber the account holder's card number
	 */
	public void createAccount(AccountType accountType, String cardNumber, String tellerID);
	
	/**
	 * Handles the result of an account being created
	 * @param isSuccessful true if the account was deleted, false
	 * if not
	 */
	public void handleAccountCreation(String accountNumber);
	
	////////////////////
	// DELETE ACCOUNT //
	////////////////////
	
	/**
	 * tells the server to delete an existing account and remove it from an
	 * existing account holder. Only tellers can do this action.
	 * @param accountType account type
	 * @param cardNumber the account holder's card number
	 */
	public void deleteAccount(String cardNumber, String tellerID);
	
	/**
	 * Handles the result of an account being deleted
	 * @param isSuccessful true if the account holder was deleted, false
	 * if not
	 */
	public void handleAccountDeletion(boolean isSuccessful);
	
	/////////////////
	// GET ACCOUNT //
	/////////////////
	
	/**
	 * Tells the server to get information about a specific account from
	 * a specific account holder
	 * @param accountType account type (chequing, savings, etc.)
	 * @param cardNumber the account holder's card number
	 */
	public void getAccounts(String cardNumber);
	
	/**
	 * Handles the information obtained from the server about an account
	 * @param accountInfo
	 */
	public void handleAccountInformation(ArrayList<AccountInfo> accountInfo);
	
	////////////////////////////
	// SETUP MORTGAGE ACCOUNT //
	////////////////////////////
	
	/**
	 * Tells the server to setup a mortgage account given a set of information
	 * @param accountNumber the account number of the mortgage account
	 * @param mortgageLength the length of the mortgage in years
	 * @param interestRate the interest rate
	 * @param principleAmount the principle amount of the mortgage
	 */
	public void setupMortgageAccount(String accountNumber, String mortgageLength, String interestRate, String principleAmount, String tellerID);
	
	/**
	 * handles the result of populating a mortgage account with information
	 * @param isSuccessful true if the information is populated, false if not
	 */
	public void handleMortgageAccountSetupResult(String accNumber);
	
	//////////////////////////////////
	// SETUP LINE OF CREDIT ACCOUNT //
	//////////////////////////////////
	
	/**
	 * Tells the server to setup a line of credit account given a set of information
	 * @param accountNumber the account number of the line of credit account
	 * @param creditLimit the credit limit of the account
	 * @param interestRate the interest rate
	 */
	public void setupLineOfCreditAccount(String accountNumber, String creditLimit, String interestRate, String tellerID);
	
	/**
	 * handles the result of populating a line of credit account with information
	 * @param isSuccessful true if the information is populated, false if not
	 */
	public void handleLineOfCreditSetupResult(String accNumber);
	
	//////////////
	// TRANSFER //
	//////////////
	
	/**
	 * handles the transfer of funds from one account to another (or to an account if depositing or withdrawing)
	 * @param accountType the account to transfer to or from (chequing, savings, etc.)
	 * @param transferType deposit, withdraw, or transfer
	 * @param recipientEmail the email address of the recipient (if doing a transfer)
	 * @param amount the amount to send
	 */
	public void transfer(TransferType transferType, String sendingAccountNum, String recipientAccountNum, String amount);
	
	/**
	 * handles the transfer result
	 * @param isSuccessful true if the transfer was successful, false if not
	 * @param newBalance the new balance after the transfer
	 */
	public void handleTransferResult(boolean isSuccessful, String newBalance);
	
	/////////////////
	// MANAGE BILL //
	/////////////////
	
	/**
	 * Tells the server to either create a bill or delete a bill that belongs to a line
	 * of credit account
	 * @param billAction the action to perform (create a new bill or delete an existing bill)
	 * @param locAccountNumber the account number of the line of credit account
	 */
	public void manageBill(BillAction billAction, String locAccountNumber, String amount, String receiver);
	
	/**
	 * Handles the result of the management of a bill
	 * @param isSuccessful true if the action was performed successfully, false
	 * if not
	 */
	public void handleBillManagementResult(boolean isSuccessful);
	
	//////////////////////
	// GET TRANSACTIONS //
	//////////////////////
	
	/**
	 * tells the server to fetch all of the transactions for a specific account
	 * @param accountNumber the account number of the account
	 */
	public void getTransactions(String accountNumber);
	
	/**
	 * handles the transactions received by the server
	 * @param transactions a list of information about each transaction in the
	 * transaction history
	 */
	public void handleTransactions(ArrayList<TransactionInfo> transactions);
	
	/////////////////////////
	// GET ACCOUNT RECORDS //
	/////////////////////////
	
	/**
	 * tells the server to fetch all of the account records on the server
	 */
	public void getAccountRecords();
	
	/**
	 * handles the account records received by the server
	 * @param accountRecords a list of account records
	 */
	public void handleAccountRecords(ArrayList<RecordInfo> accountRecords);
	
	//////////////////////////
	// GET CUSTOMER RECORDS //
	//////////////////////////
	
	/**
	 * tells the server to fetch all of the customer records on the server
	 */
	public void getCustomerRecords();
	
	/**
	 * handles the customer records received by the server
	 * @param customerRecords a list of customer records
	 */
	public void handleCustomerRecords(ArrayList<RecordInfo> customerRecords);
}	
