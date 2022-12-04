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
	 * an email as a parameter. The server returns SUCCESS along with the
	 * account number, email, and person's name if the account is found. The server
	 * returns FAIL with no information if the account was not found.
	 */
	FIND_ACCOUNTHOLDER_BY_EMAIL,
	
	/**
	 * TODO
	 * This tells the server to find an existing person on the server. This takes a
	 * person's social insurance number as a parameter. The server returns SUCCESS
	 * along with the person's first name, last name, social insurance number, and
	 * date of birth if the person is found.
	 */
	FIND_PERSON_BY_SIN,
	
	/**
	 * TODO
	 * This tells the server to add money to an account. This takes the account number's
	 * card number, account holder's pin, the account number of the account to deposit to,
	 * and the amount to deposit to that account as parameters. The server returns SUCCESS
	 * and the new balance if the deposit goes through.
	 */
	DEPOSIT,
	
	/**
	 * TODO
	 * This tells the server to remove money from an account. This takes the account number's
	 * card number, account holder's pin, the account number of the account to withdraw from,
	 * and the amount to withdraw from that account as parameters. The server returns SUCCESS
	 * and the new balance if the withdrawal goes through.
	 */
	WITHDRAW,
	
	/**
	 * TODO
	 * The tells the server to transfer money from one account to another. This takes the recipient's
	 * email address, the account holder's account number, the account holder's pin number, the account
	 * number of the account to transfer from, and the amount to transfer. The server returns SUCCESS
	 * and the new balance if the transfer goes through.
	 * to transfer 
	 */
	TRANSFER,
	
	/**
	 * This tells the server to create a new account holder. This takes an email address, and a pin
	 * The server returns SUCCESS and all of the data relating to the account holder
	 * (see Datatype.ACCOUNT_HOLDER)
	 */
	CREATE_ACCOUNTHOLDER,
	
	/**
	 * TODO
	 * This tells the server to delete an existing account holder from the server. This takes an
	 * card number, and a pin number. The server will return SUCCESS if the account holder was
	 * successfully deleted.
	 */
	DELETE_ACCOUNTHOLDER,
		
	/**
	 * TODO
	 */
	CREATE_ACCOUNT,
	
	/**
	 * TODO
	 */
	DELETE_ACCOUNT,
	
	/**
	 * TODO
	 */
	CREATE_TELLER,
	
	/**
	 * TODO
	 */
	DELETE_TELLER,
	
	/**
	 * This tells the server to create a new person. This takes a first name, a last name, a social
	 * security number, and a date of birth. The server will return SUCCESS if the person was created
	 * and FAIL if not.
	 */
	CREATE_PERSON,
	
	/**
	 * This tells the server to delete an existing person. This takes a social security number.
	 * The server will return SUCCESS if the person was deleted and FAIL if not.
	 */
	DELETE_PERSON,
		
	/**
	 * TODO
	 * This tells the server to associate an account holder with a person. This takes a social security
	 * number and an email address 
	 */
	ADD_ACCOUNTHOLDER_ROLE_TO_PERSON,
	
	/**
	 * TODO
	 */
	ADD_TELLER_ROLE_TO_PERSON,
	
	/**
	 * TODO
	 */
	CREATE_BILL,
	
	/**
	 * TODO
	 */
	PAY_BILL,
	
	/**
	 * TODO
	 */
	DELETE_BILL,
	
	/**
	 * TODO
	 */
	GET_ACCOUNT_TRANSACTIONS,
	
	/**
	 * TODO
	 */
	GET_ACCOUNT_RECORDS,
	
	/**
	 * TODO
	 */
	GET_CUSTOMER_RECORDS,
	
	/**
	 * TODO
	 */
	GET_LOGIN_ATTEMPTS_FOR_ACCOUNTHOLDER,
	
	/**
	 * TODO
	 */
	ADD_ADDRESS_TO_PERSON,
	
	/**
	 * TODO
	 */
	REMOVE_ADDRESS_FROM_PERSON,
	
	/**
	 * This is used as a way to test the client-server communication. This takes one
	 * parameter that can be any string message. The server returns SUCCESS if the
	 * message was received.
	 */
	TEST
}
