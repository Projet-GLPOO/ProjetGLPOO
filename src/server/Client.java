package server;

import user.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private Socket socket;

    public void connect(String ip)
    {
        int port = 8082;
        try  {
            //create the socket; it is defined by an remote IP address (the address of the server) and a port number
            socket = new Socket(ip, port);

            //create the streams that will handle the objects coming and going through the sockets
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());

           /* String textToSend = new String("send me the student info!");
            System.out.println("text sent to the server: " + textToSend);
            output.writeObject(textToSend);		//serialize and write the String to the stream*/

            User user = (User) input.readObject();	//deserialize and read the Student object from the stream
            System.out.println("Received student id: " + user.getId() + " and student name:" + user.getPseudo() + " from server");
        } catch  (UnknownHostException uhe) {
            uhe.printStackTrace();
        }
        catch  (IOException ioe) {
            ioe.printStackTrace();
        }
        catch  (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
        finally {
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