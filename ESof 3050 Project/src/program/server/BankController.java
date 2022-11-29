package src.program.server;
import java.util.*;

// *** TODO: Implement Interface *** //
public class BankController implements IBankController
{
	
	List<Person> personList = new ArrayList<Person>();
	List<Record> recordList = new ArrayList<Record>(); //potential problem with Record name
	List<Account> accountList = new ArrayList<Account>();
	List<AccountHolder> accountHolderList = new ArrayList<AccountHolder>();
	double generalInterestRate;
	
	public BankController() {
		
	}
	//setter
	public void setGeneralInterestRate(double x) {
		this.generalInterestRate = x;
	}
	public void addAccountHolder(AccountHolder x) {
		this.accountHolderList.add(x);
	}
	//getter
	public double getGeneralInterestRate() {
		return this.generalInterestRate;
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
	
}

