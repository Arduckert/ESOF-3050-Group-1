package program.server;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

public class Server extends AbstractServer{
	
	public Server(int port) {
		super(port);
	}
	
	@Override
	public abstract void serverClosed();
}
