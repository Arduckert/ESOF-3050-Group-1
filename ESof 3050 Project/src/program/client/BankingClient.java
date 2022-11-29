package src.program.client;

import java.io.IOException;
import java.net.SocketException;

import src.ocsf.client.AbstractClient;
import src.protocol.*;

public class BankingClient extends AbstractClient
{
	private IBankingClientController bcc;
	
	public BankingClient(String host, int port, IBankingClientController bcc) {
		super(host, port);
		this.bcc = bcc;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void handleMessageFromServer(Object msg)
	{
		ServerProtocol sp = (ServerProtocol)msg;
		
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
	
	@Override
	public void sendToServer(Object msg) throws IOException {
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
	
	private void ProcessBasicMessage(ServerProtocol sp)
	{
		System.out.println(sp.GetData().get(0));
	}
	
	// TODO: setup code for login attempt
	private void ProcessLoginAttempt(ServerProtocol sp)
	{
		if(sp.getMessageStatus() == MessageStatus.SUCCESS) {
			//switch scene
		}
		else {
			//pop up
		}
	}
}
