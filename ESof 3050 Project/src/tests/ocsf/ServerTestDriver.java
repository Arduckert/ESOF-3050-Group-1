package src.tests.ocsf;

import src.program.server.BankingServer;
import src.program.server.IBankController;

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
}
