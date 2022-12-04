package src.protocol;

/**
 * @author Connor, Created 12/03/2022
 *
 * @apiinfo denotes the login type defined in the connection to client. This is used
 * by the Server to determine the login type 
 */
public enum LoginType
{
	/**
	 * The client logged in is an account holder.
	 */
	ACCOUNTHOLDER,
	
	/**
	 * The client logged in is a teller
	 */
	TELLER
}
