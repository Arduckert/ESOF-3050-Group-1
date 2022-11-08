package program.server;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

public class BankingServer extends AbstractServer
{
	public BankingServer(int port)
	{
		super(port);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client)
	{
		// TODO Auto-generated method stub
	}
}
