package src.program.server;
import java.util.*;

import src.program.structs.AccountHolderInfo;
import src.program.structs.AccountType;

// *** TODO: IMPLEMENT INTERFACE METHODS (REFER TO BLUE MARKS ON THE SCROLL BAR) *** //
public class BankController implements IBankController
{
	
	List<Person> personList = new ArrayList<Person>();
	List<Record> recordList = new ArrayList<Record>(); //potential problem with Record name
	List<Account> accountList = new ArrayList<Account>();
	List<AccountHolder> accountHolderList = new ArrayList<AccountHolder>();
	List<Teller> tellerList = new ArrayList<Teller>();
	
	public BankController() {
		
	}
	
	public void addAccountHolder(AccountHolder x) {
		this.accountHolderList.add(x);
	}
	public void addTeller(Teller x) {
		this.tellerList.add(x);
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
	
	public AccountHolder searchAccountHolder(int accountNumber) {
		for(int i=0; i<accountHolderList.size(); i++) {
			if(accountHolderList.get(i).getCardNum() == accountNumber) {
				return accountHolderList.get(i);
			}
		}
		return null; //throw error maybe
	}
	
	public Teller searchTeller(int ID) {
		for(int i=0; i<tellerList.size();i++) {
			if(tellerList.get(i).getEmpNum() == ID) {
				return tellerList.get(i);
			}
		}
		return null;
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
		if(accountHolder==null)
			return false;
		if(accountHolder.getPin() == PIN) {
			return true;
		}
		else
			return false;
	}
	
	//function for accepting from server
	
	


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
		int ID = stringToInt(empID);
		Teller t = searchTeller(ID);
		if(t==null)
			return false;
		if(t.getPassword().equals(password)) {
			return true;
		}
		else
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
			String accountNumber = null;
			String pin = null;	
			
			return new AccountHolderInfo(email, accountNumber, pin);
		}
		else
		{
			return new AccountHolderInfo();
		}
	}

	/**
	 * create a new account holder
	 */
	@Override											                	/*for creating a record*/
	public AccountHolderInfo createAccountHolder(String email, String pin, String sin, String tellerEmpID)
	{
		int p = stringToInt(pin);
		int id = stringToInt(tellerEmpID);
		int s = stringToInt(sin);
		Person person = searchPerson(s);
		
		if(person.getRoles().size() < 2 && person != null) {  //If this person is not already registered as an account holder and exists in the system
			
			//AccountHolder accountHolder = new AccountHolder(p, card, email, person);
		}
		boolean accountHolderCreated = false;
		
		if (accountHolderCreated)
		{

			String e = null;
			String x = null;
			String card = null;
			AccountHolderInfo info = new AccountHolderInfo(e, card, x);
			return info;
		}
		else
		{
			return new AccountHolderInfo();
		}
	}

	/**
	 * delete an existing account holder
	 */
	@Override												         /*for creating a record*/
	public boolean deleteAccountHolder(String accountNumber, String tellerEmpID)
	{
		int num = stringToInt(accountNumber);
		AccountHolder a = searchAccountHolder(num);
		if(a.accountList.isEmpty()) { //if the accountHolder has no open accounts
			accountList.remove(a);
			return true;
		}
		return false;
	}

	/**
	 * create a new person
	 */
	@Override
	public boolean createPerson(String firstName, String lastName, String sin, String dateOfBirth)
	{
		int s = stringToInt(sin);
		if(searchPerson(s) == null) { //Person is not in the system already
			Person p = new Person(firstName,lastName,s,dateOfBirth);
			personList.add(p);
			return true;
		}
		return false;
	}

	/**
	 * delete an existing person
	 */
	@Override
	public boolean deletePerson(String sin)
	{
		int s = stringToInt(sin);
		Person p = searchPerson(s);
		if(p.getRoles().isEmpty()) { //if this person is not a teller or an account holder
			personList.remove(p);
			return true;
		}
		return false;
	}

	@Override
	public boolean addAddress(String streetName, String streetNumber, String postalCode, String province,
			String country, String sid)
	{
		//TODO: call a create method that adds an address to a person
		boolean addressAdded = false;
		return addressAdded;
	}

	@Override
	public boolean removeAddress(String sin, String postalCode)
	{
		//TODO: call a create method that removes an address to a person
		boolean addressRemoved = false;
		return addressRemoved;
	}

	@Override
	public boolean addAccountHolderToPerson(String sin, String email)
	{
		//TODO: call a create method that adds an account holder to a person
		boolean personRoleCreated = false;
		return personRoleCreated;
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
		//TODO: call a create method that adds an account to an account holder
		boolean accountCreated = false;
		return accountCreated;
	}
	
	/**
	 * Deletes an account and removes it from an account holder
	 * @param accountType
	 * @param cardNumber
	 * @return true if the account was deleted successfully, false if not
	 */
	@Override
	public boolean deleteAccount(AccountType accountType, String cardNumber)
	{
		//TODO: call a create method that removes an account to an account holder
		boolean accountDeleted = false;
		return accountDeleted;
	}
	
	
	//MAIN
	public static void main(String args[])
	{
		BankController b = new BankController();
		Address a1 = new Address(111, "John", "Thunder Bay", "Ontario", "P7656");
		Person p1 = new Person("James", "Doe", 7777,"2000-03-02");
		AccountHolder testAccountHolder = new AccountHolder(1111,12345,"test@email.com",p1);
		Teller testTeller = new Teller(0000,"password",p1);
		b.addAccountHolder(testAccountHolder);
		b.addTeller(testTeller);
		
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
}

