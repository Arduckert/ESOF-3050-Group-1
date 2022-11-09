package src.protocol;

/**
 * Server Action
 * @author Written by Connor McNally on 11/08/2022
 * 
 * @apiNote The Server Action enum contains the actions that the server can perform on
 * behalf on the client. For proper usage and what data to send, see proper usage for
 * each type below.
 * 
 * @TYPE TEST: This is used as a way to test the client-server communication. This takes one
 * parameter that can be any string message.
 */
public enum ServerAction
{
	TEST
}
