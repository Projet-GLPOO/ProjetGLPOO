package server;


import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class FirstServer extends AbstractServer
{
	private String ip = "localhost";
	private ServerSocket ss;
	private List<ServerThread> clientConnectionList = new ArrayList<>();

	/**
	 * Permet la connection d'un client au serveur
	 * @param ip l'ip du serveur
	 */
	public void connect(String ip) {
		try {
				//Le socket du serveur est seulement défini par un port (cet IP est le Localhost)
				ss = new ServerSocket(6666);
			while(true) {
				System.out.println("Server waiting for connection...");
				Socket socket = ss.accept();//establishes connection
				System.out.println("Connected as " + ip);
				//Créer un nouveau thread pour gérer le socket Client
				ServerThread serverThread = new ServerThread(socket, clientConnectionList);
				clientConnectionList.add(serverThread);
				new Thread(serverThread).start();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
			//Si IOException fermé le socket du serveur
			if (ss != null && !ss.isClosed()) {
				try {
					ss.close();
				} catch (IOException e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

}