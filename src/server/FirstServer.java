package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;



public class FirstServer extends AbstractServer{


    private String ip = "localhost";
    private ServerSocket ss;

    public void connect(String ip) {
        try {
            //the server socket is defined only by a port (its IP is localhost)
            ss = new ServerSocket (8082);
            System.out.println("Server waiting for connection...");
            while (true) {
                Socket socket = ss.accept();//establishes connection
                System.out.println("Connected as " + ip);
                // create a new thread to handle client socket
                new ServerThread(socket).start();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
            //if IOException close the server socket
            if (ss != null && !ss.isClosed()) {
                try {
                    ss.close();
                } catch (IOException e) {
                    e.printStackTrace(System.err);
                }
            }
        }
    }
}
