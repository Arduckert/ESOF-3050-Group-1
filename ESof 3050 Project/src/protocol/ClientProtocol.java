package src.protocol;

import java.util.ArrayList;
import java.util.List;
import src.protocol.*;

public class ClientProtocol
{
	/**
	 * serverAction is an enum type that contains all the different types
	 * of actions that the server can do on behalf of the client
	 */
	private ServerAction serverAction;
	
	/**
	 * These parameters contain the data that the client sends to the server
	 * that will allow the server to perform the specific action. For instance,
	 * to deposit money, the client would need to provide an account number, a
	 * pin, and the amount to be deposited in that account.
	 */
	private List<String> parameters;
	
	/**
	 * Constructor that takes a server action and any number of parameters
	 * @param _serverAction - the action the server will do on the data
	 * @param _parameters - the data to be sent to the server
	 */
	public ClientProtocol(ServerAction _serverAction, List<String> _parameters)
	{
		serverAction = _serverAction;
		parameters = _parameters;
	}
	
	/**
	 * Constructor that takes a server action and one parameter
	 * @param _serverAction - the action the server will do on the data
	 * @param _parameters - the parameter to send to the server
	 */
	public ClientProtocol(ServerAction _serverAction, String parameter1)
	{
		serverAction = _serverAction;
		
		parameters = new ArrayList<String>();
		parameters.add(parameter1);
	}
	
	/**
	 * Constructor that takes a server action and two parameters
	 * @param _serverAction - the action the server will do on the data
	 * @param parameter1 - the first parameter
	 * @param parameter2 - the second parameter
	 */
	public ClientProtocol(ServerAction _serverAction, String parameter1, String parameter2)
	{
		serverAction = _serverAction;
		
		parameters = new ArrayList<String>();
		parameters.add(parameter1);
		parameters.add(parameter2);
	}
	
	/**
	 * Constructor that takes a server action and three parameters
	 * @param _serverAction - the action the server will do on the data
	 * @param parameter1 - the first parameter
	 * @param parameter2 - the second parameter
	 * @param parameter3 - the third parameter
	 */
	public ClientProtocol(ServerAction _serverAction, String parameter1, String parameter2, String parameter3)
	{
		serverAction = _serverAction;
		
		parameters = new ArrayList<String>();
		parameters.add(parameter1);
		parameters.add(parameter2);
		parameters.add(parameter3);
	}
	
	/**
	 * Constructor that takes a server action and four parameters
	 * @param _serverAction - the action the server wil do on the data
	 * @param parameter1 - the first parameter
	 * @param parameter2 - the second parameter
	 * @param parameter3 - the third parameter
	 * @param parameter4 - the fourth parameter
	 */
	public ClientProtocol(ServerAction _serverAction, String parameter1, String parameter2, String parameter3, String parameter4)
	{
		serverAction = _serverAction;
		
		parameters = new ArrayList<String>();
		parameters.add(parameter1);
		parameters.add(parameter2);
		parameters.add(parameter3);
		parameters.add(parameter4);
	}
	
	/**
	 * Returns the server action
	 * @return - server action
	 */
	public ServerAction GetServerAction()
	{
		return serverAction;
	}
	
	/**
	 * Returns a list of parameters 
	 * @return - a list of parameters of type string list
	 */
	public List<String> GetParameters()
	{
		return parameters;
	}
}
