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
 * 
 * @TYPE TEST: this is used as a way to test the client-server communication. This type
 * of data needs one parameter which can contain a message and an object size of 1.
 * @TYPE NONE: no data is passed (this is the default value of the status only constructor).
 */
public enum Datatype
{
	TEST,
	NONE,
}
