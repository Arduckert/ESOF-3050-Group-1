package src.program.structs;

/**
 * @author Connor, Created on December 5th, 2022
 *
 * Holds information about an account that the client can use to
 * display information about a specific account
 */
public class AccountInfo
{
	/**
	 * the account's account type
	 */
	public AccountType accountType;
	
	/**
	 * the account's balance
	 */
	public String balance;
	
	/**
	 * The date the account was opened
	 */
	public String dateOpened;
	
	/**
	 * the account's number
	 */
	public String accountNumber;
	
	/**
	 * boolean that determines if the instance is populated with information
	 */
	private boolean hasInfo;
	
	/**
	 * constructor that takes arguments for each instance variable
	 * @param _accountType
	 * @param _balance
	 * @param _dateOpened
	 * @param _accountNumber
	 */
	public AccountInfo(AccountType _accountType, String _balance, String _dateOpened, String _accountNumber)
	{
		accountType = _accountType;
		balance = _balance;
		dateOpened = _dateOpened;
		accountNumber = _accountNumber;
		hasInfo = true;
	}
	
	/**
	 * default constructor
	 */
	public AccountInfo()
	{
		accountType = null;
		balance = null;
		dateOpened = null;
		accountNumber = null;
		hasInfo = false;
	}
}
