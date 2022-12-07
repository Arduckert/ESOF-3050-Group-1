package src.protocol;

/**
 * Server Action
 * @author Written by Connor McNally on 11/08/2022 (Last Updated 12/07/2022)
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
	
	/*
	 * The tells the server to transfer money from one account to another. This takes the transfer type,
	 * the recipient's email address, the account type (cheqing, savings, etc.), and the amount to transfer.
	 * The server returns SUCCESS and the new balance if the transfer goes through. 
	 */
	TRANSFER,
	
	/**
	 * The tells the server to pay a bill. This takes the recipient, and the amount that needs to be paid,
	 * and an account number. The server returns SUCCESS and the new balance if the payment goes through. 
	 */
	MANAGE_BILL,
	
	/**
	 * This tells the server to create a new account holder. This takes an email address, and a pin
	 * The server returns SUCCESS and all of the data relating to the account holder
	 * (see Datatype.ACCOUNT_HOLDER)
	 */
	CREATE_ACCOUNTHOLDER,
	
	/**
	 * This tells the server to delete an existing account holder from the server. This takes an
	 * card number, and a pin number. The server will return SUCCESS if the account holder was
	 * successfully deleted.
	 */
	DELETE_ACCOUNTHOLDER,
		
	/**
	 * This tells a server to create a new account on the server. This takes an account type
	 * (chequing, savings, mortgage, or line of credit) and the card number of the account
	 * holder to add the account to. The server will return SUCCESS if the account is created
	 * successfully, fail if not.
	 */
	CREATE_ACCOUNT,
	
	/**
	 * This tells a server to delete an existing account on the server. This takes an account type
	 * (chequing, savings, mortgage, or line of credit) and the card number of the account
	 * holder to remove the account from. The server will return SUCCESS if the account is created
	 * successfully, fail if not.
	 */
	DELETE_ACCOUNT,
	
	/**
	 * This tells a server to get information from an existing account on the server. This takes an
	 * account type (chequing, savings, mortgage, or line of credit). The server will return SUCCESS
	 * and the account information, fail and nothing if not.
	 */
	GET_ACCOUNTS,
	
	/**
	 * This tells a server to setup the information about a mortgage account. This takes an account number,
	 * a mortgage length, an interest rate, and a principle amount. The server will return SUCCESS
	 * if the information was added successfully.
	 */
	SETUP_MORTGAGE_ACCOUNT,
	
	/**
	 * This tells a server to setup the information about a line of credit account. This takes an account number,
	 * a credit limit and an interest rate. The server will return SUCCESS
	 * if the information was added successfully.
	 */
	SETUP_LINE_OF_CREDIT_ACCOUNT,
	
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
	 * This tells the server to associate an account holder with a person. This takes a social security
	 * number and an email address 
	 */
	ADD_ACCOUNTHOLDER_ROLE_TO_PERSON,
	
	/**
	 * This tells the server to return the transaction history of a specific account. This takes
	 * an account type (cheqing, savings, etc.). The server will return SUCCESS and a list of transactions
	 * back to the client, fail and nothing if not.
	 */
	GET_ACCOUNT_TRANSACTIONS,
	
	/**
	 * This tells the server to return the account records on the server. This takes no information.
	 * The server will return SUCCESS and a list of account records back to the client, fail and nothing
	 * if not.
	 */
	GET_ACCOUNT_RECORDS,
	
	/**
	 * This tells the server to return the customer records on the server. This takes no information.
	 * The server will return SUCCESS and a list of customer records back to the client, fail and nothing
	 * if not.
	 */
	GET_CUSTOMER_RECORDS,
	
	/**
	 * This tells the server to add an address to a person. This takes all of the
	 * parameters of an address (see Address.java in src.program.server) along with
	 * the person's social insurance number.
	 */
	ADD_ADDRESS_TO_PERSON,
	
	/**
	 * This tells the server to remove an address to a person. This takes
	 * the person's social insurance number and the postal code of the address.
	 */
	REMOVE_ADDRESS_FROM_PERSON,
	
	/**
	 * This is used as a way to test the client-server communication. This takes one
	 * parameter that can be any string message. The server returns SUCCESS if the
	 * message was received.
	 */
	TEST
}
