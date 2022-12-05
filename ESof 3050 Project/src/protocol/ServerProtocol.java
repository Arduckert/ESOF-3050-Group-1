package src.protocol;
import java.io.Serializable;
import java.util.*;

/**
 * Client Protocol
 * @author Written by Connor McNally on 11/08/2022
 * 
 * @apiNote The server protocol class is the "language" that the server will use to communicate
 * with the client on what data to receive and the status of a communication. This is what the
 * server can use to send data back to the client which the client can use. Whenever you want
 * the server to send a message to the client, an instance of this class is passed with the specific
 * status, data type, object size, and data as an object to the client. The client will then cast this
 * object back to a server protocol to be read and to extract the data that exists on the server
 * protocol class.
 * 
 * @VAR status: status is a MessageStatus enum type that denotes the status of the communication,
 * whether it succeeded or failed. See the comments in MessageStatus.java for more info.
 * 
 * @VAR dataType: dataType is a Datatype enum that identifies what data the server sent to the client.
 * The client needs to know this in order to know how to process the data. See Datatype.java for more
 * info on what data to send and what object size to use for each type.
 * 
 * @VAR sizePerObject: this denotes how many parameters are required for each object in the data list.
 * 
 * @VAR data: This is a string list that contains all the data that is to be sent to the client. The
 * format of this list is a serialized list of objects placed one after another with each of their
 * parameters taking up a certain amount of elements depending on the sizePerObject. For example, to
 * send a list of objects with three parameters using this protocol, the sizePerObject variable should
 * be set to 3 and the data list should be as follows: { obj1_par1, obj1_par2, obj1_par3, obj2_par1,
 * obj2_par2, obj2_par3, obj3_par1, obj3_par2, obj3_par3, ... }
 */
public class ServerProtocol implements Serializable
{
	private static final long serialVersionUID = 6813136696707215035L;

	/**
	 * status is a MessageStatus enum type that denotes the status of the communication,
	 * whether it succeeded or failed. See the comments in MessageStatus.java for more info.
	 */
	private MessageStatus status;
	
	/**
	 * dataType is a Datatype enum that identifies what data the server sent to the client.
	 * The client needs to know this in order to know how to process the data. See Datatype.java for more
	 * info on what data to send and what object size to use for each type.
	 */
	private Datatype dataType;
	
	/**
	 * this denotes how many parameters are required for each object in the data list.
	 */
	private int sizePerObject;
	
	/**
	 * This is a string list that contains all the data that is to be sent to the client. The
	 * format of this list is a serialized list of objects placed one after another with each of their
	 * parameters taking up a certain amount of elements depending on the sizePerObject. For example, to
	 * send a list of objects with three parameters using this protocol, the sizePerObject variable should
	 * be set to 3 and the data list should be as follows: { obj1_par1, obj1_par2, obj1_par3, obj2_par1,
	 * obj2_par2, obj2_par3, obj3_par1, obj3_par2, obj3_par3, ... }
	 */
	private List<String> data;
	
	/**
	 * Constructor that just sends the message status, use this as a way to just communicate a success or failure
	 * to the client
	 */
	public ServerProtocol(MessageStatus _status)
	{
		status = _status;
		dataType = Datatype.NONE;
		sizePerObject = 0;
		data = new ArrayList<String>();
	}
	
	/**
	 * Construtor that takes a message status, and a data type
	 * @param _status the status of the message
	 * @param _dataType the data type to send
	 */
	public ServerProtocol(MessageStatus _status, Datatype _dataType)
	{
		status = _status;
		dataType = _dataType;
		sizePerObject = GetSizePerObjectForDataType(_dataType);
		data = new ArrayList<String>();
	}
	
	/**
	 * Returns the size per object for a specific data type
	 * @param dataType the data type
	 * @return an integer denoting the size per object of that data type
	 */
	private int GetSizePerObjectForDataType(Datatype dataType)
	{
		switch (dataType)
		{
		case NONE:
			return 0;
		case BASIC_MESSAGE:
			return 1;
		case BALANCE:
			return 1;
		case ACCOUNT:
			return 4;
		case BILL:
			return 3;
		case CUSTOMER_RECORD:
			return 4;
		case ACCOUNT_RECORD:
			return 3;
		case LOGIN_RESULT_ACCOUNTHOLDER:
			return 0;
		case LOGIN_RESULT_TELLER:
			return 0;
		case ACCOUNT_HOLDER_FIND_RESULT:
			return 3;
		case ACCOUNT_HOLDER_CREATION_RESULT:
			return 3;
		case ACCOUNT_HOLDER_DELETION_RESULT:
			return 0;
		default:
			return 0;
		}
	}
	
	/**
	 * Adds a new object with one parameter to the data list.
	 * @param parameter1
	 */
	public void AddData(String parameter1) throws ParameterException
	{
		if (sizePerObject == 1)
		{
			data.add(parameter1);
		}
		else
		{
			throw new ParameterException("Cannot add an object of size 1, the dataType " + dataType + "requires an object size of " + sizePerObject);
		}
	}
	
	/**
	 * Adds a new object with two parameters to the data list.
	 * @param parameter1
	 * @param parameter2
	 */
	public void AddData(String parameter1, String parameter2) throws ParameterException
	{
		if (sizePerObject == 2)
		{
			data.add(parameter1);
			data.add(parameter2);
		}
		else
		{
			throw new ParameterException("Cannot add an object of size 2, the dataType " + dataType + "requires an object size of " + sizePerObject);
		}
	}
	
	/**
	 * Adds a new object with three parameters to the data list.
	 * @param parameter1
	 * @param parameter2
	 * @param parameter3
	 */
	public void AddData(String parameter1, String parameter2, String parameter3) throws ParameterException
	{
		if (sizePerObject == 3)
		{
			data.add(parameter1);
			data.add(parameter2);
			data.add(parameter3);
		}
		else
		{
			throw new ParameterException("Cannot add an object of size 3, the dataType " + dataType + "requires an object size of " + sizePerObject);
		}
	}
	
	/**
	 * Adds a new object with four parameters to the data list.
	 * @param parameter1
	 * @param parameter2
	 * @param parameter3
	 * @param parameter4
	 */
	public void AddData(String parameter1, String parameter2, String parameter3, String parameter4) throws ParameterException
	{
		if (sizePerObject == 4)
		{
			data.add(parameter1);
			data.add(parameter2);
			data.add(parameter3);
			data.add(parameter4);
		}
		else
		{
			throw new ParameterException("Cannot add an object of size 4, the dataType " + dataType + "requires an object size of " + sizePerObject);
		}
	}
	
	/**
	 * returns the status of the message
	 * @return message status
	 */
	public MessageStatus getMessageStatus()
	{
		return status;
	}
	
	/**
	 * returns the data type
	 * @return data type
	 */
	public Datatype getDataType()
	{
		return dataType;
	}
	
	/**
	 * returns the size per object
	 * @return size per object
	 */
	public int getSizePerObject()
	{
		return sizePerObject;
	}
	
	/**
	 * returns the list of data
	 * @return a string list of data
	 */
	public List<String> GetData()
	{
		return data;
	}
}