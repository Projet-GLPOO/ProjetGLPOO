package server;

import javax.swing.*;

public class ClientConnection
{
	public ClientConnection() // A supprimer
	{
		SimpleClient c1 = new SimpleClient();
		c1.connect("localhost");
	}
}