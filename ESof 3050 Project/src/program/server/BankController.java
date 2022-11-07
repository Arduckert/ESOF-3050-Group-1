package program.server;
import java.util.*;

public class BankController
{
	List<Person> personList = new ArrayList<Person>();
	List<Record> recordList = new ArrayList<Record>(); //potential problem with Record name
	List<Account> accountList = new ArrayList<Account>();
	double generalInterestRate;
	
	public void setGeneralInterestRate(double x) {
		this.generalInterestRate = x;
	}
	public double getGeneralInterestRate() {
		return this.generalInterestRate;
	}
	public static void main(String args[])
	{
		System.out.println("testing the server...");
	}
	
}

