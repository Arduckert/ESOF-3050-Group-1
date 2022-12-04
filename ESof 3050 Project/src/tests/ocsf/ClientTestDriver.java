package src.tests.ocsf;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;

import src.program.client.BankingClient;
import src.program.client.IBankingClientController;
import src.program.structs.AccountHolderInfo;

public class ClientTestDriver implements IBankingClientController
{
	private static final int port = 9950;
	private static String ipAdd = "10.0.0.119";
	private BankingClient bc;
	
	//these are here for tests that require multiple runs
	private static int accountHolderTestCount = 0;
	private static int tellerTestCount = 0;
	private static int findAccountHolderByEmailTestCount = 0;
	private static int createAccountHolderTestCount = 0;
	private static int deleteAccountHolderTestCount = 0;
	
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
			
			//find account holder by email request tests
			sendFindAccountHolderByEmailRequest(TestVariables.availableAccountHolderFindEmail); //should return true and the account holder information
			sendFindAccountHolderByEmailRequest(TestVariables.unavailableAccountHolderFindEmail); //should return false and no information
			
			createNewAccountHolder(TestVariables.availableCreateAccountHolderEmail); //handler should return true
			createNewAccountHolder(TestVariables.unavailableCreateAccountHolderEmail); //handler should return false
			
			deleteAccountHolder(TestVariables.availableDeleteAccountHolderNumber, TestVariables.deleteAccountHolderPin); //handler should return true
			deleteAccountHolder(TestVariables.unavailableDeleteAccountHolderNumber, TestVariables.deleteAccountHolderPin); //handler should return false		
			
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

	/////////////////////////////////////////
	//	FIND ACCOUNT HOLDER BY EMAIL TEST  //
	/////////////////////////////////////////
	
	/**
	 * sends a request to the server to find an account holder given
	 * the email address
	 */
	@Override
	public void sendFindAccountHolderByEmailRequest(String email)
	{
		try
		{
			bc.findAccountHolderByEmail(email);
		}
		catch (Exception e)
		{
			System.err.println("FIND ACCOUNT HOLDER BY EMAIL FAILED: EXCEPTION");
			e.printStackTrace();
		}
	}

	/**
	 * handles the result of the find account holder by email request
	 */
	@Override
	public void handleFindAccountHolderByEmailResult(AccountHolderInfo ahi)
	{		
		//the first test involves seeing if the account holder was found
		if (findAccountHolderByEmailTestCount == 0 && ahi.getHasInfo())
		{
			//is all the information the expected information?
			if (ahi.accountNumber.equals(TestVariables.availableAccountHolderFindNumber)
				&& ahi.email.equals(TestVariables.availableAccountHolderFindEmail)
				&& ahi.pin.equals(TestVariables.availableAccountHolderFindPin))
			{
				System.out.println("FIND ACCOUNT HOLDER BY EMAIL WITH INFO TEST " + (findAccountHolderByEmailTestCount + 1) + " PASSED");
			}
			else
			{
				System.err.println("FIND ACCOUNT HOLDER BY EMAIL WITH INFO TEST " + (findAccountHolderByEmailTestCount + 1) + " FAILED");
				assert false;
			}
		}
		else if (findAccountHolderByEmailTestCount > 0 && !ahi.getHasInfo())
		{
			//is all of the information not the expected information?
			if (ahi.accountNumber == null && ahi.email == null && ahi.pin == null)
			{
				System.out.println("FIND ACCOUNT HOLDER BY EMAIL WITHOUT INFO TEST " + (findAccountHolderByEmailTestCount + 1) + " PASSED");
			}
			else
			{
				System.err.println("FIND ACCOUNT HOLDER BY EMAIL WITHOUT INFO TEST " + (findAccountHolderByEmailTestCount + 1) + " FAILED");
				assert false;
			}
		}
		else
		{
			System.err.println("FIND ACCOUNT HOLDER BY EMAIL TEST " + (findAccountHolderByEmailTestCount + 1) + " FAILED");
			assert false;
		}
		findAccountHolderByEmailTestCount++;
	}

	//////////////////////////////////
	//	CREATE ACCOUNT HOLDER TEST  //
	//////////////////////////////////
	
	/**
	 * sends a request to the server to create a new account holder
	 */
	@Override
	public void createNewAccountHolder(String email)
	{
		try
		{
			bc.createAccountHolder(email);
		}
		catch (IOException e)
		{
			System.err.println("CREATE ACCOUNT HOLDER TEST FAILED: EXCEPTION");
			e.printStackTrace();
		}	
	}

	/**
	 * handles the result of the account holder creation
	 */
	@Override
	public void handleCreateNewAccountHolderResult(AccountHolderInfo info)
	{
		if (createAccountHolderTestCount == 0 && info.getHasInfo())
		{
			if (info.email.equals(TestVariables.availableCreateAccountHolderEmail)
					&& info.accountNumber.equals(TestVariables.createAccountHolderNumber)
					&& info.pin.equals(TestVariables.createAccountHolderPin))
			{
				System.out.println("CREATE ACCOUNT HOLDER TRUE TEST PASSED");
			}
			else
			{
				System.err.println("CREATE ACCOUNT HOLDER TRUE TEST " + (createAccountHolderTestCount + 1) + " FAILED - INFO DOES NOT MATCH");
			}
		}
		else if (createAccountHolderTestCount == 1 && !info.getHasInfo())
		{
			System.out.println("CREATE ACCOUNT HOLDER FALSE TEST PASSED");
		}
		else
		{
			System.err.println("CREATE ACCOUNT HOLDER TEST " + (createAccountHolderTestCount + 1) + " FAILED");
			assert false;
		}
		createAccountHolderTestCount++;
	}

	//////////////////////////////////
	//	DELETE ACCOUNT HOLDER TEST  //
	//////////////////////////////////
	
	@Override
	public void deleteAccountHolder(String accountNumber, String pin)
	{
		try
		{
			bc.deleteAccountHolder(accountNumber, pin);
		}
		catch (IOException e)
		{
			System.err.println("DELETE ACCOUNT HOLDER TEST FAILED: EXCEPTION");
			e.printStackTrace();
		}	
	}

	@Override
	public void handleAccountHolderDeletion(boolean isSuccessful)
	{
		if (deleteAccountHolderTestCount == 0 && isSuccessful)
		{
			System.out.println("CREATE ACCOUNT HOLDER TRUE TEST PASSED");
		}
		else if (deleteAccountHolderTestCount == 1 && !isSuccessful)
		{
			System.out.println("CREATE ACCOUNT HOLDER FALSE TEST PASSED");
		}
		else
		{
			System.err.println("CREATE ACCOUNT HOLDER TEST " + (deleteAccountHolderTestCount + 1) + " FAILED");
			assert false;
		}
		deleteAccountHolderTestCount++;
	}
}
