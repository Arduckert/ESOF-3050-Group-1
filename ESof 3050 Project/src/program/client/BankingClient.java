package src.program.client;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;

import src.ocsf.client.AbstractClient;
import src.protocol.*;
import src.program.structs.*;

public class BankingClient extends AbstractClient
{
	//abstract banking controller interface. An interface is used here to
	//accompany both the actual bank controller and a test driver to test
	//the banking client
	private IBankingClientController bcc;
	
	/**
	 * Creates a new banking client
	 * @param host the host's IP address
	 * @param port the host's port
	 * @param bcc an instance of a banking client controller
	 */
	public BankingClient(String host, int port, IBankingClientController bcc)
	{
		super(host, port);
		this.bcc = bcc;
	}
	
	/**
	 * This function is responsible for decoding the object as a server protocol
	 * and determining which function to call based on the data type included
	 * within the ServerProtocol object
	 */
	@Override
	protected void handleMessageFromServer(Object msg)
	{
		//casts the incoming object as a serverProtocol object
		ServerProtocol sp = (ServerProtocol)msg;
		
		//determines which function to call based on the data type
		//included in the ServerProtool object. Additional processing
		//is done within these functions before they are passed to
		//the banking client controller for analysis
		switch(sp.getDataType())
		{
			case LOGIN_RESULT_ACCOUNTHOLDER:
				processAccountHolderLoginResult(sp);
				break;
			case LOGIN_RESULT_TELLER:
				processTellerLoginResult(sp);
				break;
			case ACCOUNT_HOLDER_FIND_RESULT:
				processFindAccountHolderByEmailRequest(sp);
				break;
			case ACCOUNT_HOLDER_CREATION_RESULT:
				processAccountHolderResult(sp);
				break;
			case ACCOUNT_HOLDER_DELETION_RESULT:
				processAccountHolderDeletionResult(sp);
				break;
			case PERSON_CREATION_RESULT:
				processPersonCreationResult(sp);
				break;
			case PERSON_DELETION_RESULT:
				processPersonDeletionResult(sp);
				break;
			case ADDRESS_ADDITION_RESULT:
				processAddressAdditionResult(sp);
				break;
			case ADDRESS_REMOVAL_RESULT:
				processAddressRemovalResult(sp);
				break;
			case ACCOUNTHOLDER_ROLE_ASSOCIATION_RESULT:
				processAccountHolderToPersonResult(sp);
				break;
			case ACCOUNT_CREATION_RESULT:
				processAccountCreationResult(sp);
				break;
			case ACCOUNT_DELETION_RESULT:
				processAccountDeletionResult(sp);
				break;
			case ACCOUNT:
				processAccountInformation(sp);
				break;
			case TRANSFER_BALANCE:
				processTransferResult(sp);
				break;
			case TRANSACTION:
				processTransactions(sp);
				break;
			case ACCOUNT_RECORD:
				processAccountRecords(sp);
				break;
			case CUSTOMER_RECORD:
				processCustomerRecords(sp);
				break;
			case BASIC_MESSAGE:
				processBasicMessage(sp);
				break;
			default:
				break;
		}
	}
	
	/**
	 * Sends an object to the server.
	 * @implNote the object MUST be of type ClientProtocol
	 */
	@Override
	public void sendToServer(Object msg) throws IOException
	{
		super.sendToServer(msg);
	}
	
	//////////////////////
	//	BASIC MESSAGES  //
	//////////////////////
	
	/**
	 * Sends a test message to the server, the server will reply with a test
	 * message if successful
	 * @param message
	 * can be any string value
	 * @throws IOException
	 */
	public void SendTestMessageToServer(String message) throws IOException
	{
		ClientProtocol cp = new ClientProtocol(ServerAction.TEST, message);
		sendToServer(cp);
	}
	
	/**
	 * If the message from the server is a basic message, the client will
	 * print the message to the console
	 * @param sp serverProtocol passed in by the server
	 */
	private void processBasicMessage(ServerProtocol sp)
	{
		//passes the string message from the server protocol to the
		//banking client controller instance
		bcc.handleBasicMessage(sp.GetData().get(0));
	}
	
	///////////////////////////
	//	LOGIN ACCOUNT HOLDER //
	///////////////////////////
	
