package src.program.server;
import java.io.IOException;

import src.ocsf.server.AbstractServer;
import src.ocsf.server.ConnectionToClient;
import src.protocol.*;

public class BankingServer extends AbstractServer
{
	public BankingServer(int port)
	{
		super(port);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client)
	{
		ClientProtocol cp = (ClientProtocol)msg;
		System.out.println("Client" + client.getInetAddress()  + " sent message: " + cp.GetParameters().get(0));
		SendTestMessageToClient(client, "Message received by the server");
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
}
