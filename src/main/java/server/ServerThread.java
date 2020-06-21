package server;

import user.Message;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Ce thread est responsable du maintient de la connexion client
 */
public class ServerThread extends Thread {
    private Socket socket;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private List<Socket> socketList;
	private List<ServerThread> clientConnectionList;

	/**
	 *
	 * 	Constructeur de ServerThread
	 *
	 * @param socket le socket du serveur
	 * @param clientConnectionList La liste des clients connectés
	 */
    public ServerThread(Socket socket, List<ServerThread> clientConnectionList) {
    	this.clientConnectionList = clientConnectionList;
        this.socket = socket;
		socketList = new ArrayList<Socket>();
		try {
			input = new ObjectInputStream(socket.getInputStream());
			output = new ObjectOutputStream(socket.getOutputStream());

		} catch (IOException e) {
			e.printStackTrace();
		}
    }

	/**
	 * Permet de recevoir le message d'un client puis de l'envoyer à tout les clients
	 */
	public void run() {
		Message message;
    	while(true) {
			try {
				//créer les flux qui traiteront les objets passant par les sockets
				message = (Message) input.readObject();  //lire l'objet reçu par le flux et le désérialiser
				System.out.println("server received a text:" + message.getMessage());
				for (ServerThread serverThread : clientConnectionList) {
					serverThread.output.writeObject(message);
				}
				//Créer une (liste de Socket) = (nbr client)
			} catch (IOException | ClassNotFoundException ex) {
				System.out.println("Server exception: " + ex.getMessage());
				ex.printStackTrace();
			}
    	}
    }
}