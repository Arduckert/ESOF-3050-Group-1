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
	 * No data is passed.
	 * @object_size 0
	 */
	NONE,
	
	/**
	 * This sends a string message back to the client. This is used as a way to test
	 * the client-server communication and can also be used to send a message back to
	 * the client if the message has a FAIL status.
	 * @object_size 1
	 */
	BASIC_MESSAGE,
	
	/**
	 * This sends a balance of a specific account back to the client after a transaction
	 * has been made. The information included in this data type is a balance.
	 * @object_size 1
	 */
	TRANSFER_BALANCE,
	
	/**
	 * This sends the result of the management of a bill 
	 * @object_size 0
	 */
	BILL_MANAGE_RESULT,
	
	/**
	 * This sends information about an account back to the client. The information
	 * included in this data type is the balance, the account type (chequing, savings,
	 * mortgage, or line of credit), and the account number
	 * @object_size 3
	 */
	ACCOUNT,
	
	/**
	 * This sends information about a transaction back to the client. The information
	 * included in this data type is the recipiant, date, transaction type, and amount
	 * @object_size 4
	 */
	TRANSACTION,
	
	/**
	 * This sends information about a person's address back to the client. The information
	 * included in this data type is the street number, street name, postal code, province,
	 * and country
	 * @object_size 5
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
	 * No information is included in this data type since the information required is
	 * within the message status value.
	 * @object_size 0
	 */
	ACCOUNT_HOLDER_DELETION_RESULT,
	
	/**
	 * This sends information about a person creation result back to the client.
	 * No information is included in this data type since the information required is
	 * within the message status value.
	 * @object_size 0
	 */
	PERSON_CREATION_RESULT,
	
	/**
	 * This sends information about a person deletion result back to the client.
	 * No information is included in this data type since the information required is
	 * within the message status value.
	 * @object_size 0
	 */
	PERSON_DELETION_RESULT,
	
	/**
	 * This sends information about a address addition result back to the client.
	 * No information is included in this data type since the information required is
	 * within the message status value.
	 * @object_size 0
	 */
	ADDRESS_ADDITION_RESULT,
	
	/**
	 * This sends information about an address removal result back to the client.
	 * No information is included in this data type since the information required is
	 * within the message status value.
	 * @object_size 0
	 */
	ADDRESS_REMOVAL_RESULT,
	
	/**
	 * This sends information about an account holder association with a person.
	 * No information is included in this data type since the information required is
	 * within the message status value.
	 * @object_size 0
	 */
	ACCOUNTHOLDER_ROLE_ASSOCIATION_RESULT,
	
	/**
	 * This sends information about an account creation back to the client.
	 * No information is included in this data type since the information required is
	 * within the message status value.
	 * @object_size 0
	 */
	ACCOUNT_CREATION_RESULT,
	
	/**
	 * This sends information about an account deletion back to the client.
	 * No information is included in this data type since the information required is
	 * within the message status value.
	 * @object_size 0
	 */
	ACCOUNT_DELETION_RESULT,
	
	/**
	 * This sends information about the result of the setup of a mortgage account.
	 * No information is required in this data type since the information required is
	 * within the message status value.
	 * @object_size 0
	 */
	MORTGAGE_ACCOUNT_SETUP_RESULT,
	
	/**
	 * This sends information about the result of the setup of a line of credit account.
	 * No information is required in this data type since the information required is
	 * within the message status value.
	 * @object_size 0
	 */
	LOC_ACCOUNT_SETUP_RESULT
}
