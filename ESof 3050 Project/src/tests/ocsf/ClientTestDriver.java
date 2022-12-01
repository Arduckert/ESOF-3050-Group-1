package src.tests.ocsf;

import java.io.IOException;

import src.program.client.BankingClient;
import src.program.client.IBankingClientController;

public class ClientTestDriver implements IBankingClientController
{
	private static final int port = 9950;
	private static final String ipAdd = "10.0.0.119";
	private BankingClient bc;
	
	public ClientTestDriver()
	{
		bc = new BankingClient(ipAdd, port, this);
	}
	
	public void RunTests()
	{
		OpenServerConnection();
		Sleep(1000); //wait for connection to open
		
		RunBasicMessageTest();
		
		Sleep(1000); //wait for connection to close
		CloseServerConnection();
		System.out.println("ALL TESTS PASSED!");
	}
	
	private void Sleep(int ms)
	{
		try
		{
			Thread.sleep(ms);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	//////////////////////
	// CONNECTION TESTS //
	//////////////////////
	
	private void OpenServerConnection()
	{
		try
		{
			bc.openConnection();
		}
		catch (IOException e)
		{
			System.out.println("OPEN CONNECTION FAILED: IO EXCEPTION");
			e.printStackTrace();
			assert false;
		}
		System.out.println("OPEN CONNECTION PASSED");
	}
	
	private void CloseServerConnection()
	{
		try
		{
			bc.closeConnection();
		}
		catch (IOException e)
		{
			System.out.println("CLOSE CONNECTION FAILED: IO EXCEPTION");
			e.printStackTrace();
			assert false;
		}
		System.out.println("CLOSE CONNECTION PASSED");
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
			System.err.println("TEST MESSAGE FAILED: IO EXCEPTION");
			e.printStackTrace();
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
		if (message.equals(testMessage_Expected))
		{
			System.out.println("TEST MESSAGE PASSED");
		}
		else
		{
			System.err.println("TEST MESSAGE FAILED: MESSAGE NOT THE SAME");
			System.err.println("EXPECTED: " + testMessage_Expected);
			System.err.println("ACTUAL: " + message);		
			assert false;
		}
	}
}
