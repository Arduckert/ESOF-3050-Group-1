package src.tests.ocsf;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;

import src.program.client.BankingClient;
import src.program.client.IBankingClientController;
import src.program.structs.AccountHolderInfo;
import src.program.structs.AccountInfo;
import src.program.structs.AccountType;

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
	private static int createPersonTestCount = 0;
	private static int deletePersonTestCount = 0;
	private static int addAddressTestCount = 0;
	private static int removeAddressTestCount = 0;
	private static int accountHolderToPersonTestCount = 0;
	private static int accountCreationTestCount = 0;
	private static int accountDeletionTestCount = 0;
	private static int accountGetTestCount = 0;
	
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
			
			//create new account holder tests
			createNewAccountHolder(TestVariables.availableCreateAccountHolderEmail, TestVariables.createAccountHolderPin, TestVariables.createAccountHolderSin); //handler should return true
			createNewAccountHolder(TestVariables.unavailableCreateAccountHolderEmail, TestVariables.createAccountHolderPin, TestVariables.createAccountHolderSin); //handler should return false
			
			//delete account holder tests
			deleteAccountHolder(TestVariables.availableDeleteAccountHolderNumber); //handler should return true
			deleteAccountHolder(TestVariables.unavailableDeleteAccountHolderNumber); //handler should return false		
			
			//create person tests
			createNewPerson(TestVariables.availablePersonFirstName, TestVariables.personLastName, TestVariables.availablePersonSIN, TestVariables.personDOB); //handler should return true
			createNewPerson(TestVariables.unavailablePersonFirstName, TestVariables.personLastName, TestVariables.availablePersonSIN, TestVariables.personDOB); //handler should return false
			
			//delete person tests
			deletePerson(TestVariables.availablePersonSIN); //handler should return true
			deletePerson(TestVariables.unavailablePersonSIN); //handler should return false
			
			//add address tests
			addAddress(TestVariables.addressStreetName, TestVariables.addressStreetNumber, TestVariables.availablePostalCode, TestVariables.addressProvince,
					TestVariables.addressCountry, TestVariables.availablePersonSIN); //handler should return true
			addAddress(TestVariables.addressStreetName, TestVariables.addressStreetNumber, TestVariables.availablePostalCode, TestVariables.addressProvince,
					TestVariables.addressCountry, TestVariables.unavailablePersonSIN); //handler should return false
			
			//remove address tests
			removeAddress(TestVariables.availablePersonSIN, TestVariables.availablePostalCode); //handler should return true
			removeAddress(TestVariables.availablePersonSIN, TestVariables.unavailablePostalCode); //handler should return false
			
			//add account holder to person test
			addAccountHolderToPerson(TestVariables.availablePersonSIN, TestVariables.availableRoleEmail); //handler should return true
			addAccountHolderToPerson(TestVariables.availablePersonSIN, TestVariables.unavailableRoleEmail); //handler should return false
			
			//create account test
			createAccount(TestVariables.accountType, TestVariables.accountCardNumber); //handler should return true
			createAccount(TestVariables.accountType, TestVariables.availableCreateAccountHolderEmail); //handler should return false
			
			//delete account test
			deleteAccount(TestVariables.accountType, TestVariables.accountCardNumber); //handler should return true
			deleteAccount(TestVariables.accountType, TestVariables.availableCreateAccountHolderEmail); //handler should return false
			
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
			if (ahi.cardNumber.equals(TestVariables.availableAccountHolderFindNumber)
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
			if (ahi.cardNumber == null && ahi.email == null && ahi.pin == null)
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
	public void createNewAccountHolder(String email, String pin, String sin)
	{
		try
		{
			bc.createAccountHolder(email, pin, sin);
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
					&& info.cardNumber.equals(TestVariables.createAccountHolderNumber)
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
	public void deleteAccountHolder(String accountNumber)
	{
		try
		{
			bc.deleteAccountHolder(accountNumber);
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
			System.out.println("DELETE ACCOUNT HOLDER TRUE TEST PASSED");
		}
		else if (deleteAccountHolderTestCount == 1 && !isSuccessful)
		{
			System.out.println("DELETE ACCOUNT HOLDER FALSE TEST PASSED");
		}
		else
		{
			System.err.println("DELETE ACCOUNT HOLDER TEST " + (deleteAccountHolderTestCount + 1) + " FAILED");
			assert false;
		}
		deleteAccountHolderTestCount++;
	}

	//////////////////////////
	//	CREATE PERSON TEST  //
	//////////////////////////
	
	/**
	 * sends a request to the server to test person creation
	 */
	@Override
	public void createNewPerson(String firstName, String lastName, String sin, String dateOfBirth)
	{
		try
		{
			bc.createPerson(firstName, lastName, sin, dateOfBirth);
		}
		catch (IOException e)
		{
			System.err.println("CREATE PERSON TEST FAILED: EXCEPTION");
			e.printStackTrace();
		}
	}

	/**
	 * test handler for person creation
	 */
	@Override
	public void handleCreatePersonResult(boolean isSuccessful)
	{
		if (createPersonTestCount == 0 && isSuccessful)
		{
			System.out.println("CREATE PERSON TRUE TEST PASSED");
		}
		else if (createPersonTestCount == 1 && !isSuccessful)
		{
			System.out.println("CREATE PERSON FALSE TEST PASSED");
		}
		else
		{
			System.err.println("CREATE PERSON TEST " + (createPersonTestCount + 1) + " FAILED");
			assert false;
		}
		createPersonTestCount++;
	}

	//////////////////////////
	//	DELETE PERSON TEST  //
	//////////////////////////
	
	/**
	 * sends a person deletion request to the server
	 */
	@Override
	public void deletePerson(String sin)
	{
		try
		{
			bc.deletePerson(sin);
		}
		catch (IOException e)
		{
			System.err.println("DELETE PERSON TEST FAILED: EXCEPTION");
			e.printStackTrace();
		}
	}

	/**
	 * test handler for person deletion
	 */
	@Override
	public void handlePersonDeletion(boolean isSuccessful)
	{
		if (deletePersonTestCount == 0 && isSuccessful)
		{
			System.out.println("DELETE PERSON TRUE TEST PASSED");
		}
		else if (deletePersonTestCount == 1 && !isSuccessful)
		{
			System.out.println("DELETE PERSON FALSE TEST PASSED");
		}
		else
		{
			System.err.println("DELETE PERSON TEST " + (deletePersonTestCount + 1) + " FAILED");
			assert false;
		}
		deletePersonTestCount++;
	}

	///////////////////////
	// ADD ADDRESS TEST  //
	///////////////////////
	
	/**
	 * runs add address test
	 */
	@Override
	public void addAddress(String streetName, String streetNumber, String postalCode, String province, String country,
			String sin)
	{
		try
		{
			bc.addAddress(streetName, streetNumber, postalCode, province, country, sin);
		}
		catch (IOException e)
		{
			System.err.println("ADD ADDRESS TEST FAILED: EXCEPTION");
			e.printStackTrace();
		}
	}

	/**
	 * handles add address test result
	 */
	@Override
	public void handleAddAddressResult(boolean isSuccessful)
	{
		if (addAddressTestCount == 0 && isSuccessful)
		{
			System.out.println("ADD ADDRESS TRUE TEST PASSED");
		}
		else if (addAddressTestCount == 1 && !isSuccessful)
		{
			System.out.println("ADD ADDRESS FALSE TEST PASSED");
		}
		else
		{
			System.err.println("DELETE PERSON TEST " + (addAddressTestCount + 1) + " FAILED");
			assert false;
		}
		addAddressTestCount++;
	}

	/////////////////////////
	// REMOVE ADDRESS TEST //
	/////////////////////////
	
	/**
	 * runs remove address test
	 */
	@Override
	public void removeAddress(String sin, String postalCode)
	{
		try
		{
			bc.removeAddress(sin, postalCode);
		}
		catch (IOException e)
		{
			System.err.println("REMOVE ADDRESS TEST FAILED: EXCEPTION");
			e.printStackTrace();
		}
	}

	/**
	 * handles remove address test result
	 */
	@Override
	public void handleRemoveAddressResult(boolean isSuccessful)
	{
		if (removeAddressTestCount == 0 && isSuccessful)
		{
			System.out.println("REMOVE ADDRESS TRUE TEST PASSED");
		}
		else if (removeAddressTestCount == 1 && !isSuccessful)
		{
			System.out.println("REMOVE ADDRESS FALSE TEST PASSED");
		}
		else
		{
			System.err.println("DELETE PERSON TEST " + (removeAddressTestCount + 1) + " FAILED");
			assert false;
		}
		removeAddressTestCount++;
	}

	///////////////////////////////////////
	// ADD ACCOUNT HOLDER ROLE TO PERSON //
	///////////////////////////////////////
	
	/**
	 * sends the required data to the server for testing
	 */
	@Override
	public void addAccountHolderToPerson(String sin, String email)
	{
		try
		{
			bc.addAccountHolderToPerson(sin, email);
		}
		catch (IOException e)
		{
			System.err.println("ADD ACCOUNT HOLDER TO PERSON TEST FAILED: EXCEPTION");
			e.printStackTrace();
		}
	}

	/**
	 * handles the result sent by the server
	 */
	@Override
	public void handleAccountHolderToPersonResult(boolean isSuccessful)
	{
		if (accountHolderToPersonTestCount == 0 && isSuccessful)
		{
			System.out.println("ADD ACCOUNT HOLDER TO PERSON TRUE TEST PASSED");
		}
		else if (accountHolderToPersonTestCount == 1 && !isSuccessful)
		{
			System.out.println("ADD ACCOUNT HOLDER TO PERSON TEST PASSED");
		}
		else
		{
			System.err.println("ADD ACCOUNT HOLDER TO PERSON TEST " + (accountHolderToPersonTestCount + 1) + " FAILED");
			assert false;
		}
		accountHolderToPersonTestCount++;
	}
	
	////////////////////
	// CREATE ACCOUNT //
	////////////////////
	
	/**
	 * tells the server to create a new account and add it to an
	 * existing account holder. Only tellers can do this action.
	 * @param accountType account type
	 * @param cardNumber the account holder's card number
	 */
	@Override
	public void createAccount(AccountType accountType, String cardNumber)
	{
		try
		{
			bc.createAccount(accountType, cardNumber);
		}
		catch (IOException e)
		{
			System.err.println("ADD ACCOUNT TEST FAILED: EXCEPTION");
			e.printStackTrace();
		}
	}
	
	/**
	 * Handles the result of an account being created
	 * @param isSuccessful true if the account was deleted, false
	 * if not
	 */
	@Override
	public void handleAccountCreation(boolean isSuccessful)
	{
		if (accountCreationTestCount == 0 && isSuccessful)
		{
			System.out.println("ADD ACCOUNT TRUE TEST PASSED");
		}
		else if (accountCreationTestCount == 1 && !isSuccessful)
		{
			System.out.println("ADD ACCOUNT FALSE TEST PASSED");
		}
		else
		{
			System.err.println("ADD ACCOUNT TEST " + (accountCreationTestCount + 1) + " FAILED");
			assert false;
		}
		accountCreationTestCount++;
	}
	
	////////////////////
	// DELETE ACCOUNT //
	////////////////////
	
	/**
	 * tells the server to delete an existing account and remove it from an
	 * existing account holder. Only tellers can do this action.
	 * @param accountType account type
	 * @param cardNumber the account holder's card number
	 */
	@Override
	public void deleteAccount(AccountType accountType, String cardNumber)
	{
		try
		{
			bc.deleteAccount(accountType, cardNumber);
		}
		catch (IOException e)
		{
			System.err.println("DELETE ACCOUNT TEST FAILED: EXCEPTION");
			e.printStackTrace();
		}
	}
	
	/**
	 * Handles the result of an account being deleted
	 * @param isSuccessful true if the account holder was deleted, false
	 * if not
	 */
	@Override
	public void handleAccountDeletion(boolean isSuccessful)
	{
		if (accountDeletionTestCount == 0 && isSuccessful)
		{
			System.out.println("DELETE ACCOUNT TRUE TEST PASSED");
		}
		else if (accountDeletionTestCount == 1 && !isSuccessful)
		{
			System.out.println("DELETE ACCOUNT FALSE TEST PASSED");
		}
		else
		{
			System.err.println("DELETE ACCOUNT TEST " + (accountDeletionTestCount + 1) + " FAILED");
			assert false;
		}
		accountDeletionTestCount++;
	}
	
	/////////////////
	// GET ACCOUNT //
	/////////////////
	
	/**
	 * Tells the server to get information about a specific account from
	 * a specific account holder
	 * @param accountType account type (chequing, savings, etc.)
	 * @param cardNumber the account holder's card number
	 */
	@Override
	public void getAccount(AccountType accountType, String cardNumber)
	{
		try
		{
			bc.getAccount(accountType, cardNumber);
		}
		catch (IOException e)
		{
			System.err.println("GET ACCOUNT TEST FAILED: EXCEPTION");
			e.printStackTrace();
		}
	}
	
	/**
	 * Handles the information obtained from the server about an account and tests
	 * for data integrity
	 * @param accountInfo
	 */
	public void handleAccountInformation(AccountInfo accountInfo)
	{
		if (accountInfo.getHasInfo() && accountGetTestCount == 0)
		{
			//tests for data integrity
			if (accountInfo.accountType == TestVariables.getAccountType
					&& accountInfo.balance.equals(TestVariables.getAccountBalance)
					&& accountInfo.accountNumber.equals(TestVariables.getAccountNumber))
			{
				System.out.println("GET ACCOUNT TRUE TEST PASSED");
			}
		}
		else if (!accountInfo.getHasInfo() && accountGetTestCount == 1)
		{
			System.out.println("GET ACCOUNT FALSE TEST PASSED");
		}
		else
		{
			System.err.println("GET ACCOUNT TEST " + (accountGetTestCount + 1) + " FAILED");
			assert false;
		}
		accountGetTestCount++;
	}
}
