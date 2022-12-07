package src.program.structs;

/**
 * 
 * @author Connor, Created 12/07/2022
 * Denotes the type of input when searching for an account holder
 *
 */
public enum InputType
{
	/**
	 * search by the account holder's email address
	 */
	EMAIL,
	
	/**
	 * search by the account holder's card number
	 */
	CARD_NUMBER,
	
	/**
	 * search by the owner's social insurance number
	 */
	SIN
}
