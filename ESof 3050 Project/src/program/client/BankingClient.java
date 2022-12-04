package src.program.client;

import java.io.IOException;
import java.net.SocketException;

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
	public void createAccountHolder(String email) throws IOException
	{
		ClientProtocol cp = new ClientProtocol(ServerAction.CREATE_ACCOUNTHOLDER, email);
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
	public void deleteAccountHolder(String accountNumber, String pin) throws IOException
	{
		ClientProtocol cp = new ClientProtocol(ServerAction.DELETE_ACCOUNTHOLDER, accountNumber, pin);
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
}
