package src.program.server;
import java.util.*;
import java.lang.Math;

import src.program.structs.AccountHolderInfo;
import src.program.structs.AccountInfo;
import src.program.structs.AccountType;
import src.program.structs.TransferType;

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
	
	//generates a random card number of 9 digits
	public int generateCardNumber() {
		boolean numExists = true;
		int num = 0;
		while(numExists) {
			numExists = false;
			num = (int)(Math.random()*(999999999-100000000+1)+100000000); //generate random 9 digit number
			for(int i=0; i<accountHolderList.size(); i++) { 
				if(accountHolderList.get(i).getCardNum() == num) { //checks if the number is already in use
					numExists = true;
				}
			}
		}
		return num;
	}
	
	//Method for generating Account Number
	public int generateAccountNumber() {
		boolean numExists = true;
		int num = 0;
		while(numExists) {
			numExists = false;
			num = (int)(Math.random()*(999999-100000+1)+100000); //generate random 6 digit number
			for(int i=0; i<accountList.size(); i++) {
				if(accountList.get(i).getAccountNum() == num) {
					numExists = true;
				}
			}
		}
		return num;
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
	
	public AccountHolder searchAccountHolder(int cardNumber) {
		for(int i=0; i<accountHolderList.size(); i++) {
			if(accountHolderList.get(i).getCardNum() == cardNumber) {
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
	
	//function for accepting from server
	
	


	/******************************************
	 * PROCESS METHODS FOR THE OCSF
	 * FUNCTION WITH TODO NEED IMPLEMENTATION
	 */
	
	//Authenticating the login of an account holder
	@Override
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
		AccountHolder accountHolder = null;
		for(int i=0; i < accountHolderList.size(); i++) {  //check all account holders
			if(accountHolderList.get(i).getEmail().equals(email)) {
				accountHolder = accountHolderList.get(i);
			}
		}
		if(accountHolder == null) { //if no accountHolder was found with that email
			return new AccountHolderInfo(); //return empty info
		}
		
		String cardNumber = accountHolder.getCardNum() + "";
		String pin = accountHolder.getPin() + "";
		
		return new AccountHolderInfo(email, cardNumber, pin);
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
		Teller teller = searchTeller(id);
		
		if(person.getRoles().size() < 2 && person != null && teller != null) {  //If this person is not already registered as an account holder and exists in the system
			int card = generateCardNumber();
			AccountHolder accountHolder = new AccountHolder(p, card, email, person);
			accountHolderList.add(accountHolder);
			CustomerRecord customerRecord = new CustomerRecord(teller, "created", accountHolder);
			recordList.add(customerRecord);
			
			String cardText = card + "";
			AccountHolderInfo info = new AccountHolderInfo(null, cardText, null);
			return info;
			}
		else {
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
		int num = stringToInt(streetNumber);
		int sin = stringToInt(sid);
		Person p = searchPerson(sin);
		
		if(p == null) {
			return false;
		}
		else {
			Address a = new Address(num, streetName, postalCode, province, country);
			p.addAddress(a);
			return true;
		}
	}

	@Override
	public boolean removeAddress(String sin, String postalCode)
	{
		int s = stringToInt(sin);
		Person person = searchPerson(s);
		if(person == null) { //if the person is not in the database return false
			return false;
		}
		Address address = person.searchAddress(postalCode);
		
		if(address == null) { //if the address does not exist in the database
			return false;
		}
	
		person.getAdresses().remove(address);
		return true;
	}

	@Override
	public boolean addAccountHolderToPerson(String sin, String email) //This function should not be needed
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
	public boolean createAccount(AccountType accountType, String cardNumber)//savings or chequing
	{
		return false;
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
	
	/**
	 * Returns information about a specific account from a specific account holder
	 * @param accountType type of account (chequing, savings, etc.)
	 * @param cardNumber the account holder's card number
	 * @return
	 */
	@Override
	public AccountInfo getAccount(AccountType accountType, String cardNumber) {
		// TODO Auto-generated method stub
		return null;
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
	public String transfer(TransferType transferType, String sendingAccountNum, String receivingAccountNum, String a)
	{
		//converting from strings
		int sendingNum = stringToInt(sendingAccountNum);
		int receivingNum = stringToInt(receivingAccountNum);
		double amount = Double.valueOf(a);
		
		//setting Accounts
		Account sender = searchAccount(sendingNum);
		Account receiver = searchAccount(receivingNum);
		
		if(sender == null || receiver == null) { //if the receiver or sender does not exist
			return null;
		}
		
		switch (transferType)
		{
		case DEPOSIT:
			if(sender.getBalance() < amount) { //only if the sender has enough money
				if(receiver.getClass() == ChequingAccount.class || receiver.getClass() == SavingsAccount.class) { //if the receiver is a savings or chequing account
					double senderNewBalance = sender.getBalance() - amount;
					double receiverNewBalance = receiver.getBalance() + amount;
					sender.setBalance(senderNewBalance);
					receiver.setBalance(receiverNewBalance);
					return senderNewBalance + "";
				}
				if(receiver.getClass() == MortgageAccount.class) {
					double senderNewBalance = sender.getBalance() - amount;
					MortgageAccount MA = (MortgageAccount)receiver;
					MA.payMortgage(amount);
					return senderNewBalance + "";
				}
			}
			else {
				return null; //insufficient funds
			}
			
		case WITHDRAW:
			//remove money from account
			break;
		case TRANSFER:
			//transfer money to recipient email
		}
		
		//return the new balance after the transfer is complete
		String newBalance = a;
		return newBalance;
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

