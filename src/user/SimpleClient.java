package user;

import user.Message;
import user.User;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class SimpleClient {

	private ObjectOutputStream output;
	private ObjectInputStream input;
	private Socket socket;
	private ArrayList<Observer> observers;
	private User user;
	private String message;


	public void connect(String ip)
	{
		int port = 6666;
		try  {


			//create the socket; it is defined by an remote IP address (the address of the server) and a port number
			socket = new Socket(ip, port);

			//create the streams that will handle the objects coming and going through the sockets
			output = new ObjectOutputStream(socket.getOutputStream());
			input = new ObjectInputStream(socket.getInputStream());

			message = "saut";//(String) input.readObject();
			//User userToSend = (User) input.readObject();
			System.out.println("text sent to the server: " + message);
			//System.out.println("\nuser sent to the server: " + userToSend.getPseudo());
			output.writeObject(message);		//serialize and write the String to the stream




			message = (String)input.readObject();
			//registerObserver();
			//notifyObservers();
			System.out.println(message);

			//String text = (String) input.readObject();	//deserialize and read the Student object from the stream
			// notifier obs

			//System.out.println("Received user id: " + user.getPseudo() + " and user name:" + user.getPseudo() + " from server");
		} catch  (IOException | ClassNotFoundException uhe) {
			uhe.printStackTrace();
		} finally {
			try {
				input.close();
				output.close();
				socket.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}

	}
}