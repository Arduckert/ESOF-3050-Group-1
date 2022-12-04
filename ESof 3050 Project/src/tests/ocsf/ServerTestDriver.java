package src.tests.ocsf;

import src.program.server.BankingServer;
import src.program.server.IBankController;
import src.program.structs.AccountHolderInfo;

import java.io.IOException;

import src.ocsf.server.ConnectionToClient;

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
	public AccountHolderInfo createAccountHolder(String email)
	{
		if (email.equals(TestVariables.availableCreateAccountHolderEmail))
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
	public boolean deleteAccountHolder(String accountNumber, String pin, String tellerEmpID)
	{
		return accountNumber.equals(TestVariables.availableDeleteAccountHolderNumber)
				&& pin.equals(TestVariables.deleteAccountHolderPin)
				&& tellerEmpID.equals(TestVariables.deleteAccountHolderTellerID);
	}
}
