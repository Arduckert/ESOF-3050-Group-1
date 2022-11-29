package src.tests.ocsf;

import src.program.client.BankingClient;
import src.program.client.IBankingClientController;

public class ClientTestDriver implements IBankingClientController
{
	private static final int port = 9950;
	private static final String ipAdd = "10.100.131.6";
	private BankingClient bc;
	
	public ClientTestDriver()
	{
		bc = new BankingClient(ipAdd, port, this);
	}
	
	public void RunTests()
	{
		//TODO: implement function
	}
}
