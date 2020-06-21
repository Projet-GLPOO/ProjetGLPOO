package server;



public class ServerConnection
{
	/**
	 * Lance le serveur
	 * @param args Argument du lancement du main du serveur
	 */
	public static void main (String[] args) {
		AbstractServer as = new FirstServer();
		String ip = "localhost";
		as.connect(ip);
	}
}