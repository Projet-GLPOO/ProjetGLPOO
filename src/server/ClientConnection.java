package server;

public class ClientConnection
{
	public ClientConnection()
	{
		SimpleClient c1 = new SimpleClient();
		c1.connect("localhost");
	}
}