package src.program.server;

import java.io.IOException;
import src.ocsf.server.AbstractServer;
import src.ocsf.server.ConnectionToClient;
import src.protocol.*;
import src.program.structs.*;

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
			case FIND_ACCOUNTHOLDER_BY_EMAIL:
				handleFindAccountHolderByEmailRequest(cp, client);
				break;
			case CREATE_ACCOUNTHOLDER:
				handleAccountHolderCreationRequest(cp, client);
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
	
	/**
	 * handles a find account holder by email request
	 * @param cp
	 * @param client
	 */
	private void handleFindAccountHolderByEmailRequest(ClientProtocol cp, ConnectionToClient client)
	{
		//sends a find account holder request to the bank controller
		AccountHolderInfo info = bc.findAccountHolder(cp.GetParameters().get(0));
		
		MessageStatus status = info.getHasInfo() ? MessageStatus.SUCCESS : MessageStatus.FAIL;
		ServerProtocol sp = new ServerProtocol(status, Datatype.ACCOUNT_HOLDER_FIND_RESULT);
		
		try
		{
			//adds the account holder information to the server protocol
			sp.AddData(info.accountHolderName, info.email, info.accountNumber, info.pin);
			
			try
			{
				//sends that data to the client
				client.sendToClient(sp);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		catch (ParameterException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * handles an account creation request and dispatches it to the rest of the server
	 * @param cp
	 * @param client
	 */
	private void handleAccountHolderCreationRequest(ClientProtocol cp, ConnectionToClient client)
	{
		bc.createAccount(cp.GetParameters().get(0), cp.GetParameters().get(1));
		
		//success if the account creation was successful, fail if not
		MessageStatus status = bc.createAccount(cp.GetParameters().get(0), cp.GetParameters().get(1)) ? MessageStatus.SUCCESS : MessageStatus.FAIL;	
		ServerProtocol sp = new ServerProtocol(status, Datatype.ACCOUNT_HOLDER_CREATION_RESULT);
		
		try
		{
			client.sendToClient(sp);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
