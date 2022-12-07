package src.tests.ocsf;

import src.program.server.BankingServer;
import src.program.server.IBankController;
import src.program.structs.AccountHolderInfo;
import src.program.structs.AccountInfo;
import src.program.structs.AccountType;
import src.program.structs.RecordInfo;
import src.program.structs.TransactionInfo;
import src.program.structs.TransferType;

import java.io.IOException;
import java.util.ArrayList;

public class ServerTestDriver implements IBankController
{
	private static final int port = 9950;
	private BankingServer bs;
	
	/**
	 * starts the server
	 */
	public ServerTestDriver()
	{
		bs = new BankingServer(port, this);
		
		try
		{
			bs.listen();
			System.out.println("LISTENING...");
		}
		catch (IOException e)
		{
			System.out.println("LISTEN TEST FAILED: IO EXCEPTION");
			e.printStackTrace();
			assert false;
		}
	}
	
	/**
	 * returns a boolean that represents if the account holder and pin match the available account
	 */
	@Override
	public boolean authenticateAccountHolderLogin(String cardNumber, String pin)
	{
		return cardNumber.equals(TestVariables.availableAccountHolderNumber) && pin.equals(TestVariables.availableAccountHolderPin);
	}
	
	//echos the message sent by the client back to the client
	@Override
	public String handleTestMessage(String message)
	{
		return message;
	}

	/**
	 * returns a boolean that represents if the teller id and password match the available account
	 */
	@Override
	public boolean authenticateTellerLogin(String empID, String password)
	{
		return empID.equals(TestVariables.availableTellerID) && password.equals(TestVariables.availableTellerPassword);
	}

	/**
	 * returns an account holder info with information if the email matches the
	 * available one, returns an account holder info with no information if
	 * the email doesn't match
	 */
	@Override
	public AccountHolderInfo findAccountHolder(String email)
	{
		//does the email match the available one?
		if (email.equals(TestVariables.availableAccountHolderFindEmail))
		{
			//creates a new account holder info with the information about
			//the account holder information
			AccountHolderInfo info = new AccountHolderInfo(
					TestVariables.availableAccountHolderFindEmail,
					TestVariables.availableAccountHolderNumber,
					TestVariables.availableAccountHolderFindPin
					);
			return info;
		}
		else
		{
			//returns an account holder info with no information
			return new AccountHolderInfo();
		}
	}

	/**
	 * returns the available email to test both true and false
	 */
	@Override
	public AccountHolderInfo createAccountHolder(String email, String pin, String sin, String tellerEmpID)
	{
		if (email.equals(TestVariables.availableCreateAccountHolderEmail)
				&& tellerEmpID.equals(TestVariables.availableTellerID)
				&& pin.equals(TestVariables.createAccountHolderPin)
				&& sin.equals(TestVariables.createAccountHolderSin))
		{
			AccountHolderInfo info = new AccountHolderInfo(email, TestVariables.createAccountHolderNumber, TestVariables.createAccountHolderPin);
			return info;
		}
		else
		{
			AccountHolderInfo info = new AccountHolderInfo();
			return info;
		}
	}

	/**
	 * returns the available email to test both true and false
	 */
	@Override
	public boolean deleteAccountHolder(String accountNumber,String tellerEmpID)
	{
		return accountNumber.equals(TestVariables.availableDeleteAccountHolderNumber)
				&& tellerEmpID.equals(TestVariables.availableTellerID);
	}

	/**
	 * tests the data obtained from the client to check if its the same
	 */
	@Override
	public boolean createPerson(String firstName, String lastName, String sin, String dateOfBirth)
	{
		return firstName.equals(TestVariables.availablePersonFirstName)
				&& lastName.equals(TestVariables.personLastName)
				&& sin.equals(TestVariables.availablePersonSIN)
				&& dateOfBirth.equals(TestVariables.personDOB);
	}

	/**
	 * tests the data obtained from the client to check if its the same
	 */
	@Override
	public boolean deletePerson(String sin)
	{
		return sin.equals(TestVariables.availablePersonSIN);
	}

	/**
	 * tests the data obtained from the client to check if its the same
	 */
	@Override
	public boolean addAddress(String streetName, String streetNumber, String postalCode, String province,
			String country, String sin)
	{
		return streetName.equals(TestVariables.addressStreetName)
				&& streetNumber.equals(TestVariables.addressStreetNumber)
				&& postalCode.equals(TestVariables.availablePostalCode)
				&& province.equals(TestVariables.addressProvince)
				&& country.equals(TestVariables.addressCountry)
				&& sin.equals(TestVariables.availablePersonSIN);
	}

	/**
	 * tests the data obtained from the client to check if its the same
	 */
	@Override
	public boolean removeAddress(String sin, String postalCode)
	{
		return sin.equals(TestVariables.availablePersonSIN)
				&& postalCode.equals(TestVariables.availablePostalCode);
	}

