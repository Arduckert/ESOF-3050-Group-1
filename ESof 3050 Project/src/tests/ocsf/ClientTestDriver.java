package src.tests.ocsf;

import java.io.IOException;

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
		RunBasicMessageTest();
	}
	
	//////////////////////////
	//	BASIC MESSAGE TEST  //
	//////////////////////////
	
	private static final String testMessage_Expected = "testing123";
	
	/**
	 * sends a basic message to the server
	 */
	private void RunBasicMessageTest()
	{
		try
		{
			bc.SendTestMessageToServer(testMessage_Expected);
		}
		catch (IOException e)
		{
			System.out.println("TEST MESSAGE FAILED: IO EXCEPTION");
			assert false;
		}
	}
	
	/**
	 * returned message from the server, tests if the message is the
	 * same as the returned message
	 */
	@Override
	public void HandleBasicMessage(String message)
	{
		if (message == testMessage_Expected)
		{
			System.out.println("TEST MESSAGE PASSED");
			assert true;
		}
		else
		{
			System.out.println("TEST MESSAGE FAILED: MESSAGE NOT THE SAME");
			System.out.println("EXPECTED: " + testMessage_Expected);
			System.out.println("ACTUAL" + message);		
			assert false;
		}
	}
}
