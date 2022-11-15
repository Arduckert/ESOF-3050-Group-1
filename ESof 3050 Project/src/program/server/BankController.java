package src.program.server;
import java.util.*;

public class BankController
{
	
	List<Person> personList = new ArrayList<Person>();
	List<Record> recordList = new ArrayList<Record>(); //potential problem with Record name
	List<Account> accountList = new ArrayList<Account>();
	double generalInterestRate;
	
	//setter
	public void setGeneralInterestRate(double x) {
		this.generalInterestRate = x;
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
	
	//function for accepting from server
	
	
	public static void main(String args[])
	{
		int port = 9950;
		BankingServer bs = new BankingServer(port);
		try {
			bs.listen();
		}
		catch (Exception ex) {System.err.println(ex);}
		System.out.println("testing the server...");
		while(true) {
			//System.out.println(bs.getMessage());
			System.out.println(bs.isListening());
			try {
				Thread.sleep(1000);
			}
			catch (Exception ex) {System.err.println(ex);}
		}
	}
	
}

