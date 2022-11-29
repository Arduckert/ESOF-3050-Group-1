package src.tests.ocsf;

import src.program.server.BankingServer;
import src.program.server.IBankController;

import java.io.IOException;

import src.ocsf.server.ConnectionToClient;

public class ServerTestDriver implements IBankController
{
	private static final int port = 9950;
	private BankingServer bs;
	
	public ServerTestDriver()
	{
		bs = new BankingServer(port, this);
	}
	
	@Override
	public boolean authenticateAccountHolderLogin(String cardNumber, String pin)
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	public void RunTests()
	{
		try
		{
			bs.listen();
		}
		catch (IOException e)
		{
			System.out.println("LISTEN TEST FAILED: IO EXCEPTION");
			assert false;
		}
	}
	
	//echos the message sent by the client back to the client
	@Override
	public void HandleTestMessage(String message, ConnectionToClient client)
	{
		bs.SendTestMessageToClient(client, message);
	}
}