	/**
	 * sends a login request to the server. The server will reply with a SUCCESS if
	 * the login credentials match an account
	 * @param accountNumber
	 * the account holder's number
	 * @param pin
	 * the account holder's pin
	 * @throws IOException
	 */
	public void loginAsAccountHolder(String accountNumber, String pin) throws IOException
	{
		ClientProtocol cp = new ClientProtocol(ServerAction.LOGIN_ACCOUNTHOLDER, accountNumber, pin);
		sendToServer(cp);
	}
	
	/**
	 * Processes the ServerProtocol to fetch the result of the login attempt
	 * @param sp
	 */
	private void processAccountHolderLoginResult(ServerProtocol sp)
	{
		//sends true to the banking client controller handle method if the
		//login result is successful
		if (sp.getMessageStatus() == MessageStatus.SUCCESS)
		{
			bcc.handleAccountHolderLoginResult(true);
		}
		else
		{
			bcc.handleAccountHolderLoginResult(false);
		}
	}
	
	////////////////////
	//	LOGIN TELLER  //
	////////////////////
	
	/**
	 * sends a login request as the teller to the server. The server will reply with a SUCCESS
	 * if the login was successful
	 * @param empID
	 * the teller's employee ID
	 * @param password
	 * the teller's password
	 * @throws IOException
	 */
	public void loginAsTeller(String empID, String password) throws IOException
	{
		ClientProtocol cp = new ClientProtocol(ServerAction.LOGIN_TELLER, empID, password);
		sendToServer(cp);
	}
	
	/**
	 * Processes the ServerProtocol to fetch the result of the login attempt
	 * @param sp
	 */
	private void processTellerLoginResult(ServerProtocol sp)
	{
		//sends true to the banking client controller handle method if the
		//login result is successful
		if (sp.getMessageStatus() == MessageStatus.SUCCESS)
		{
			bcc.handleTellerLoginResult(true);
		}
		else
		{
			bcc.handleTellerLoginResult(false);
		}
	}
	
	////////////////////////////////////
	//	FIND ACCOUNT HOLDER BY EMAIL  //
	////////////////////////////////////
	
	/**
	 * Sends a request to the server to find an account holder by email
	 * @param email the email address of the account holder you want to find
	 * @throws IOException
	 */
	public void findAccountHolderByEmail(String email) throws IOException
	{
		ClientProtocol cp = new ClientProtocol(ServerAction.FIND_ACCOUNTHOLDER_BY_EMAIL, email);
		sendToServer(cp);
	}
	
	/**
	 * Processes the data sent by the server for a find account holder
	 * by email address request
	 * @param sp
	 */
	private void processFindAccountHolderByEmailRequest(ServerProtocol sp)
	{
		//sends information about the account holder if found
		if (sp.getMessageStatus() == MessageStatus.SUCCESS)
		{
			//sends an account holder info with the information
			//from the data sent by the server to the banking
			//client controller
			AccountHolderInfo info = new AccountHolderInfo(
					sp.GetData().get(0),
					sp.GetData().get(1),
					sp.GetData().get(2)
					);
			
			bcc.handleFindAccountHolderByEmailResult(info);
		}
		else
		{
			//return an account holder info with no information
			bcc.handleFindAccountHolderByEmailResult(new AccountHolderInfo());
		}
	}
	
	/////////////////////////////
	//	CREATE ACCOUNT HOLDER  //
	/////////////////////////////
	
	/**
	 * sends a request to the server to create a new account holder
	 * @param email the desired email address
	 * @param pin the desired pin number
	 * @throws IOException
	 */
	public void createAccountHolder(String email, String pin, String sin) throws IOException
	{
		ClientProtocol cp = new ClientProtocol(ServerAction.CREATE_ACCOUNTHOLDER, email, pin, sin);
		sendToServer(cp);
	}
	
	/**
	 * processes an account holder creation result
	 * @param sp
	 */
	private void processAccountHolderResult(ServerProtocol sp)
	{
		//sends true to the banking client controller handle method if the
		//creation was successful
		if (sp.getMessageStatus() == MessageStatus.SUCCESS)
		{
			AccountHolderInfo info = new AccountHolderInfo(sp.GetData().get(0), sp.GetData().get(1), sp.GetData().get(2));
			bcc.handleCreateNewAccountHolderResult(info);
		}
		else
		{
			AccountHolderInfo info = new AccountHolderInfo();
			bcc.handleCreateNewAccountHolderResult(info);
		}
	}
	
