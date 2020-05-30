package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ClientConnection {

    public static void main(String[] args){

        Socket socket;
        final BufferedReader in;
        final PrintWriter out;
        final Scanner sc = new Scanner(System.in);

        try {
            socket = new Socket("192.168.1.53", 8082);
            out = new PrintWriter(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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
                        System.out.println("Serveur : " + message);
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