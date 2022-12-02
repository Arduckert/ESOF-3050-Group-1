package src.program.structs;

/**
 * @author Connor, Created on December 1st, 2022
 *
 * Holds information about an account holder that the client can use to
 * display information about a specific account holder
 */
public class AccountHolderInfo
{
	/**
	 * The full name of the account holder (first and last name)
	 */
	public String accountHolderName;
	
	/**
	 * the account holder's email address
	 */
	public String email;
	
	/**
	 * the account holder's account number
	 */
	public String accountNumber;
	
	/**
	 * this boolean is true if the values have information, false if all the values are null
	 */
	private boolean hasInfo;
	
	/**
	 * constructor for AccountHolderInfo that takes three arguments
	 * @param _accountHolderName
	 * @param _email
	 * @param _accountNumber
	 */
	public AccountHolderInfo(String _accountHolderName, String _email, String _accountNumber)
	{
		accountHolderName = _accountHolderName;
		email = _email;
		accountNumber = _accountNumber;
		hasInfo = true;
	}
	
	/**
	 * default constructor, sets all values to null
	 */
	public AccountHolderInfo()
	{
		accountHolderName = null;
		email = null;
		accountNumber = null;
		hasInfo = false;
	}
	
	/**
	 * returns the hasInfo boolean
	 * @return
	 */
	public boolean getHasInfo()
	{
		return hasInfo;
	}
}
