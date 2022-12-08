//--------------------------------------------------------------
//Brief description of this file:
//
//Related Documents:
//		Specifications Document
//		Design Document
//
//File created by Aric Duckert
//File approved by Connor McNally and Mathew Camire
//
//List of Important variable names and their brief description:
//
//----------------------------------------------------------------

package src.program.server;
import java.util.*;
import java.lang.Math;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import src.program.structs.AccountHolderInfo;
import src.program.structs.AccountInfo;
import src.program.structs.AccountType;
import src.program.structs.BillAction;
import src.program.structs.InputType;
import src.program.structs.RecordInfo;
import src.program.structs.TransactionInfo;
import src.program.structs.TransferType;

// *** TODO: IMPLEMENT INTERFACE METHODS (REFER TO BLUE MARKS ON THE SCROLL BAR) *** //
public class BankController implements IBankController, Serializable
{
	
	List<Person> personList = new ArrayList<Person>();
	List<Record> recordList = new ArrayList<Record>(); //potential problem with Record name
	List<Account> accountList = new ArrayList<Account>();
	List<AccountHolder> accountHolderList = new ArrayList<AccountHolder>();
	List<Teller> tellerList = new ArrayList<Teller>();
	double generalInterestRate = 0.01;
	
	public BankController() {
		
	}
	//SAVE
	public static void save(BankController z) {
		try {
			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("bank.dat"));
			output.writeObject(z);
			output.close();
		} catch (FileNotFoundException e) {
			System.out.println("file not found");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//LOAD
	public static BankController load() {
		BankController b = null;
		try {
			ObjectInputStream input = new ObjectInputStream(new FileInputStream("bank.dat"));
			try {
				b = (BankController) input.readObject();
			} catch (ClassNotFoundException e) {
				System.out.println("Class not found");
			}
		} catch (FileNotFoundException e) {
			System.out.println("file not found");
		} catch (IOException e) {
			System.out.println("IO exception 2");
		}
		
		return b;
	}
	
	//getter
	public List<Account> getAccountList(){
		
		return this.accountList;
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
	
	public List<Person> getPersonList(){
		return this.personList;
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
	public AccountHolderInfo findAccountHolder(InputType inputType, String parameter)
	{
		AccountHolderInfo info = null;
		AccountHolder holder = null;
		
		switch(inputType) {
		case EMAIL:
			for(int i=0; i < accountHolderList.size(); i++) {  //check all account holders
				if(accountHolderList.get(i).getEmail().equals(parameter)) {
					holder = accountHolderList.get(i);
				}
			}
		case CARD_NUMBER:
			int cardNum = stringToInt(parameter);
			for(int i=0; i < accountHolderList.size(); i++) {  //check all account holders
				if(accountHolderList.get(i).getCardNum() == cardNum) {
					holder = accountHolderList.get(i);
				}	
			}
		case SIN:
			int sin = stringToInt(parameter);
			for(int i=0; i < accountHolderList.size(); i++) {  //check all account holders
				if(accountHolderList.get(i).getPerson().getSIN() == sin) {
					holder = accountHolderList.get(i);
				}	
			}
		}
			
		if(holder == null) {
			return null; // holder not found
		}
		
		String email = holder.getEmail();
		String card = holder.getCardNum() + "";
		String pin = holder.getPin() + "";
		String name = String.format("%s %s", holder.getPerson().getFName(), holder.getPerson().getLName());
		String sNum = holder.getPerson().getSIN() + "";
		
		info = new AccountHolderInfo(email, card, pin, name, sNum);
		return info;
				
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
			
			accountHolder.getPerson().addPersonRole(accountHolder);
			
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
	public boolean deleteAccountHolder(String cardNumber, String tellerEmpID)
	{
		int num = stringToInt(cardNumber);
		int id = stringToInt(tellerEmpID);
		Teller t = searchTeller(id);
		AccountHolder a = searchAccountHolder(num);
		if(a == null) {
			return false; //couldnt find account holder
		}
		if(a.getAccounts().isEmpty()) { //if the accountHolder has no open accounts
			a.getPerson().removeRole(a);
			accountHolderList.remove(a);
			
			CustomerRecord customerRecord = new CustomerRecord(t, "removed", a);
			recordList.add(customerRecord);
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
	 * 
	 */
	
	@Override
	public String createAccount(AccountType accountType, String cardNumber, String tellerID)//savings or chequing
	{
		int cardNum = stringToInt(cardNumber);
		int id = stringToInt(tellerID);
		Teller teller = searchTeller(id);
		AccountHolder a = searchAccountHolder(cardNum);
		int accountNum = generateAccountNumber();
		
		if(teller == null) {
			return null; //teller could not be found
		}
		if(a == null) {
			return null; //could not find account holder with that card number
		}
		switch(accountType) {
		case CHEQUING:
			ChequingAccount cAccount = new ChequingAccount(accountNum,a);
			accountList.add(cAccount);
			
			a.addAccount(cAccount);
			
			AccountRecord cRecord = new AccountRecord(teller,"created",cAccount);
			recordList.add(cRecord);
			
			
			return cAccount.getAccountNum()+"";
		
		case SAVINGS:
			SavingsAccount sAccount = new SavingsAccount(accountNum,a,generalInterestRate);
			accountList.add(sAccount);
			
			a.addAccount(sAccount);
			
			AccountRecord sRecord = new AccountRecord(teller,"created",sAccount);
			recordList.add(sRecord);
			
			return sAccount.getAccountNum()+"";
			
		default:
			break;
		}
		
		return null;
	}
	
	/**
	 * Deletes an account and removes it from an account holder
	 * @param accountType
	 * @param cardNumber
	 * @return true if the account was deleted successfully, false if not
	 */
	@Override
	public boolean deleteAccount(String accountNumber, String tellerID)
	{
		int num = stringToInt(accountNumber);
		int id = stringToInt(tellerID);
		Teller teller = searchTeller(id);
		Account account = searchAccount(num);
		
		if(teller == null) {
			return false;//teller not found
		}
		if(account == null) {
			return false; //account holder not found
		}
		if(account.getBalance() > 0) {
			return false; //money is in account
		}
		else {
			account.getAccountHolder().getAccounts().remove(account);
			accountList.remove(account);
			
			AccountRecord record = new AccountRecord(teller,"removed",account);
			recordList.add(record);
			return true;

		}
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
	public String transfer(TransferType transferType, String sendingAccountNum, String receivingAccountNum, String a) //make transaction record
	{
		//converting from strings
		int sendingNum = stringToInt(sendingAccountNum);
		int receivingNum = stringToInt(receivingAccountNum);
		double amount = Double.valueOf(a);
		
		//setting Accounts
		Account sender = searchAccount(sendingNum);
		Account receiver = searchAccount(receivingNum);
		
		
		
		if(amount < 0) {
			return null;
		}
		switch (transferType)
		{
		case TRANSFER:
			if(sender == null || receiver == null) //if the receiver or sender does not exist
				return null;
			if(sender.getBalance() >= amount) { //only if the sender has enough money
				if(receiver.getClass() == ChequingAccount.class || receiver.getClass() == SavingsAccount.class) { //if the receiver is a savings or chequing account
					double senderNewBalance = sender.getBalance() - amount;
					double receiverNewBalance = receiver.getBalance() + amount;
					
					sender.setBalance(senderNewBalance); //setting the values of the actual account
					receiver.setBalance(receiverNewBalance);
					TransactionRecord record = new TransactionRecord(sender, receiver,"transfer",amount); //creates a new transaction record
					sender.addTransaction(record);
					receiver.addTransaction(record);
			
					return senderNewBalance + "";
				}
				if(receiver.getClass() == MortgageAccount.class) {
					double senderNewBalance = sender.getBalance() - amount;
					MortgageAccount MA = (MortgageAccount)receiver;
					MA.payMortgage(amount);
					TransactionRecord record = new TransactionRecord(sender,receiver,"transfer",amount); //creates a new transaction record
					sender.addTransaction(record);
					receiver.addTransaction(record);
					
					return senderNewBalance + "";
				}
				if(receiver.getClass() == LineOfCreditAccount.class) {
					double senderNewBalance = sender.getBalance() - amount;
					LineOfCreditAccount LA = (LineOfCreditAccount)receiver;
					LA.pay(amount);
					
					TransactionRecord record = new TransactionRecord(sender,receiver,"transfer",amount); //creates a new transaction record
					sender.addTransaction(record);
					receiver.addTransaction(record);
					
					return senderNewBalance + "";
				}
			}
			else {
				return null; //insufficient funds
			}
			
			
		case WITHDRAW:
			if(sender == null) //if the receiver or sender does not exist
				return null;
			if(sender.getBalance() >= amount) { //sufficient funds
				double newBalance = sender.getBalance() - amount;
				sender.setBalance(newBalance);
				
				TransactionRecord record = new TransactionRecord(sender,null,"withdraw",amount); //creates a new transaction record
				sender.addTransaction(record);
				
				return newBalance + "";
			}
			else {
				return null;
			}
		case DEPOSIT:
			if(receiver == null) //if the receiver or sender does not exist
				return null;
			double newBalance = receiver.getBalance() + amount;
			receiver.setBalance(newBalance);
			
			TransactionRecord record = new TransactionRecord(null,receiver,"deposit",amount); //creates a new transaction record
			receiver.addTransaction(record);
			
			return newBalance + "";
		}
		
		return null;
	}
	
	/**
	 * Returns information about a specific account from a specific account holder
	 * @param accountType type of account (chequing, savings, etc.)
	 * @param cardNumber the account holder's card number
	 * @return
	 */
	@Override
	public ArrayList<AccountInfo> getAccounts(String cardNumber) {
		int num = stringToInt(cardNumber);
		AccountHolder accountHolder = searchAccountHolder(num);
		ArrayList<AccountInfo> infoList = new ArrayList<AccountInfo>();
		AccountType type = null;
		
		if(accountHolder == null) {
			return null; //account holder does not exists
		}
		for(int i = 0; i < accountHolder.getAccounts().size(); i++) { //iterate through all of the holders accounts
			if(accountHolder.getAccounts().get(i).getClass() == ChequingAccount.class) {
				type = AccountType.CHEQUING;
			}
			if(accountHolder.getAccounts().get(i).getClass() == SavingsAccount.class) {
				type = AccountType.SAVINGS;
			}
			if(accountHolder.getAccounts().get(i).getClass() == MortgageAccount.class) {
				type = AccountType.MORTGAGE;
			}
			if(accountHolder.getAccounts().get(i).getClass() == LineOfCreditAccount.class) {
				type = AccountType.LINE_OF_CREDIT;
			}
			AccountInfo info = new AccountInfo(type,accountHolder.getAccounts().get(i).getBalance() + "",accountHolder.getAccounts().get(i).getAccountNum() + "");
			infoList.add(info);
		}
		return infoList;
	}
	
	@Override
	public ArrayList<TransactionInfo> getTransactionHistory(String accountNumber) {
		int num = stringToInt(accountNumber);
		Account account = searchAccount(num);
		ArrayList<TransactionInfo> infoList = new ArrayList<TransactionInfo>(); //transactionInfo(date,rec,type,amount)
		
		if(account == null) {
			return null; //account not found
		}
		
		for(int i=0; i<account.getRecords().size(); i++) {
			String date = account.getRecords().get(i).getDate();
			String recepient = account.getRecords().get(i).getRecepient();
			String sender = account.getRecords().get(i).getSender();
			String type = account.getRecords().get(i).getType();
			String amount = account.getRecords().get(i).getAmount();
			TransactionInfo info = new TransactionInfo(date, recepient, sender, type, amount);
			infoList.add(info);
		}
		return infoList;
	}

	@Override
	public ArrayList<RecordInfo> getAccountRecords() {
		ArrayList<RecordInfo> infoList = null;
		String date;
		String teller;
		String num;
		String type = null;
		
		for(int i=0; i<recordList.size(); i++) {//iterate through recordList
			if(recordList.get(i).getClass() == AccountRecord.class) {
				date = recordList.get(i).getRecordDate(); //String of account date
				teller = recordList.get(i).getTeller().getEmpNum() + ""; //string of teller ID
				if(recordList.get(i).getAction().equals("created")){
					type = "account creation";
				}
				if(recordList.get(i).getAction().equals("removed")){
					type = "account deletion";
				}
				AccountRecord accountRecord = (AccountRecord)recordList.get(i);
				num = accountRecord.getAccount().getAccountNum() + ""; //String of Account number
				
				RecordInfo info = new RecordInfo(date,teller,num,type);
				infoList.add(info);		
			}	
		}
		return infoList;
	}

	@Override
	public ArrayList<RecordInfo> getCustomerRecords() {
		ArrayList<RecordInfo> infoList = null;
		String date;
		String teller;
		String num;
		String type = null;
		
		for(int i=0; i<recordList.size(); i++) {//iterate through recordList
			if(recordList.get(i).getClass() == CustomerRecord.class) {
				date = recordList.get(i).getRecordDate(); //String of account date
				teller = recordList.get(i).getTeller().getEmpNum() + ""; //string of teller ID
				if(recordList.get(i).getAction().equals("created")){
					type = "customer creation";
				}
				if(recordList.get(i).getAction().equals("removed")){
					type = "customer deletion";
				}
				CustomerRecord customerRecord = (CustomerRecord)recordList.get(i);
				num = customerRecord.getAccountHolder().getCardNum() + ""; //String of Card number
				
				RecordInfo info = new RecordInfo(date,teller,num,type);
				infoList.add(info);		
			}	
		}
		return infoList;
		
	}
	
	@Override
	public boolean manageBill(BillAction billAction, String locAccountNumber, String a, String receiver) {
		int accountNum = stringToInt(locAccountNumber);
		double amount = Double.valueOf(a);
		
		LineOfCreditAccount account = (LineOfCreditAccount)searchAccount(accountNum);
		
		if(account == null) {
			return false; //account not found
		}
		switch(billAction) {
		case CREATE_BILL:
			MonthlyBill bill = new MonthlyBill(amount,receiver,account);
			account.addBill(bill);
			return true;
			
		case DELETE_BILL:
			MonthlyBill mBill = account.searchBill(receiver);
			if(mBill == null) {
				return false; //bill not found
			}
			account.removeBill(mBill);
			return true;
		}
		return false;
	}

	@Override
	public String setupMortgageAccount(String cardNumber, String mortgageLength, String interestRate, String principleAmount, String tellerID) {
		int cardNum = stringToInt(cardNumber);
		int mortgageLen = stringToInt(mortgageLength);
		double ir = Double.valueOf(interestRate);
		double principle = Double.valueOf(principleAmount);
		int id = stringToInt(tellerID);
		int accountNumber = generateAccountNumber();
		
		AccountHolder accountHolder = searchAccountHolder(cardNum);
		Teller teller = searchTeller(id);
		
		if(accountHolder == null) {
			return null; //account holder not found
		}
		if(teller == null) {
			return null; //teller not found
		}
		MortgageAccount mAccount = new MortgageAccount(accountNumber,accountHolder,mortgageLen,principle,ir);
		accountList.add(mAccount);
		
		accountHolder.addAccount(mAccount);
		
		AccountRecord record = new AccountRecord(teller,"created",mAccount);
		recordList.add(record);
		
		
		return mAccount.getAccountNum()+"";
	}

	@Override
	public String setupLineOfCreditAccount(String cardNumber, String creditLimit, String interestRate, String tellerID) {
		
		int cardNum = stringToInt(cardNumber);
		double creditLim = Double.valueOf(creditLimit);
		double ir = Double.valueOf(interestRate);
		int id = stringToInt(tellerID);
		int accountNumber = generateAccountNumber();
		
		AccountHolder accountHolder = searchAccountHolder(cardNum);
		Teller teller = searchTeller(id);
		
		if(accountHolder == null) {
			return null; //not found
		}
		
		if(teller == null) {
			return null; //not found teller
		}
		
		LineOfCreditAccount account = new LineOfCreditAccount(accountNumber,accountHolder,ir,creditLim);
		accountList.add(account);
		
		accountHolder.addAccount(account);
		
		AccountRecord record = new AccountRecord(teller, "created", account);
		recordList.add(record);
		
		return account.getAccountNum() + "";
	}
	

	
	

	
	//MAIN
	public static void main(String args[])
	{
		BankController b;
		b = load();
		
		int port = 9950;
		BankingServer bs = new BankingServer(port, b);
		try {
			bs.listen();
			while(true) {
				Timer timer = new Timer(b);
				timer.run();
				System.out.println("2 Minutes have past: Mortgages and Credit accounts have been updated...");
				save(b);
				System.out.println("Bank Data Saved...");
				Thread.sleep(120000);
				
			}
		}
		catch (Exception ex) {
			System.err.println(ex);
		}
		
		System.out.println("testing the server...");
	}

	
}



/*
 * BankController b = new BankController();
		Person aric = new Person("Aric", "Duckert", 1111, "2000-05-18");
		Person matt = new Person("Matt", "Camire", 2222 , "2000-05-18");
		Person connor = new Person("Connor", "McNally", 3333 , "2000-05-18");
		
		Teller tellerAric = new Teller(1111,"aric",aric);
		Teller tellerMatt = new Teller(2222,"matt",matt);
		Teller tellerConnor = new Teller(3333,"connor",connor);
		
		b.addTeller(tellerConnor);
		b.addTeller(tellerMatt);
		b.addTeller(tellerAric);
		save(b);
 */