	/////////////////////////////
	//	DELETE ACCOUNT HOLDER  //
	/////////////////////////////
	
	/**
	 * sends a request to the server to delete an existing account holder
	 * @param email the desired email address
	 * @param pin the desired pin number
	 * @throws IOException
	 */
	public void deleteAccountHolder(String accountNumber) throws IOException
	{
		ClientProtocol cp = new ClientProtocol(ServerAction.DELETE_ACCOUNTHOLDER, accountNumber);
		sendToServer(cp);
	}
	
	/**
	 * processes an account holder deletion result
	 * @param sp
	 */
	private void processAccountHolderDeletionResult(ServerProtocol sp)
	{
		//sends true to the banking client controller handle method if the
		//deletion was successful
		if (sp.getMessageStatus() == MessageStatus.SUCCESS)
		{
			bcc.handleAccountHolderDeletion(true);
		}
		else
		{
			bcc.handleAccountHolderDeletion(false);
		}
	}
	
	/////////////////////
	//	CREATE PERSON  //
	/////////////////////
	
	/**
	 * sends a request to the server to create a new person
	 * @param firstName
	 * @param lastName
	 * @param sin social insurance number
	 * @param dateOfBirth
	 * @throws IOException
	 */
	public void createPerson(String firstName, String lastName, String sin, String dateOfBirth) throws IOException
	{
		ClientProtocol cp = new ClientProtocol(ServerAction.CREATE_PERSON, firstName, lastName, sin, dateOfBirth);
		sendToServer(cp);
	}
	
	/**
	 * processes a person creation result
	 * @param sp
	 */
	private void processPersonCreationResult(ServerProtocol sp)
	{
		//sends true to the banking client controller handle method if the
		//creation was successful
		if (sp.getMessageStatus() == MessageStatus.SUCCESS)
		{
			bcc.handleCreatePersonResult(true);
		}
		else
		{
			bcc.handleCreatePersonResult(false);
		}
	}
	
	/////////////////////
	//	DELETE PERSON  //
	/////////////////////
	
	/**
	 * sends a request to the server to delete a person
	 * @param sin social insurance number
	 * @throws IOException
	 */
	public void deletePerson(String sin) throws IOException
	{
		ClientProtocol cp = new ClientProtocol(ServerAction.DELETE_PERSON, sin);
		sendToServer(cp);
	}
	
	/**
	 * processes a person deletion result
	 * @param sp
	 */
	private void processPersonDeletionResult(ServerProtocol sp)
	{
		//sends true to the banking client controller handle method if the
		//deletion was successful
		if (sp.getMessageStatus() == MessageStatus.SUCCESS)
		{
			bcc.handlePersonDeletion(true);
		}
		else
		{
			bcc.handlePersonDeletion(false);
		}
	}
	
	///////////////////
	//	ADD ADDRESS  //
	///////////////////
	
	/**
	 * sends a request to the server to add an address to a person
	 * @param sin social insurance number
	 * @throws IOException
	 */
	public void addAddress(String streetName, String streetNumber, String postalCode, String province, String country, String sid) throws IOException
	{
		ClientProtocol cp = new ClientProtocol(ServerAction.ADD_ADDRESS_TO_PERSON, streetName, streetNumber, postalCode, province, country, sid);
		sendToServer(cp);
	}
	
	/**
	 * processes an address addition result
	 * @param sp
	 */
	private void processAddressAdditionResult(ServerProtocol sp)
	{
		//sends true to the banking client controller handle method if the
		//address was added successfully
		if (sp.getMessageStatus() == MessageStatus.SUCCESS)
		{
			bcc.handleAddAddressResult(true);
		}
		else
		{
			bcc.handleAddAddressResult(false);
		}
	}
	
	//////////////////////
	//	REMOVE ADDRESS  //
	//////////////////////
	
	/**
	 * sends a request to the server to remove an address from a person
	 * @param sin social insurance number
	 * @param postalCode
	 * @throws IOException
	 */
	public void removeAddress(String sin, String postalCode) throws IOException
	{
		ClientProtocol cp = new ClientProtocol(ServerAction.REMOVE_ADDRESS_FROM_PERSON, sin, postalCode);
		sendToServer(cp);
	}
	
