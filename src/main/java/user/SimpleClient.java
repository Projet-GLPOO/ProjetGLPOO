package user;

import server.ServerThread;
import user.Message;
import user.User;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class SimpleClient implements Observer{

	private ObjectOutputStream output;
	private ObjectInputStream input;
	private Socket socket;
	private ArrayList<Observer> observers;
	private JTextArea chatArea;
	private String ip;

	/**
	 *
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
	 *
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



	Thread t = new Thread() {
		public void run() {
			try {
				while(true) {
					Message message = (Message) input.readObject();
					if(message == null)
						break;
					chatArea.append(message.getUser().getPseudo() + "#" + message.getUser().getId() + " " + message.getPostDate() + "\n" + message.getMessage() + "\n\n");
					//chatArea.append(message.getMessage() + "\n");
				}
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	};

}