package program.server;
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
	
	
	
	
	public static void main(String args[])
	{
		System.out.println("testing the server...");
	}
	
}

