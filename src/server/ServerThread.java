package server;

import user.Message;
import user.User;

import javax.swing.*;
import java.io.*;
import java.net.*;

/**
 * This thread is responsible to handle client connection.
 */
public class ServerThread extends Thread {
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;


    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    public void run(Message message, User user, JTextArea chatArea) {
        try {

            // Faut récupérer le User et le message



            //create the streams that will handle the objects coming through the sockets
            input = new ObjectInputStream(socket.getInputStream());
            output = new ObjectOutputStream(socket.getOutputStream());

           /*  String text = (String)input.readObject();  //read the object received through the stream and deserialize it
            System.out.println("server received a text:" + text);*/

            output.writeObject(message);
            output.writeObject(user);		//serialize and write the Student object to the stream

            chatArea.append(user.getPseudo()+"#"+user.getId() + message.getPostDate() +"\n" + message.getMessage() + "\n\n");

        } catch (IOException ex) {
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