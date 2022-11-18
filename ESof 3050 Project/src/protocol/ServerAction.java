package src.protocol;

/**
 * Server Action
 * @author Written by Connor McNally on 11/08/2022, updated on 11/17/2022
 * 
 * @apiNote The Server Action enum contains the actions that the server can perform on
 * behalf on the client. For proper usage and what data to send, see proper usage for
 * each type below.
 */
public enum ServerAction
{
	/**
	 * This tells the server that an account holder wants to login to
	 * their account. This takes two parameters, an account number, and a pin.
	 * The server returns SUCEESS if the credentials are correct, FAIL if
	 * the credentials do not match any account on the server.
	 */
	LOGIN_ACCOUNTHOLDER,
	
	/**
	 * This tells the server that a teller wants to login to the system. This
	 * takes two parameters, an employee number, and a password.
	 * The server returns SUCEESS if the credentials are correct, FAIL if
	 * the credentials do not match any account on the server.
	 */
	LOGIN_TELLER,
	
	/**
	 * This tells the server to find an existing account on the server. This takes
	 * the account number as a parameter. The server returns SUCCESS along with the
	 * account number, email, and date creation. The server returns FAIL with no
	 * information if the account was not found.
	 */
	FIND_ACCOUNTHOLDER_BY_NUMBER,
	
	/**
	 * This tells the server to find an existing account on the server. This takes
	 * an email as a parameter. The server returns SUCESS along with the
	 * account number, email, and person's name if the account is found. The server
	 * returns FAIL with no information if the account was not found.
	 */
	FIND_ACCOUNTHOLDER_BY_EMAIL,
	
	/**
	 * 
	 */
	FIND_PERSON_BY_NAME,
	
	/**
	 * 
	 */
	FIND_PERSON_BY_SIN,
	
	/**
	 * 
	 */
	FIND_TELLER_BY_NUMEBR,
	
	/**
	 * 
	 */
	DEPOSIT,
	
	/**
	 * 
	 */
	WITHDRAW,
	
	/**
	 * 
	 */
	TRANSFER,
	
	/**
	 * 
	 */
	CREATE_ACCOUNTHOLDER,
	
	/**
	 * 
	 */
	DELETE_ACCOUNTHOLDER,
		
	/**
	 * 
	 */
	CREATE_ACCOUNT,
	
	/**
	 * 
	 */
	DELETE_ACCOUNT,
	
	/**
	 * 
	 */
	CREATE_TELLER,
	
	/**
	 * 
	 */
	DELETE_TELLER,
	
	/**
	 * 
	 */
	CREATE_PERSON,
	
	/**
	 * 
	 */
	DELETE_PERSON,
		
	/**
	 * 
	 */
	ADD_ACCOUNTHOLDER_ROLE_TO_PERSON,
	
	/**
	 * 
	 */
	ADD_TELLER_ROLE_TO_PERSON,
	
	/**
	 * 
	 */
	CREATE_BILL,
	
	/**
	 * 
	 */
	PAY_BILL,
	
	/**
	 * 
	 */
	DELETE_BILL,
	
	/**
	 * 
	 */
	GET_ACCOUNT_TRANSACTIONS,
	
	/**
	 * 
	 */
	GET_ACCOUNT_INFORMATION,
	
	/**
	 * 
	 */
	GET_ACCOUNTHOLDER_INFORMATION,
	
	/**
	 * 
	 */
	GET_ACCOUNT_RECORDS,
	
	/**
	 * 
	 */
	GET_CUSTOMER_RECORDS,
	
	/**
	 * 
	 */
	GET_LOGIN_ATTEMPTS_FOR_ACCOUNTHOLDER,
	
	/**
	 * 
	 */
	ADD_ADDRESS_TO_PERSON,
	
	/**
	 * 
	 */
	REMOVE_ADDRESS_FROM_PERSON,
	
	/**
	 * This is used as a way to test the client-server communication. This takes one
	 * parameter that can be any string message. The server returns SUCCESS if the
	 * message was received.
	 */
	TEST
}
