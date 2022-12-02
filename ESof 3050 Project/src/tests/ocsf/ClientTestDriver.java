package src.tests.ocsf;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;

import src.program.client.BankingClient;
import src.program.client.IBankingClientController;

public class ClientTestDriver implements IBankingClientController
{
	private static final int port = 9950;
	private static String ipAdd = "10.0.0.119";
	private BankingClient bc;
	
	private static int accountHolderTestCount = 0;
	private static int tellerTestCount = 0;
	
	public ClientTestDriver()
	{
		bc = new BankingClient(ipAdd, port, this);
		
		try 
		{
			ipAdd = Inet4Address.getLocalHost().getHostAddress();
		}
		catch (UnknownHostException e) 
		{
			System.err.println("GETTING IP ADDRESS FAILED");
			e.printStackTrace();
		}
	}
	
	public void RunTests()
	{
		try
		{
			OpenServerConnection();
			Sleep(1000); //wait for connection to open
			
			//sends a basic message to the server. The server echoes the message back
			//to the client. The test checks if the message is retained 
			sendBasicMessage(TestVariables.testMessage_Expected);
			
			//account holder login request tests. 
			sendAccountHolderLoginRequest(TestVariables.availableAccountHolderNumber, TestVariables.availableAccountHolderPin); //should return true
			sendAccountHolderLoginRequest(TestVariables.availableAccountHolderNumber, TestVariables.unavailableAccountHolderPin); //should return false
			sendAccountHolderLoginRequest(TestVariables.unavailableAccountHolderNumber, TestVariables.availableAccountHolderPin); //should return false
			sendAccountHolderLoginRequest(TestVariables.unavailableAccountHolderNumber, TestVariables.unavailableAccountHolderPin); //should return false
			
			//teller login request tests. 
			sendTellerLoginRequest(TestVariables.availableTellerID, TestVariables.availableTellerPassword); //should return true
			sendTellerLoginRequest(TestVariables.availableTellerID, TestVariables.unavailableTellerPassword); //should return false
			sendTellerLoginRequest(TestVariables.unavailableTellerID, TestVariables.availableTellerPassword); //should return false
			sendTellerLoginRequest(TestVariables.unavailableTellerID, TestVariables.unavailableTellerPassword); //should return false
			
			Sleep(1000); //wait for connection to close
			CloseServerConnection();
		}
		catch (Exception e)
		{
			System.err.println("TESTS FAILED");
			e.printStackTrace();
			assert false;
		}
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
		catch (Exception e)
		{
			System.err.println("OPEN CONNECTION FAILED");
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
			System.err.println("CLOSE CONNECTION FAILED");
			e.printStackTrace();
			assert false;
		}
		System.out.println("CLOSE CONNECTION PASSED");
	}
	
	//////////////////////////
	//	BASIC MESSAGE TEST  //
	//////////////////////////
	
	/**
	 * sends a basic message to the server
	 */
	@Override
	public void sendBasicMessage(String message)
	{
		try
		{
			bc.SendTestMessageToServer(message);
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
	public void handleBasicMessage(String message)
	{
		if (message.equals(TestVariables.testMessage_Expected))
		{
			System.out.println("TEST MESSAGE PASSED");
		}
		else
		{
			System.err.println("TEST MESSAGE FAILED: MESSAGE NOT THE SAME");
			System.err.println("EXPECTED: " + TestVariables.testMessage_Expected);
			System.err.println("ACTUAL: " + message);		
			assert false;
		}
	}

	/////////////////////////////////
	//	LOGIN ACCOUNT HOLDER TEST  //
	/////////////////////////////////
	
	/**
	 * Sends a login request to the server
	 */
	@Override
	public void sendAccountHolderLoginRequest(String cardNumber, String pin)
	{
		try
		{
			bc.loginAsAccountHolder(cardNumber, pin);
		}
		catch (Exception e)
		{
			System.err.println("LOGIN ACCOUNT HOLDER FAILED: EXCEPTION");
			e.printStackTrace();
		}
	}

	/**
	 * Handles the login results to ensure they
	 * are the expected results given the information provided
	 */
	@Override
	public void handleAccountHolderLoginResult(boolean isSuccessful)
	{
		if (accountHolderTestCount == 0 && isSuccessful)
		{
			System.out.println("LOGIN ACCOUNT HOLDER TEST " + (accountHolderTestCount + 1) + " PASSED");
		}
		else if (accountHolderTestCount > 0 && !isSuccessful)
		{
			System.out.println("LOGIN ACCOUNT HOLDER TEST " + (accountHolderTestCount + 1) + " PASSED");
		}
		else
		{
			System.err.println("LOGIN ACCOUNT HOLDER TEST " + (accountHolderTestCount + 1) + " FAILED");
			assert false;
		}
		accountHolderTestCount++;
	}
	
	/////////////////////////
	//	LOGIN TELLER TEST  //
	/////////////////////////
	
	@Override
	public void sendTellerLoginRequest(String empID, String password)
	{
		try
		{
			bc.loginAsTeller(empID, password);
		}
		catch (Exception e)
		{
			System.err.println("LOGIN TELLER FAILED: EXCEPTION");
			e.printStackTrace();
		}
	}

	@Override
	public void handleTellerLoginResult(boolean isSuccessful)
	{
		if (tellerTestCount == 0 && isSuccessful)
		{
			System.out.println("LOGIN TELLER TEST " + (tellerTestCount + 1) + " PASSED");
		}
		else if (tellerTestCount > 0 && !isSuccessful)
		{
			System.out.println("LOGIN TELLER TEST " + (tellerTestCount + 1) + " PASSED");
		}
		else
		{
			System.err.println("LOGIN TELLER TEST " + (tellerTestCount + 1) + " FAILED");
			assert false;
		}
		tellerTestCount++;
	}
}
