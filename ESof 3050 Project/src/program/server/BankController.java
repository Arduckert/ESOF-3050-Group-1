package src.program.server;
import java.util.*;

import src.program.structs.AccountHolderInfo;

// *** TODO: IMPLEMENT INTERFACE METHODS (REFER TO BLUE MARKS ON THE SCROLL BAR) *** //
public class BankController implements IBankController
{
	
	List<Person> personList = new ArrayList<Person>();
	List<Record> recordList = new ArrayList<Record>(); //potential problem with Record name
	List<Account> accountList = new ArrayList<Account>();
	List<AccountHolder> accountHolderList = new ArrayList<AccountHolder>();
	
	public BankController() {
		
	}
	
	public void addAccountHolder(AccountHolder x) {
		this.accountHolderList.add(x);
	}
	
	
	//search functions
	public Person searchPerson(int sin) {
		for(int i=0; i<personList.size(); i++) {
			if(personList.get(i).getSIN() == sin) {
				return personList.get(i);
			}
		}
		return null; //throw error maybe
	}
	
	public Account searchAccount(int num) {
		for(int i=0; i<accountList.size(); i++) {
			if(accountList.get(i).getAccountNum() == num) {
				return accountList.get(i);
			}
		}
		return null; //throw error maybe
	}
	
	public AccountHolder searchAccountHolder(int cardNum) {
		for(int i=0; i<accountHolderList.size(); i++) {
			if(accountHolderList.get(i).getCardNum() == cardNum) {
				return accountHolderList.get(i);
			}
		}
		return null; //throw error maybe
	}
	
	//Function to convert string to integer
	public int stringToInt(String str) {
		try {
			return Integer.parseInt(str);
		}
		catch (NumberFormatException e) {
			return -1;
		}
	}

	//Authenticating the login of an account holder
	public boolean authenticateAccountHolderLogin(String cardNumber, String pin) {
		int cardNum = stringToInt(cardNumber);
		int PIN = stringToInt(pin);
		
		AccountHolder accountHolder = searchAccountHolder(cardNum);
		if(accountHolder.getPin() == PIN) {
			return true;
		}
		else
			return false;
	}
	
	//function for accepting from server
	
	
	public static void main(String args[])
	{
		BankController b = new BankController();
		Address a = new Address(111, "John", "Thunder Bay", "Ontario", "P7656");
		Person p = new Person("James", "Doe", 7777,"2000-03-02", a);
		AccountHolder testAccountHolder = new AccountHolder(1111,1234567,"test@email.com",p);
		b.addAccountHolder(testAccountHolder);
		
		int port = 9950;
		BankingServer bs = new BankingServer(port, b);
		try {
			bs.listen();
		}
		catch (Exception ex) {
			System.err.println(ex);
		}
		System.out.println("testing the server...");
	}

	/******************************************
	 * PROCESS METHODS FOR THE OCSF
	 * FUNCTION WITH TODO NEED IMPLEMENTATION
	 */
	
	/**
	 * authenticate teller login request
	 */
	@Override
	public boolean authenticateTellerLogin(String empID, String password)
	{
		// TODO add code here
		return false;
	}

	
	/**
	 * echoes the message back to the client
	 */
	@Override
	public String handleTestMessage(String message)
	{
		return message;
	}

	/**
	 * finds an account holder on the server
	 */
	@Override
	public AccountHolderInfo findAccountHolder(String email)
	{
		//TODO: call a find method that returns the index
		//of the accountHolder (-1 if it doesn't find it)
		int accountHolderIndex = -1;
		
		if (accountHolderIndex != -1)
		{
			//TODO: populate these fields (make a get at index method)
			String accountHolderName = null;
			String accountNumber = null;
			String pin = null;	
			
			return new AccountHolderInfo(accountHolderName, email, accountNumber, pin);
		}
		else
		{
			return new AccountHolderInfo();
		}
	}

	/**
	 * create a new account holder
	 */
	@Override
	public boolean createAccountHolder(String email, String pin)
	{
		//TODO: call a create method that returns a boolean where true means
		//the account was created, false if not (if there's a duplicate email
		//or something)
		boolean accountCreated = false;
		return accountCreated;
	}
	
}