	/**
	 * processes an address removal from an address
	 * @param sp
	 */
	private void processAddressRemovalResult(ServerProtocol sp)
	{
		//sends true to the banking client controller handle method if the
		//removal was successful
		if (sp.getMessageStatus() == MessageStatus.SUCCESS)
		{
			bcc.handleRemoveAddressResult(true);
		}
		else
		{
			bcc.handleRemoveAddressResult(false);
		}
	}
	
	/////////////////////////////////////////
	//	ADD ACCOUNT HOLDER ROLE TO PERSON  //
	/////////////////////////////////////////
	
	/**
	 * sends a request to the server to add an account holder to a person
	 * @param sin social insurance number
	 * @param email account holder's email address
	 * @throws IOException
	 */
	public void addAccountHolderToPerson(String sin, String email) throws IOException
	{
		ClientProtocol cp = new ClientProtocol(ServerAction.ADD_ACCOUNTHOLDER_ROLE_TO_PERSON, sin, email);
		sendToServer(cp);
	}
	
	/**
	 * processes an account holder to person association result
	 * @param sp
	 */
	private void processAccountHolderToPersonResult(ServerProtocol sp)
	{
		//sends true to the banking client controller handle method if the
		//removal was successful
		if (sp.getMessageStatus() == MessageStatus.SUCCESS)
		{
			bcc.handleAccountHolderToPersonResult(true);
		}
		else
		{
			bcc.handleAccountHolderToPersonResult(false);
		}
	}
	
	////////////////////
	// CREATE ACCOUNT //
	////////////////////
	
	/**
	 * tells the server to create a new account and add it to an
	 * existing account holder. Only tellers can do this action.
	 * @param accountType account type
	 * @param cardNumber the account holder's card number
	 */
	public void createAccount(AccountType accountType, String cardNumber) throws IOException
	{
		ClientProtocol cp = new ClientProtocol(ServerAction.CREATE_ACCOUNT, accountType.toString(), cardNumber);
		sendToServer(cp);
	}
	
	/**
	 * Processes the result of an account being created
	 * @param sp
	 */
	private void processAccountCreationResult(ServerProtocol sp)
	{
		//sends true to the banking client controller handle method if the
		//creation was successful
		if (sp.getMessageStatus() == MessageStatus.SUCCESS)
		{
			bcc.handleAccountCreation(true);
		}
		else
		{
			bcc.handleAccountCreation(false);
		}
	}
	
	////////////////////
	// DELETE ACCOUNT //
	////////////////////
	
	/**
	 * tells the server to delete an existing account and remove it from an
	 * existing account holder. Only tellers can do this action.
	 * @param accountType account type
	 * @param cardNumber the account holder's card number
	 */
	public void deleteAccount(String accountNumber) throws IOException
	{
		ClientProtocol cp = new ClientProtocol(ServerAction.DELETE_ACCOUNT, accountNumber);
		sendToServer(cp);
	}
	
	/**
	 * Processes the result of an account being deleted
	 * @param sp
	 */
	private void processAccountDeletionResult(ServerProtocol sp)
	{
		//sends true to the banking client controller handle method if the
		//deletion was successful
		if (sp.getMessageStatus() == MessageStatus.SUCCESS)
		{
			bcc.handleAccountDeletion(true);
		}
		else
		{
			bcc.handleAccountDeletion(false);
		}
	}
	
	/////////////////
	// GET ACCOUNT //
	/////////////////
	
	/**
	 * Tells the server to get information about all the accounts from
	 * a specific account holder
	 * @param cardNumber the account holder's card number
	 */
	public void getAccounts(String cardNumber) throws IOException
	{
		ClientProtocol cp = new ClientProtocol(ServerAction.GET_ACCOUNTS, cardNumber);
		sendToServer(cp);
	}
	
	/**
	 * Handles the information obtained from the server about an account
	 * @param accountInfo
	 */
	private void processAccountInformation(ServerProtocol sp)
	{
		//the list to send
		ArrayList<AccountInfo> accounts = new ArrayList<AccountInfo>();
		
		//gets the data of each account and places it into the array list of account info
		for (int i = 0; i < sp.GetData().size(); i += sp.getSizePerObject())
		{
			AccountInfo info = new AccountInfo(AccountType.valueOf(sp.GetData().get(i)), sp.GetData().get(i+1), sp.GetData().get(i+2));
			accounts.add(info);
		}
		
		//sends the list to the banking client controller
		bcc.handleAccountInformation(accounts);
	}
	
