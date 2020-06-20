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

	/**
	 *
	 * @param socket
	 */
    public ServerThread(Socket socket) {
        this.socket = socket;
		socketList = new ArrayList<Socket>();
		try {
			input = new ObjectInputStream(socket.getInputStream());
			output = new ObjectOutputStream(socket.getOutputStream());

		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	public void run() {
		String text;
		try {
			//create the streams that will handle the objects coming through the sockets
			text = (String) input.readObject();  //read the object received through the stream and deserialize it
			System.out.println("server received a text:" + text);
			output.writeObject(text);
		//Créer une (liste de Socket) = (nbr client)
		} catch (IOException | ClassNotFoundException ex) {
			System.out.println("Server exception: " + ex.getMessage());
			ex.printStackTrace();
		} finally {
			try {
				output.close();
				input.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
    }
}