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
	public AccountInfo(AccountType _accountType, String _balance, String _accountNumber)
	{
		accountType = _accountType;
		balance = _balance;
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
		accountNumber = null;
		hasInfo = false;
	}
	
	/**
	 * @return true if the instance has information, false if not
	 */
	public boolean getHasInfo()
	{
		return hasInfo;
	}
	
	public String getAccountTypeString() {
		if(accountType.equals(AccountType.CHEQUING))
			return "Chequing Account";
		else if(accountType.equals(AccountType.SAVINGS))
			return "Savings Account";
		else if(accountType.equals(AccountType.MORTGAGE))
				return "Mortgage Account";
		else if(accountType.equals(AccountType.LINE_OF_CREDIT))
			return "Line-Of-Credit";
		return null;
		
	}
	
	@Override
	public String toString() {
		return accountNumber;
	}
}
