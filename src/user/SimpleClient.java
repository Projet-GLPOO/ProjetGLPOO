package user;

import server.ServerThread;
import user.Message;
import user.User;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class SimpleClient implements Observer{

	private ObjectOutputStream output;
	private ObjectInputStream input;
	private Socket socket;
	private ArrayList<Observer> observers;
	private User user;
	private String message ="";
	JTextArea chatArea;
	String ip;


	public SimpleClient(JTextArea chatArea, String ip) {
		this.ip =ip;
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


	@Override
	public void send(String message) {
		try {
			output.writeObject(message);
			System.out.println(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	Thread t = new Thread() {
		public void run() {
			try {
				message = (String) input.readObject();

			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			chatArea.append(message);
			System.out.println("1" + message);
		}
	};

}