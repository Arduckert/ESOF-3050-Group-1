package src.program.server;
import java.io.IOException;

import src.ocsf.server.AbstractServer;
import src.ocsf.server.ConnectionToClient;
import src.protocol.*;

public class BankingServer extends AbstractServer
{
	IBankController bc;
	
	public BankingServer(int port, IBankController b)
	{
		super(port);
		this.bc = b;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client)
	{
		ClientProtocol cp = (ClientProtocol)msg;
		
		switch(cp.GetServerAction())
		{
			case LOGIN_ACCOUNTHOLDER:
				HandleAccountHolderLogin(cp, client);
				break;
			case LOGIN_TELLER:
				handleTellerLogin(cp, client);
				break;
			case TEST:
				ProcessTestMessage(cp, client);
				break;
			default:
				break;
		}
	}
	
	@Override
	protected void clientConnected(ConnectionToClient client)
	{
		System.out.println("Client " + client.getInetAddress() + " connected to the system");
	}
	
	////////////////////
	// TEST MESSAGES  //
	////////////////////
	
	/**
	 * sends a test message back to the client
	 * @param client
	 * @param message
	 */
	public void SendTestMessageToClient(ConnectionToClient client, String message)
	{
		ServerProtocol sp = new ServerProtocol(MessageStatus.SUCCESS, Datatype.BASIC_MESSAGE);
		
		try
		{
			sp.AddData(message);
		} 
		catch (ParameterException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try
		{
			client.sendToClient(sp);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//extracts the test message from the client protocol and sends it to the bank
	//controller for further processing
	private void ProcessTestMessage(ClientProtocol cp, ConnectionToClient client)
	{
		bc.handleTestMessage(cp.GetParameters().get(0), client);
	}
	
	/**
	 * Handles an account holder login request
	 * @param cp
	 * @param client
	 */
	private void HandleAccountHolderLogin(ClientProtocol cp, ConnectionToClient client)
	{
		if (bc.authenticateAccountHolderLogin(cp.GetParameters().get(0),cp.GetParameters().get(1)))
		{
			ServerProtocol sp = new ServerProtocol(MessageStatus.SUCCESS, Datatype.LOGIN_RESULT_ACCOUNTHOLDER);

			try
			{
				client.sendToClient(sp);
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}
		else
		{
			ServerProtocol sp = new ServerProtocol(MessageStatus.FAIL, Datatype.LOGIN_RESULT_ACCOUNTHOLDER);

			try
			{
				client.sendToClient(sp);
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} //END OF LOGIN ACCOUNT HOLDER CASE
	}
	
	/**
	 * Handles an account holder login request
	 * @param cp
	 * @param client
	 */
	private void handleTellerLogin(ClientProtocol cp, ConnectionToClient client)
	{
		if (bc.authenticateTellerLogin(cp.GetParameters().get(0),cp.GetParameters().get(1)))
		{
			ServerProtocol sp = new ServerProtocol(MessageStatus.SUCCESS, Datatype.LOGIN_RESULT_TELLER);

			try
			{
				client.sendToClient(sp);
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}
		else
		{
			ServerProtocol sp = new ServerProtocol(MessageStatus.FAIL, Datatype.LOGIN_RESULT_TELLER);

			try
			{
				client.sendToClient(sp);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		} //END OF LOGIN TELLER CASE
	}
}
