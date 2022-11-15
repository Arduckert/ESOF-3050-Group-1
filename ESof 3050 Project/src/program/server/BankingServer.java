package src.program.server;
import src.ocsf.server.AbstractServer;
import src.ocsf.server.ConnectionToClient;

public class BankingServer extends AbstractServer
{
	private BankController bankController;
	public BankingServer(int port)
	{
		super(port);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client)
	{
		
	}
	
	public static void main(String args[])
	{
		//System.out.println("testing the server...");
	}
}
