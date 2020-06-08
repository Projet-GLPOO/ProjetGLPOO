package server;

import user.Message;
import user.User;

import javax.swing.*;
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
 
    public ServerThread(Socket socket) {
        this.socket = socket;
		socketList = new ArrayList<Socket>();
    }


	public void run() {
		try {
			input = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(true) {


			try {
				//create the streams that will handle the objects coming through the sockets

				//	output = new ObjectOutputStream(socket.getOutputStream());

				String text = (String) input.readObject();  //read the object received through the stream and deserialize it
				//User user = (User)input.readObject();
				System.out.println("server received a text:" + text);
				//System.out.println("server received a text:" + user.getPseudo());

				//output.writeObject(text);
				//output.writeObject(user);		//serialize and write the Student object to the stream

				//Créer une (liste de Socket) = (nbr client)
				//	if(!socket.isConnected())
				//		socketList.add(socket);


			} catch (IOException | ClassNotFoundException ex) {
				System.out.println("Server exception: " + ex.getMessage());
				ex.printStackTrace();
			} finally {
		/*	try {
	//			output.close();
				input.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}*/
			}
		}
    }
}