package src.tests.ocsf;

import src.program.server.BankingServer;
import src.program.server.IBankController;
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
		
	}
	
	//echos the message sent by the client back to the client
	@Override
	public void HandleTestMessage(String message, ConnectionToClient client)
	{
		bs.SendTestMessageToClient(client, message);
	}
}
