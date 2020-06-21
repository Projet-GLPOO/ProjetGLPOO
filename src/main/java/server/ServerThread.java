package server;

import user.Message;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This thread is responsible to handle client connection.
 */
public class ServerThread extends Thread {
    private Socket socket;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private List<Socket> socketList;
	List<ServerThread> clientConnectionList;

	/**
	 *
	 * 	Constructeur de ServerThread
	 *
	 * @param socket le socket du serveur
	 * @param clientConnectionList La liste des clients connecter
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
				//create the streams that will handle the objects coming through the sockets
				message = (Message) input.readObject();  //read the object received through the stream and deserialize it
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