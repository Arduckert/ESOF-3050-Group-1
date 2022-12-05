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
	 * the account holder's email address
	 */
	public String email;
	
	/**
	 * the account holder's account number
	 */
	public String accountNumber; //should be card number
	
	/**
	 * the account holder's pin number
	 */
	public String pin;
	
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
	public AccountHolderInfo(String _email, String _accountNumber, String _pin)
	{
		email = _email;
		accountNumber = _accountNumber;
		pin = _pin;
		hasInfo = true;
	}
	
	/**
	 * default constructor, sets all values to null
	 */
	public AccountHolderInfo()
	{
		email = null;
		accountNumber = null;
		pin = null;
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
