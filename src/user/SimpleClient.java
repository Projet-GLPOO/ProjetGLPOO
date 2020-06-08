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


	public SimpleClient(JTextArea chatArea) {
		this.chatArea = chatArea;
	}
	public void connect(String ip)
	{
		this.ip =ip;


	}

	@Override
	public void send(String message) {

		if(socket == null){
			try {
				int port = 6666;
				socket = new Socket(ip, port);
				output = new ObjectOutputStream(socket.getOutputStream());

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		try {

			output.writeObject(message);
			System.out.println(message);

		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			/*try {

				output.close();
				socket.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}*/
		}



	}
}