package src.tests.ocsf;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.ArrayList;

import src.program.client.BankingClient;
import src.program.client.IBankingClientController;
import src.program.structs.AccountHolderInfo;
import src.program.structs.AccountInfo;
import src.program.structs.AccountType;
import src.program.structs.BillAction;
import src.program.structs.InputType;
import src.program.structs.RecordInfo;
import src.program.structs.TransactionInfo;
import src.program.structs.TransferType;

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
	private static int transferTestCount = 0;
	private static int manageBillTestCount = 0;
	private static int mortgageSetupTestCount = 0;
	private static int locSetupTestCount = 0;
	
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
			
			//transfer test
			transfer(TestVariables.transferType, TestVariables.sendingAccountNum, TestVariables.availableReceivingAccountNum, TestVariables.transferAmount);
			transfer(TestVariables.transferType, TestVariables.sendingAccountNum, TestVariables.unavailableReceivingAccountNum, TestVariables.transferAmount);
			
			//get transactions test
			getTransactions(TestVariables.sendingAccountNum);
			
			//manage bill test
			manageBill(TestVariables.billAction, TestVariables.availableLOCAccountNumber, TestVariables.billAmount, TestVariables.billRecipient); //handler should return true
			manageBill(TestVariables.billAction, TestVariables.unavailableLOCAccountNumber, TestVariables.billAmount, TestVariables.billRecipient); //handler should return false
			
			//teller login request tests. 
			sendTellerLoginRequest(TestVariables.availableTellerID, TestVariables.availableTellerPassword); //should return true
			sendTellerLoginRequest(TestVariables.availableTellerID, TestVariables.unavailableTellerPassword); //should return false
			sendTellerLoginRequest(TestVariables.unavailableTellerID, TestVariables.availableTellerPassword); //should return false
			sendTellerLoginRequest(TestVariables.unavailableTellerID, TestVariables.unavailableTellerPassword); //should return false
			
			//find account holder by email request tests
			sendFindAccountHolderRequest(TestVariables.findAccountHolderType, TestVariables.availableAccountHolderFindEmail); //should return true and the account holder information
			sendFindAccountHolderRequest(TestVariables.findAccountHolderType, TestVariables.unavailableAccountHolderFindEmail); //should return false and no information
			
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
			//addAccountHolderToPerson(TestVariables.availablePersonSIN, TestVariables.availableRoleEmail); //handler should return true
			//addAccountHolderToPerson(TestVariables.availablePersonSIN, TestVariables.unavailableRoleEmail); //handler should return false
			
			//create account test
			createAccount(TestVariables.accountType, TestVariables.accountCardNumber, TestVariables.availableTellerID); //handler should return true
			createAccount(TestVariables.accountType, TestVariables.availableCreateAccountHolderEmail, TestVariables.availableTellerID); //handler should return false
			
			//delete account test
			deleteAccount(TestVariables.accountCardNumber, TestVariables.availableTellerID); //handler should return true
			deleteAccount(TestVariables.availableCreateAccountHolderEmail, TestVariables.availableTellerID); //handler should return false
			
			//get account test
			getAccounts(TestVariables.availableAccountHolderNumber);
			
			//get account and customer records tests
			getAccountRecords();
			getCustomerRecords();
			
			//setup mortgage account tests
			setupMortgageAccount(TestVariables.availableMortgageCardNumber, TestVariables.mortgageLength, TestVariables.interestRate, TestVariables.principleAmount, TestVariables.availableTellerID); //handler should return true
			setupMortgageAccount(TestVariables.unavailableMortgageCardNumber, TestVariables.mortgageLength, TestVariables.interestRate, TestVariables.principleAmount, TestVariables.availableTellerID); //handler should return false
			
			//setup line of credit account tests
			setupLineOfCreditAccount(TestVariables.availableLOCSetupCardNumber, TestVariables.locCreditLimit, TestVariables.locInterestRate, TestVariables.availableTellerID); //handler should return true
			setupLineOfCreditAccount(TestVariables.unavailableLOCSetupCardNumber, TestVariables.locCreditLimit, TestVariables.locInterestRate, TestVariables.availableTellerID); //handler should return false
			
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
	public void sendFindAccountHolderRequest(InputType inputType, String parameter)
	{
		try
		{
			bc.findAccountHolder(inputType, parameter);
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
	public void handleFindAccountHolderResult(AccountHolderInfo ahi)
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
	public void createAccount(AccountType accountType, String cardNumber, String tellerID)
	{
		try
		{
			bc.createAccount(accountType, cardNumber, tellerID);
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
	public void handleAccountCreation(String accountNumber)
	{
		if (accountCreationTestCount == 0 && accountNumber.equals(TestVariables.createAccountNumber))
		{
			System.out.println("ADD ACCOUNT TRUE TEST PASSED");
		}
		else if (accountCreationTestCount == 1 && accountNumber == null)
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
	public void deleteAccount(String accountNumber, String tellerID)
	{
		try
		{
			bc.deleteAccount(accountNumber, tellerID);
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
	public void getAccounts(String cardNumber)
	{
		try
		{
			bc.getAccounts(cardNumber);
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
	public void handleAccountInformation(ArrayList<AccountInfo> accountInfo)
	{
		if (accountInfo.get(0).accountType == TestVariables.account1.accountType
				&& accountInfo.get(0).balance.equals(TestVariables.account1.balance)
				&& accountInfo.get(0).accountNumber.equals(TestVariables.account1.accountNumber)
				&& accountInfo.get(1).accountType == TestVariables.account2.accountType
				&& accountInfo.get(1).balance.equals(TestVariables.account2.balance)
				&& accountInfo.get(1).accountNumber.equals(TestVariables.account2.accountNumber)
				&& accountInfo.get(2).accountType == TestVariables.account3.accountType
				&& accountInfo.get(2).balance.equals(TestVariables.account3.balance)
				&& accountInfo.get(2).accountNumber.equals(TestVariables.account3.accountNumber))
		{
			System.out.println("GET ACCOUNTS TEST PASSED");
		}
		else
		{			
			assert false;
		}
	}
	
	//////////////
	// TRANSFER //
	//////////////
	
	/**
	 * handles the transfer of funds from one account to another (or to an account if depositing or withdrawing)
	 * @param accountType the account to transfer to or from (chequing, savings, etc.)
	 * @param transferType deposit, withdraw, or transfer
	 * @param recipientEmail the email address of the recipient (if doing a transfer)
	 * @param amount the amount to send
	 */
	public void transfer(TransferType transferType, String sendingAccountNum, String recipientAccountNum, String amount)
	{
		try
		{
			bc.transfer(transferType, sendingAccountNum, recipientAccountNum, amount);
		}
		catch (IOException e)
		{
			System.err.println("GET ACCOUNT TEST FAILED: EXCEPTION");
			e.printStackTrace();
		}
	}
	
	/**
	 * handles the transfer result
	 * @param isSuccessful true if the transfer was successful, false if not
	 * @param newBalance the new balance after the transfer
	 */
	public void handleTransferResult(boolean isSuccessful, String newBalance)
	{
		//checks data integrity and if the test is the expected result
		if (isSuccessful && transferTestCount == 0 && newBalance.equals(TestVariables.transferAmount))
		{
			System.out.println("TRANSFER TRUE TEST PASSED");
		}
		else if (!isSuccessful && transferTestCount == 1 && newBalance == null)
		{
			System.out.println("TRANSFER FALSE TEST PASSED");
		}
		else
		{
			System.err.println("TRANSFER TEST " + (transferTestCount + 1) + " FAILED");
			assert false;
		}
		transferTestCount++;
	}
	
	//////////////////////
	// GET TRANSACTIONS //
	//////////////////////
	
	/**
	 * tells the server to fetch all of the transactions for a specific account
	 * @param accountNumber the account number of the account
	 */
	public void getTransactions(String accountNumber)
	{
		try
		{
			bc.getTransactions(accountNumber);
		}
		catch (IOException e)
		{
			System.err.println("GET TRANSACTIONS TEST FAILED: EXCEPTION");
			e.printStackTrace();
		}
	}
	
	/**
	 * handles the transactions received by the server
	 * @param transactions a list of information about each transaction in the
	 * transaction history
	 */
	public void handleTransactions(ArrayList<TransactionInfo> transactions)
	{
		if (transactions.get(0).date.equals(TestVariables.transaction1.date)
				&& transactions.get(0).recipient.equals(TestVariables.transaction1.recipient)
				&& transactions.get(0).transactionType.equals(TestVariables.transaction1.transactionType)
				&& transactions.get(0).amount.equals(TestVariables.transaction1.amount)
				&& transactions.get(0).sender.equals(TestVariables.transaction1.sender)
				&& transactions.get(1).date.equals(TestVariables.transaction2.date)
				&& transactions.get(1).recipient.equals(TestVariables.transaction2.recipient)
				&& transactions.get(1).transactionType.equals(TestVariables.transaction2.transactionType)
				&& transactions.get(1).amount.equals(TestVariables.transaction2.amount)
				&& transactions.get(1).sender.equals(TestVariables.transaction2.sender)
				&& transactions.get(2).date.equals(TestVariables.transaction3.date)
				&& transactions.get(2).recipient.equals(TestVariables.transaction3.recipient)
				&& transactions.get(2).transactionType.equals(TestVariables.transaction3.transactionType)
				&& transactions.get(2).amount.equals(TestVariables.transaction3.amount)
				&& transactions.get(2).sender.equals(TestVariables.transaction3.sender))
		{
			System.out.println("GET TRANSACTIONS TEST PASSED");
		}
		else
		{		
			assert false;
		}
	}
	
	/////////////////////////
	// GET ACCOUNT RECORDS //
	/////////////////////////
	
	/**
	 * tells the server to fetch all of the account records on the server
	 */
	public void getAccountRecords()
	{
		try
		{
			bc.getAccountRecords();
		}
		catch (IOException e)
		{
			System.err.println("GET ACCOUNT RECORDS TEST FAILED: EXCEPTION");
			e.printStackTrace();
		}
	}
	
	/**
	 * handles the account records received by the server
	 * @param accountRecords a list of account records
	 */
	public void handleAccountRecords(ArrayList<RecordInfo> accountRecords)
	{
		if (accountRecords.get(0).recordDate.equals(TestVariables.accountRecord1.recordDate)
				&& accountRecords.get(0).tellerEmpID.equals(TestVariables.accountRecord1.tellerEmpID)
				&& accountRecords.get(0).recordType.equals(TestVariables.accountRecord1.recordType)
				&& accountRecords.get(0).accountNumber.equals(TestVariables.accountRecord1.accountNumber)
				&& accountRecords.get(1).recordDate.equals(TestVariables.accountRecord2.recordDate)
				&& accountRecords.get(1).tellerEmpID.equals(TestVariables.accountRecord2.tellerEmpID)
				&& accountRecords.get(1).recordType.equals(TestVariables.accountRecord2.recordType)
				&& accountRecords.get(1).accountNumber.equals(TestVariables.accountRecord2.accountNumber)
				&& accountRecords.get(2).recordDate.equals(TestVariables.accountRecord3.recordDate)
				&& accountRecords.get(2).tellerEmpID.equals(TestVariables.accountRecord3.tellerEmpID)
				&& accountRecords.get(2).recordType.equals(TestVariables.accountRecord3.recordType)
				&& accountRecords.get(2).accountNumber.equals(TestVariables.accountRecord3.accountNumber))
		{
			System.out.println("ACCOUNT RECORDS TEST PASSED");
		}
		else
		{
			System.err.println("GET ACCOUNT RECORDS TEST FAILED: DATA NOT EQUAL");
			assert false;
		}
	}
	
	//////////////////////////
	// GET CUSTOMER RECORDS //
	//////////////////////////
	
	/**
	 * tells the server to fetch all of the customer records on the server
	 */
	public void getCustomerRecords()
	{
		try
		{
			bc.getCustomerRecords();
		}
		catch (IOException e)
		{
			System.err.println("GET CUSTOMER RECORDS TEST FAILED: EXCEPTION");
			e.printStackTrace();
		}
	}
	
	/**
	 * handles the customer records received by the server
	 * @param customerRecords a list of customer records
	 */
	public void handleCustomerRecords(ArrayList<RecordInfo> customerRecords)
	{
		if (customerRecords.get(0).recordDate.equals(TestVariables.customerRecord1.recordDate)
				&& customerRecords.get(0).tellerEmpID.equals(TestVariables.customerRecord1.tellerEmpID)
				&& customerRecords.get(0).recordType.equals(TestVariables.customerRecord1.recordType)
				&& customerRecords.get(0).accountNumber.equals(TestVariables.customerRecord1.accountNumber)
				&& customerRecords.get(1).recordDate.equals(TestVariables.customerRecord2.recordDate)
				&& customerRecords.get(1).tellerEmpID.equals(TestVariables.customerRecord2.tellerEmpID)
				&& customerRecords.get(1).recordType.equals(TestVariables.customerRecord2.recordType)
				&& customerRecords.get(1).accountNumber.equals(TestVariables.customerRecord2.accountNumber)
				&& customerRecords.get(2).recordDate.equals(TestVariables.customerRecord3.recordDate)
				&& customerRecords.get(2).tellerEmpID.equals(TestVariables.customerRecord3.tellerEmpID)
				&& customerRecords.get(2).recordType.equals(TestVariables.customerRecord3.recordType)
				&& customerRecords.get(2).accountNumber.equals(TestVariables.customerRecord3.accountNumber))
		{
			System.out.println("CUSTOMER RECORDS TEST PASSED");
		}
		else
		{
			System.err.println("GET CUSTOMER RECORDS TEST FAILED: DATA NOT EQUAL");
			assert false;
		}
	}
	
	/////////////////
	// MANAGE BILL //
	/////////////////
	
	/**
	 * Tells the server to either create a bill or delete a bill that belongs to a line
	 * of credit account
	 * @param billAction the action to perform (create a new bill or delete an existing bill)
	 * @param locAccountNumber the account number of the line of credit account
	 */
	@Override
	public void manageBill(BillAction billAction, String locAccountNumber, String amount, String receiver)
	{
		try
		{
			bc.manageBill(billAction, locAccountNumber, amount, receiver);
		}
		catch (IOException e)
		{
			System.err.println("MANAGE BILL TEST FAILED: EXCEPTION");
			e.printStackTrace();
		}
	}
	
	/**
	 * Handles the result of the management of a bill
	 * @param isSuccessful true if the action was performed successfully, false
	 * if not
	 */
	@Override
	public void handleBillManagementResult(boolean isSuccessful)
	{
		if (manageBillTestCount == 0 && isSuccessful)
		{
			System.out.println("MANAGE BILL TRUE TEST PASSED");
		}
		else if (manageBillTestCount == 1 && !isSuccessful)
		{
			System.out.println("MANAGE BILL FALSE TEST PASSED");
		}
		else
		{
			System.err.println("MANAGE BILL TEST " + (manageBillTestCount + 1) + " FAILED");
			assert false;
		}
		manageBillTestCount++;
	}
	
	////////////////////////////
	// SETUP MORTGAGE ACCOUNT //
	////////////////////////////
	
	/**
	 * Tells the server to setup a mortgage account given a set of information
	 * @param accountNumber the account number of the mortgage account
	 * @param mortgageLength the length of the mortgage in years
	 * @param interestRate the interest rate
	 * @param principleAmount the principle amount of the mortgage
	 */
	public void setupMortgageAccount(String cardNumber, String mortgageLength, String interestRate, String principleAmount, String tellerID)
	{
		try
		{
			bc.setupMortgageAccount(cardNumber, mortgageLength, interestRate, principleAmount, tellerID);
		}
		catch (IOException e)
		{
			System.err.println("SETUP MORTGAGE ACCOUNT TEST FAILED: EXCEPTION");
			e.printStackTrace();
		}
	}
	
	/**
	 * handles the result of populating a mortgage account with information
	 * @param isSuccessful true if the information is populated, false if not
	 */
	public void handleMortgageAccountSetupResult(String accountNumber)
	{
		if (mortgageSetupTestCount == 0 && accountNumber.equals(TestVariables.mortgageAccountNumber))
		{
			System.out.println("SETUP MORTGAGE ACCOUNT TRUE TEST PASSED");
		}
		else if (mortgageSetupTestCount == 1 && accountNumber == null)
		{
			System.out.println("SETUP MORTGAGE ACCOUNT FALSE TEST PASSED");
		}
		else
		{
			System.err.println("SETUP MORTGAGE ACCOUNT TEST " + (mortgageSetupTestCount + 1) + " FAILED");
			assert false;
		}
		mortgageSetupTestCount++;
	}
	
	//////////////////////////////////
	// SETUP LINE OF CREDIT ACCOUNT //
	//////////////////////////////////
	
	/**
	 * Tells the server to setup a line of credit account given a set of information
	 * @param accountNumber the account number of the line of credit account
	 * @param creditLimit the credit limit of the account
	 * @param interestRate the interest rate
	 */
	public void setupLineOfCreditAccount(String cardNumber, String creditLimit, String interestRate, String tellerID)
	{
		try
		{
			bc.setupLineOfCreditAccount(cardNumber, creditLimit, interestRate, tellerID);
		}
		catch (IOException e)
		{
			System.err.println("SETUP LOC ACCOUNT TEST FAILED: EXCEPTION");
			e.printStackTrace();
		}
	}
	
	/**
	 * handles the result of populating a line of credit account with information
	 * @param isSuccessful true if the information is populated, false if not
	 */
	public void handleLineOfCreditSetupResult(String accountNumber)
	{
		if (locSetupTestCount == 0 && accountNumber.equals(TestVariables.locAccountNumber))
		{
			System.out.println("SETUP LOC ACCOUNT TRUE TEST PASSED");
		}
		else if (locSetupTestCount == 1 && accountNumber == null)
		{
			System.out.println("SETUP LOC ACCOUNT FALSE TEST PASSED");
		}
		else
		{
			System.err.println("SETUP LOC ACCOUNT TEST " + (locSetupTestCount + 1) + " FAILED");
			assert false;
		}
		locSetupTestCount++;
	}
}
