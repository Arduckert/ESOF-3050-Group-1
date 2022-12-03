package src.protocol;

/**
 * Server Action
 * @author Written by Connor McNally on 11/08/2022 (Last Updated 11/29/2022)
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
	 * an email as a parameter. The server returns SUCCESS along with the
	 * account number, email, and person's name if the account is found. The server
	 * returns FAIL with no information if the account was not found.
	 */
	FIND_ACCOUNTHOLDER_BY_EMAIL,
	
	/**
	 * This tells the server to find an existing person on the server. This takes a
	 * person's first name and last name as parameters. The server returns SUCCESS
	 * along with the person's first name, last name, social insurance number, and
	 * date of birth if the person is found.
	 */
	FIND_PERSON_BY_NAME,
	
	/**
	 * This tells the server to find an existing person on the server. This takes a
	 * person's social insurance number as a parameter. The server returns SUCCESS
	 * along with the person's first name, last name, social insurance number, and
	 * date of birth if the person is found.
	 */
	FIND_PERSON_BY_SIN,
	
	/**
	 * This tells the server to find an existing teller on the server. This takes the
	 * teller's employee number as a parameter. The server returns SUCCESS
	 * teller's employee number and password if the teller is found.
	 */
	FIND_TELLER_BY_NUMBER,
	
	/**
	 * This tells the server to add money to an account. This takes the account number's
	 * card number, account holder's pin, the account number of the account to deposit to,
	 * and the amount to deposit to that account as parameters. The server returns SUCCESS
	 * and the new balance if the deposit goes through.
	 */
	DEPOSIT,
	
	/**
	 * This tells the server to remove money from an account. This takes the account number's
	 * card number, account holder's pin, the account number of the account to withdraw from,
	 * and the amount to withdraw from that account as parameters. The server returns SUCCESS
	 * and the new balance if the withdrawal goes through.
	 */
	WITHDRAW,
	
	/**
	 * The tells the server to transfer money from one account to another. This takes the recipient's
	 * email address, the account holder's account number, the account holder's pin number, the account
	 * number of the account to transfer from, and the amount to transfer. The server returns SUCCESS
	 * and the new balance if the transfer goes through.
	 * to transfer 
	 */
	TRANSFER,
	
	/**
	 * This tells the server to create a new account holder. This takes an email address, and the person's
	 * social insurance number (the one who will own the account). The server returns SUCCESS and all of the
	 * data relating to the account holder (see Datatype.ACCOUNT_HOLDER)
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
