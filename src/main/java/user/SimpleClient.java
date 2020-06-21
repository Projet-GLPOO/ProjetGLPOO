package user;

import javax.swing.*;
import java.io.*;
import java.net.*;


public class SimpleClient implements Observer{

	private ObjectOutputStream output;
	private ObjectInputStream input;
	private Socket socket;
	private JTextArea chatArea;
	private String ip;
	private int idGroup;

	/**
	 * Constructeur de SimpleClient qui permet d'initialiser d'initaliser l'input, l'output, le socket et le thread du client,
	 * @param chatArea
	 * @param ip
	 */
	public SimpleClient(JTextArea chatArea, String ip) {
		this.ip = ip;
		int port = 6666;
		try {
			socket = new Socket(ip, port);
			output = new ObjectOutputStream(socket.getOutputStream());
			input = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.chatArea = chatArea;
		t.start();
	}

	/**
	 * Envoie le message au serveur
	 * @param message
	 */
	@Override
	public void send(Message message) {
		try {
			output.writeObject(message);
			System.out.println(message.getMessage());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Permet de recevoir le bon ID de groupe
	 * @param idGroup
	 */
	@Override
	public void sendIdGroup(int idGroup) {
		this.idGroup = idGroup;
	}


	Thread t = new Thread() {
		/**
	 	* Reçoit les messages du serveur et l'affiche dans le chatArea
		 */
		public void run() {
			try {
				while(true) {
					Message message = (Message) input.readObject();
					if(message == null)
						break;
					if(idGroup == message.getGroupID()) {
						chatArea.append(message.getUser().getPseudo() + "#" + message.getUser().getId() + " " + message.getPostDate() + "\n" + message.getMessage() + "\n\n");
					}
				}
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	};

}