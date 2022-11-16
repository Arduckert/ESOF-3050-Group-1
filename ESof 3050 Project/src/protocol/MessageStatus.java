package src.protocol; 

/**
 * Data Type
 * @author Written by Connor McNally on 11/08/2022
 * 
 * @apiNote The Message Status enum contains the different message statuses the server can
 * send to the client depending on the status of the action performed.
 */
public enum MessageStatus
{
	/**
	 * Action was performed successfully by the server. For instance, if the
	 * account number and pin match an account, the server will send a SUCCESS status.
	 */
	SUCCESS,
	
	/**
	 * Action failed to perform successfully. For instance, entering the wrong pin
	 * for an account will make the server send a FAIL status. 
	 */
	FAIL
}