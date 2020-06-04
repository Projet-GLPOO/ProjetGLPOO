package user;

import user.Message;
import user.User;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class SimpleClient implements Subject {
	
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

			String textToSend = "null";
			textToSend = "saut";//(String) input.readObject();
            //User userToSend = (User) input.readObject();
			System.out.println("text sent to the server: " + textToSend);
			//System.out.println("\nuser sent to the server: " + userToSend.getPseudo());
			output.writeObject(textToSend);		//serialize and write the String to the stream
			textToSend = (String)input.readObject();
 
			//User user = (User) input.readObject();	//deserialize and read the Student object from the stream
			// notifier obs
			//System.out.println("Received user id: " + user.getPseudo() + " and user name:" + user.getPseudo() + " from server");
	    } catch  (IOException|ClassNotFoundException uhe) {
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


	@Override
	public void registerObserver(Observer o) {
		observers.add(o);
	}

	@Override
	public void removeObserver(Observer o) {
		int i = observers.indexOf(o);
		if (i >= 0) observers.remove(i);
	}

	@Override
	public void notifyObservers() {
		System.out.println("notify observers ");
		for (int i = 0; i < observers.size(); i++) {
			Observer o = observers.get(i);
			o.update(message, user);
		}
	}
}