package src.protocol;
import java.util.*;


public class ServerProtocol
{
	private MessageStatus status;
	private Datatype dataType;
	
	private int sizePerObject;
	private List<String> data;
	
	public ServerProtocol(MessageStatus _status)
	{
		status = _status;
		dataType = Datatype.NONE;
		sizePerObject = 0;
		data = new ArrayList<String>();
	}
	
	public ServerProtocol(MessageStatus _status, Datatype _dataType, int _sizePerObject)
	{
		status = _status;
		dataType = _dataType;
		sizePerObject = _sizePerObject;
		data = new ArrayList<String>();
	}
	
	public void AddData(String parameter1) 
	{
		data.add(parameter1);
	}
}