	////////////////////////////
	// SETUP MORTGAGE ACCOUNT //
	////////////////////////////
	
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
	public void transfer(TransferType transferType, String sendingAccountNum, String recipientAccountNum, String amount) throws IOException
	{
		ClientProtocol cp = new ClientProtocol(ServerAction.TRANSFER, transferType.toString(), sendingAccountNum, recipientAccountNum, amount);
		sendToServer(cp);
	}
	
	/**
	 * processes the transfer result sent by the server
	 * @param sp
	 */
	private void processTransferResult(ServerProtocol sp)
	{
		//sends the transfer result back to the client
		if (sp.getMessageStatus() == MessageStatus.SUCCESS)
		{
			bcc.handleTransferResult(true, sp.GetData().get(0));
		}
		else
		{
			bcc.handleTransferResult(false, null);
		}
	}
	
	//////////////
	// PAY BILL //
	//////////////
	
	//////////////////////
	// GET TRANSACTIONS //
	//////////////////////
	
	/**
	 * tells the server to fetch all of the transactions for a specific account
	 * @param accountNumber the account number of the account
	 */
	public void getTransactions(String accountNumber) throws IOException
	{
		ClientProtocol cp = new ClientProtocol(ServerAction.GET_ACCOUNT_TRANSACTIONS, accountNumber);
		sendToServer(cp);
	}
	
	/**
	 * handles the transactions received by the server
	 * @param transactions a list of information about each transaction in the
	 * transaction history
	 */
	private void processTransactions(ServerProtocol sp)
	{
		ArrayList<TransactionInfo> transactions = new ArrayList<TransactionInfo>();
		
		//gets the data of each transaction and places it into the array list of transaction info
		for (int i = 0; i < sp.GetData().size(); i += sp.getSizePerObject())
		{
			TransactionInfo info = new TransactionInfo(sp.GetData().get(i), sp.GetData().get(i+1), sp.GetData().get(i+2), sp.GetData().get(i+3));
			transactions.add(info);
		}
		
		//sends the list to the banking client controller
		bcc.handleTransactions(transactions);
	}
	
	/////////////////////////
	// GET ACCOUNT RECORDS //
	/////////////////////////
	
	/**
	 * tells the server to fetch all of the account records on the server
	 */
	public void getAccountRecords() throws IOException
	{
		ClientProtocol cp = new ClientProtocol(ServerAction.GET_ACCOUNT_RECORDS);
		sendToServer(cp);
	}
	
	/**
	 * handles the account records received by the server
	 * @param accountRecords a list of account records
	 */
	private void processAccountRecords(ServerProtocol sp)
	{
		ArrayList<RecordInfo> records = new ArrayList<RecordInfo>();
		
		//gets the data of each account record and places it into the array list
		for (int i = 0; i < sp.GetData().size(); i += sp.getSizePerObject())
		{
			RecordInfo info = new RecordInfo(sp.GetData().get(i), sp.GetData().get(i+1), sp.GetData().get(i+2), sp.GetData().get(i+3));
			records.add(info);
		}
		
		//sends the list to the banking client controller
		bcc.handleAccountRecords(records);
	}
	
	//////////////////////////
	// GET CUSTOMER RECORDS //
	//////////////////////////
	
	/**
	 * tells the server to fetch all of the customer records on the server
	 */
	public void getCustomerRecords() throws IOException
	{
		ClientProtocol cp = new ClientProtocol(ServerAction.GET_CUSTOMER_RECORDS);
		sendToServer(cp);
	}
	
	/**
	 * handles the customer records received by the server
	 * @param customerRecords a list of customer records
	 */
	private void processCustomerRecords(ServerProtocol sp)
	{
		ArrayList<RecordInfo> records = new ArrayList<RecordInfo>();
		
		//gets the data of each customer record and places it into the array list
		for (int i = 0; i < sp.GetData().size(); i += sp.getSizePerObject())
		{
			RecordInfo info = new RecordInfo(sp.GetData().get(i), sp.GetData().get(i+1), sp.GetData().get(i+2), sp.GetData().get(i+3));
			records.add(info);
		}
		
		//sends the list to the banking client controller
		bcc.handleCustomerRecords(records);
	}
}
