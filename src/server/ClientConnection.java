package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ClientConnection {

    public ClientConnection() {
        Client c1 = new Client();
        c1.connect("localhost");
    }
}