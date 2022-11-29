package src.program.client;

import java.io.IOException;
import java.net.SocketException;

import src.ocsf.client.AbstractClient;
import src.protocol.*;

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
			case LOGIN_ATTEMPT:
				ProcessLoginAttempt(sp);
				break;
			case BASIC_MESSAGE:
				ProcessBasicMessage(sp);
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
		System.out.println("Sending message to " + super.getHost() + ":" + super.getPort());
		super.sendToServer(msg);
	}
	
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
	 * sends a login request to the server. The server will reply with a SUCCESS if
	 * the login credentials match an account
	 * @param accountNumber
	 * the account holder's number
	 * @param pin
	 * the account holder's pin
	 * @throws IOException
	 */
	public void LoginAsAccountHolder(String accountNumber, String pin) throws IOException
	{
		ClientProtocol cp = new ClientProtocol(ServerAction.LOGIN_ACCOUNTHOLDER, accountNumber, pin);
		sendToServer(cp);
	}
	
	/**
	 * sends a login request as the teller to the server. The server will reply with a SUCCESS
	 * if the login was successful
	 * @param empID
	 * the teller's employee ID
	 * @param password
	 * the teller's password
	 * @throws IOException
	 */
	public void LoginAsTeller(String empID, String password) throws IOException
	{
		ClientProtocol cp = new ClientProtocol(ServerAction.LOGIN_TELLER, empID, password);
		sendToServer(cp);
	}
	
	/**
	 * If the message from the server is a basic message, the client will
	 * print the message to the console
	 * @param sp serverProtocol passed in by the server
	 */
	private void ProcessBasicMessage(ServerProtocol sp)
	{
		//passes the string message from the server protocol to the
		//banking client controller instance
		bcc.HandleBasicMessage(sp.GetData().get(0));
	}
	
	// TODO: setup code for login attempt
	private void ProcessLoginAttempt(ServerProtocol sp)
	{
		if(sp.getMessageStatus() == MessageStatus.SUCCESS) {
			//TODO: switch scene
		}
		else {
			//TODO: pop up
		}
	}
}
