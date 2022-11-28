package src.program.server;
import java.io.IOException;

import src.ocsf.server.AbstractServer;
import src.ocsf.server.ConnectionToClient;
import src.protocol.*;

public class BankingServer extends AbstractServer
{
	IBankController bankController;
	
	public BankingServer(int port, IBankController b)
	{
		super(port);
		this.bankController = b;
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
			case TEST:
				HandleTestMessage(cp, client);
				break;
			default:
				break;
		}
	}
	
	@Override
	protected void clientConnected(ConnectionToClient client)
	{
		System.out.println("Client " + client.getInetAddress() + " connected to the system");
		SendTestMessageToClient(client, "Welcome to the ACM banking system.");
	}
	
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
	
	private void HandleAccountHolderLogin(ClientProtocol cp, ConnectionToClient client)
	{
		if(bankController.authenticateAccountHolderLogin(cp.GetParameters().get(0),cp.GetParameters().get(1))) {
			ServerProtocol sp = new ServerProtocol(MessageStatus.SUCCESS, Datatype.LOGIN_ATTEMPT);
			//System.out.println("LOGIN SUCCESSFUL");
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
		else {
			ServerProtocol sp = new ServerProtocol(MessageStatus.FAIL, Datatype.LOGIN_ATTEMPT);
			//System.out.println("LOGIN FAILED");
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
	
	private void HandleTestMessage(ClientProtocol cp, ConnectionToClient client)
	{
		System.out.println("Client" + client.getInetAddress()  + " sent message: " + cp.GetParameters().get(0));
		SendTestMessageToClient(client, "Message received by the server");
	}
}
