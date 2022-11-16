package src.protocol;

/**
 * Data Type
 * @author Written by Connor McNally on 11/08/2022
 * 
 * @apiNote The Datatype enum contains the different types of data the server can
 * send to the client. This can vary from just sending a test message to sending
 * the transaction history of an account. BE CAREFUL! Please ensure that for the
 * specific data type you're using, it needs to have the correct parameters otherwise
 * some data corruption could occur. Find the type you want to use below and follow
 * the instructions given for what data to send and what object size you need for
 * proper use.
 */
public enum Datatype
{
	/**
	 * 
	 */
	PERSON,
	
	/**
	 * 
	 */
	TELLER,
	
	/**
	 * 
	 */
	ACCOUNT_HOLDER,
	
	/**
	 * 
	 */
	ACCOUNT,
	
	/**
	 * 
	 */
	CHEQUING_ACCOUNT,
	
	/**
	 * 
	 */
	SAVINGS_ACCOUNT,
	
	/**
	 * 
	 */
	MORTGAGE_ACCOUNT,
	
	/**
	 * 
	 */
	LINE_OF_CREDIT_ACCOUNT,
	
	/**
	 * 
	 */
	TRANSACTION,
	
	/**
	 * 
	 */
	ADDRESS,
	
	/**
	 * 
	 */
	BILL,
	
	/**
	 * 
	 */
	CUSTOMER_RECORD,
	
	/**
	 * 
	 */
	ACCOUNT_RECORD,
	
	/**
	 * 
	 */
	LOGIN_ATTEMPT,
	
	/**
	 * This sends a string message back to the client. This is used as a way to test
	 * the client-server communication and can also be used to send a message back to
	 * the client if the message has a FAIL status.
	 * @object_size 1
	 */
	BASIC_MESSAGE,
	
	/**
	 * No data is passed.
	 * @object_size 0
	 */
	NONE
}
