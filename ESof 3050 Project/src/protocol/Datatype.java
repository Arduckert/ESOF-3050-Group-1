package src.protocol;

/**
 * Data Type
 * @author Written by Connor McNally on 11/08/2022 (Last Modified 11/29/2022)
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
	 * This sends information about a person back to the client. The information included
	 * in this data type is the person's first name, last name, date of birth, and social
	 * insurance number.
	 * @object_size 4
	 */
	PERSON,
	
	/**
	 * This sends information about a teller back to the client. The information included
	 * in this data type is the teller's employee number, and password.
	 * @object_size 2
	 */
	TELLER,
	
	/**
	 * This sends information about an account holder back to the client. The information
	 * included in this data type is the card number, pin number, and email
	 * address.
	 * @object_size 3
	 */
	ACCOUNT_HOLDER,
	
	/**
	 * This sends information about an account back to the client. The information
	 * included in this data type is the account type (cheqing, savings, etc.), 
	 * account number, balance, and date opened.
	 * @object_size 4
	 */
	ACCOUNT,
	
	/**
	 * This sends a balance of a specific account back to the client. The information
	 * included in this data type is a balance.
	 * @object_size 1
	 */
	BALANCE,
	
	/**
	 * This sends information about a chequing account back to the client. The information
	 * included in this data type is the account number, and balance.
	 * @object_size 2
	 */
	CHEQUING_ACCOUNT,
	
	/**
	 * This sends information about a savings account back to the client. The information
	 * included in this data type is the account number, balance, and interest rate.
	 * @object_size 3
	 */
	SAVINGS_ACCOUNT,
	
	/**
	 * This sends information about a mortgage account back to the client. The information
	 * included in this data type is the account number, balance, principle amount, and
	 * interest amount.
	 * @object_size 4
	 */
	MORTGAGE_ACCOUNT,
	
	/**
	 * This sends information about a LOC account back to the client. The information
	 * included in this data type is the account number, balance, interest rate, and credit
	 * limit.
	 * @object_size 4
	 */
	LINE_OF_CREDIT_ACCOUNT,
	
	/**
	 * This sends information about a transaction back to the client. The information
	 * included in this data type is the recipiant, date, transaction type, and amount
	 * @object_size 4
	 */
	TRANSACTION,
	
	/**
	 * This sends information about an address back to the client. The information
	 * included in this data type is the full address name (streetNumber streetName),
	 * postal code, provice, and country
	 * @object_size 4
	 */
	ADDRESS,
	
	/**
	 * This sends information about a bill back to the client. The information
	 * included in this data type is the receiving party, the account number
	 * of the line of credit account that pays the bill, and the amount
	 * @object_size 3
	 */
	BILL,
	
	/**
	 * This sends information about a customer record back to the client.
	 * The information included in this data type is the teller name, the
	 * card number of the account holder, the record date, and record type
	 * @object_size 4
	 */
	CUSTOMER_RECORD,
	
	/**
	 * This sends information about an account record back to the client.
	 * The information included in this data type is the teller name, the
	 * account number of the account, the record date, and record type
	 * @object_size 4
	 */
	ACCOUNT_RECORD,
	
	/**
	 * This sends information about a login attempt back to the client.
	 * The information included in this data type is the fail count,
	 * the card number of the account holder doing the login, and
	 * a boolean "y" or "n" whether or not the login was successful 
	 * @object_size 3
	 */
	LOGIN_ATTEMPT,
	
	/**
	 * This sends information about an account holder login result back to the client.
	 * There is no information included in this data type as this result
	 * is solely based on the value of the MessageStatus variable
	 * @object_size 0
	 */
	LOGIN_RESULT_ACCOUNTHOLDER,
	
	/**
	 * This sends information about a teller login result back to the client.
	 * There is no information included in this data type as this result
	 * is solely based on the value of the MessageStatus variable
	 * @object_size 0
	 */
	LOGIN_RESULT_TELLER,
	
	/**
	 * This sends information about a find result back to the client. The
	 * information included in this data type is the account holder's full
	 * name, email address, account holder
	 * @object_size 4
	 */
	ACCOUNT_HOLDER_FIND_RESULT,
	
	/**
	 * This sends information about a creation result back to the client. The information
	 * included in this datatype is the account holder's email address, pin number, and
	 * account number
	 * @object_size 3
	 */
	ACCOUNT_HOLDER_CREATION_RESULT,
	
	/**
	 * This sends information about an account holder deletion result back to the client.
	 * No information is included in this data type since the information is required is
	 * within the message status value.
	 * @object_size 0
	 */
	ACCOUNT_HOLDER_DELETION_RESULT,
	
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