	/**
	 * tests the data obtained from the client to check if its the same
	 */
	@Override
	public boolean addAccountHolderToPerson(String sin, String email)
	{
		return sin.equals(TestVariables.availablePersonSIN)
				&& email.equals(TestVariables.availableRoleEmail);
	}
	
	/**
	 * Creates an account and adds it to an account holder
	 * @param accountType
	 * @param cardNumber
	 * @return true if the account was created successfully, false if not
	 */
	@Override
	public boolean createAccount(AccountType accountType, String cardNumber)
	{
		return accountType == TestVariables.accountType
				&& cardNumber.equals(TestVariables.accountCardNumber);
	}
	
	/**
	 * Deletes an account and removes it from an account holder
	 * @param accountType
	 * @param cardNumber
	 * @return true if the account was deleted successfully, false if not
	 */
	@Override
	public boolean deleteAccount(String accountNumber)
	{
		return accountNumber.equals(TestVariables.accountCardNumber);
	}
	
	/**
	 * Returns information about a specific account from a specific account holder
	 * @param accountType type of account (chequing, savings, etc.)
	 * @param cardNumber the account holder's card number
	 * @return
	 */
	@Override
	public ArrayList<AccountInfo> getAccounts(String cardNumber)
	{
		if (!(cardNumber.equals(TestVariables.availableAccountHolderNumber)))
		{
			System.err.println("GET ACCOUNTS TEST FAILED: ACCOUNT NUMBER DOESN'T MATCH");
			assert false;
		}
		
		ArrayList<AccountInfo> accounts = new ArrayList<AccountInfo>();
		accounts.add(TestVariables.account1);
		accounts.add(TestVariables.account2);
		accounts.add(TestVariables.account3);
		return accounts;
	}
	
	/**
	 * Completes a transfer of funds from one party to another.
	 * @param accountType the account type (chequing, savings, etc.)
	 * @param transferType the transfer type (deposit, withdraw, transfer)
	 * @param cardNumber the sender's card number
	 * @param recipientEmail the recipient's email address
	 * @param amount the amount of funds to transfer
	 * @return the sender's new balance after the transfer is complete
	 */
	@Override
	public String transfer(TransferType transferType, String sendingAccountNum, String recipientAccountNum, String amount)
	{
		//tests data integrity
		if (transferType == TestVariables.transferType
				&& sendingAccountNum.equals(TestVariables.sendingAccountNum)
				&& recipientAccountNum.equals(TestVariables.availableReceivingAccountNum)
				&& amount.equals(TestVariables.transferAmount))
		{
			//returns a different balance from the input one if the data is equal
			return TestVariables.transferAmount;
		}
		else
		{
			//returns the same balance 
			return null;
		}
	}
	
	/**
	 * gets the transaction history of a specific account
	 * @param cardNumber the account holder's card number
	 * @param accountType the account to fetch from (chequing, savings, etc.)
	 * @return a list of information about each transaction
	 */
	public ArrayList<TransactionInfo> getTransactionHistory(String cardNumber, AccountType accountType)
	{
		if (!cardNumber.equals(TestVariables.availableAccountHolderNumber))
		{
			System.err.println("GET TRANSACTIONS TEST FAILED: CARD NUMBER DOESN'T MATCH");
			assert false;
		}
		
		if (!(accountType == TestVariables.accountType))
		{
			System.err.println("GET TRANSACTIONS TEST FAILED: ACCOUNT TYPE DOESN'T MATCH");
			assert false;
		}
		
		ArrayList<TransactionInfo> transactions = new ArrayList<TransactionInfo>();
		transactions.add(TestVariables.transaction1);
		transactions.add(TestVariables.transaction2);
		transactions.add(TestVariables.transaction3);
		return transactions;
	}
	
	/**
	 * gets all the account records from the server
	 * @return a list of record info that contains information about each
	 * account record
	 */
	public ArrayList<RecordInfo> getAccountRecords()
	{
		ArrayList<RecordInfo> records = new ArrayList<RecordInfo>();
		records.add(TestVariables.accountRecord1);
		records.add(TestVariables.accountRecord2);
		records.add(TestVariables.accountRecord3);
		return records;
	}
	
	
	/**
	 * gets all the customer records from the server
	 * @return a list of record info that contains information about each
	 * customer record
	 */
	public ArrayList<RecordInfo> getCustomerRecords()
	{
		ArrayList<RecordInfo> records = new ArrayList<RecordInfo>();
		records.add(TestVariables.customerRecord1);
		records.add(TestVariables.customerRecord2);
		records.add(TestVariables.customerRecord3);
		return records;
	}
}
