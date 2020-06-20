package server;

import org.h2.tools.Server;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class FirstServer extends AbstractServer
{
	private String ip = "localhost";
	private ServerSocket ss;
	private List<ServerThread> clientConnectionList = new ArrayList<>();

	/**
	 *
	 * @param ip
	 */
	public void connect(String ip) {
		try {

				//the server socket is defined only by a port (its IP is localhost)
				ss = new ServerSocket(6666);
			while(true) {
				System.out.println("Server waiting for connection...");
				Socket socket = ss.accept();//establishes connection
				System.out.println("Connected as " + ip);
				// create a new thread to handle client socket
				ServerThread serverThread = new ServerThread(socket, clientConnectionList);
				clientConnectionList.add(serverThread);
				new Thread(serverThread).start();
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