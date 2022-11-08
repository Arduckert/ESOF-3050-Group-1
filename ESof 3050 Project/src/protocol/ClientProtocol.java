package protocol;

import java.util.ArrayList;
import java.util.List;
import protocol.*;

public class ClientProtocol
{
	private ServerAction serverAction;
	private List<String> parameters;
	
	public ClientProtocol(ServerAction _serverAction, List<String> _parameters)
	{
		serverAction = _serverAction;
		parameters = _parameters;
	}
	
	public ClientProtocol(ServerAction _serverAction, String parameter1)
	{
		serverAction = _serverAction;
		
		parameters = new ArrayList<String>();
		parameters.add(parameter1);
	}
}
