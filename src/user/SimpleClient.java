package user;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class SimpleClient implements Observer {
	
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private Socket socket;
	private ArrayList<Observer> observers;
	private User user;
	private String message;
	JTextArea chatArea;
	String ip;

	
	public void connect(String ip)
	{
		this.ip=ip;
       /* try  {


			//create the socket; it is defined by an remote IP address (the address of the server) and a port number


			//create the streams that will handle the objects coming and going through the sockets

			/*input = new ObjectInputStream(socket.getInputStream());

			message = "saut";//(String) input.readObject();
            //User userToSend = (User) input.readObject();
			System.out.println("text sent to the server: " + message);
			//System.out.println("\nuser sent to the server: " + userToSend.getPseudo());
					//serialize and write the String to the stream




			message = (String)input.readObject();
			//registerObserver();
			//notifyObservers();


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
		}*/

	}



	@Override
	public void update(String message, JTextArea chatArea) {
		if(socket == null){
			int port = 6666;

			try {
				socket = new Socket(ip, port);
				output = new ObjectOutputStream(socket.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.message = message;
		this.chatArea = chatArea;
		chatArea.append(message);
		try {
			output.writeObject(message);
			System.out.println(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}