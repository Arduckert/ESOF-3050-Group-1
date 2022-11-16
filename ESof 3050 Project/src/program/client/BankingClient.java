package src.program.client;

import java.io.IOException;
import java.net.SocketException;

import src.ocsf.client.AbstractClient;
import src.protocol.*;

public class BankingClient extends AbstractClient
{
	public BankingClient(String host, int port) {
		super(host, port);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void handleMessageFromServer(Object msg)
	{
		ServerProtocol sp = (ServerProtocol)msg.getClass().cast(msg);
		System.out.println(sp.GetData().get(0));
	}
	
	@Override
	public void sendToServer(Object msg) throws IOException {
		System.out.println("Trying to send: " + msg);
		System.out.println("Host: "+super.getHost());
		System.out.println("Port: "+super.getPort());
		super.sendToServer(msg);
	}
	
	public void SendTestMessageToServer(String message) throws IOException
	{
		ClientProtocol cp = new ClientProtocol(ServerAction.TEST, message);
		sendToServer(cp);
	}
	
}
