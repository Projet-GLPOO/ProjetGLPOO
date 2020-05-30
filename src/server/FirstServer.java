package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;



public class FirstServer {


    public static void main(String[] args){
        ServerSocket socketserver;
        Socket socketduserveur;
        final BufferedReader in;
        final PrintWriter out;
        final Scanner sc = new Scanner(System.in);

        try {
            socketserver = new ServerSocket(8082);
            socketduserveur = socketserver.accept();
            out = new PrintWriter(socketduserveur.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socketduserveur.getInputStream()));
            Thread envoyer = new Thread(new Runnable() {
                String message;
                @Override
                public void run() {
                    while(true){
                        message = sc.next();
                        out.println(message);
                        out.flush();
                    }
                }
            });
            envoyer.start();

            Thread recevoir = new Thread(new Runnable() {
                String message;
                @Override
                public void run() {
                    while(true){
                        try {
                            message = in.readLine();
                        }
                        catch (IOException e){
                            e.printStackTrace();
                        }
                        System.out.println("Client: " + message);
                    }
                }
            });
            recevoir.start();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
