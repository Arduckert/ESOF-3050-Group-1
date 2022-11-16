package src.program.server;
import src.ocsf.server.AbstractServer;
import src.ocsf.server.ConnectionToClient;

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
		System.out.println(msg);
	}
}
