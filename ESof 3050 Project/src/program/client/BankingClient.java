package src.program.client;

import java.io.IOException;
import java.net.SocketException;

import src.ocsf.client.AbstractClient;

public class BankingClient extends AbstractClient
{
	public BankingClient(String host, int port) {
		super(host, port);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		// TODO Auto-generated method stub
		System.out.println(msg);
	}
	
	@Override
	public void sendToServer(Object msg) throws IOException {
		System.out.println("Trying to send: " + msg);
		super.sendToServer(msg);
		System.out.println("Success");
		
	}
	
}
